/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import session.BookSessionLocal;
import session.LendAndReturnSessionLocal;
import session.MemberSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "lendBookManagedBean")
@SessionScoped
public class LendBookManagedBean implements Serializable {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;

    @EJB(name = "LendAndReturnSessionLocal")
    private LendAndReturnSessionLocal lendAndReturnSessionLocal;

    private String bookIdStr;
    private String memberIdNo;

    /**
     * Creates a new instance of LendBookManagedBean
     */
    public LendBookManagedBean() {
    }

    public String lendBook() {
        Long bookId = Long.parseLong(bookIdStr.trim());
        Member member = memberSessionLocal.getMemberByIdNo(memberIdNo);
        Book book = bookSessionLocal.findBook(bookId);
        int numLending = book.getLending().size();
        if (book == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ", "No book found with this BookID!"));
        } else if (member == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ", "No member found with this identification number!"));
        } else if (numLending > 0 && book.getLending().get(numLending - 1).getReturnDate() == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ", "Book is currently unavailable!"));
        } else {
            LendAndReturn lend = new LendAndReturn();
            lend.setBook(book);
            lend.setMember(member);
            lend.setLendDate(new Date());
            lend.setFineAmount(BigDecimal.ZERO);
            lend.setReturnDate(null);
            lendAndReturnSessionLocal.createLendAndReturn(lend, book.getBookId(), member.getMemberId());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Success: ", "Book " + book.getTitle() + " lent to member " + member.getFirstName() + " " + member.getLastName()));
            bookIdStr = null;
            memberIdNo = null;
            return "/secret/successfulLendTemplateClient.xhtml?faces-redirect=true";
        }
        bookIdStr = null;
        memberIdNo = null;
        return "";
    }

    public String cancel() {
        bookIdStr = null;
        memberIdNo = null;
        return "/secret/searchBooksTemplateClient.xhtml?faces-redirect=true";
    }

    public void lendBook(String bookIdStr) {
        this.bookIdStr = bookIdStr;
        lendBook();
    }

    public String getBookIdStr() {
        return bookIdStr;
    }

    public void setBookIdStr(String bookIdStr) {
        this.bookIdStr = bookIdStr;
    }

    public String getMemberIdNo() {
        return memberIdNo;
    }

    public void setMemberIdNo(String memberIdNo) {
        this.memberIdNo = memberIdNo;
    }
}
