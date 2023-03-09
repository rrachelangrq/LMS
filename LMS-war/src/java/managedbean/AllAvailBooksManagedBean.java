/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import session.BookSessionLocal;

/**
 *
 * @author rachelang
 */
@Named(value = "allAvailBooksManagedBean")
@RequestScoped
public class AllAvailBooksManagedBean {

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;
    List<Book> books = new ArrayList<>();
    int numRes = 0;

    /**
     * Creates a new instance of AllAvailBooksManagedBean
     */
    public AllAvailBooksManagedBean() {
    }
    
    public List<Book> getAllAvailBooks() {
        books = bookSessionLocal.retrieveAllAvailBooks();
        numRes = books.size();
        return books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getNumRes() {
        return numRes;
    }

    public void setNumRes(int numRes) {
        this.numRes = numRes;
    }
    
}
