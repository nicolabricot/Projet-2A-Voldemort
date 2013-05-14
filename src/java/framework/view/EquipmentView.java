/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.character.Equipment;

/**
 *
 * @author bruno
 */
public class EquipmentView {
    
    private Equipment model;
    
    public EquipmentView(Equipment model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("<ul>");
        str.append("<li>Armor : </li>");
        str.append(new ArmorView(this.model.getArmor()));
        
        str.append("<li>Arm : </li>");
        if (this.model.getArm() != null)
            str.append(new ItemView(this.model.getArm()));
        str.append("</ul>");
        return str.toString();
    }
}
