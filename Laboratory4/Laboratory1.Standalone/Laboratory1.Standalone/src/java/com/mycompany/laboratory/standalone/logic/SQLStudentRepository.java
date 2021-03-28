/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone.logic;

import com.mycompany.laboratory.standalone.entity.Student;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zimma
 */
public class SQLStudentRepository implements StudentRepository {

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
    private final String SELECT_BY_FINDER = "SELECT * FROM Student s WHERE (? is null OR s.id = ?) and (? is null OR s.name = ?) "
            + "and (? is null OR s.surname = ?) and (? is null OR s.thirdname = ?) "
            + "and (? is null OR s.birthday = ?) and (? is null OR s.birthplace = ?)";
    private final String connectionString;

    public SQLStudentRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public List<Student> findStudents(List<FieldFinder> fieldFinders) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {

            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_BY_FINDER);

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
                    students.add(new Student(resultSet.getInt("id"), resultSet.getString("name"),
                            resultSet.getString("surname"), resultSet.getString("thirdname"),
                            resultSet.getString("birthday"), resultSet.getString("birthplace")));
                }
            }

            return students;

        } catch (SQLException ex) {
            Logger.getLogger(SQLStudentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

}
