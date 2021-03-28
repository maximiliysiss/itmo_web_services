/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console;

import com.mycompany.laboratory1.console.rest.StudentAPI;
import com.mycompany.laboratory1.console.rest.contract.FindStudentResponse;
import com.mycompany.laboratory1.console.rest.contract.FindStudentsRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author zimma
 */
public class Program {

    public static void main(String[] args) {
        try {
            JavaEEService();
            StandaloneService();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void JavaEEService() throws MalformedURLException, IOException {
        Retrofit build = new retrofit2.Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/Laboratory.EE-war/").build();
        StudentAPI client = build.create(StudentAPI.class);

        Call<FindStudentResponse> findStudents = client.findStudents(new FindStudentsRequest(1, null, null, null, null, null));
        Response<FindStudentResponse> execute = findStudents.execute();
    }

    private static void StandaloneService() throws MalformedURLException, IOException {
        Retrofit build = new retrofit2.Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/Laboratory1.Standalone/").build();
        StudentAPI client = build.create(StudentAPI.class);

        Call<FindStudentResponse> findStudents = client.findStudents(new FindStudentsRequest(1, null, null, null, null, null));
        Response<FindStudentResponse> execute = findStudents.execute();
    }
}
