/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console;

import com.mycompany.laboratory1.console.client.standalone.NotFoundException;
import com.mycompany.laboratory1.console.client.standalone.Student;
import com.mycompany.laboratory1.console.client.standalone.StudentWebService;
import com.mycompany.laboratory1.console.factory.standalone.ServiceEntityFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zimma
 */
public class Program {

    public static void main(String[] args) {
        try {
            StandaloneService();
            System.out.println("com.mycompany.laboratory1.console.Program.main()");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void StandaloneService() throws MalformedURLException, NotFoundException {

        URL wsdlLocation = new URL("http://localhost:8080/Laboratory.Standalone/StudentWebService?wsdl");
        com.mycompany.laboratory1.console.client.standalone.StudentWebService_Service studentWebService_Service = new com.mycompany.laboratory1.console.client.standalone.StudentWebService_Service(wsdlLocation);

        com.mycompany.laboratory1.console.client.standalone.FindStudentsRequest findStudentsRequest = new com.mycompany.laboratory1.console.client.standalone.FindStudentsRequest();
        List<com.mycompany.laboratory1.console.client.standalone.FieldFind> fieldFinds = findStudentsRequest.getFieldFinds();
        fieldFinds.add(com.mycompany.laboratory1.console.factory.standalone.ServiceEntityFactory.createFieldFind("id", 1));

        StudentWebService studentWebServicePort = studentWebService_Service.getStudentWebServicePort();

        com.mycompany.laboratory1.console.client.standalone.FindStudentResponse findStudents = studentWebServicePort.findStudents(findStudentsRequest);

        Student createdStudent = studentWebServicePort.create(ServiceEntityFactory.createStudentRequest("Maxim", "Zimin", "03-02-1998", "Arch", "Andreevich"));
        assert createdStudent != null;

        createdStudent = studentWebServicePort.update(ServiceEntityFactory.editStudentRequest(createdStudent.getId(), "42",
                createdStudent.getSurname(), createdStudent.getBirthday(), createdStudent.getBirthplace(), createdStudent.getThirdname()));
        assert createdStudent != null && "42".equals(createdStudent.getName());

        Student byId = studentWebServicePort.getById(createdStudent.getId());
        assert Objects.equals(byId.getId(), createdStudent.getId()) && !byId.getName().equals(createdStudent.getName());

        studentWebServicePort.delete(byId.getId());

        try {
            byId = studentWebServicePort.getById(createdStudent.getId());
            assert false;
        } catch (NotFoundException e) {
            System.out.println(e.getClass().getName() + " " + e.getMessage());
        }
    }
}
