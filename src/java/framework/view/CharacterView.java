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
        str.append("<ul>");
        str.append("<li>Life : ").append(this.model.getProperties().getLife()).append("</li>");
         
        // <li>Machin : valeur totale ( valeur du perso + valeur totale de l'équipement)</li>
        str.append("<li>Attack : ").append(this.model.getAttack()). append(" (").append(this.model.getProperties().getAttack()).append(" + ").append(this.model.getEquipment().getAttack()).append(")</li>");
        str.append("<li>Defense : ").append(this.model.getDefense()). append(" (").append(this.model.getProperties().getDefense()).append(" + ").append(this.model.getEquipment().getDefense()).append(")</li>");
        str.append("<li>Initiative : ").append(this.model.getInitiative()). append(" (").append(this.model.getProperties().getInitiative()).append(" + ").append(this.model.getEquipment().getInitiative()).append(")</li>");
        str.append("<li>Luck : ").append(this.model.getLuck()). append(" (").append(this.model.getProperties().getLuck()).append(" + ").append(this.model.getEquipment().getLuck()).append(")</li>");
        str.append("<li>Robustness : ").append(this.model.getRobustness()). append(" (").append(this.model.getProperties().getRobustness()).append(" + ").append(this.model.getEquipment().getRobustness()).append(")</li>");
        str.append("</ul>");
        str.append("<li>Equipment :</li>");
        str.append(new EquipmentView(this.model.getEquipment()));
        
        str.append("</ul>");
        return str.toString();
    }
}
