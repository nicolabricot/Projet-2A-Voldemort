/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.item;

/**
 *
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
    ARM("arm"),
    RING("ring"),
    BAG("bag"),
    OHTER("other");
    
    private final String json;

    private ItemType(final String json) {
        this.json = json;
    }

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
    
    public boolean isArmor() {
        return (this.equals(CUIRASS) || this.equals(GAUNTLET) ||
                this.equals(GREAVE) || this.equals(HELMET) ||
                this.equals(PAULDRON) || this.equals(SOLLERET) ||
                this.equals(VAMBRACE));
    }
}
