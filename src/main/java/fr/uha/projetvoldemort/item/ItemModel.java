/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.item;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public final class ItemModel {

    public static final String COLLECTION = "item_model";
    private static final String ID = "_id";
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    private ObjectId id;
    private String name, description, image;
    private ItemType type;
    private ItemCategory category;


    public ItemModel(ObjectId oid) {
        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public ItemModel(ItemCategory category, ItemType type) {
        this.type = type;
        this.category = category;
        this.image = "default";

    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.type = ItemType.fromString(ob.getString(TYPE));
        this.category = ItemCategory.fromString(ob.getString(CATEGORY));
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
        this.image = ob.getString(IMAGE);
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();

        if (this.id != null) {
            ob.append(ID, id);
        }

        ob.append(TYPE, this.type.toString());
        ob.append(CATEGORY, this.category.toString());
        ob.append(NAME, this.name);
        ob.append(DESCRIPTION, this.description);
        ob.append(IMAGE, image);

        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put("id", this.id.toString());
        ob.put(NAME, this.name);
        ob.put(TYPE, this.type.toString());
        ob.put(CATEGORY, this.category.toString());
        ob.put(DESCRIPTION, this.description);
        ob.put(IMAGE, this.image);
        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        //Resources.getInstance().getCollection(COLLECTION).insert(ob);
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("ItemModel.save: " + ob);
    }

    public ObjectId getId() {
        return this.id;
    }

    public ItemType getType() {
        return this.type;
    }
    
    public ItemCategory getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

