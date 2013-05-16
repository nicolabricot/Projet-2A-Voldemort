/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.Properties;
import fr.uha.projetvoldemort.ressource.RessourceNotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class ItemModel {

    public static final String COLLECTION = "item_model";
    private static final String ID = "_id";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private ObjectId id;
    private String name, description;
    private ItemType type;
    private Properties properties;

    public ItemModel(ObjectId oid) {
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new RessourceNotFoundException();
        }
        this.hydrate(ob);
    }

    public ItemModel() {
        this.properties = new Properties();
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.type = ItemType.fromString(ob.getString(TYPE));
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
        this.properties = new Properties((DBObject) ob.get(Properties.PROPERTIES));
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, id);
        }
        ob.append(TYPE, type.toString());
        ob.append(NAME, name);
        ob.append(DESCRIPTION, description);
        ob.append(Properties.PROPERTIES, this.properties.toDBObject());

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("item_model_id", this.id.toString());
        ob.put(NAME, this.name);
        ob.put(TYPE, this.type.toString());
        ob.put(DESCRIPTION, this.description);
        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Cette méthode ne doit pas être appelée directement pour le calcul des
     * combats Les propriétées doivent être obtenues par les méthodes get de la
     * classe <i>Item</i>
     *
     * @return
     * @see Item
     */
    public Properties getProperties() {
        return this.properties;
    }
}
