/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.ressource.Ressources;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public final class CharacterClass {

    public static final String COLLECTION = "character_class";
    
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
 
    private ObjectId id;
    private String name, description;
    
    public CharacterClass(ObjectId oid) {                
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid); 
        this.hydrate(ob);
    }
    
    public CharacterClass(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }
    
    public CharacterClass() {
    }
    
    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
    }
    
    public BasicDBObject toBasicDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        if (this.id != null) ob.append(ID, this.id);
        ob.append(NAME, this.name);
        ob.append(DESCRIPTION, this.description);
        
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
