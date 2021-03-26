/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone.logic;

import com.mycompany.laboratory.standalone.entity.Student;
import java.util.List;

/**
 *
 * @author zimma
 */
public interface StudentRepository {

    List<Student> findStudents(List<FieldFinder> fieldFinders);

    public void delete(int id);

    public Student getById(int id);

    public Student createStudent(Student student);

    public Student updateStudent(Student student);
}
