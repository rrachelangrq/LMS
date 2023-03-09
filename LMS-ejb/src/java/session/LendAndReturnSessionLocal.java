/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.LendAndReturn;
import error.FineNotPaidException;
import error.LendingNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rachelang
 */
@Local
public interface LendAndReturnSessionLocal {

    List<LendAndReturn> retrieveAllLendAndReturn();

    void createLendAndReturn(LendAndReturn lendAndReturn, Long bookId, Long memberId);

    void returnBook(Long lendId, BigDecimal fineAmount, boolean isPaid) throws LendingNotFoundException, FineNotPaidException;

    BigDecimal viewFineAmount(Long lendingId) throws LendingNotFoundException;

    LendAndReturn getLending(Long lendId);
    
}
