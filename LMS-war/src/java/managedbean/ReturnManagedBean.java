/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author rachelang
 */
@Named(value = "returnManagedBean")
@SessionScoped
public class ReturnManagedBean implements Serializable {

    /**
     * Creates a new instance of ReturnManagedBean
     */
    public ReturnManagedBean() {
    }
    
    public String returnHomePage() {
        return "/secret/frontPageTemplateClient.xhtml?faces-redirect=true";
    }
}
