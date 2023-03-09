/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

/**
 *
 * @author rachelang
 */
public class EntityAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>EntityAlreadyExistsException</code>
     * without detail message.
     */
    public EntityAlreadyExistsException() {
    }

    /**
     * Constructs an instance of <code>EntityAlreadyExistsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}
