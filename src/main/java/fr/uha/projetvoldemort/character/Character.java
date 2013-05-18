/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.Attributes;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import java.util.HashMap;
import java.util.Iterator;
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
    private static final String PANOPLIES = "panoplies";
    private static final String ACTIVE_PANOPLY = "active_panoply";
    private ObjectId id;
    private CharacterModel model;
    private Attributes attributes;
    private String name;
    private HashMap<ObjectId, Panoply> panoplies;
    private Panoply activePanoply;
    private Inventory inventory;

    public Character(ObjectId oid) {
        this.panoplies = new HashMap<ObjectId, Panoply>();
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public Character(CharacterModel model) {
        this.model = model;
        this.attributes = new Attributes();
        this.inventory = new Inventory();
        this.panoplies = new HashMap<ObjectId, Panoply>();
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.model = new CharacterModel(ob.getObjectId(MODEL_ID));
        this.name = ob.getString(NAME);
        this.attributes = new Attributes((DBObject) ob.get(Attributes.ATTRIBUTES));
        this.inventory = new Inventory((DBObject) ob.get(Inventory.INVENTORY));
        BasicDBList list = (BasicDBList) ob.get(PANOPLIES);
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            ObjectId id = (ObjectId) it.next();
            this.panoplies.put(id, new Panoply(this.inventory, id));
        }
        this.activePanoply = this.panoplies.get((ObjectId) ob.get(ACTIVE_PANOPLY));


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
        ob.append(Attributes.ATTRIBUTES, this.attributes.toDBObject());
        ob.append(Inventory.INVENTORY, this.inventory.toDBObject());

        BasicDBList list = new BasicDBList();
        Iterator<Panoply> it = this.panoplies.values().iterator();
        
        while (it.hasNext()) {
            list.add(it.next().getId());
        }
        
        ob.append(PANOPLIES, list);
        ob.append(ACTIVE_PANOPLY, this.activePanoply.getId());

        return ob;
    }

    public Panoply createPanoply() {
        Panoply p = new Panoply(this.inventory);
        p.save();
        this.panoplies.put(p.getId(), p);
        return p;
    }

    public void setActivePanoply(Panoply pa) {
        if (!this.panoplies.containsValue(pa)) {
            throw new RuntimeException("Panoply doesn't belong to the character.");
        }
        
        this.activePanoply = pa;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("id", this.id.toString());
        ob.put(NAME, this.name);
        ob.put(Attributes.ATTACK, this.getAttack());
        ob.put(Attributes.DEFENSE, this.getDefense());
        ob.put(Attributes.INITIATIVE, this.getInitiative());
        ob.put(Attributes.LIFE, this.getLife());
        ob.put(Attributes.LUCK, this.getLuck());
        ob.put(Attributes.STRENGTH, this.getStrength());
        ob.put(Attributes.INTELLIGENCE, this.getIntelligence());
        ob.put(Attributes.AGILITY, this.getAgility());
        ob.put(Attributes.STEALTH, this.getStealth());
        ob.put(Attributes.ABILITY, this.getAbility());
        ob.put(Inventory.INVENTORY, this.inventory.toJSONArray());
        ob.put(ACTIVE_PANOPLY, this.activePanoply.toJSONObject());
        
        ob.put("model", this.model.toJSONObject());
        
        JSONArray a = new JSONArray();
        Iterator<Panoply> it = this.panoplies.values().iterator();
        while (it.hasNext()) {
            a.put(it.next().toJSONObject());
        }
        ob.put(PANOPLIES, a);
        
        return ob;
    }

    public void save() {
        this.inventory.save();
        
        Iterator<Panoply> it = this.panoplies.values().iterator();
        while (it.hasNext())
            it.next().save();
        
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Character.save: " + ob);
    }

    /**
     * Cette méthode ne doit pas être appelée directement pour le calcul des
     * combats Les propriétées doivent être obtenues par les méthodes get
     *
     * @return
     */
    public Attributes getAttributes() {
        return this.attributes;
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

    public int getLife() {
        return this.attributes.getLife();
    }

    /**
     * Obtient le total d'attaque.
     *
     * @return Le total d'attaque.
     */
    public int getAttack() {
        int val = 0;
        val += this.attributes.getAttack();
        val += this.activePanoply.getAttack();
        return val;
    }

    /**
     * Obtient le total de défense.
     *
     * @return Le total de défense.
     */
    public int getDefense() {
        int val = 0;
        val += this.attributes.getDefense();
        val += this.activePanoply.getDefense();
        return val;
    }

    /**
     * Obtient le total d'initiative.
     *
     * @return Le total d'initiative.
     */
    public int getInitiative() {
        int val = 0;
        val += this.attributes.getInitiative();
        val += this.activePanoply.getInitiative();
        return val;
    }

    /**
     * Obtient le total de chance.
     *
     * @return Le total de chance.
     */
    public int getLuck() {
        int val = 0;
        val += this.attributes.getLuck();
        val += this.activePanoply.getLuck();
        return val;
    }

   public int getStrength() {
        int val = 0;
        val += this.attributes.getStrength();
        val += this.activePanoply.getStrength();
        return val;
    }

    public int getIntelligence() {
        int val = 0;
        val += this.attributes.getIntelligence();
        val += this.activePanoply.getIntelligence();
        return val;
    }

    public int getAbility() {
        int val = 0;
        val += this.attributes.getAbility();
        val += this.activePanoply.getAbility();
        return val;
    }

    public int getStealth() {
        int val = 0;
        val += this.attributes.getStealth();
        val += this.activePanoply.getStealth();
        return val;
    }

    public int getAgility() {
        int val = 0;
        val += this.attributes.getAgility();
        val += this.activePanoply.getAgility();
        return val;
    }
}
