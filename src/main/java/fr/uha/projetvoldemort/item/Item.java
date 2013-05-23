/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.Attributes;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
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
    private static final String USAGE = "usage";
    private ItemModel model;
    private ObjectId id;
    private ItemUsage usage;

    public Item(ObjectId id) {
        Resources res = Resources.getInstance();
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
        if (ob.containsField(USAGE)) {
            this.usage = ItemUsage.fromString(ob.getString(USAGE));
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

        if (usage != null) {
            ob.put(USAGE, this.usage.toString());
        }

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
        ob.put(Attributes.ATTACK, this.getAttack());
        ob.put(Attributes.DEFENSE, this.getDefense());
        ob.put(Attributes.INITIATIVE, this.getInitiative());
        ob.put(Attributes.LUCK, this.getLuck());
        ob.put(Attributes.STRENGTH, this.getStrength());
        ob.put(Attributes.INTELLIGENCE, this.getIntelligence());
        ob.put(Attributes.AGILITY, this.getAgility());
        ob.put(Attributes.STEALTH, this.getStealth());
        ob.put(Attributes.ABILITY, this.getAbility());

        if (usage != null) {
            ob.put(USAGE, this.usage.toString());
        }

        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Item.save: " + ob);
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setUsage(ItemUsage usage) {
        this.usage = usage;
    }

    public ItemUsage getUsage() {
        return this.usage;
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
        return this.model.getAttributes().getAttack();
    }

    /**
     * Obtient le total de défense de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total de défense
     */
    public int getDefense() {
        return this.model.getAttributes().getDefense();
    }

    /**
     * Obtient le total d'initiative de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total d'initiative
     */
    public int getInitiative() {
        return this.model.getAttributes().getInitiative();
    }

    /**
     * Obtient le total de chance de l'item (en fonction de l'usure et des items
     * pouvant ou non le constituer)
     *
     * @return Le total de chance
     */
    public int getLuck() {
        return this.model.getAttributes().getLuck();
    }

    public int getStrength() {
        return this.model.getAttributes().getStrength();
    }

    public int getIntelligence() {
        return this.model.getAttributes().getIntelligence();
    }

    public int getAbility() {
        return this.model.getAttributes().getAbility();
    }

    public int getStealth() {
        return this.model.getAttributes().getStealth();
    }

    public int getAgility() {
        return this.model.getAttributes().getAgility();
    }
}
