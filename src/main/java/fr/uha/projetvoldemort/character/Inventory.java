/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.item.Item;
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

    Inventory(DBObject ob) {
        this.items = new HashMap<ObjectId, Item>();
        this.hydrate((BasicDBList) ob);
    }

    Inventory() {
        this.items = new HashMap<ObjectId, Item>();
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
        while (it.hasNext())
            it.next().save();
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
    
    public Item getItem(ObjectId id) {
        if (!this.items.containsKey(id)) throw new NotFoundException("Item not found in inventory.");
        
        return this.items.get(id);
    }
    
    /**
     * Ajoute un item à l'inventaire.
     *
     * @param item L'item à ajouter.
     */
    public void add(Item item) {
        if (item.getId() == null)
            item.save();
        this.items.put(item.getId(), item);
    }

    /**
     * Retire un item de l'inventaire.
     *
     * @param item L'item à retirer.
     */
    public void remove(Item item) {
        if (!this.items.containsValue(item)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }
        
        // TODO, supprimer de la BDD
        this.items.remove(item.getId());
    }
    
    public boolean contains(Item item) {
        return this.items.containsValue(item);
    }
}
