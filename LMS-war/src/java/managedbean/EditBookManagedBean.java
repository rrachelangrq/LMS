/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.BookSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "editBookManagedBean")
@SessionScoped
public class EditBookManagedBean implements Serializable {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;
    private Book book;
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    
    /**
     * Creates a new instance of EditBookManagedBean
     */
    public EditBookManagedBean() {
    }
    
    public void generateBookDetails() {
        Book book = bookSessionLocal.findBook(bookId);
        if (book == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error: ",  "Error retrieving book details!"));
        }
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }
    
    public String editBook() {
        boolean success = bookSessionLocal.editBook(bookId, title, author, isbn);
        if (success) {
            return "/secret/editBookSuccessfulTemplateClient.xhtml?faces-redirect=true";
        } else {
            return "/secret/editBookUnsuccessfulTemplateClient.xhtml?faces-redirect=true";
        }
    }
    
    public String cancel() {
        this.bookId = null;
        this.title = null;
        this.author = null;
        this.isbn = null;
        this.book = null;
        return "/secret/searchBooksTemplateClient.xhtml?faces-redirect=true";
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    
}
