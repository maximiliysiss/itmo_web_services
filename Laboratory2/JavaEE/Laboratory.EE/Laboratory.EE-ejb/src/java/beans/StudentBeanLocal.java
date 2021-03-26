/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.models.FieldFind;
import entities.Student;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author zimma
 */
@Local
public interface StudentBeanLocal {

    public List<Student> getStudents(List<FieldFind> fieldFinds);

    public Student createStudent(Student student);
    
    public Student updateStudent(Student student);
    
    public Student getById(int id);
    
    public void delete(int id);
    
}
