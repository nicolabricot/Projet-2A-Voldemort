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
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Armor {

    public static final String ARMOR = "armor";
    private EnumMap<ItemType, Item> armor;
    private Inventory inventory;

    protected Armor(Inventory inventory, DBObject ob) {
        this.inventory = inventory;
        this.armor = new EnumMap<ItemType, Item>(ItemType.class);
        this.hydrate((BasicDBObject) ob);
    }

    protected Armor(Inventory inventory) {
        this.inventory = inventory;
        this.armor = new EnumMap<ItemType, Item>(ItemType.class);
    }

    private void hydrate(BasicDBObject ob) {
        Iterator<Entry<String, Object>> it = ob.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            this.armor.put(ItemType.fromString(e.getKey()), this.inventory.getItem((ObjectId) e.getValue()));
        }
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo.
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        Iterator<Entry<ItemType, Item>> it = this.armor.entrySet().iterator();
        while (it.hasNext()) {
            Entry<ItemType, Item> e = it.next();
                ob.append(e.getKey().toString(), e.getValue().getId());
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

        Iterator<Entry<ItemType, Item>> it = this.armor.entrySet().iterator();
        while (it.hasNext()) {
            Entry<ItemType, Item> e = it.next();
                ob.put(e.getKey().toString(), e.getValue().toJSONObject());
        }

        return ob;
    }

    /**
     * Permet d'obtenir un item équipant l'armure.
     *
     * @param itemType Type de l'item à obtenir.
     * @return L'item ou <code>null</code>.
     *
     * @see ItemType
     * @see Item
     */
    public Item getItem(ItemType itemType) {
        return this.armor.get(itemType);
    }

    /**
     * Place un item sur l'armure.
     *
     * @param item Un item pouvant être placé sur l'armure.
     * @return L'item précédemment équipé ou <code>null</code>.
     * @throws UnexpectedItemException Si l'item ne peut pas être équipé.
     *
     * @see Item
     * @see UnexectedItemException
     */
    public Item setItem(Item item) throws UnexpectedItemException {
        if (!this.inventory.contains(item)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }
        
        ItemType type = item.getModel().getType();

        Item previous = null;
        if (type.isArmor()) {
            previous = this.armor.get(type);
            this.armor.put(type, item);
        } else {
            StringBuilder str = new StringBuilder();
            str.append("Item \"");
            str.append(item.getModel().getName());
            str.append("\" of type \"");
            str.append(item.getModel().getType());
            str.append("\" cannot be placed on an Armor");
            throw new UnexpectedItemException(str.toString());
        }

        return previous;
    }

    /**
     * Obtient le total d'attaque des différents éléments d'armure.
     *
     * @return Le total d'attaque des différents élements d'armure.
     */
    public int getAttack() {
        int attack = 0;
        Iterator<Item> it = this.armor.values().iterator();
        while (it.hasNext()) {
            attack += it.next().getAttack();
        }
        return attack;
    }

    /**
     * Obtient le total de défense des différents éléments d'armure.
     *
     * @return Le total de défense des différents éléments d'armure.
     */
    public int getDefense() {
        int defense = 0;
        Iterator<Item> it = this.armor.values().iterator();
        while (it.hasNext()) {
            defense += it.next().getDefense();
        }
        return defense;
    }

    /**
     * Obtient le total d'initiative des différents éléments d'armure.
     *
     * @return Le total d'initiative des différents éléments d'armure.
     */
    public int getInitiative() {
        int initiative = 0;
        Iterator<Item> it = this.armor.values().iterator();
        while (it.hasNext()) {
            initiative += it.next().getInitiative();
        }
        return initiative;
    }

    /**
     * Obtient le total de chance des différents éléments d'armure.
     *
     * @return Le total de chance des différents éléments d'armure.
     */
    public int getLuck() {
        int luck = 0;
        Iterator<Item> it = this.armor.values().iterator();
        while (it.hasNext()) {
            luck += it.next().getLuck();
        }
        return luck;
    }

    /**
     * Obtient le total de robustesse des différents éléments d'armure.
     *
     * @return Le total de robustesse des différents éléments d'armure.
     */
    public int getRobustness() {
        int robustness = 0;
        Iterator<Item> it = this.armor.values().iterator();
        while (it.hasNext()) {
            robustness += it.next().getRobustness();
        }
        return robustness;
    }
}
