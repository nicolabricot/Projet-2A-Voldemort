/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

/**
 * Les différents types d'item existants
 * @author bruno
 */
public enum ItemType {

    CUIRASS("cuirass"),
    GAUNTLET("gauntlet"),
    GREAVE("greave"),
    HELMET("helmet"),
    PAULDRON("pauldron"),
    SOLLERET("solleret"),
    VAMBRACE("vambrace"),
    ARM("weapon"),
    RING("ring"),
    BAG("bag"),
    OHTER("other");
    
    private final String json;

    /**
     * 
     * @param json 
     * Code json de l'item
     */
    private ItemType(final String json) {
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
    
    public static ItemType fromString(String string) {
        
        for (ItemType i : ItemType.values())
            if (i.toString().equals(string))
                return i;
        
        return null;
    }
    
    /**
     * 
     * @return 
     * <i>true</i> si l'item est un élément d'armure
     */
    public boolean isArmor() {
        return (this.equals(CUIRASS) || this.equals(GAUNTLET) ||
                this.equals(GREAVE) || this.equals(HELMET) ||
                this.equals(PAULDRON) || this.equals(SOLLERET) ||
                this.equals(VAMBRACE));
    }
    /**
     * 
     * @return 
     * <i>true</i> si l'item est une arme
     */
    public boolean isArm() {
        return (this.equals(ARM));
    }
}
