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
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.NotFoundException;
import javax.xml.ws.WebServiceContext;
import servlets.contracts.CreateStudentRequest;
import servlets.contracts.EditStudentRequest;
import servlets.contracts.FieldFind;
import servlets.contracts.FindStudentResponse;
import servlets.contracts.FindStudentsRequest;

/**
 *
 * @author zimma
 */
@WebService(serviceName = "StudentWebService")
public class StudentWebService {

    @Resource
    WebServiceContext context;
    
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
    
    @WebMethod(operationName = "upload")
    public void uploadFile(byte[] data){
        System.out.println("Get data "+ data.length + " bytes");
    }

    @WebMethod(operationName = "create")
    public servlets.contracts.Student createStudent(@WebParam(name = "request") CreateStudentRequest createStudentReqeust) {
        hardCodeAuth();
        com.mycompany.laboratory.standalone.entity.Student createdStudent = repo.createStudent(new com.mycompany.laboratory.standalone.entity.Student(0, createStudentReqeust.getName(), createStudentReqeust.getSurname(), createStudentReqeust.getThirdname(),
                createStudentReqeust.getBirthday(), createStudentReqeust.getBirthplace()));
        return new servlets.contracts.Student(createdStudent.getId(), createdStudent.getName(), createdStudent.getSurname(),
                createdStudent.getThirdname(), createdStudent.getBirthday(), createdStudent.getBirthplace());
    }

    @WebMethod(operationName = "update")
    public servlets.contracts.Student updateStudent(@WebParam(name = "request") EditStudentRequest editStudent) {
        hardCodeAuth();
        com.mycompany.laboratory.standalone.entity.Student editedStudent = repo.updateStudent(new com.mycompany.laboratory.standalone.entity.Student(editStudent.getId(), editStudent.getName(), editStudent.getSurname(), editStudent.getThirdname(),
                editStudent.getBirthday(), editStudent.getBirthplace()));
        return new servlets.contracts.Student(editedStudent.getId(), editedStudent.getName(), editedStudent.getSurname(),
                editedStudent.getThirdname(), editedStudent.getBirthday(), editedStudent.getBirthplace());
    }

    @WebMethod(operationName = "getById")
    public servlets.contracts.Student getById(@WebParam(name = "request") int id)  {
        com.mycompany.laboratory.standalone.entity.Student student = repo.getById(id);
        return new servlets.contracts.Student(student.getId(), student.getName(), student.getSurname(),
                student.getThirdname(), student.getBirthday(), student.getBirthplace());
    }

    @WebMethod(operationName = "delete")
    public void delete(@WebParam(name = "request") int id) {
        hardCodeAuth();
        repo.delete(id);
    }
    
    private void hardCodeAuth(){
        if(!"root".equals(context.getMessageContext().get("AUTHORIZE"))){
            System.out.println("auth error");
            throw new NotFoundException("Auth error");
        }
    }
}
