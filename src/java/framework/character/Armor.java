/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.item.Item;
import java.util.HashMap;

/**
 *
 * @author bruno
 */
public final class Armor {
        
    private HashMap<String, Item> armor;

    protected Armor(BasicDBObject ob) {
        this.armor = new HashMap<String, Item>();
        this.hydrate(ob);
    }

    protected Armor() {
        this.armor = new HashMap<String, Item>();
    }
    
    public void hydrate(BasicDBObject ob) {
        this.armor.put(Item.CUIRASS, new Item((DBObject) ob.get(Item.CUIRASS)));
        this.armor.put(Item.GAUNTLET, new Item((DBObject) ob.get(Item.GAUNTLET)));
        this.armor.put(Item.GREAVE, new Item((DBObject) ob.get(Item.GREAVE)));
        this.armor.put(Item.HELMET, new Item((DBObject) ob.get(Item.HELMET)));
        this.armor.put(Item.PAULDRON, new Item((DBObject) ob.get(Item.PAULDRON)));
        this.armor.put(Item.SOLLERET, new Item((DBObject) ob.get(Item.SOLLERET)));
        this.armor.put(Item.VAMBRACE, new Item((DBObject) ob.get(Item.VAMBRACE)));
    }

    public BasicDBObject toBasicDBObject() {
        BasicDBObject ob = new BasicDBObject();
        ob.append(Item.CUIRASS, this.getCuirass().toBasicDBObject());
        ob.append(Item.GAUNTLET, this.getGauntlet().toBasicDBObject());
        ob.append(Item.GREAVE, this.getGreave().toBasicDBObject());
        ob.append(Item.HELMET, this.getHelmet().toBasicDBObject());
        ob.append(Item.PAULDRON, this.getPauldron().toBasicDBObject());
        ob.append(Item.SOLLERET, this.getSolleret().toBasicDBObject());
        ob.append(Item.VAMBRACE, this.getVambrace().toBasicDBObject());   
        
        return ob;
    }
    
    public Item getCuirass() {
        return this.armor.get(Item.CUIRASS);
    }

    public void setCuirass(Item item) {
        this.armor.put(Item.CUIRASS, item);
    }

    public Item getGauntlet() {
        return this.armor.get(Item.GAUNTLET);
    }

    public void setGauntlet(Item item) {
        this.armor.put(Item.GAUNTLET, item);
    }

    public Item getGreave() {
        return this.armor.get(Item.GREAVE);
    }

    public void setGreave(Item item) {
        this.armor.put(Item.GREAVE, item);
    }

    public Item getHelmet() {
        return this.armor.get(Item.HELMET);
    }

    public void setHelmet(Item item) {
        this.armor.put(Item.HELMET, item);
    }

    public Item getPauldron() {
        return this.armor.get(Item.PAULDRON);
    }

    public void setPauldron(Item item) {
        this.armor.put(Item.PAULDRON, item);
    }

    public Item getSolleret() {
        return this.armor.get(Item.SOLLERET);
    }

    public void setSolleret(Item item) {
        this.armor.put(Item.SOLLERET, item);
    }

    public Item getVambrace() {
        return this.armor.get(Item.VAMBRACE);
    }

    public void setVambrace(Item item) {
        this.armor.put(Item.VAMBRACE, item);
    }
}
