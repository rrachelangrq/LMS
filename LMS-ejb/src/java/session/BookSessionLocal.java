/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rachelang
 */
@Local
public interface BookSessionLocal {

    void createBook(Book book);

    List<Book> retrieveAllBooks();

    List<Book> retrieveAllAvailBooks();

    List<Book> searchAvailBookByTitle(String title);
    
    List<Book> searchAvailBookByIsbn(String isbn);
    
    List<Book> searchAvailBookByAuthor(String author);

    Book findBook(Long bookId);
}
