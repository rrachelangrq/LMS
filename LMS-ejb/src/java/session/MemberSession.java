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
public class MemberSession implements MemberSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void createMember(Member member) throws EntityAlreadyExistsException {
        List<Member> allMembers = retrieveAllMembers();
        for (Member m:allMembers) {
            if (m.getIdentityNo().trim().toLowerCase().equals(member.getIdentityNo().trim().toLowerCase())) {
                throw new EntityAlreadyExistsException("Member with this identification number already exists!");
            }
        }
        em.persist(member);
    }

    @Override
    public List<Member> retrieveAllMembers() {
        Query q = em.createQuery("SELECT m FROM Member m");
        return q.getResultList();
    }

    @Override
    public List<LendAndReturn> viewAllMembersCurrLending(String idNo) throws MemberNotFoundException {
        Member currMember = null;
        List<Member> allMembers = retrieveAllMembers();
        for (Member m:allMembers) {
            if (m.getIdentityNo().toLowerCase().equals(idNo.toLowerCase().trim())) {
                currMember = m;
            }
        }
        if (currMember == null) {
            throw new MemberNotFoundException("No member found with this Identity No.");
        } else {
            List<LendAndReturn> lending = currMember.getLending();
            List<LendAndReturn> currLending = new ArrayList<>();
            for (LendAndReturn l:lending) {
                if (l.getReturnDate() == null) {
                    currLending.add(l);
                }
            }
            return currLending;
        }
    }

    @Override
    public Member getMember(Long mId) throws MemberNotFoundException {
        Member m = em.find(Member.class, mId);
        if (m == null) {
            throw new MemberNotFoundException("No member with this id");
        }
        return m;
    }

    @Override
    public Member getMemberByIdNo(String idNo) {
        List<Member> allMembers = retrieveAllMembers();
        for (Member m:allMembers) {
            if (m.getIdentityNo().trim().toLowerCase().equals(idNo.trim().toLowerCase())) {
                return m;
            }
        }
        return null;
    } 

    @Override
    public boolean editMember(Long memberId, String firstName, String lastName, String phoneNumber, String address) {
        Member member = em.find(Member.class, memberId);
        if (member == null) {
            return false;
        }
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setPhone(address);
        member.setAddress(address);
        return true;
    }
    
}
