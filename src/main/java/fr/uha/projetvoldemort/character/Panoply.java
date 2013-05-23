/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.item.UnexpectedItemException;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Panoply {

    public static final String COLLECTION = "panoply";
    
    private static final String ID = "_id";
    private static final String ARM = "arm";
    
    private ObjectId id;
    private Armor armor;
    private Item arm;
    private Inventory inventory;

    protected Panoply(Inventory inventory, ObjectId oid) {
        this.inventory = inventory;
        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    protected Panoply(Inventory inventory) {
        this.inventory = inventory;
        this.armor = new Armor(this.inventory);
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.armor = new Armor(this.inventory, (BasicDBObject) ob.get(Armor.ARMOR));
        if (ob.containsField(ARM)) {
            this.arm = this.inventory.getItem((ObjectId) ob.get(ARM));
        }
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo.
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        ob.append(Armor.ARMOR, this.armor.toDBObject());
        if (this.arm != null) {
            ob.append(ARM, this.arm.getId());
        }

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON.
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();

        ob.append(Armor.ARMOR, this.armor.toJSONObject());
        if (this.arm != null) {
            ob.put(ARM, this.arm.toJSONObject());
        }

        return ob;
    }
    
    protected void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Panoply.save: " + ob);
    }
    
    public ObjectId getId() {
        return this.id;
    }

    /**
     * S'équiper d'un item.
     *
     * @param item L'item.
     * @return L'ancien item.
     * @throws UnexpectedItemException Si l'item ne peut pas être équipé.
     */
    public Item setItem(Item item) throws UnexpectedItemException {
         if (!this.inventory.contains(item)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }
        
        Item previous = null;

        if (item.getModel().getType().isArmor()) {
            previous = this.armor.setItem(item);
        } else if (item.getModel().getType().isArm()) {
            previous = this.setArm(item);
        } else {
            StringBuilder str = new StringBuilder();
            str.append("Item \"");
            str.append(arm.getModel().getName());
            str.append("\" of type \"");
            str.append(arm.getModel().getType());
            str.append("\" cannot be used in the equipment.");
            throw new UnexpectedItemException(str.toString());
        }

        return previous;
    }

    /**
     * Obtient un item selon son type.
     *
     * @param itemType Le type de l'item à obtenir.
     * @return L'item ou <i>null</i>
     */
    public Item getItem(ItemType itemType) {
        Item item = null;

        if (itemType.isArmor()) {
            item = this.armor.getItem(itemType);
        } else if (itemType.isArm()) {
            item = this.arm;
        }

        return item;
    }

    /**
     * Obtient l'armure.
     *
     * @return l'armure.
     */
    public Armor getArmor() {
        return armor;
    }

    /**
     * Obtient l'arme
     *
     * @return l'arme.
     */
    public Item getArm() {
        return arm;
    }

    /**
     * Définit l'arme.
     *
     * @param arm L'arme.
     * @return L'ancienne arme.
     * @throws UnexpectedItemException Si l'arme ne peut pas être équipée.
     */
    public Item setArm(Item arm) throws UnexpectedItemException {
        if (!this.inventory.contains(arm)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }
        
        Item previous = null;

        if (arm.getModel().getType().isArm()) {
            previous = this.arm;
            this.arm = arm;
        } else {
            StringBuilder str = new StringBuilder();
            str.append("Item \"");
            str.append(arm.getModel().getName());
            str.append("\" of type \"");
            str.append(arm.getModel().getType());
            str.append("\" cannot be used as an Arm");
            throw new UnexpectedItemException(str.toString());
        }

        return previous;
    }

    /**
     * Obtient le total d'attaque des différents équipements
     *
     * @return Le total d'attaque des différents équipements
     */
    public int getAttack() {
        int val = 0;
        val += this.armor.getAttack();
        if (this.arm != null) {
            val += this.arm.getAttack();
        }
        return val;
    }

    /**
     * Obtient le total de défense des différents équipements
     *
     * @return Le total de défense des différents équipements
     */
    public int getDefense() {
        int val = 0;
        val += this.armor.getDefense();
        if (this.arm != null) {
            val += this.arm.getDefense();
        }
        return val;
    }

    /**
     * Obtient le total d'initative des différents équipements
     *
     * @return Le total d'initiative des différents équipements
     */
    public int getInitiative() {
        int val = 0;
        val += this.armor.getInitiative();
        if (this.arm != null) {
            val += this.arm.getInitiative();
        }
        return val;
    }

    /**
     * Obtient le total de chance des différents équipements
     *
     * @return Le total de chance des différents équipements
     */
    public int getLuck() {
        int val = 0;
        val += this.armor.getLuck();
        if (this.arm != null) {
            val += this.arm.getLuck();
        }
        return val;
    }

   public int getStrength() {
        int val = 0;
        val += this.armor.getStrength();
        if (this.arm != null) {
            val += this.arm.getStrength();
        }
        return val;
    }

    public int getIntelligence() {
        int val = 0;
        val += this.armor.getIntelligence();
        if (this.arm != null) {
            val += this.arm.getIntelligence();
        }
        return val;
    }

    public int getAbility() {
        int val = 0;
        val += this.armor.getAbility();
        if (this.arm != null) {
            val += this.arm.getAbility();
        }
        return val;
    }

    public int getStealth() {
        int val = 0;
        val += this.armor.getStealth();
        if (this.arm != null) {
            val += this.arm.getStealth();
        }
        return val;
    }

    public int getAgility() {
        int val = 0;
        val += this.armor.getAgility();
        if (this.arm != null) {
            val += this.arm.getAgility();
        }
        return val;
    }
}
