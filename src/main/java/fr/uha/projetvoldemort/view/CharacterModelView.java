/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.view;

import fr.uha.projetvoldemort.character.CharacterModel;

/**
 *
 * @author bruno
 */
public class CharacterModelView {
    
    CharacterModel model;
    
    public CharacterModelView(CharacterModel model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("<ul>");
        str.append("<li>Id : ").append(this.model.getId()).append("</li>");
        str.append("<li>Name : ").append(this.model.getName()).append("</li>");
        str.append("<li>Description : ").append(this.model.getDescription()).append("</li>");
        str.append("</ul>");
        
        return str.toString();
    }
    
}
