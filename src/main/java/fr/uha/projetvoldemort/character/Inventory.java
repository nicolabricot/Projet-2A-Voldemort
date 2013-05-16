/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.character;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.item.Item;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author bruno
 */
public final class Inventory {
    
    public static final String INVENTORY = "inventory";
    
    private ArrayList<Item> items;

    Inventory(DBObject ob) {
        this.items = new ArrayList<Item>();
        this.hydrate((BasicDBList) ob);
    } 
    
    Inventory() {
        this.items = new ArrayList<Item>();
    }
    
    public void hydrate(BasicDBList obl) {
        Iterator<Object> it = obl.iterator();
        while (it.hasNext())
            this.items.add(new Item((DBObject) it.next()));
    }
    
    public DBObject toDBObject() {
        BasicDBList obl = new BasicDBList();
        
        Iterator<Item> it = this.items.iterator();
        while (it.hasNext())
            obl.add(it.next().toDBObject());
        
        return obl;
    }
    
    /**
     * Obtient la liste des items qui composent l'inventaire.
     * @return 
     * L'ArrayList des items.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Definit la liste des items qui composent l'inventaire.
     * @param items
     * L'ArrayList des items.
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    /**
     * Obtient un iterator sur la liste d'item.
     * @return 
     * Iterator sur la liste d'item.
     */
    public Iterator<Item> iterator() {
        return this.items.iterator();
    }
    
    /**
     * Ajoute un item à l'inventaire.
     * @param item 
     * L'item à ajouter.
     */
    public void add(Item item) {
        this.items.add(item);
    }
    
    /**
     * Retire un item de l'inventaire.
     * @param item 
     * L'item à retirer.
     */
    public void remove(Item item) {
        this.items.remove(item);
    }
    
}
