/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone;

import com.mycompany.laboratory.standalone.logic.FieldFinder;
import com.mycompany.laboratory.standalone.logic.SQLStudentRepository;
import com.mycompany.laboratory.standalone.logic.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    
    private StudentRepository repo = SQLStudentRepository.create("jdbc:sqlserver://localhost:54813;databaseName=itmo_webserver;user=root;password=root;");

    /**
     * This is a sample web service operation
     *
     * @param findStudentsRequest
     * @return
     */
    @WebMethod(operationName = "findStudents")
    public FindStudentResponse findStudents(@WebParam(name = "finders") FindStudentsRequest findStudentsRequest) {
        List<FieldFinder> fieldFinds = new ArrayList<>();
        if (findStudentsRequest.getFieldFinds() != null) {
            for (FieldFind ff : findStudentsRequest.getFieldFinds()) {
                fieldFinds.add(new FieldFinder(ff.getField(), ff.getValue()));
            }
        }
        
        List<com.mycompany.laboratory.standalone.entity.Student> findStudents = repo.findStudents(fieldFinds);
        
        return new FindStudentResponse(findStudents.stream().map(x -> {
            return new servlets.contracts.Student(x.getId(), x.getName(), x.getSurname(), x.getThirdname(), x.getBirthday(), x.getBirthplace());
        }).collect(Collectors.toList()));
    }
}
