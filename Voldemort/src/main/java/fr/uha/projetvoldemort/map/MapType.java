/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.map;

/**
 *
 * @author bruno
 */
public enum MapType {
    
    MAP("map"),         // Une carte
    FIGHT("fight"),     // Un combat
    PANOPLY("panoply"), // Une panoplie
    STORE("store"),     // Un magasin
    FACTION("faction"); // La faction 
    
    private final String json;

    /**
     * 
     * @param json 
     * Code json de la map
     */
    private MapType(final String json) {
        this.json = json;
    }

    /**
     * 
     * @return 
     * Le code json de la map
     */
    @Override
    public String toString() {
        return json;
    }
    
    public static MapType fromString(String string) {
        
        for (MapType i : MapType.values())
            if (i.toString().equals(string))
                return i;
        
        StringBuilder str = new StringBuilder();
        str.append(string);
        str.append(" is not a type of MapType.");
        
        throw new RuntimeException(str.toString());
    }
    
    
}
