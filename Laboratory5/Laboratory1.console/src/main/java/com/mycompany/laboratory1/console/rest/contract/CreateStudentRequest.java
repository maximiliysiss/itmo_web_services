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
public class CreateStudentRequest {

    public CreateStudentRequest(String name, String surname, String thirdname, String birthday, String birthplace) {
        this.name = name;
        this.surname = surname;
        this.thirdname = thirdname;
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    
    
    private String name;
    private String surname;
    private String thirdname;
    private String birthday;
    private String birthplace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

}
