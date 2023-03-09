/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import error.FineNotPaidException;
import error.LendingNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rachelang
 */
@Stateless
public class LendAndReturnSession implements LendAndReturnSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


    @Override
    public List<LendAndReturn> retrieveAllLendAndReturn() {
        Query q = em.createQuery("SELECT l FROM LendAndReturn l");
        return q.getResultList();
    }

    @Override
    public void createLendAndReturn(LendAndReturn lendAndReturn, Long bookId, Long memberId) throws EntityNotFoundException {    
        Book b = em.find(Book.class, bookId);
        if (b == null) {
            throw new EntityNotFoundException("No book found with this id");
        }
        Member m = em.find(Member.class, memberId); 
        if (m == null) {
            throw new EntityNotFoundException("No member found with this id");
        }
        b.getLending().add(lendAndReturn);
        lendAndReturn.setBook(b);
        m.getLending().add(lendAndReturn);
        lendAndReturn.setMember(m);
        em.persist(lendAndReturn);
    }

    @Override
    public void returnBook(Long lendId, BigDecimal fineAmount, boolean isPaid) throws LendingNotFoundException, FineNotPaidException {
        if (!isPaid) { throw new FineNotPaidException("Fine not paid. Cannot return book."); }
        LendAndReturn lending = em.find(LendAndReturn.class, lendId);
        if (lending == null) {
            throw new LendingNotFoundException("Error finding LendAndReturn record!");
        }
        Calendar today = Calendar.getInstance();
        //today.clear(Calendar.HOUR);
        //today.clear(Calendar.MINUTE);
        //today.clear(Calendar.SECOND);
        Date currDate = today.getTime();
        lending.setReturnDate(currDate);
        lending.setFineAmount(fineAmount);
        em.merge(lending); //?? does the lendandreturn entry in book and member also update
    }

    @Override
    public BigDecimal viewFineAmount(Long lendingId) throws LendingNotFoundException {
        Calendar today = Calendar.getInstance();
        //today.clear(Calendar.HOUR);
        //today.clear(Calendar.MINUTE);
        //today.clear(Calendar.SECOND);
        Date currDate = today.getTime();
        LendAndReturn lending = em.find(LendAndReturn.class, lendingId);
        if (lending == null) {
            throw new LendingNotFoundException("Error finding LendAndReturn record!");
        }
        Date startDate = lending.getLendDate();
        long diff = currDate.getTime() - startDate.getTime();
        Long numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (numDays <= 14) {
            return new BigDecimal(0);
        } else {
            Long fineDays = numDays - 14;
            Double amount = fineDays.doubleValue() * 0.5;
            return new BigDecimal(amount);
        }
    }

    @Override
    public LendAndReturn getLending(Long lendId) {
        return em.find(LendAndReturn.class, lendId);
    }  
}
