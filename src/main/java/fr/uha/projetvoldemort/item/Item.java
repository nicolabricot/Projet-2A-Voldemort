/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.Properties;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import org.bson.types.ObjectId;
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
    private ItemModel model;
    private ObjectId id;
    // TODO : usure
    // TODO : custimzation d'item

    public Item(ObjectId id) {
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(id);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public Item(ItemModel model) {
        this.model = model;
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.model = new ItemModel(ob.getObjectId(MODEL_ID));
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) ob.append(ID, this.id);
        ob.append(MODEL_ID, this.model.getId());

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
        ob.put(Properties.ATTACK, this.getAttack());
        ob.put(Properties.DEFENSE, this.getDefense());
        ob.put(Properties.INITIATIVE, this.getInitiative());
        ob.put(Properties.LUCK, this.getLuck());
        ob.put(Properties.ROBUSTNESS, this.getRobustness());
        return ob;
    }
    
    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Item.save: " + ob);
    }
    
    public ObjectId getId() {
        return this.id;
    }

    public ItemModel getModel() {
        return this.model;
    }

    /**
     * Obtient le total d'attaque de l'item (en fonction de l'usure et des items
     * pouvant ou non le constituer)
     *
     * @return Le total d'attaque
     */
    public int getAttack() {
        return this.model.getProperties().getAttack();
    }

    /**
     * Obtient le total de défense de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total de défense
     */
    public int getDefense() {
        return this.model.getProperties().getDefense();
    }

    /**
     * Obtient le total d'initiative de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total d'initiative
     */
    public int getInitiative() {
        return this.model.getProperties().getInitiative();
    }

    /**
     * Obtient le total de chance de l'item (en fonction de l'usure et des items
     * pouvant ou non le constituer)
     *
     * @return Le total de chance
     */
    public int getLuck() {
        return this.model.getProperties().getLuck();
    }

    /**
     * Obtient le total de robustesse de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total de robustesse
     */
    public int getRobustness() {
        return this.model.getProperties().getRobustness();
    }
}
