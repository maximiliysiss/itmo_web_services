/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone.logic;

import com.mycompany.laboratory.standalone.entity.Student;
import java.util.List;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author zimma
 */
public interface StudentRepository {

    List<Student> findStudents(List<FieldFinder> fieldFinders);

    public void delete(int id) throws NotFoundException;

    public Student getById(int id) throws NotFoundException;

    public Student createStudent(Student student);

    public Student updateStudent(Student student) throws NotFoundException;
}
