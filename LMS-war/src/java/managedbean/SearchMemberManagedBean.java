/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Member;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.MemberSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "searchMemberManagedBean")
@SessionScoped
public class SearchMemberManagedBean implements Serializable {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;
    private Member member;
    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNumber;
    private String phoneNumber;
    private String address;
    /**
     * Creates a new instance of SearchMemberManagedBean
     */
    public SearchMemberManagedBean() {
    }
    
    public String searchMember() {
        Member member = memberSessionLocal.getMemberByIdNo(identityNumber);
        if (member == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "No member found with this identity number!"));
            this.identityNumber = null;
            return "/secret/searchMemberTemplateClient.xhtml";
        }
        this.member = member;
        this.identityNumber = identityNumber.toUpperCase();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.gender = member.getGender();
        this.age = member.getAge();
        this.phoneNumber = member.getPhone();
        this.address = member.getAddress();
        return "/secret/editMemberTemplateClient.xhtml?faces-redirect=true";
    }
    
    public String editMemberDetails() {
        boolean success = memberSessionLocal.editMember(member.getMemberId(), firstName.trim(), lastName.trim(), phoneNumber.trim(), address.trim());
        this.identityNumber = null;
        this.member = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.age = null;
        this.phoneNumber = null;
        this.address = null;
        if (success) {
            return "/secret/editSuccessfulTemplateClient.xhtml?faces-redirect=true";
        } else {
            return "/secret/editUnsuccessfulTemplateClient.xhtml?faces-redirect=true";
        }
    }
    
    public String cancel() {
        this.member = null;
        this.identityNumber = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.age = null;
        this.phoneNumber = null;
        this.address = null;
        return "/secret/memberDetailsTemplateClient.xhtml?faces-redirect=true";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
