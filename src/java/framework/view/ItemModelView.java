/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.item.ItemModel;

/**
 *
 * @author bruno
 */
public class ItemModelView {

    private ItemModel model;

    public ItemModelView(ItemModel model) {
        this.model = model;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append("<ul>");
        str.append("<li>Id : ").append(this.model.getId()).append("</li>");
        str.append("<li>Type : ").append(this.model.getType()).append("</li>");
        str.append("<li>Name : ").append(this.model.getName()).append("</li>");
        str.append("<li>Description : ").append(this.model.getDescription()).append("</li>");
        str.append("</ul>");

        return str.toString();

    }
}
