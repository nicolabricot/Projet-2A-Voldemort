/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import framework.item.ItemType;
import framework.item.Item;
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
    
    private void hydrate(BasicDBObject ob) {
        
       Iterator<Entry<String, Object>> it = ob.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            this.armor.put(ItemType.fromString(e.getKey()), new Item((ObjectId) e.getValue()));
        }
    }

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
    
    public Item get(ItemType item) {
        return this.armor.get(item);
    }
    
    public void set(Item item) {
        ItemType type = item.getType();
        
        if (type.isArmor())    
            this.armor.put(type, item);
    }
}
