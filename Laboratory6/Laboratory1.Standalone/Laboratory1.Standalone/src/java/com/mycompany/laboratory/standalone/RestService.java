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
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import servlets.contracts.CreateStudentRequest;
import servlets.contracts.EditStudentRequest;
import servlets.contracts.FindStudentResponse;
import servlets.contracts.FindStudentsRequest;
import servlets.contracts.Student;

/**
 * REST Web Service
 *
 * @author zimma
 */
@Path("students")
public class RestService {

    private StudentRepository repo = SQLStudentRepository.create("jdbc:sqlserver://localhost:54813;databaseName=itmo_webserver;user=root;password=root;");

    /**
     * Creates a new instance of GenericResource
     */
    public RestService() {
    }

    @POST
    @Path("find")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FindStudentResponse findStudents(FindStudentsRequest findStudentsRequest) {
        List<FieldFinder> fieldFinds = new ArrayList<>();
        if (findStudentsRequest != null) {
            for (Map.Entry<String, Object> ff : findStudentsRequest.toMap().entrySet()) {
                fieldFinds.add(new FieldFinder(ff.getKey(), ff.getValue()));
            }
        }

        List<com.mycompany.laboratory.standalone.entity.Student> findStudents = repo.findStudents(fieldFinds);

        FindStudentResponse findStudentResponse = new FindStudentResponse(findStudents.stream().map(x -> {
            return new servlets.contracts.Student(x.getId(), x.getName(), x.getSurname(), x.getThirdname(), x.getBirthday(), x.getBirthplace());
        }).collect(Collectors.toList()));

        return findStudentResponse;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Student findById(@QueryParam("id") int id) throws NotFoundException {
        com.mycompany.laboratory.standalone.entity.Student student = repo.getById(id);
        return new servlets.contracts.Student(student.getId(), student.getName(), student.getSurname(), student.getThirdname(), student.getBirthday(), student.getBirthplace());
    }

    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student editStudent(EditStudentRequest editStudent) throws NotFoundException {
        com.mycompany.laboratory.standalone.entity.Student editedStudent = repo.updateStudent(new com.mycompany.laboratory.standalone.entity.Student(editStudent.getId(), editStudent.getName(), editStudent.getSurname(), editStudent.getThirdname(),
                editStudent.getBirthday(), editStudent.getBirthplace()));
        return new servlets.contracts.Student(editedStudent.getId(), editedStudent.getName(), editedStudent.getSurname(),
                editedStudent.getThirdname(), editedStudent.getBirthday(), editedStudent.getBirthplace());
    }

    @DELETE
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@QueryParam("id") int id) throws NotFoundException{
        repo.delete(id);
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student createStudent(CreateStudentRequest createStudentReqeust) {
        com.mycompany.laboratory.standalone.entity.Student createdStudent = repo.createStudent(new com.mycompany.laboratory.standalone.entity.Student(0, createStudentReqeust.getName(), createStudentReqeust.getSurname(), createStudentReqeust.getThirdname(),
                createStudentReqeust.getBirthday(), createStudentReqeust.getBirthplace()));
        return new servlets.contracts.Student(createdStudent.getId(), createdStudent.getName(), createdStudent.getSurname(),
                createdStudent.getThirdname(), createdStudent.getBirthday(), createdStudent.getBirthplace());
    }
}
