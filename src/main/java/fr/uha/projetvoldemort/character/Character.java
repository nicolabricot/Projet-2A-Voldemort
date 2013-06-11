/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Character {

    public static final String COLLECTION = "character";
    private static final String ID = "_id";
    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";
    private static final String ATTRIBUTES = "attributes";
    private static final String PANOPLIES = "panoplies";
    private static final String ACTIVE_PANOPLY = "active_panoply";
    private static final String FACTION = "faction";
    private ObjectId id;
    private CharacterModel model;
    private String name;
    private EnumMap<CharacterAttribute, Integer> attributes;
    private Inventory inventory;
    private HashMap<ObjectId, Panoply> panoplies;
    private Panoply activePanoply;
    private Faction faction;

    public Character(ObjectId oid) {
        this.attributes = new EnumMap<CharacterAttribute, Integer>(CharacterAttribute.class);
        this.panoplies = new HashMap<ObjectId, Panoply>();

        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public Character(CharacterModel model) {
        this.attributes = new EnumMap<CharacterAttribute, Integer>(CharacterAttribute.class);
        this.panoplies = new HashMap<ObjectId, Panoply>();

        this.model = model;
        this.inventory = new Inventory();
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.model = new CharacterModel(ob.getObjectId(MODEL_ID));
        this.name = ob.getString(NAME);

        BasicDBObject obAttributes = (BasicDBObject) ob.get(ATTRIBUTES);
        Iterator<String> itAttributes = obAttributes.keySet().iterator();
        while (itAttributes.hasNext()) {
            String key = itAttributes.next();
            Integer value = obAttributes.getInt(key);
            this.attributes.put(CharacterAttribute.fromString(key), value);
        }

        this.inventory = new Inventory((DBObject) ob.get(Inventory.INVENTORY));

        BasicDBList listPanoplies = (BasicDBList) ob.get(PANOPLIES);
        Iterator<Object> itPanoplies = listPanoplies.iterator();
        while (itPanoplies.hasNext()) {
            ObjectId pid = (ObjectId) itPanoplies.next();
            Panoply p = new Panoply(this.inventory, pid);
            this.panoplies.put(pid, p);
            this.inventory.addListener(p);
        }

        this.activePanoply = this.panoplies.get((ObjectId) ob.get(ACTIVE_PANOPLY));

        this.faction = new Faction((ObjectId) ob.get(FACTION));
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo.
     */
    public DBObject toDBObject() {
        if (this.activePanoply == null) {
            throw new RuntimeException("No active panoply definded.");
        }

        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, this.id);
        }

        ob.append(MODEL_ID, this.model.getId());
        ob.append(NAME, this.name);

        BasicDBObject obAttributes = new BasicDBObject();
        Iterator<Entry<CharacterAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
        while (itAttributes.hasNext()) {
            Entry<CharacterAttribute, Integer> attribute = itAttributes.next();
            obAttributes.append(attribute.getKey().toString(), attribute.getValue());
        }
        ob.append(ATTRIBUTES, obAttributes);

        ob.append(Inventory.INVENTORY, this.inventory.toDBObject());

        BasicDBList listPanoplies = new BasicDBList();
        Iterator<Panoply> itPanoplies = this.panoplies.values().iterator();
        while (itPanoplies.hasNext()) {
            listPanoplies.add(itPanoplies.next().getId());
        }
        ob.append(PANOPLIES, listPanoplies);

        ob.append(ACTIVE_PANOPLY, this.activePanoply.getId());

        ob.append(FACTION, this.faction.getId());

        return ob;
    }

    public Panoply createPanoply() {
        Panoply p = new Panoply(this.inventory);
        p.save();
        this.panoplies.put(p.getId(), p);
        this.inventory.addListener(p);
        return p;
    }

    public void setActivePanoply(Panoply pa) {
        if (!this.panoplies.containsValue(pa)) {
            StringBuilder str = new StringBuilder();
            str.append("Panoply ");
            str.append(pa.getId().toString());
            str.append(" doesn't belong to ");
            str.append(this.getId().toString());
            str.append(".");
            throw new NotFoundException(str.toString());
        }

        this.activePanoply = pa;
    }

    public JSONObject toJSONObject(boolean full) throws JSONException {
        JSONObject ob = new JSONObject();

        if (full) {
            ob.put("id", this.id.toString());
            ob.put("model", this.model.toJSONObject());

            ob.put(NAME, this.name);

            JSONObject obAttributes = new JSONObject();
            Iterator<Entry<CharacterAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
            while (itAttributes.hasNext()) {
                Entry<CharacterAttribute, Integer> attribute = itAttributes.next();
                obAttributes.put(attribute.getKey().toString(), attribute.getValue());
            }
            ob.put(ATTRIBUTES, obAttributes);

            ob.put(Inventory.INVENTORY, this.inventory.toJSONArray());

            JSONArray a = new JSONArray();
            Iterator<Panoply> it = this.panoplies.values().iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }
            ob.put(PANOPLIES, a);

            ob.put(ACTIVE_PANOPLY, this.activePanoply.toJSONObject());

            ob.put(FACTION, this.faction.toJSONObject());
        } else {

            ob.put("id", this.id.toString());
            ob.put("name", this.name);

            JSONObject obAttributes = new JSONObject();
            Iterator<Entry<CharacterAttribute, Integer>> itAttributes = this.attributes.entrySet().iterator();
            while (itAttributes.hasNext()) {
                Entry<CharacterAttribute, Integer> attribute = itAttributes.next();
                obAttributes.put(attribute.getKey().toString(), attribute.getValue());
            }
            ob.put(ATTRIBUTES, obAttributes);
        }

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        return this.toJSONObject(true);
    }

    public void save() {
        this.inventory.save();

        Iterator<Panoply> it = this.panoplies.values().iterator();
        while (it.hasNext()) {
            it.next().save();
        }

        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Character.save: " + ob);
    }

    public CharacterModel getModel() {
        return this.model;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Panoply getActivePanoply() {
        return this.activePanoply;
    }

    public boolean isDead() {
        return (this.getAttribute(CharacterAttribute.LIFE) < 1);
    }

    public int getAttribute(CharacterAttribute attribute) {
        if (!this.attributes.containsKey(attribute)) {
            StringBuilder str = new StringBuilder();
            str.append(this.getName());
            str.append(" does not contain attribute ");
            str.append(attribute.toString());
            Logger.getLogger(Character.class.getName()).log(Level.WARNING, str.toString());
            return 0;
        }
        return this.attributes.get(attribute);
    }

    public void setAttribute(CharacterAttribute attribute, int value) {
        this.attributes.put(attribute, value);
    }

    public Collection<Panoply> getPanoplies() {
        return this.panoplies.values();
    }

    public Panoply getPanoply(ObjectId id) {
        if (!this.panoplies.containsKey(id)) {
            StringBuilder str = new StringBuilder();
            str.append("Panoply ");
            str.append(id.toString());
            str.append(" doesn't belong to ");
            str.append(this.getId().toString());
            str.append(".");
            throw new NotFoundException(str.toString());
        }
        return this.panoplies.get(id);
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Faction getFaction() {
        return this.faction;
    }
}
