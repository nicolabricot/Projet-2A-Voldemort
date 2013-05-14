/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.character.Inventory;
import framework.item.Item;
import java.util.Iterator;

/**
 *
 * @author bruno
 */
public class InventoryView {
    
    private Inventory model;
    
    public InventoryView(Inventory model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("<ul>");
        
        Iterator<Item> it = this.model.iterator();
        while(it.hasNext()) {
            str.append("<li>Item :</li>");
            str.append(new ItemView(it.next()));
        }
        
        str.append("</ul>");
        
        return str.toString();
    }
    
}
