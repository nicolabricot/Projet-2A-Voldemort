/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class Panoply implements InventoryListener {

    public static final String COLLECTION = "panoply";
    private static final String ID = "_id";
    private static final String ITEMS = "items";
    private ObjectId id;
    private Inventory inventory;
    private ArrayList<Item> items;

    protected Panoply(Inventory inventory, ObjectId oid) {
        this.items = new ArrayList<Item>();
        this.inventory = inventory;

        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    protected Panoply(Inventory inventory) {
        this.items = new ArrayList<Item>();
        this.inventory = inventory;
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);

        BasicDBList listItems = (BasicDBList) ob.get("items");
        Iterator<Object> itItems = listItems.iterator();
        while (itItems.hasNext()) {
            ObjectId id = (ObjectId) itItems.next();
            this.items.add(this.inventory.getItem(id));
        }
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo.
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, this.id);
        }

        BasicDBList listItems = new BasicDBList();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            listItems.add(it.next().getId());
        }
        ob.append(ITEMS, listItems);

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON.
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();

        ob.append(ID, this.id.toString());

        JSONArray listItems = new JSONArray();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            listItems.put(it.next().toJSONObject());
        }
        ob.append(ITEMS, listItems);

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

    public void setItem(Item item) {
        if (!this.inventory.contains(item)) {
            throw new RuntimeException("Item doesn't belong to the character.");
        }

        this.items.add(item);

    }

    /**
     * Obtient le premier item de la panoplie correspondant au type transmis en
     * paramètre
     *
     * @param type Type de l'item
     * @return Le premier item de la panoplie correspondant au type transmis en
     * paramètre ou <code>null</code>
     */
    public Item getItem(ItemType type) {
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getType().equals(type)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Obtient la liste des items de la panoplie correspondant au type transmis
     * en paramètre
     *
     * @param type Type des items
     * @return La liste des items de la panoplie correspondant au type transmis
     * en paramètre
     */
    public ArrayList<Item> getItems(ItemType type) {
        ArrayList<Item> list = new ArrayList<Item>();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getType().equals(type)) {
                list.add(item);
            }
        }

        return list;
    }

    /**
     * Obtient la liste des items de la panoplie correspondant à la catégorie
     * transmise en paramètre
     *
     * @param category Categorie des items
     * @return La liste des items de la panoplie correspondant à la catégorie
     * transmise en paramètre
     */
    public ArrayList<Item> getItems(ItemCategory category) {
        ArrayList<Item> list = new ArrayList<Item>();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getCategory().equals(category)) {
                list.add(item);
            }
        }

        return list;
    }

    @Override
    public void remove(Item item) {
        this.inventory.remove(item);
    }
}
