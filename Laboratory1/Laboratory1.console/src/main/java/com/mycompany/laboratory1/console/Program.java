/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console;

import com.mycompany.laboratory1.client.FieldFind;
import com.mycompany.laboratory1.client.FindStudentResponse;
import com.mycompany.laboratory1.client.FindStudentsRequest;
import com.mycompany.laboratory1.client.StudentWebService_Service;
import java.util.List;

/**
 *
 * @author zimma
 */
public class Program {
    public static void main(String[] args){
        
        StudentWebService_Service studentWebService_Service = new StudentWebService_Service( );
        
        FindStudentsRequest findStudentsRequest = new FindStudentsRequest();
        List<FieldFind> fieldFinds = findStudentsRequest.getFieldFinds();
        
        FindStudentResponse findStudents = studentWebService_Service.getStudentWebServicePort().findStudents(findStudentsRequest);
    }
}
