/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.factory.standalone;

import com.mycompany.laboratory.standalone.CreateStudentRequest;
import com.mycompany.laboratory.standalone.EditStudentRequest;
import com.mycompany.laboratory.standalone.FieldFind;

/**
 *
 * @author zimma
 */
public class ServiceEntityFactory {

    public static FieldFind createFieldFind(String field, Object value) {
        FieldFind ff = new FieldFind();
        ff.setField(field);
        ff.setValue(value);
        return ff;
    }

    public static CreateStudentRequest createStudentRequest(String name, String surname, String birthday, String birthplace, String thirdName) {
        CreateStudentRequest createStudentRequest = new CreateStudentRequest();

        createStudentRequest.setName(name);
        createStudentRequest.setBirthday(birthday);
        createStudentRequest.setBirthplace(birthplace);
        createStudentRequest.setSurname(surname);
        createStudentRequest.setThirdname(thirdName);

        return createStudentRequest;
    }

    public static EditStudentRequest editStudentRequest(int id, String name, String surname, String birthday, String birthplace, String thirdName) {
        EditStudentRequest edit = new EditStudentRequest();

        edit.setName(name);
        edit.setBirthday(birthday);
        edit.setBirthplace(birthplace);
        edit.setSurname(surname);
        edit.setThirdname(thirdName);
        edit.setId(id);

        return edit;
    }
}
