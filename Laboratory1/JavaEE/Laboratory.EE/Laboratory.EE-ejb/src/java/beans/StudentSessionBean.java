/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.models.FieldFind;
import data.AbstractFacade;
import entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zimma
 */
@Stateless
public class StudentSessionBean extends AbstractFacade<Student> implements StudentBeanLocal {

    @PersistenceContext(unitName = "Laboratory.EE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentSessionBean() {
        super(Student.class);
    }

    public List<Student> getStudents(List<FieldFind> fieldFinds) {
        return findByFieldsAndValue("Student.findByFields", fieldFinds);
    }
}
