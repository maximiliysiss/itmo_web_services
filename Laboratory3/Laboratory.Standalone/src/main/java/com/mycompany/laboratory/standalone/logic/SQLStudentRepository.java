/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone.logic;

import com.mycompany.laboratory.standalone.entity.Student;
import com.mycompany.laboratory.standalone.exceptions.NotFoundException;
import com.mycompany.laboratory.standalone.exceptions.PersonServiceFault;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zimma
 */
public class SQLStudentRepository implements StudentRepository {

    private Student convertToStudent(ResultSet resultSet) throws SQLException {
        return new Student(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("surname"), resultSet.getString("thirdname"),
                resultSet.getString("birthday"), resultSet.getString("birthplace"));
    }

    public static class SQLQueries {

        public static final String SELECT_BY_FINDER = "SELECT * FROM dbo.Student s WHERE (? is null OR s.id = ?) and (? is null OR s.name = ?) "
                + "and (? is null OR s.surname = ?) and (? is null OR s.thirdname = ?) "
                + "and (? is null OR s.birthday = ?) and (? is null OR s.birthplace = ?)";
        public static final String DELETE_STUDENT = "delete from dbo.Student where id = ?";
        public static final String SELECT_BY_ID = "select * from dbo.Student where id = ?";
        public static final String CREATE_STUDENT = "insert into dbo.Student(name, surname, thirdname, birthday, birthplace) values (?,?,?,?,?)";
        public static final String UPDATE_STUDENT = "update dbo.Student set name = ?, surname = ?, thirdname = ?, birthday = ?, birthplace = ? where id = ?";
    }

    public static StudentRepository create(String url) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();
            return new SQLStudentRepository(url);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private final ArrayList<String> fields = new ArrayList<>(Arrays.asList("id", "name", "surname", "thirdname", "birthday", "birthplace"));

    private final String connectionString;

    public SQLStudentRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public List<Student> findStudents(List<FieldFinder> fieldFinders) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.SELECT_BY_FINDER);

            Map<String, Object> args = fields.stream().collect(HashMap::new, (m, v) -> m.put(v, null), HashMap::putAll);

            fieldFinders.forEach((ff) -> {
                args.put(ff.getField(), ff.getValue());
            });

            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setObject(1 + i * 2, args.get(fields.get(i)));
                preparedStatement.setObject(1 + i * 2 + 1, args.get(fields.get(i)));
            }

            ArrayList<Student> students = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(convertToStudent(resultSet));
                }
            }

            return students;

        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.DELETE_STUDENT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (!resultSet.next()) {
                    throw new NotFoundException("Not found student", PersonServiceFault.defaultInstance());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student getById(int id) throws NotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.SELECT_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return convertToStudent(resultSet);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new NotFoundException("Not found student", PersonServiceFault.defaultInstance());
    }

    @Override
    public Student createStudent(Student student) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getThirdname());
            preparedStatement.setString(4, student.getBirthday());
            preparedStatement.setString(5, student.getBirthplace());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return getById(resultSet.getInt(1));
                }
            } catch (NotFoundException ex) {
                Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Student updateStudent(Student student) throws NotFoundException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.UPDATE_STUDENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getThirdname());
            preparedStatement.setString(4, student.getBirthday());
            preparedStatement.setString(5, student.getBirthplace());
            preparedStatement.setInt(6, student.getId());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (!resultSet.next()) {
                    throw new NotFoundException("Not found student", PersonServiceFault.defaultInstance());
                }
            }

            return getById(student.getId());

        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
