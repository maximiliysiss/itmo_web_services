/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.contracts;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zimma
 */
@XmlRootElement(name = "findRequest")
public class FindStudentsRequest implements Serializable {

    private List<FieldFind> fieldFinds;

    public List<FieldFind> getFieldFinds() {
        return fieldFinds;
    }

    public void setFieldFinds(List<FieldFind> fieldFinds) {
        this.fieldFinds = fieldFinds;
    }

}
