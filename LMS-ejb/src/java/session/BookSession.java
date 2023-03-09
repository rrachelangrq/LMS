/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rachelang
 */
@Stateless
public class BookSession implements BookSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void createBook(Book book) {
        em.persist(book);
    }

    @Override
    public List<Book> retrieveAllBooks() {
        Query q = em.createQuery("SELECT b FROM Book b");
        return q.getResultList();
    }

    @Override
    public List<Book> retrieveAllAvailBooks() {
        List<Book> availBooks = new ArrayList<>();
        List<Book> allBooks = retrieveAllBooks();
        for (Book b:allBooks) {
            List<LendAndReturn> allLending = b.getLending();
            int size = allLending.size();
            if (size < 1) {
                availBooks.add(b);
            } else if (allLending.get(size-1).getReturnDate() != null) {
                availBooks.add(b);
            }
        }
        return availBooks;
    }

    @Override
    public List<Book> searchAvailBookByTitle(String title) {
        //List<Book> searchBooks = new ArrayList<>();
        List<Book> availBooks = new ArrayList<>();
        /*for (Book b:availBooks) {
            if (b.getTitle().equals(title)) {
                searchBooks.add(b);
            }
        }
        return searchBooks;*/
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title");
        q.setParameter("title", "%" + title + "%");
        List<Book> allBooks = q.getResultList();
        for (Book b:allBooks) {
            List<LendAndReturn> allLending = b.getLending();
            int size = allLending.size();
            if (size < 1) {
                availBooks.add(b);
            } else if (allLending.get(size-1).getReturnDate() != null) {
                availBooks.add(b);
            }
        }
        return availBooks;
    }
    
    @Override
    public List<Book> searchAvailBookByIsbn(String isbn) {
        List<Book> searchBooks = new ArrayList<>();
        List<Book> availBooks = retrieveAllAvailBooks();
        for (Book b:availBooks) {
            if (b.getIsbn().equals(isbn)) {
                searchBooks.add(b);
            }
        }
        return searchBooks;
    }
    
    @Override
    public List<Book> searchAvailBookByAuthor(String author) {
        //List<Book> searchBooks = new ArrayList<>();
        List<Book> availBooks = new ArrayList<>();
        /*for (Book b:availBooks) {
            if (b.getAuthor().equals(author)) {
                searchBooks.add(b);
            }
        }
        return searchBooks;*/
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author");
        q.setParameter("author", "%" + author + "%");
        List<Book> allBooks = q.getResultList();
        for (Book b:allBooks) {
            List<LendAndReturn> allLending = b.getLending();
            int size = allLending.size();
            if (size < 1) {
                availBooks.add(b);
            } else if (allLending.get(size-1).getReturnDate() != null) {
                availBooks.add(b);
            }
        }
        return availBooks;
    }

    @Override
    public Book findBook(Long bookId) {
        return em.find(Book.class, bookId);
    }
    
    
}
