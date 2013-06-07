/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Item {

    public static final String COLLECTION = "item";
    private static final String ID = "_id";
    private static final String MODEL_ID = "model_id";
    private static final String ATTRIBUTES = "attributes";

    private ObjectId id;
    private ItemModel model;
    private EnumMap<ItemAttribute, Integer> attributes;

    public Item(ObjectId id) {
        this.attributes = new EnumMap<ItemAttribute, Integer>(ItemAttribute.class);
        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(id);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public Item(ItemModel model) {
        this.attributes = new EnumMap<ItemAttribute, Integer>(ItemAttribute.class);
        this.model = model;
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.model = new ItemModel(ob.getObjectId(MODEL_ID));
        
        BasicDBObject obAttributes = (BasicDBObject) ob.get(ATTRIBUTES);
        Iterator<String> itAttributes = obAttributes.keySet().iterator();
        while (itAttributes.hasNext()) {
            String key = itAttributes.next();
            Integer value = obAttributes.getInt(key);
            this.attributes.put(ItemAttribute.fromString(key), value);
        }

    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, this.id);
        }
        ob.append(MODEL_ID, this.model.getId());

        BasicDBObject obAttributes = new BasicDBObject();
        Iterator<Entry<ItemAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
        while (itAttributes.hasNext()) {
            Entry<ItemAttribute, Integer> attribute = itAttributes.next();
            obAttributes.append(attribute.getKey().toString(), attribute.getValue());
        }
        ob.append(ATTRIBUTES, obAttributes);

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("model", this.model.toJSONObject());
        ob.put("id", this.id.toString());

        JSONObject obAttributes = new JSONObject();
        Iterator<Entry<ItemAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
        while (itAttributes.hasNext()) {
            Entry<ItemAttribute, Integer> attribute = itAttributes.next();
            obAttributes.put(attribute.getKey().toString(), attribute.getValue());
        }
        ob.put(ATTRIBUTES, obAttributes);

        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        //Resources.getInstance().getCollection(COLLECTION).insert(ob);
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Item.save: " + ob);
    }

    public ObjectId getId() {
        return this.id;
    }

    
    public ItemType getType() {
        return this.model.getType();
    }
    
    public ItemCategory getCategory() {
        return this.model.getCategory();
    }
    
    public ItemModel getModel() {
        return this.model;
    }
    
    public int getAttribute(ItemAttribute attribute) {  
        return this.attributes.get(attribute);
    }
    
    public void setAttribute(ItemAttribute attribute, int value) {
        this.attributes.put(attribute, value);
    }
    
    public Item getAmelioration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
