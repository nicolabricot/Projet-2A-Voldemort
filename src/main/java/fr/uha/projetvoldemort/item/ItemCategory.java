/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

/**
 *
 * @author bruno
 */
public enum ItemCategory {
     SUSTAINABLE("sustainable"),    // Pérenne
     CONSUMABLE("consumable"),      // Consommable
     DEGRADABLE("degradable");      // Dégradable
     
     private final String json;

    /**
     * 
     * @param json 
     * Code json de l'item
     */
    private ItemCategory(final String json) {
        this.json = json;
    }

    /**
     * 
     * @return 
     * Le code json de l'item
     */
    @Override
    public String toString() {
        return json;
    }
    
    public static ItemCategory fromString(String string) {
        
        for (ItemCategory i : ItemCategory.values())
            if (i.toString().equals(string))
                return i;
        
        return null;
    }
}
