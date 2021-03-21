/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.models.FieldFind;
import entities.Student;
import java.util.List;
import javax.ejb.Stateful;

/**
 *
 * @author zimma
 */
@Stateful
public class StudentSessionBean implements StudentSessionBeanLocal {

    @Override
    public List<Student> getStudents(List<FieldFind> fieldFinds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
