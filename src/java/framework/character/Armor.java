/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import framework.item.armor.Gauntlet;
import framework.item.armor.Helmet;
import framework.item.armor.Vambrace;
import framework.item.armor.Solleret;
import framework.item.armor.Greave;
import framework.item.armor.Cuirass;
import framework.item.armor.Pauldron;
import com.mongodb.BasicDBObject;

/**
 *
 * @author bruno
 */
public final class Armor {
    
    private static final String CUIRASS = "cuirass";
    private static final String GAUNTLET = "gauntlet";
    private static final String Greave = "greave";
    private static final String HELMET = "helmet";
    private static final String PAULDRON = "pauldron";
    private static final String SOLLERET = "solleret";
    private static final String VEMBRACE = "vembrace";
    
    private Cuirass cuirass;
    private Gauntlet gauntlet;
    private Greave greave;
    private Helmet helmet;
    private Pauldron pauldron;
    private Solleret solleret;
    private Vambrace vambrace;

    Armor(BasicDBObject ob) {
        this.hydrate(ob);
    }

    Armor() {
    }
    
    public void hydrate(BasicDBObject ob) {
        
        
    }

    public BasicDBObject toBasicDBObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Cuirass getCuirass() {
        return cuirass;
    }

    public void setCuirass(Cuirass cuirass) {
        this.cuirass = cuirass;
    }

    public Gauntlet getGauntlet() {
        return gauntlet;
    }

    public void setGauntlet(Gauntlet gauntlet) {
        this.gauntlet = gauntlet;
    }

    public Greave getGreave() {
        return greave;
    }

    public void setGreave(Greave greave) {
        this.greave = greave;
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public Pauldron getPauldron() {
        return pauldron;
    }

    public void setPauldron(Pauldron pauldron) {
        this.pauldron = pauldron;
    }

    public Solleret getSolleret() {
        return solleret;
    }

    public void setSolleret(Solleret solleret) {
        this.solleret = solleret;
    }

    public Vambrace getVambrace() {
        return vambrace;
    }

    public void setVambrace(Vambrace vambrace) {
        this.vambrace = vambrace;
    }
}
