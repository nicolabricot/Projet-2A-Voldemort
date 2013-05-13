/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import framework.item.ItemType;
import framework.item.Item;
import framework.item.UnexpectedItemException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public final class Armor {
        
    private EnumMap<ItemType, Item> armor;

    protected Armor(BasicDBObject ob) {
        this.armor = new EnumMap<ItemType, Item>(ItemType.class);
        this.hydrate(ob);
    }

    protected Armor() {
        this.armor = new EnumMap<ItemType, Item>(ItemType.class);
    }
    
    /**
     * Construit l'objet à partir d'un objet JSON
     */
    private void hydrate(BasicDBObject ob) {
        
       Iterator<Entry<String, Object>> it = ob.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            this.armor.put(ItemType.fromString(e.getKey()), new Item((ObjectId) e.getValue()));
        }
    }

    /**
     * Génère l'objet sous forme d'un objet JSON
     * @return 
     * Objet JSON
     */
    public BasicDBObject toBasicDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        Iterator<Entry<ItemType, Item>> it = this.armor.entrySet().iterator();
        while (it.hasNext()) {
            Entry<ItemType, Item> e = it.next();
            if (e.getValue() != null)
                ob.append(e.getKey().toString(), e.getValue().getId());
            else
                System.out.println("Mouhouhouahhahah tu m'auras pas, je suis plus malin que toi");
        }
  
        return ob;
    }
    
    /**
     * Permet d'obtenir un item équipant l'armure
     * @param itemType
     * Type de l'item à obtenir
     * @return 
     * L'item associé au type ou <i>null</i>
     * 
     * @see ItemType
     * @see Item
     */
    public Item get(ItemType itemType) {
        return this.armor.get(itemType);
    }
    
    /**
     * Place un item sur l'armure
     * @param item
     * Un item pouvant être placé sur l'armure
     * @return
     * L'item précédemment équipé ou <i>null</i>
     * @throws UnexpectedItemException
     * Si l'item ne peut pas être placé sur l'armure à cause de sont type ou du niveau minimum requis
     * 
     * @see Item
     * @see UnexectedItemException
     */
    public Item set(Item item) throws UnexpectedItemException {
        ItemType type = item.getType();
        
        Item previous = null;
        if (type.isArmor()) {
            previous = this.armor.get(type);
            this.armor.put(type, item);            
        }
        else {
            StringBuilder str = new StringBuilder();
            str.append("Item \"");
            str.append(item.getName());
            str.append("\" of type \"");
            str.append(item.getType());
            str.append("\" cannot be placed on an Armor");
            throw new UnexpectedItemException(str.toString());
        }
        
        return previous;
    }
}
