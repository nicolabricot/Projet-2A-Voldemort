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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Equipment {

    public static final String EQUIPMENT = "equipment";
    private static final String ARM = "arm";
    private Armor armor;
    private Item arm;

    protected Equipment(DBObject ob) {
        hydrate((BasicDBObject) ob);
    }

    protected Equipment() {
        this.armor = new Armor();
    }

    private void hydrate(BasicDBObject ob) {
        this.armor = new Armor((BasicDBObject) ob.get(Armor.ARMOR));
        if (ob.containsField(ARM)) {
            this.arm = new Item((DBObject) ob.get(ARM));
        }
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        ob.append(Armor.ARMOR, this.armor.toDBObject());
        if (this.arm != null) {
            ob.append(ARM, this.arm.toDBObject());
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

        ob.append(Armor.ARMOR, this.armor.toJSONObject());
        if (this.arm != null) {
            ob.put(ARM, this.arm.toJSONObject());
        }

        return ob;
    }

    /**
     * S'équiper d'un item.
     *
     * @param item L'item.
     * @return L'ancien item.
     * @throws UnexpectedItemException Si l'item ne peut pas être équipé.
     */
    public Item setItem(Item item) throws UnexpectedItemException {
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
     * Définit l'armure.
     *
     * @param armor l'armure.
     */
    public void setArmor(Armor armor) {
        this.armor = armor;
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
        int attack = 0;
        attack += this.armor.getAttack();
        if (this.arm != null) {
            attack += this.arm.getAttack();
        }
        return attack;
    }

    /**
     * Obtient le total de défense des différents équipements
     *
     * @return Le total de défense des différents équipements
     */
    public int getDefense() {
        int defense = 0;
        defense += this.armor.getDefense();
        if (this.arm != null) {
            defense += this.arm.getDefense();
        }
        return defense;
    }

    /**
     * Obtient le total d'initative des différents équipements
     *
     * @return Le total d'initiative des différents équipements
     */
    public int getInitiative() {
        int initiative = 0;
        initiative += this.armor.getInitiative();
        if (this.arm != null) {
            initiative += this.arm.getInitiative();
        }
        return initiative;
    }

    /**
     * Obtient le total de chance des différents équipements
     *
     * @return Le total de chance des différents équipements
     */
    public int getLuck() {
        int luck = 0;
        luck += this.armor.getLuck();
        if (this.arm != null) {
            luck += this.arm.getLuck();
        }
        return luck;
    }

    /**
     * Obtient le total de robustesse des différents équipements
     *
     * @return Le total de robustesse des différents équipements
     */
    public int getRobustness() {
        int robustness = 0;
        robustness += this.armor.getAttack();
        if (this.arm != null) {
            robustness += this.arm.getRobustness();
        }
        return robustness;
    }
}
