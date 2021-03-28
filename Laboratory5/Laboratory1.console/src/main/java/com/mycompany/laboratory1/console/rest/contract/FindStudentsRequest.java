/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.rest.contract;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zimma
 */
@XmlRootElement(name = "findRequest")
public class FindStudentsRequest implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private String thirdName;
    private String birthday;
    private String birthplace;

    public FindStudentsRequest(Integer id, String name, String surname, String thirdName, String birthday, String birthplace) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.thirdName = thirdName;
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    public FindStudentsRequest() {
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

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();

        if (id != null) {
            map.put("id", id);
        }
        if (birthday != null) {
            map.put("birthday", birthday);
        }
        if (birthplace != null) {
            map.put("birthplace", birthplace);
        }
        if (name != null) {
            map.put("name", name);
        }
        if (surname != null) {
            map.put("surname", surname);
        }
        if (thirdName != null) {
            map.put("thirdname", thirdName);
        }

        return map;
    }

}
