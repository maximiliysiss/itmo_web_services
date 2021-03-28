/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.StudentBeanLocal;
import beans.models.FieldFind;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
@RequestScoped
public class RestService {

    @Inject
    private StudentBeanLocal beanLocal;

    @POST
    @Path("find")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FindStudentResponse findStudents(FindStudentsRequest findStudentsRequest) {
        List<FieldFind> fieldFinds = new ArrayList<>();
        if (findStudentsRequest != null) {
            for (Map.Entry<String, Object> ff : findStudentsRequest.toMap().entrySet()) {
                fieldFinds.add(new FieldFind(ff.getKey(), ff.getValue()));
            }
        }

        List<Student> findStudents = beanLocal.getStudents(fieldFinds);

        FindStudentResponse findStudentResponse = new FindStudentResponse(findStudents.stream().map(x -> {
            return new servlets.contracts.Student(x.getId(), x.getName(), x.getSurname(), x.getThirdname(), x.getBirthday(), x.getBirthplace());
        }).collect(Collectors.toList()));

        return findStudentResponse;
    }
}
