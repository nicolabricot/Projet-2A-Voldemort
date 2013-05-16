/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.ressource;

/**
 *
 * @author bruno
 */
public class RessourceNotFoundException extends RuntimeException {
    
    public RessourceNotFoundException() {
        super();
    }
    
    public RessourceNotFoundException(String s) {
        super(s);
    }
    
}
