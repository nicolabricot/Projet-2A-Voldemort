/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.faction;

/**
 *
 * @author bruno
 */
public enum FactionAttribute {
    
    HIT("hit"),
    LUCK("luck"),
    POWER("power");
    
    private final String json;
    
    /**
     * 
     * @param json 
     * Code json de l'attribut
     */
    private FactionAttribute(final String json) {
        this.json = json;
    }

    /**
     * 
     * @return 
     * Le code json de l'attribut
     */
    @Override
    public String toString() {
        return json;
    }
    
    public static FactionAttribute fromString(String string) {
        
        for (FactionAttribute i : FactionAttribute.values())
            if (i.toString().equals(string))
                return i;
        
        return null;
    }
    
}
