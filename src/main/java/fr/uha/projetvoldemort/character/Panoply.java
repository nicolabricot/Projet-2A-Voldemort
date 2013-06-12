/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.resource.Resources;
import fr.uha.projetvoldemort.exception.NotAllowedException;
import java.util.ArrayList;
import java.util.Iterator;
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
public final class Panoply implements InventoryListener {

    public static final String COLLECTION = "panoply";
    private static final String ID = "_id";
    private static final String ITEMS = "items";
    private ObjectId id;
    private Inventory inventory;
    private ArrayList<Item> items;
    //private EnumMap<ItemType, Item> items;

    protected Panoply(Inventory inventory, ObjectId oid) {
        this.items = new ArrayList<Item>();
        //this.items = new EnumMap<ItemType, Item>(ItemType.class);
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
        //this.items = new EnumMap<ItemType, Item>(ItemType.class);
        this.inventory = inventory;
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        BasicDBList listItems = (BasicDBList) ob.get(ITEMS);
        Iterator<Object> itItems = listItems.iterator();
        while (itItems.hasNext()) {
            ObjectId oid = (ObjectId) itItems.next();
            this.items.add(this.inventory.getItem(oid));
            //Item item = this.inventory.getItem(oid);
            //this.items.put(item.getType(), item);
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

        ob.put(ID, this.id.toString());

        JSONArray listItems = new JSONArray();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            listItems.put(it.next().toJSONObject());
        }
        ob.put(ITEMS, listItems);

        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("Panoply.save: " + ob);
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setItem(Item item) {
        if (!this.inventory.contains(item)) {
            StringBuilder str = new StringBuilder();
            str.append("Item ");
            str.append(item.getId().toString());
            str.append(" does not come from the inventory.");
            throw new NotAllowedException(str.toString());
        }

        if (this.items.contains(item)) {
            StringBuilder str = new StringBuilder();
            str.append("Item ");
            str.append(item.getId().toString());
            str.append(" is already in the panoply ");
            str.append(this.getId().toString());
            str.append(".");
            throw new NotAllowedException(str.toString());
        }

        this.items.add(item);
        //this.items.put(item.getType(), item);

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
        /*
        if (this.items.containsKey(type))
            return this.items.get(type);
        */
        StringBuilder str = new StringBuilder();
        str.append("Inventory does not contain an item of type ");
        str.append(type.toString());
        Logger.getLogger(Panoply.class.getName()).log(Level.WARNING, str.toString());
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
        //list.add(this.items.get(type));
        
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

    public ArrayList<Item> getItems() {
        ArrayList<Item> a = new ArrayList<Item>();
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext()) {
            a.add(it.next());
        }
        return a;
    }

    @Override
    public void remove(Item item) {
        if (!this.items.contains(item)) {
            StringBuilder str = new StringBuilder();
            str.append("Item ");
            str.append(item.getId().toString());
            str.append(" is not ine the panoply ");
            str.append(this.getId().toString());
            str.append(".");
            throw new NotFoundException(str.toString());
        }

        this.items.remove(item);
    }
}
