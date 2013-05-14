/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.view;

import framework.Properties;

/**
 *
 * @author bruno
 */
public class PropertiesView {

    private Properties model;

    public PropertiesView(Properties model) {
        this.model = model;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("<ul>");
        str.append("<li>Life : ").append(this.model.getLife()).append("</li>");
        str.append("<li>Attack : ").append(this.model.getAttack()).append("</li>");
        str.append("<li>Defense : ").append(this.model.getDefense()).append("</li>");
        str.append("<li>Initiative : ").append(this.model.getInitiative()).append("</li>");
        str.append("<li>Robustness : ").append(this.model.getRobustness()).append("</li>");
        str.append("<li>Luck : ").append(this.model.getLuck()).append("</li>");
        str.append("</ul>");
        
        return str.toString();
    }
}
