/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.character.Character;

/**
 *
 * @author bruno
 */
public class CharacterView {
    
    private Character model;
    
    public CharacterView(Character model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("<ul>");
        str.append("<li>Id : ").append(this.model.getId()).append("</li>");
        str.append("<li>Name : ").append(this.model.getName()).append("</li>");
        str.append("<li>Model :</li>");
        str.append(new CharacterModelView(this.model.getModel()));
        str.append("<li>Properties :</li>");
        str.append(new PropertiesView(this.model.getProperties()));
        str.append("<li>Equipment :</li>");
        str.append(new EquipmentView(this.model.getEquipment()));
        
        str.append("</ul>");
        return str.toString();
    }
}
