/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.rest.contract;

/**
 *
 * @author zimma
 */
public class EditStudentRequest extends CreateStudentRequest{
    private int id;

    public EditStudentRequest(int id, String name, String surname, String thirdname, String birthday, String birthplace) {
        super(name, surname, thirdname, birthday, birthplace);
        this.id = id;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
