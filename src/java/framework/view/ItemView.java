/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.item.Item;

/**
 *
 * @author bruno
 */
public class ItemView {
    
    private Item model;
    
    public ItemView(Item model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
  
        StringBuilder str = new StringBuilder();
        str.append("<ul>");
        str.append("<li>Model :</li>");
        str.append(new ItemModelView(this.model.getModel()));
        
        str.append("<li>Properties: </li>");
        str.append(new PropertiesView(this.model.getProperties()));
        str.append("</ul>");
  
        return str.toString();
    }
    
}
