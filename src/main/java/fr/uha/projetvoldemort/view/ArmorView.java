/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.view;

import fr.uha.projetvoldemort.character.Armor;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemType;

/**
 *
 * @author bruno
 */
public class ArmorView {

    private Armor model;

    public ArmorView(Armor model) {
        this.model = model;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("<ul>");

        for (ItemType it : ItemType.values()) {
            if (!it.isArmor()) {
                continue;
            }

            Item t = this.model.getItem(it);

            if (t == null) {
                continue;
            }

            str.append("<li>").append(it).append(" :</li>");
            str.append(new ItemView(t));

        }
        str.append("</ul>");

        return str.toString();
    }
}
