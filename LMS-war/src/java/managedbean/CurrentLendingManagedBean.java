/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.LendAndReturn;
import entity.Member;
import error.MemberNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import session.MemberSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "currentLendingManagedBean")
@ViewScoped
public class CurrentLendingManagedBean implements Serializable {

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;
    private String memberIdNo;
    private List<LendAndReturn> lendings;
    private int numRes = 0;

    /**
     * Creates a new instance of CurrentLendingManagedBean
     */
    public CurrentLendingManagedBean() {
    }

    public void viewAllCurrentLendings() {
        Member member = memberSessionLocal.getMemberByIdNo(memberIdNo);
        if (member == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ", "No member found with this identification number!"));
            lendings = null;
            memberIdNo = null;
        } else {
            try {
                lendings = memberSessionLocal.viewAllMembersCurrLending(memberIdNo);
            } catch (MemberNotFoundException ex) {
                //
            }
            numRes = lendings.size();
            if (lendings.isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Note: ", "Member has not lent any books!"));
                lendings = null;
                memberIdNo = null;
            }
        }
        
    }

    public List<LendAndReturn> getLendings() {
        return lendings;
    }

    public void setLendings(List<LendAndReturn> lendings) {
        this.lendings = lendings;
    }

    public String getMemberIdNo() {
        return memberIdNo;
    }

    public void setMemberIdNo(String memberIdNo) {
        this.memberIdNo = memberIdNo;
    }

    public int getNumRes() {
        return numRes;
    }

    public void setNumRes(int numRes) {
        this.numRes = numRes;
    }

}
