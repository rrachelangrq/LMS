/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import error.FineNotPaidException;
import error.LendingNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.LendAndReturnSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "returnBookManagedBean")
@SessionScoped
public class ReturnBookManagedBean implements Serializable {

    @EJB(name = "LendAndReturnSessionLocal")
    private LendAndReturnSessionLocal lendAndReturnSessionLocal;
    //@ManagedProperty("#{returnPopUpManagedBean}")
    //private ReturnPopUpManagedBean returnPopUpManagedBean;
    private String lendIdStr;
    private BigDecimal fineAmount;
    private boolean isPaid;
    private List<LendAndReturn> lendings = new ArrayList<>();
    private LendAndReturn currLending;
    private float fineAmountPaidFloat;
    /**
     * Creates a new instance of ReturnBookManagedBean
     */
    public ReturnBookManagedBean() {
    }
    
    public String calcFineAmount() {
        Long lendId = Long.parseLong(lendIdStr.trim());
        try {
            BigDecimal calcFineAmount = lendAndReturnSessionLocal.viewFineAmount(lendId);
            currLending = lendAndReturnSessionLocal.getLending(lendId);
            if (currLending.getReturnDate() != null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error: ",  "This book ("+ currLending.getBook().getTitle() +") has already been returned!"));
                this.lendIdStr = null;
                lendings = new ArrayList<>(); // empty the lendings
                currLending = null;
                fineAmountPaidFloat = 0.0f;
                fineAmount = new BigDecimal(0);
                return "/secret/returnBookTemplateClient.xhtml";
            }
            lendings.add(currLending);
            fineAmount = calcFineAmount;
            return "/secret/finalReturnTemplateClient.xhtml?faces-redirect=true";
        } catch (LendingNotFoundException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "No lending record found with this LendID!"));
        }
        return "/secret/returnBookTemplateClient.xhtml";
    }
    
    public String returnBook() {
        //isPaid = true;
        Long lendId = Long.parseLong(lendIdStr.trim());
        try {
            // check that fine amount paid is equal to fineAmount
            BigDecimal amountPaid = new BigDecimal(fineAmountPaidFloat);
            if (amountPaid.compareTo(fineAmount) < 0) {
                // too little
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error: ",  "Fine amount not paid in full, refunding, book cannot be returned!"));
                //returnPopUpManagedBean.setMessage("Fine amount not paid in full, refunding, book cannot be returned!");
                lendings = new ArrayList<>(); // empty the lendings
                currLending = null;
                fineAmountPaidFloat = 0.0f;
                fineAmount = new BigDecimal(0);
                return "/secret/deficitFineTemplateClient.xhtml?faces-redirect=true";
            } else if (amountPaid.compareTo(fineAmount) > 0) {
                // too much
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error: ",  "Excess fine amount paid, refunding, book cannot be returned!"));
                //returnPopUpManagedBean.setMessage("Excess fine amount paid, refunding, book cannot be returned!");
                lendings = new ArrayList<>(); // empty the lendings 
                currLending = null;
                fineAmountPaidFloat = 0.0f;
                fineAmount = new BigDecimal(0);
                return "/secret/excessFineTemplateClient.xhtml?faces-redirect=true";
            }
            isPaid = true;
            lendAndReturnSessionLocal.returnBook(lendId, fineAmount, isPaid);
            lendings = new ArrayList<>(); // empty the lendings 
            currLending = null;
            fineAmountPaidFloat = 0.0f;
            fineAmount = new BigDecimal(0);
            LendAndReturn lend = lendAndReturnSessionLocal.getLending(lendId);
            Book book = lend.getBook();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Note: ",  "Book " + book.getTitle() + " has been returned!")); //create redirect
            //returnPopUpManagedBean.setMessage("Book " + book.getTitle() + " has been returned!");
            return "/secret/returnSuccessfulTemplateClient.xhtml?faces-redirect=true";
        } catch (LendingNotFoundException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "No lending record found with this LendID!"));
            return "/secret/finalReturnTemplateClient.xhtml";
        } catch (FineNotPaidException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "Fine not paid!")); 
            return "/secret/finalReturnTemplateClient.xhtml";
        }
    }
    
    // 
    public String cancelReturn() {
        lendings = new ArrayList<>(); // empty the lendings 
        currLending = null;
        fineAmountPaidFloat = 0.0f;
        fineAmount = new BigDecimal(0);
        lendIdStr = null;
        return "/secret/returnBookTemplateClient.xhtml?faces-redirect=true";
    }
    
    public String getLendIdStr() {
        return lendIdStr;
    }
    
    public void setLendIdStr(String lendIdStr) {
        this.lendIdStr = lendIdStr;
    }
    
    public List<LendAndReturn> getLendings() {
        return lendings;
    }
    public void setLendings(List<LendAndReturn> lendings) {
        this.lendings = lendings;
    }
    public BigDecimal getFineAmount() {
        return fineAmount;
    }
    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public LendAndReturn getCurrLending() {
        return currLending;
    }

    public void setCurrLending(LendAndReturn currLending) {
        this.currLending = currLending;
    }

    public Float getFineAmountPaidFloat() {
        return fineAmountPaidFloat;
    }

    public void setFineAmountPaidFloat(Float fineAmountPaidFloat) {
        this.fineAmountPaidFloat = fineAmountPaidFloat;
    }
    
}
