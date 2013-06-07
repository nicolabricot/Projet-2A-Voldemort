/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

/**
 *
 * @author bruno
 */
public enum CharacterAttribute {
    
    LIFE("life"),               // vie
    ABILITY("ability"),         // habilité
    CLASS("class"),             // attribut de la classe du personnage
    LUCK("luck"),               // chance
    ATTACK("attack"),           // attaque
    DODGE("dodge"),             // esquive
    DEFENSE("defense"),         // defense
    INITIATIVE("initiative");   // initiative
    
    private final String json;
    
    /**
     * 
     * @param json 
     * Code json de l'attribut
     */
    private CharacterAttribute(final String json) {
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
    
    public static CharacterAttribute fromString(String string) {
        
        for (CharacterAttribute i : CharacterAttribute.values())
            if (i.toString().equals(string))
                return i;
        
        return null;
    }
    
}
