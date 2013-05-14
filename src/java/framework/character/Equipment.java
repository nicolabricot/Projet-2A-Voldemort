/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.item.Item;
import framework.item.ItemType;
import framework.item.UnexpectedItemException;

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
        if (ob.containsField(ARM)) this.arm = new Item((DBObject) ob.get(ARM));
    }
    
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
    
        ob.append(Armor.ARMOR, this.armor.toDBObject());
        if (this.arm != null) ob.append(ARM, this.arm.toDBObject());
         
        return ob;
    }
    
    public Item setItem(Item item) throws UnexpectedItemException {
        Item previous = null;
        
        if (item.getModel().getType().isArmor())
            previous = this.armor.setItem(item);
        else if (item.getModel().getType().isArm())
            previous = this.setArm(item);
        else {
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
    
    public Item getItem(ItemType itemType) {
        Item item = null;
        
        if (itemType.isArmor())
            item = this.armor.getItem(itemType);
        else if (itemType.isArm())
            item = this.arm;
        
        return item;
    }
    
    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }
    
    public Item getArm() {
        return arm;
    }

    public Item setArm(Item arm) throws UnexpectedItemException {
        Item previous = null;
        
        if (arm.getModel().getType().isArm()) {
            previous = this.arm;
            this.arm = arm;
        }
        else {
            StringBuilder str = new StringBuilder();
            str.append("Item \"");
            str.append(arm.getModel().getName());
            str.append("\" of type \"");
            str.append(arm.getModel().getType());
            str.append("\" cannot be used as an Arm");
            throw new UnexpectedItemException(str.toString());
        }
        
        return null;
    }
}
