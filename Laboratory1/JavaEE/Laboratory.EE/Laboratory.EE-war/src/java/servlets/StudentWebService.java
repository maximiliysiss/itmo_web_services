/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import servlets.contracts.FindStudentResponse;
import servlets.contracts.FindStudentsRequest;
import beans.StudentBeanLocal;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zimma
 */
@WebService(serviceName = "StudentWebService")
public class StudentWebService {

    @EJB
    private StudentBeanLocal beanLocal;

    /**
     * Web service findStudents
     *
     * @param findStudentsRequest
     * @return
     */
    @WebMethod(operationName = "findStudents")
    public FindStudentResponse findStudents(@WebParam(name = "finds") FindStudentsRequest findStudentsRequest) {

        List<beans.models.FieldFind> fieldFinds = new ArrayList<>();
        if (findStudentsRequest.getFieldFinds() != null) {
            fieldFinds = findStudentsRequest.getFieldFinds().stream().map(x -> {
                return new beans.models.FieldFind(x.getField(), x.getValue());
            }).collect(Collectors.toList());
        }

        List<Student> students = beanLocal.getStudents(fieldFinds);

        return new FindStudentResponse(students.stream().map(x -> {
            return new servlets.contracts.Student(x.getId(), x.getName(), x.getSurname(), x.getThirdname(), x.getBirthday(), x.getBirthplace());
        }).collect(Collectors.toList()));
    }

}
