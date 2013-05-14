/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author bruno
 */
public final class Item {

    private static final String MODEL_ID = "model_id";
    private ItemModel model;
    // TODO : usure
    // TODO : custimzation d'item

    public Item(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }

    public Item(ItemModel model) {
        this.model = model;
    }

    private void hydrate(BasicDBObject ob) {
        this.model = new ItemModel(ob.getObjectId(MODEL_ID));
    }

    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        ob.append(MODEL_ID, this.model.getId());

        return ob;
    }

    public void setModel(ItemModel model) {
        this.model = model;
    }

    public ItemModel getModel() {
        return this.model;
    }

    /**
     * Obtient le total d'attaque de l'item (en fonction de l'usure et des items
     * pouvant ou non le constituer)
     *
     * @return Le total d'attaque
     */
    public int getAttack() {
        return this.model.getProperties().getAttack();
    }

    /**
     * Obtient le total de défense de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total de défense
     */
    public int getDefense() {
        return this.model.getProperties().getDefense();
    }

    /**
     * Obtient le total d'initiative de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total d'initiative
     */
    public int getInitiative() {
        return this.model.getProperties().getInitiative();
    }

    /**
     * Obtient le total de chance de l'item (en fonction de l'usure et des items
     * pouvant ou non le constituer)
     *
     * @return Le total de chance
     */
    public int getLuck() {
        return this.model.getProperties().getLuck();
    }

    /**
     * Obtient le total de robustesse de l'item (en fonction de l'usure et des
     * items pouvant ou non le constituer)
     *
     * @return Le total de robustesse
     */
    public int getRobustness() {
        return this.model.getProperties().getRobustness();
    }
}
