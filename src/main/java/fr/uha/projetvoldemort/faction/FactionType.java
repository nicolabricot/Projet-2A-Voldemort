/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.faction;

/**
 *
 * @author bruno
 */
public enum FactionType {
    
    WEREWOLF("werewolf"),   // Lycanthrope
    VAMPIRE("vampire"),     // Vampire
    MUMMY("mummy");         // Momie
    
    private final String json;

    /**
     * 
     * @param json 
     * Code json de la faction
     */
    private FactionType(final String json) {
        this.json = json;
    }

    /**
     * 
     * @return 
     * Le code json de la faction
     */
    @Override
    public String toString() {
        return json;
    }
    
    public static FactionType fromString(String string) {
        
        for (FactionType i : FactionType.values())
            if (i.toString().equals(string))
                return i;
        
        StringBuilder str = new StringBuilder();
        str.append(string);
        str.append(" is not a type of FactionType.");
        
        throw new RuntimeException(str.toString());
    }
    
}
