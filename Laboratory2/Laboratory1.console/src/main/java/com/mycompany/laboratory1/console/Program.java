/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console;

import com.mycompany.laboratory.standalone.FieldFind;
import com.mycompany.laboratory.standalone.FindStudentResponse;
import com.mycompany.laboratory.standalone.FindStudentsRequest;
import com.mycompany.laboratory.standalone.Student;
import com.mycompany.laboratory.standalone.StudentWebService;
import com.mycompany.laboratory.standalone.StudentWebService_Service;
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
        }
    }

    private static void StandaloneService() throws MalformedURLException {

        URL wsdlLocation = new URL("http://localhost:10010/Laboratory.Standalone/StudentWebService?wsdl");
        StudentWebService_Service studentWebService_Service = new StudentWebService_Service(wsdlLocation);

        FindStudentsRequest findStudentsRequest = new FindStudentsRequest();
        List<FieldFind> fieldFinds = findStudentsRequest.getFieldFinds();
        fieldFinds.add(com.mycompany.laboratory1.console.factory.standalone.ServiceEntityFactory.createFieldFind("id", 1));

        StudentWebService studentWebServicePort = studentWebService_Service.getStudentWebServicePort();

        try {

            Student createdStudent = studentWebServicePort.create(ServiceEntityFactory.createStudentRequest("Maxim", "Zimin", "03-02-1998", "Arch", "Andreevich"));
            assert createdStudent != null;

            FindStudentResponse findStudents = studentWebServicePort.findStudents(findStudentsRequest);
            
            createdStudent = studentWebServicePort.update(ServiceEntityFactory.editStudentRequest(createdStudent.getId(), "42",
                    createdStudent.getSurname(), createdStudent.getBirthday(), createdStudent.getBirthplace(), createdStudent.getThirdname()));
            assert createdStudent != null && "42".equals(createdStudent.getName());

            Student byId = studentWebServicePort.getById(createdStudent.getId());
            assert Objects.equals(byId.getId(), createdStudent.getId()) && !byId.getName().equals(createdStudent.getName());

            studentWebServicePort.delete(byId.getId());

            byId = studentWebServicePort.getById(createdStudent.getId());
            assert false;
        } catch (Exception e) {

        }

        studentWebServicePort.upload(new byte[10]);

    }
}
