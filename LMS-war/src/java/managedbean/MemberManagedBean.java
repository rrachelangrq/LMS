/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Member;
import error.EntityAlreadyExistsException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.MemberSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "memberManagedBean")
@ViewScoped
public class MemberManagedBean implements Serializable {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;
    
    private String firstName=null;
    private String lastName=null;
    private Character gender=null;
    private Integer age=0;
    private String identityNumber=null;
    private String phoneNumber=null;
    private String address=null;
    
    /**
     * Creates a new instance of MemberManagedBean
     */
    public MemberManagedBean() {
    }
    
    public String createMember() {
        Member member = memberSessionLocal.getMemberByIdNo(getIdentityNumber());
        if (member == null) {
            try {
                firstName = getFirstName().trim();
                lastName = getLastName().trim();
                identityNumber = getIdentityNumber().trim();
                phoneNumber = getPhoneNumber().trim();
                address = getAddress().trim();
                memberSessionLocal.createMember(new Member(firstName, lastName, getGender(), getAge(), identityNumber, phoneNumber, address));
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Success: ",  "Member, " + firstName + " " + lastName + " with identification number " + identityNumber + " created!"));
                return "/secret/memberCreatedTemplateClient.xhtml?faces-redirect=true";
            } catch (EntityAlreadyExistsException ex) {
                setFirstName(null);
                setLastName(null);
                setGender(null);
                setAge(-1);
                setIdentityNumber(null);
                setPhoneNumber(null);
                setAddress(null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error: ",  "Member with this identification number already exists!"));
                return "/secret/createMemberTemplateClient.xhtml"; // no create
            }
        } else {
            setFirstName(null);
            setLastName(null);
            setGender(null);
            setAge(-1);
            setIdentityNumber(null);
            setPhoneNumber(null);
            setAddress(null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "Member with this identification number already exists!"));
            return "/secret/createMemberTemplateClient.xhtml"; // no create
        }
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

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public Character getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the identityNumber
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * @param identityNumber the identityNumber to set
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
