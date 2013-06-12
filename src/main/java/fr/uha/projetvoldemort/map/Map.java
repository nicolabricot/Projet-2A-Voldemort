/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.map;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.fight.FightDemo;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.ArrayList;
import java.util.Iterator;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public class Map {

    public static final String COLLECTION = "map";
    private static final String ID = "_id";
    private static final String TYPE = "type";
    private static final String MAPS = "maps";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";
    private static final String LEVEL = "level";
    private static final String OPENED = "opened";
    private static final String CLOSED = "closed";
    private static final String DONE = "done";
    private ArrayList<Map> maps;
    private MapType type;
    private ObjectId id;
    private String name;
    private int level;
    private String description;
    private String title;

    public Map(String name, MapType type, int level) {
        this.maps = new ArrayList<Map>();
        this.name = name;
        this.type = type;
        this.level = level;
    }

    public Map(ObjectId oid) {
        this.maps = new ArrayList<Map>();

        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    public Map(String name) {
        this.maps = new ArrayList<Map>();

        BasicDBObject query = new BasicDBObject("name", name);

        Resources res = Resources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(query);
        if (ob == null) {
            throw new NotFoundException();
        }
        this.hydrate(ob);
    }

    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.type = MapType.fromString(ob.getString(TYPE));
        this.name = ob.getString(NAME);
        this.description = ob.getString(DESCRIPTION);
        this.title = ob.getString(TITLE);
        this.level = ob.getInt(LEVEL);

        Iterator<Object> it = ((BasicDBList) ob.get(MAPS)).iterator();
        while (it.hasNext()) {
            ObjectId oid = (ObjectId) it.next();
            this.maps.add(new Map(oid));
        }
    }

    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
        if (this.id != null) {
            ob.append(ID, id);
        }

        ob.append(TYPE, this.type.toString());
        ob.append(NAME, this.name);
        ob.append(DESCRIPTION, this.description);
        ob.append(TITLE, this.title);
        ob.append(LEVEL, this.level);

        BasicDBList mapList = new BasicDBList();
        Iterator<Map> it = this.maps.iterator();
        while (it.hasNext()) {
            mapList.add(it.next().getId());
        }
        ob.append(MAPS, mapList);

        return ob;
    }

    public JSONObject getStates(Character c) throws JSONException {
        JSONObject o = new JSONObject();

        Iterator<Map> it = this.maps.iterator();
        while (it.hasNext()) {
            Map map = it.next();
            o.put(map.getName(), map.getState(c));
        }
        return o;
    }

    private String getUrl() {
        switch (this.type) {
            case MAP:
                return "./map.jsp?map=" + this.name;
            case FIGHT:
                return "./rest/fight/" + FightDemo.class.getSimpleName();
            case PANOPLY:
                return "./panoply.jsp?panoply=active";
            default:
                return "./map.jsp?map=main";
        }
    }

    protected boolean isDone(int level) {
        Iterator<Map> it = this.maps.iterator();
        while (it.hasNext()) {
            Map m = it.next();
            
            switch (m.getType()) {
                case FIGHT:
                    if (m.level >= level)
                        return false;
                case MAP:
                    m.isDone(level);
            }
        }
        return true;
    }

    private String getState(int level) {
        switch (this.type) {
            case MAP:
                if (this.level > level) {
                    return CLOSED;
                } else if (this.isDone(level)) {
                    return DONE;
                } else {
                    return OPENED;
                }
            case FIGHT:
                if (this.level == level) {
                    return OPENED;
                } else if (this.level > level) {
                    return CLOSED;
                } else {
                    return DONE;
                }
            case PANOPLY:
                return (this.level > level) ? CLOSED : OPENED;
            default:
                return CLOSED;
        }
    }

    protected JSONArray getState(Character c) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("type", this.getState(c.getLevel()));
        o.put("link", this.getUrl());
        o.put("description", this.description);

        JSONArray a = new JSONArray();
        a.put(o);
        return a;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void add(Map map) {
        this.maps.add(map);
    }

    public String getName() {
        return this.name;
    }

    public MapType getType() {
        return this.type;
    }

    public void save() {
        Iterator<Map> it = this.maps.iterator();
        while (it.hasNext()) {
            it.next().save();
        }

        BasicDBObject ob = (BasicDBObject) this.toDBObject();
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);

        System.out.println("Map.save: " + ob);
    }
}
