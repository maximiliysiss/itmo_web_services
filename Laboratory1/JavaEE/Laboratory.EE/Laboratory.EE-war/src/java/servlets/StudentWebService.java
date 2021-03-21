/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.StudentSessionBean;
import entities.Student;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import servlets.contracts.FieldFind;
import servlets.contracts.FindStudentResponse;
import servlets.contracts.FindStudentsRequest;

/**
 *
 * @author zimma
 */
@WebService(serviceName = "StudentWebService")
public class StudentWebService {

    @EJB
    StudentSessionBean sessionBean;
    
    /**
     * Web service findStudents
     */
    @WebMethod(operationName = "findStudents")
    public FindStudentResponse findStudents(@WebParam(name = "fields") FindStudentsRequest parameter) {
        //TODO write your implementation code here:
        return null;
    }

}
