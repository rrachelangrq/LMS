/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import error.EntityAlreadyExistsException;
import error.InvalidLoginException;
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
public class StaffSession implements StaffSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void createStaff(Staff staff) throws EntityAlreadyExistsException {
        List<Staff> allStaff = retrieveAllStaff();
        for (Staff s:allStaff) {
            if (s.getUserName().equals(staff.getUserName())) {
                throw new EntityAlreadyExistsException("Staff with this username already exists!");
            }
        }
        em.persist(staff);
    }

    @Override
    public List<Staff> retrieveAllStaff() {
        Query q = em.createQuery("SELECT s FROM Staff s");
        return q.getResultList();
    }

    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginException {
        List<Staff> allStaff = retrieveAllStaff();
        
        for(Staff staff:allStaff)
        {
            if(staff.getUserName().equals(username) && staff.getPassword().equals(password))
            {
                return staff;
            }
        }
        
        throw new InvalidLoginException("Staff username does not exist or invalid password!");
    }
    
    
}
