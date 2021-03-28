/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.rest.contract;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zimma
 */
@XmlRootElement(name = "student")
public class Student implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private String thirdname;
    private String birthday;
    private String birthplace;

    public Student() {
    }

    public Student(Integer id, String name, String surname, String thirdname, String birthday, String birthplace) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.thirdname = thirdname;
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
