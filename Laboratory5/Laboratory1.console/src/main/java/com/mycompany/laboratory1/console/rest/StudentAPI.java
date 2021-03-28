/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.rest;

import com.mycompany.laboratory1.console.rest.contract.CreateStudentRequest;
import com.mycompany.laboratory1.console.rest.contract.EditStudentRequest;
import com.mycompany.laboratory1.console.rest.contract.FindStudentResponse;
import com.mycompany.laboratory1.console.rest.contract.FindStudentsRequest;
import com.mycompany.laboratory1.console.rest.contract.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 *
 * @author zimma
 */
public interface StudentAPI {

    @POST("students/find")
    Call<FindStudentResponse> findStudents(@Body FindStudentsRequest findStudentsRequest);

    @GET("students")
    Call<Student> getById(@Query("id") int id);

    @DELETE("students")
    Call<ResponseBody> delete(@Query("id") int id);

    @POST("students")
    Call<Student> create(@Body CreateStudentRequest createStudentRequest);

    @PUT("students")
    Call<Student> edit(@Body EditStudentRequest editStudentRequest);
}
