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

    CUIRASS("cuirass"),     // Cuirasse
    GAUNTLET("gauntlet"),   // Gant
    GREAVE("greave"),       // Jambière
    HELMET("helmet"),       // Haume
    PAULDRON("pauldron"),   // Epaulière
    SOLLERET("solleret"),   // Solleret
    VAMBRACE("vambrace"),   // Avant-bras
    WEAPON("weapon"),       // Arme
    RING("ring"),           // Anneau
    BAG("bag"),             // Sacoche
    OHTER("other"),         // Autre
    DEFENSIVE_THROWING("defensive_throwing"),   // Jet défensif
    OFFENSIVE_THROWING("offensive_trhowing"),   // Jet offensif
    PROJECTILE("projectile"),                   // Projectile
    WEAPON_MODIFIER("weapon_modifier"),         // Modificateur d'arme
    AMELIORATION("amelioration"),               // Amélioration
    STIMULANT("stimulant"),                     // Stimulant
    SHIELD("shield"),                           // Bouclier
    SHIELD_MODIFIER("shield_modifier");         // Modificateur de bouclier
    
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
}
