/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.contracts;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zimma
 */
@XmlRootElement(name = "findResponse")
public class FindStudentResponse implements Serializable {
    private List<Student> students;

    public FindStudentResponse() {
    }
    
    public FindStudentResponse(List<Student> students) {
        this.students = students;
    }

   
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    
}
