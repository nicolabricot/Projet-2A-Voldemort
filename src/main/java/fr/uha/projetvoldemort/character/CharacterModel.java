/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.ressource.RessourceNotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class CharacterModel {

    public static final String COLLECTION = "character_model";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private ObjectId id;
    private String name, description;

    public CharacterModel(ObjectId oid) {
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new RessourceNotFoundException();
        }
        this.hydrate(ob);
    }

    public CharacterModel() {
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
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
        ob.append(NAME, this.name);
        ob.append(DESCRIPTION, this.description);

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("class_id", this.id.toString());
        ob.put("class", this.name);
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
