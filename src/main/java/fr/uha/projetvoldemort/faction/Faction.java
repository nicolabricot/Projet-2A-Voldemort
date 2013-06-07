/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.faction;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import static fr.uha.projetvoldemort.character.CharacterModel.COLLECTION;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public class Faction {
    
    public static final String COLLECTION = "faction";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String ATTRIBUTES =  "attributes";
    private static final String TYPE = "type";
    
    private ObjectId id;
    private String name, description; 
    private EnumMap<FactionAttribute, Integer> attributes;
    private FactionType type;
    
    
    public Faction(ObjectId oid) {
        this.attributes = new EnumMap<FactionAttribute, Integer>(FactionAttribute.class);
        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }
    
    public Faction(FactionType type) {
        this.attributes = new EnumMap<FactionAttribute, Integer>(FactionAttribute.class);
        this.type = type;
    }
    
    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
        this.type = FactionType.fromString(ob.getString(TYPE));
        
        BasicDBObject obAttributes = (BasicDBObject) ob.get(ATTRIBUTES);
        Iterator<String> itAttributes = obAttributes.keySet().iterator();
        while (itAttributes.hasNext()) {
            String key = itAttributes.next();
            Integer value = obAttributes.getInt(key);
            this.attributes.put(FactionAttribute.fromString(key), value);
        }
    }
    
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, this.id);
        }
        
        ob.append(NAME, this.name);
        ob.append(DESCRIPTION, this.description);
        ob.append(TYPE, this.type.toString());
        
        BasicDBObject obAttributes = new BasicDBObject();
        Iterator<Entry<FactionAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
        while (itAttributes.hasNext()) {
            Entry<FactionAttribute, Integer> attribute = itAttributes.next();
            obAttributes.append(attribute.getKey().toString(), attribute.getValue());
        }
        ob.append(ATTRIBUTES, obAttributes);
        
        return ob;
    }
    
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("id", this.id.toString());
        
        ob.put(NAME, this.name);
        ob.put(DESCRIPTION, this.description);
        ob.put(TYPE, this.type.toString());
        
        JSONObject obAttributes = new JSONObject();
        Iterator<Entry<FactionAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
        while (itAttributes.hasNext()) {
            Entry<FactionAttribute, Integer> attribute = itAttributes.next();
            obAttributes.put(attribute.getKey().toString(), attribute.getValue());
        }
        ob.put(ATTRIBUTES, obAttributes);
        
        return ob;
    }
    
    public void save() {        
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Faction.save: " + ob);
    }
    
    public ObjectId getId() {
       return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setAttribute(FactionAttribute attribute, int value) {
        this.attributes.put(attribute, value);
    }
    
    public int getAttribute(FactionAttribute attribute) {
        if (!this.attributes.keySet().contains(attribute))
            return 0;
        return this.attributes.get(attribute);
    }
    
    public FactionType getType() {
        return this.type;
    }
    
}
