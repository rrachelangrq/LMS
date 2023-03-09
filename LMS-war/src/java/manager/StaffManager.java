/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import entity.Staff;
import error.InvalidLoginException;
import session.StaffSessionLocal;

/**
 *
 * @author rachelang
 */
public class StaffManager {
    private StaffSessionLocal staffSessionLocal;

    public StaffManager() {
    }

    public StaffManager(StaffSessionLocal staffSessionLocal) {
        this.staffSessionLocal = staffSessionLocal;
    }
    
    public Staff staffLogin(String username, String password) throws InvalidLoginException {
        Staff s = new Staff();
        try {
            s = staffSessionLocal.staffLogin(username, password);
        } catch (InvalidLoginException e) {
            throw e;
        }
        return s;
    }
}
