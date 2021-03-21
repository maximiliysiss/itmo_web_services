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
public interface StudentSessionBeanLocal {

    List<Student> getStudents(List<FieldFind> fieldFinds);
}
