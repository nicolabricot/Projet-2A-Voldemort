/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import fr.uha.projetvoldemort.item.Item;

/**
 *
 * @author bruno
 */
public interface InventoryListener {
    
    /**
     * Méthode appelée quand un item est supprimé de l'inventaire
     * @param item 
     */
    public void remove(Item item);
    
}
