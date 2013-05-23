/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public class Map {

    public static final String COLLECTION = "map";
    private static final String ID = "_id";
    private static final String PATH = "path";
    private HashMap<String, String> path;
    private ObjectId id;

    public Map() {
        this.path = new HashMap<String, String>();
    }

    public Map(ObjectId oid) {
        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    private void hydrate(BasicDBObject ob) {
    }

    public HashMap<String, String> getPath() {
        return this.path;
    }

    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
        if (this.id != null) {
            ob.append(ID, id);
        }

        BasicDBObject paths = new BasicDBObject();
        Iterator<Entry<String, String>> it = this.path.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> e = it.next();
            ob.append(e.getKey(), e.getValue());
        }
        ob.append(PATH, path);
        return ob;
    }

    public void save() {
        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).insert(ob); // TODO : bug update
        this.id = ob.getObjectId(ID);
    }
}
