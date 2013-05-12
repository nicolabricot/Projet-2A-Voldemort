/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import framework.character.item.armor.*;

/**
 *
 * @author bruno
 */
public class Armor {
    
    private Cuirass cuirass;
    private Gauntlet gauntlet;
    private Greave greave;
    private Helmet helmet;
    private Pauldron pauldron;
    private Solleret solleret;
    private Vambrace vambrace;

    Armor(BasicDBObject basicDBObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Armor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void hydrate(BasicDBObject ob) {
        
    }

    public BasicDBObject toBasicDBObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
