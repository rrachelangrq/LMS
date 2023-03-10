/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import session.BookSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "searchBooksManagedBean")
@ViewScoped
public class SearchBooksManagedBean implements Serializable {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;
    private List<Book> books;
    private Book selectedBook;
    private String param = null;
    private String search = null;
    private int numRes = 0;
    
    /**
     * Creates a new instance of searchBooksManagedBean
     */
    public SearchBooksManagedBean() {
    }
    
    public void searchBooks() {
        if (param.equals("author")) {
            books = bookSessionLocal.searchAvailBookByAuthor(search.trim());
            numRes = books.size();
        } else if (param.equals("isbn")) {
            books = bookSessionLocal.searchAvailBookByIsbn(search.trim());
            numRes = books.size();
        } else if (param.equals("title")) {
            books = bookSessionLocal.searchAvailBookByTitle(search.trim());
            numRes = books.size();
        } else {
            books = bookSessionLocal.retrieveAllAvailBooks();
            numRes = books.size();
        }
        if (numRes == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Note: ",  "There are no books found!"));
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getNumRes() {
        return numRes;
    }

    public void setNumRes(int numRes) {
        this.numRes = numRes;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }
    
}
