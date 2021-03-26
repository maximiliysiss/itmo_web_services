/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.laboratory1.console.factory.standalone;

import com.mycompany.laboratory1.console.client.standalone.FieldFind;

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
}
