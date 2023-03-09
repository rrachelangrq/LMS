/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Staff;
import error.InvalidLoginException;
import java.awt.event.ActionListener;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.StaffSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB(name = "StaffSessionLocal")
    private StaffSessionLocal staffSessionLocal;
    
    private String username = null;
    private String password = null;
    private String firstName = null;
    private Long staffId = -1L;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }
    public String login() {
        try {
            Staff staff = staffSessionLocal.staffLogin(username.trim(), password.trim());
            staffId = staff.getStaffId();
            firstName = staff.getFirstName();
            return "/secret/frontPageTemplateClient.xhtml?faces-redirect=true";
        } catch (InvalidLoginException ex) {
            //login failed
            username = null;
            password = null;
            setFirstName(null);
            staffId = -1L;
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "Invalid Username or Password"));
            return "loginTemplateClient.xhtml";
        }
    } //end login
        
    public String logout() {
        username = null;
        password = null;
        setFirstName(null);
        staffId = -1L;

        return "/loginTemplateClient.xhtml?faces-redirect=true";
    } //end logout
    
    public String sessionTimedOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Warning: ", "Session timed out! Please login again!"));
        return "/loginTemplateClient.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
