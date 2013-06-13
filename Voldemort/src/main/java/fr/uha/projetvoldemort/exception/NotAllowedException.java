/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.exception;

/**
 *
 * @author bruno
 */
public class NotAllowedException extends RuntimeException {
    
    public NotAllowedException() {
        super();
    }
    
    public NotAllowedException(String s) {
        super(s);
    }  
}
