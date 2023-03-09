/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author rachelang
 */
@Named(value = "returnPopUpManagedBean")
@SessionScoped
public class ReturnPopUpManagedBean implements Serializable {

    private String message;
    /**
     * Creates a new instance of ReturnPopUpManagedBean
     */
    public ReturnPopUpManagedBean() {
        this.message = "Default message";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
