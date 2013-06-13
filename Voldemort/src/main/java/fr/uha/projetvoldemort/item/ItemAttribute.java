/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

/**
 *
 * @author bruno
 */
public enum ItemAttribute {
    
    EFFECT("effect"),
    TRIGGER("trigger"),
    CLASS("class"),
    CRITICAL_HIT_MODIFIER("critical_hit_modifier"),
    ATTACK("attack"),
    DEFENSE("defense"),
    LUCK("luck"),
    ACTIVE("active"),
    DURATION("duration");
    
    private final String json;
    
    /**
     * 
     * @param json 
     * Code json de l'attribut
     */
    private ItemAttribute(final String json) {
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
    
    public static ItemAttribute fromString(String string) {
        
        for (ItemAttribute i : ItemAttribute.values())
            if (i.toString().equals(string))
                return i;
        
        StringBuilder str = new StringBuilder();
        str.append(string);
        str.append(" is not a type of ItemAttribute.");
        
        throw new RuntimeException(str.toString());
    }
    
}
