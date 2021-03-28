/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.rest;

import com.mycompany.laboratory1.console.rest.contract.FindStudentResponse;
import com.mycompany.laboratory1.console.rest.contract.FindStudentsRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 * @author zimma
 */
public interface StudentAPI {

    @POST("students/find")
    Call<FindStudentResponse> findStudents(@Body FindStudentsRequest findStudentsRequest);
}
