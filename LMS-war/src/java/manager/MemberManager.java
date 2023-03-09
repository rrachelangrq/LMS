/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import entity.Member;
import error.EntityAlreadyExistsException;
import error.MemberNotFoundException;
import session.MemberSessionLocal;

/**
 *
 * @author rachelang
 */
public class MemberManager {

    private MemberSessionLocal memberSessionLocal;

    public MemberManager() {
    }

    public MemberManager(MemberSessionLocal memberSessionLocal) {
        this.memberSessionLocal = memberSessionLocal;
    }
    
    public Member getMember(Long cId) throws MemberNotFoundException {
        return memberSessionLocal.getMember(cId);
    }
    
    public boolean registerMember(String firstName, String lastName, String genderStr, Integer age, String idNo, String phone, String address) 
        throws EntityAlreadyExistsException {
        
        firstName = firstName.trim();
        lastName = lastName.trim();
        Character gender = genderStr.trim().toLowerCase().charAt(0);
        idNo = idNo.toLowerCase().trim();
        phone = phone.trim().toLowerCase();
        address = address.trim().toLowerCase();
        
        Member member = new Member();
        member.setAddress(address);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setGender(gender);
        member.setPhone(phone);
        member.setIdentityNo(idNo);
        member.setAge(age);
        
        try {
            memberSessionLocal.createMember(member);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        }
        return true;
    }
}
