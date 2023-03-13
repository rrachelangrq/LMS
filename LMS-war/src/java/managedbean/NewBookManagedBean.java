/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import session.BookSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "newBookManagedBean")
@ViewScoped
public class NewBookManagedBean implements Serializable {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    private String title;
    private String isbn;
    private String author;

    /**
     * Creates a new instance of NewBookManagedBean
     */
    public NewBookManagedBean() {
    }

    public String createNewBook() {
        this.title = title.trim();
        this.author = author.trim();
        this.isbn = isbn.trim();
        Book newBook = new Book();
        newBook.setAuthor(author);
        newBook.setIsbn(isbn);
        newBook.setTitle(title);
        try {
            bookSessionLocal.createBook(newBook);
            FacesContext context = FacesContext.getCurrentInstance();
            Flash flash = context.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            flash.setRedirect(true);
            context.addMessage(null, new FacesMessage("Note: ", "Book '" + newBook.getTitle() + "' created!"));
            return "/secret/bookCreatedTemplateClient.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            Flash flash = context.getExternalContext().getFlash();
            flash.setKeepMessages(true);
            flash.setRedirect(true);
            context.addMessage(null, new FacesMessage("Error: ", "Book '" + newBook.getTitle() + "' not created!"));
        }
        return "/secret/createBookTemplateClient.xhtml";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
