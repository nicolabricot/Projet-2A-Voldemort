/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fight;

import fr.uha.projetvoldemort.fightreport.FightReport;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;

/**
 *
 * @author bruno
 */
public abstract class Fight {
       
    /**
     * Retourne une valeur aléatoire entre 0 et max
     * @param max
     * @return
     */
    public int nextRandom(int max) {
        return nextRandom(0, max);
    }
    
    /**
     * Retourne une valeur aléatoire entre min et max
     * @param min
     * @param max
     * @return 
     */
    public int nextRandom(int min, int max) {
        return (int)(Math.random() * (max-min)) + min;
    }
    
    public int sum(ArrayList<Item> items, ItemAttribute attribute) {
        int val = 0;
        Iterator<Item> it = items.iterator();
        while(it.hasNext()) {
            val += it.next().getAttribute(attribute);
        }
        return val;
    }
    
    public abstract void AveCaesarMorituriTeSalutant() throws JSONException;
    
    public abstract FightReport getReport();
}
