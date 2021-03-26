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
import servlets.contracts.CreateStudentReqeust;
import servlets.contracts.EditStudentRequest;

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

    @WebMethod(operationName = "create")
    public servlets.contracts.Student createStudent(@WebParam(name = "request") CreateStudentReqeust createStudentReqeust) {
        Student createdStudent = beanLocal.createStudent(new entities.Student(0, createStudentReqeust.getName(), createStudentReqeust.getSurname(), createStudentReqeust.getThirdname(),
                createStudentReqeust.getBirthday(), createStudentReqeust.getBirthplace()));
        return new servlets.contracts.Student(createdStudent.getId(), createdStudent.getName(), createdStudent.getSurname(),
                createdStudent.getThirdname(), createdStudent.getBirthday(), createdStudent.getBirthplace());
    }

    @WebMethod(operationName = "update")
    public servlets.contracts.Student updateStudent(@WebParam(name = "request") EditStudentRequest editStudent) {
        Student editedStudent = beanLocal.createStudent(new entities.Student(editStudent.getId(), editStudent.getName(), editStudent.getSurname(), editStudent.getThirdname(),
                editStudent.getBirthday(), editStudent.getBirthplace()));
        return new servlets.contracts.Student(editedStudent.getId(), editedStudent.getName(), editedStudent.getSurname(),
                editedStudent.getThirdname(), editedStudent.getBirthday(), editedStudent.getBirthplace());
    }

    @WebMethod(operationName = "getById")
    public servlets.contracts.Student getById(@WebParam(name = "request") int id) {
        Student student = beanLocal.getById(id);
        return new servlets.contracts.Student(student.getId(), student.getName(), student.getSurname(),
                student.getThirdname(), student.getBirthday(), student.getBirthplace());
    }

    @WebMethod(operationName = "delete")
    public void delete(@WebParam(name = "request") int id) {
        beanLocal.delete(id);
    }

}
