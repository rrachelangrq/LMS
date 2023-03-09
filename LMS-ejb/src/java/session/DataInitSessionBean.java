/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.Member;
import entity.Staff;
import error.EntityAlreadyExistsException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author rachelang
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "StaffSessionLocal")
    private StaffSessionLocal staffSessionLocal;

    @EJB(name = "MemberSessionLocal")
    private MemberSessionLocal memberSessionLocal;

    @EJB(name = "BookSessionLocal")
    private BookSessionLocal bookSessionLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public DataInitSessionBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        List<Staff> staffs = staffSessionLocal.retrieveAllStaff();
        if (staffs.isEmpty()) {
            dataInit();
        }
    }
    
    private void dataInit() {
        try {
            staffSessionLocal.createStaff(new Staff("Eric", "Some", "eric", "password"));
            staffSessionLocal.createStaff(new Staff("Sarah", "Brightman", "sarah", "password"));
            
            bookSessionLocal.createBook(new Book("Anna Karenina", "0451528611", "Leo Tolstoy"));
            bookSessionLocal.createBook(new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert"));
            bookSessionLocal.createBook(new Book("Hamlet", "1980625026", "William Shakespeare"));
            bookSessionLocal.createBook(new Book("The Hobbits", "9780007458424", "J R R Tolkien"));
            bookSessionLocal.createBook(new Book("Great Expectations", "1521853592", "Charles Dickens"));
            bookSessionLocal.createBook(new Book("Pride and Prejudice", "979-8653642272", "Jane Austen"));
            bookSessionLocal.createBook(new Book("Wuthering Heights", "3961300224", "Emily Brontë"));
            
            memberSessionLocal.createMember(new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3"));
            memberSessionLocal.createMember(new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr"));
        } catch (EntityAlreadyExistsException ex) {
            ex.printStackTrace();
        }
    }
}
