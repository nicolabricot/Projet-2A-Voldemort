/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

/**
 * Généré si un item ne peux pas être équipé :
 *
 * <ul>
 * <li>Si le type n'est pas correcte</li>
 * <li>Si le niveau minimum requis n'est pas atteind</li>
 * </ul>
 *
 * @author bruno
 *
 * @see Armor.set(Item item)
 * @see Individual.setArm(Item item)
 */
public class UnexpectedItemException extends Exception {

    public UnexpectedItemException() {
        super();
    }

    /**
     *
     * @param s Message précisant des infomations supplémentaires
     */
    public UnexpectedItemException(String s) {
        super(s);
    }
}
