/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort;

/**
 *
 * @author bruno
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException() {
        super();
    }
    
    public NotFoundException(String s) {
        super(s);
    }
    
}
