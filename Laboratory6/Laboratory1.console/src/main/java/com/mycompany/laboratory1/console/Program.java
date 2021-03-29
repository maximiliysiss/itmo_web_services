/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console;

import com.mycompany.laboratory1.console.rest.StudentAPI;
import com.mycompany.laboratory1.console.rest.contract.CreateStudentRequest;
import com.mycompany.laboratory1.console.rest.contract.EditStudentRequest;
import com.mycompany.laboratory1.console.rest.contract.FindStudentResponse;
import com.mycompany.laboratory1.console.rest.contract.FindStudentsRequest;
import com.mycompany.laboratory1.console.rest.contract.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.misc.IOUtils;

/**
 *
 * @author zimma
 */
public class Program {

    public static void main(String[] args) {
        try {
            StandaloneService();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getString(InputStream inputStream) {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textBuilder.toString();
    }

    private static void StandaloneService() throws MalformedURLException, IOException {
        Retrofit build = new retrofit2.Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/Laboratory1.Standalone/").build();
        StudentAPI client = build.create(StudentAPI.class);

        Call<FindStudentResponse> findStudents = client.findStudents(new FindStudentsRequest(1, null, null, null, null, null));
        FindStudentResponse execute = findStudents.execute().body();
        assert execute.getStudents().size() == 1;

        Student created = client.create(new CreateStudentRequest("Test", "Test", "Test", "03-02-1998", "Arch")).execute().body();

        assert created != null;
        assert "Test".equals(created.getName());

        created = client.edit(new EditStudentRequest(created.getId(), created.getName(), "42", created.getThirdname(),
                created.getBirthday(), created.getBirthplace())).execute().body();

        assert "42".equals(created.getSurname());

        Student finded = client.getById(created.getId()).execute().body();
        assert "42".equals(finded.getSurname());

        client.delete(finded.getId()).execute().body();

        finded = client.getById(created.getId()).execute().body();
        assert finded == null;

        try {
            Response<Student> errorResult = client.getById(1500).execute();
            if (!errorResult.isSuccessful()) {
                String string = getString(errorResult.errorBody().byteStream());
                System.out.println(string);
            }
        } catch (IOException ex) {
            System.out.println("com.mycompany.laboratory1.console.Program.StandaloneService() " + ex.getMessage());
        }
    }
}
