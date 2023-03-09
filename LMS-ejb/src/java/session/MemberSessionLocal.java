/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import error.EntityAlreadyExistsException;
import error.MemberNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rachelang
 */
@Local
public interface MemberSessionLocal {

    void createMember(Member member) throws EntityAlreadyExistsException;

    List<Member> retrieveAllMembers();

    List<LendAndReturn> viewAllMembersCurrLending(String idNo) throws MemberNotFoundException;

    Member getMember(Long mId) throws MemberNotFoundException;

    Member getMemberByIdNo(String idNo);

    boolean editMember(Long memberId, String firstName, String lastName, String phoneNumber, String address);
    
}
