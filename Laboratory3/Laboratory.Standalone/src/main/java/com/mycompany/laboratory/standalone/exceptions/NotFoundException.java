/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory.standalone.exceptions;

import javax.xml.ws.WebFault;

/**
 *
 * @author zimma
 */
@WebFault(name = "PersonServiceFault")
public class NotFoundException extends Exception {

    private final PersonServiceFault fault;

    public NotFoundException(String message, PersonServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public NotFoundException(String message, PersonServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public PersonServiceFault getFaultInfo() {
        return fault;
    }
}
