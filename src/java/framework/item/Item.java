/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.ressource.Ressources;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public final class Item {
    
    /* Armor */
    public static final String CUIRASS = "cuirass";
    public static final String GAUNTLET = "gauntlet";
    public static final String GREAVE = "greave";
    public static final String HELMET = "helmet";
    public static final String PAULDRON = "pauldron";
    public static final String SOLLERET = "solleret";
    public static final String VAMBRACE = "vambrace";
    
    /* Ohter */
    public static final String ARM = "arm";
    public static final String RING = "ring";
    public static final String BAG = "bag";
    public static final String OHTER = "other";
    
    public static final String COLLECTION = "item";
    
    private static final String ID = "_id";
    private static final String TYPE = "type";
    
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    
    private ObjectId id;
    private String type, name, description;

    public Item(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }
    
    public Item(ObjectId oid) {
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid); 
        this.hydrate(ob);
    }
    
    public Item() {
    }
    
    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.type = ob.getString(TYPE);
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
    }
    
    public BasicDBObject toBasicDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        if (this.id != null) ob.append(ID, id);
        ob.append(TYPE, type);
        ob.append(NAME, name);
        ob.append(DESCRIPTION, description);
        
        return ob;
    }
    
    public void save() {
        BasicDBObject ob = this.toBasicDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
