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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import servlets.contracts.FindStudentResponse;
import servlets.contracts.FindStudentsRequest;

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
}
