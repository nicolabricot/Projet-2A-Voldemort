/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

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
public final class Character {

    public static final String COLLECTION = "character";
    private static final String ID = "_id";
    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";
    private ObjectId id;
    private CharacterModel model;
    private Properties properties;
    private String name;
    private Equipment equipment;
    private Inventory inventory;

    public Character(ObjectId oid) {
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new RessourceNotFoundException();
        }
        this.hydrate(ob);
    }

    public Character(CharacterModel model) {
        this.model = model;
        this.properties = new Properties();
        this.equipment = new Equipment();
        this.inventory = new Inventory();
    }

    private void hydrate(BasicDBObject ob) {
        System.out.println(ob);
        this.id = ob.getObjectId(ID);
        this.model = new CharacterModel(ob.getObjectId(MODEL_ID));
        this.name = ob.getString(NAME);
        this.properties = new Properties((DBObject) ob.get(Properties.PROPERTIES));
        this.equipment = new Equipment((DBObject) ob.get(Equipment.EQUIPMENT));
        this.inventory = new Inventory((DBObject) ob.get(Inventory.INVENTORY));
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
        ob.append(NAME, this.name);
        ob.append(Properties.PROPERTIES, this.properties.toDBObject());
        ob.append(Equipment.EQUIPMENT, this.equipment.toDBObject());
        ob.append(Inventory.INVENTORY, this.inventory.toDBObject());

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = this.model.toJSONObject();
        ob.put("character_id", this.id.toString());
        ob.put(NAME, this.name);
        ob.put(Properties.ATTACK, this.getAttack());
        ob.put(Properties.DEFENSE, this.getDefense());
        ob.put(Properties.INITIATIVE, this.getInitiative());
        ob.put(Properties.LIFE, this.getLife());
        ob.put(Properties.LUCK, this.getLuck());
        ob.put(Properties.ROBUSTNESS, this.getRobustness());
        ob.put(Equipment.EQUIPMENT, this.equipment.toJSONObject());
        ob.put(Inventory.INVENTORY, this.inventory.toJSONArray());
        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob); // TODO : bug update
        this.id = ob.getObjectId(ID);
    }

    /**
     * Cette méthode ne doit pas être appelée directement pour le calcul des
     * combats Les propriétées doivent être obtenues par les méthodes get
     *
     * @return
     */
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getLife() {
        return this.properties.getLife();
    }
    
    /**
     * Obtient le total d'attaque.
     *
     * @return Le total d'attaque.
     */
    public int getAttack() {
        int attack = 0;
        attack += this.properties.getAttack();
        attack += this.equipment.getAttack();
        return attack;
    }

    /**
     * Obtient le total de défense.
     *
     * @return Le total de défense.
     */
    public int getDefense() {
        int defense = 0;
        defense += this.properties.getDefense();
        defense += this.equipment.getDefense();
        return defense;
    }

    /**
     * Obtient le total d'initiative.
     *
     * @return Le total d'initiative.
     */
    public int getInitiative() {
        int initiative = 0;
        initiative += this.properties.getInitiative();
        initiative += this.equipment.getInitiative();
        return initiative;
    }

    /**
     * Obtient le total de chance.
     *
     * @return Le total de chance.
     */
    public int getLuck() {
        int luck = 0;
        luck += this.properties.getLuck();
        luck += this.equipment.getLuck();
        return luck;
    }

    /**
     * Obtient le total de robustesse.
     *
     * @return Le total de robustesse.
     */
    public int getRobustness() {
        int robustness = 0;
        robustness += this.properties.getRobustness();
        robustness += this.equipment.getRobustness();
        return robustness;
    }
}
