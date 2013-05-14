/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.Properties;

/**
 *
 * @author bruno
 */
public final class Item {
    
    private static final String MODEL_ID = "model_id";
    
    private Properties properties;
    private ItemModel model;

    public Item(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }
    
    public Item(ItemModel model) {
        this.properties = new Properties();
        this.model = model;
    }
    
    private void hydrate(BasicDBObject ob) {
        this.model = new ItemModel(ob.getObjectId(MODEL_ID));
        this.properties = new Properties((DBObject) ob.get(Properties.PROPERTIES));
    }
    
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        ob.append(MODEL_ID, this.model.getId());
        ob.append(Properties.PROPERTIES, this.properties.toDBObject());
        
        return ob;
    }
    
    public void setModel(ItemModel model) {
        this.model = model;
    }

    public ItemModel getModel() {
        return this.model;
    }
    
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
}
