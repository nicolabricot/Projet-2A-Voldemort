/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

/**
 *
 * @author bruno
 */
public enum ItemUsage {
     SUSTAINABLE("sustainable"),
     CONSUMABLE("consumable"),
     DEGRADABLE("degradable");
     
     private final String json;

    /**
     * 
     * @param json 
     * Code json de l'item
     */
    private ItemUsage(final String json) {
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
    
    public static ItemUsage fromString(String string) {
        
        for (ItemUsage i : ItemUsage.values())
            if (i.toString().equals(string))
                return i;
        
        return null;
    }
}
