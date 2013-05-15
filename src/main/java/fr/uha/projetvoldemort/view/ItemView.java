/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.view;

import fr.uha.projetvoldemort.item.Item;

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
        str.append("<li>Properties : (peuvent être différentes du modèle à cause de l'usure et de la customization d'item)</li>");
        str.append("<ul>");
        str.append("<li>Attack : ").append(this.model.getAttack()).append("</li>");
        str.append("<li>Defense : ").append(this.model.getDefense()).append("</li>");
        str.append("<li>Initiative : ").append(this.model.getInitiative()).append("</li>");
        str.append("<li>Luck : ").append(this.model.getLuck()).append("</li>");
        str.append("<li>Robustness : ").append(this.model.getRobustness()).append("</li>");
        str.append("</ul>");
        
        str.append("</ul>");
  
        return str.toString();
    }
    
}
