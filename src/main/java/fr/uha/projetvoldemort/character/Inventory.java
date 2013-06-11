/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemCategory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author bruno
 */
public final class Inventory {

    public static final String INVENTORY = "inventory";
    public static final String ID = "_id";
    
    private HashMap<ObjectId, Item> items;
    private ArrayList<InventoryListener> listeners;

    Inventory(DBObject ob) {
        this.items = new HashMap<ObjectId, Item>();
        this.listeners = new ArrayList<InventoryListener>();
        this.hydrate((BasicDBList) ob);
    }

    Inventory() {
        this.items = new HashMap<ObjectId, Item>();
        this.listeners = new ArrayList<InventoryListener>();
    }

    public void hydrate(BasicDBList obl) {
        Iterator<Object> it = obl.iterator();
        while (it.hasNext()) {
            ObjectId id = (ObjectId) it.next();
            this.items.put(id, new Item(id));
        }
    }

    protected void save() {
        Iterator<Item> it = this.items.values().iterator();
        while (it.hasNext()) {
            it.next().save();
        }
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo.
     */
    public DBObject toDBObject() {
        BasicDBList obl = new BasicDBList();

        Iterator<Item> it = this.items.values().iterator();
        while (it.hasNext()) {
            obl.add(it.next().getId());
        }

        return obl;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON.
     */
    public JSONArray toJSONArray() throws JSONException {
        JSONArray ob = new JSONArray();

        Iterator<Item> it = this.items.values().iterator();
        while (it.hasNext()) {
            ob.put(it.next().toJSONObject());
        }

        return ob;
    }
    
/**
 * Ajouter un listener. Utile pour mettre à jour les panoplies.
 * @param il Une panoplie par exemple.
 */
    public void addListener(InventoryListener il) {
        this.listeners.add(il);
    }
    
/**
 * Retirer un listener
 * @param il 
 */
    public void removeListener(InventoryListener il) {
        this.listeners.remove(il);
    }

        /**
     * Obtient la liste des items de l'inventaire correspondant à la catégorie
     * transmise en paramètre
     *
     * @param category Categorie des items
     * @return La liste des items de l'inventaure correspondant à la catégorie
     * transmise en paramètre
     */
    public ArrayList<Item> getItems(ItemCategory category) {
        ArrayList<Item> listItems = new ArrayList<Item>();

        Iterator<Item> it = this.items.values().iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getCategory().equals(category)) {
                listItems.add(item);
            }
        }

        return listItems;
    }

    /**
     * Obtient un item selon son id
     * @param id Id de l'item
     * @return L'item correspondant à l'id
     */
    public Item getItem(ObjectId id) {
        return this.items.get(id);
    }

    /**
     * Ajoute un item à l'inventaire.
     *
     * @param item L'item à ajouter.
     */
    public void add(Item item) {
        if (item.getId() == null) {
            item.save();
        }
        this.items.put(item.getId(), item);
    }

    /**
     * Retire un item de l'inventaire.
     * Informe les listeners.
     *
     * @param item L'item à retirer.
     * @see InventoryListener
     */
    public void remove(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (!this.items.containsValue(item)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }

        // TODO, supprimer de la BDD
        this.items.remove(item.getId());
        
        Iterator<InventoryListener> it = this.listeners.iterator();
        while (it.hasNext())
            it.next().remove(item);
    }

    public boolean contains(Item item) {
        return this.items.containsValue(item);
    }
}
