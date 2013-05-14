/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.Properties;
import framework.ressource.Ressources;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public final class Character  {
    
    public static final String COLLECTION = "character";
    
    private static final String ID = "_id";
    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";

    private ObjectId id;
    private CharacterModel model;
    private Properties properties;
    private String name;
    private Equipment equipment;

    public Character(ObjectId oid) {                
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        this.hydrate(ob);
    }
    
    public Character(CharacterModel model) {
        this.model = model;
        this.properties = new Properties();
        this.equipment = new Equipment();
    }
    
    private void hydrate(BasicDBObject ob) {
        System.out.println(ob);
        this.id = ob.getObjectId(ID);
        this.model = new CharacterModel(ob.getObjectId(MODEL_ID));
        this.name = ob.getString(NAME);
        this.properties = new Properties((DBObject) ob.get(Properties.PROPERTIES));
        this.equipment = new Equipment((DBObject) ob.get(Equipment.EQUIPMENT));
    }  
    
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        if (this.id != null) ob.append(ID, this.id);
        ob.append(MODEL_ID, this.model.getId());
        ob.append(NAME, this.name);
        ob.append(Properties.PROPERTIES, this.properties.toDBObject());
        ob.append(Equipment.EQUIPMENT, this.equipment.toDBObject());
        
        return ob;
    }
    
    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob); // TODO : bug update
        this.id = ob.getObjectId(ID);
    }
    
   public Properties getProperties() {
       return this.properties;
   }
   
   public void setProperties(Properties properties) {
       this.properties = properties;
   }

    public CharacterModel getModel() {
        return this.model;
    }

    public void setModel(CharacterModel model) {
        this.model = model;
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
    
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    
    public Equipment getEquipment() {
        return this.equipment;
    }
}
