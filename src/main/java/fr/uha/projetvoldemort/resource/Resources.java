/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.resource;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import fr.uha.projetvoldemort.character.CharacterModel;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.CharacterAttribute;
import fr.uha.projetvoldemort.character.Panoply;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.faction.FactionType;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.item.ItemType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author bruno
 */
public final class Resources {

    private static String FILE = "fr/uha/projetvoldemort/resource/conf.json";
    private static String SERVER_ADDRESS = "server_address";
    private static String SERVER_PORT = "server_port";
    private static String DATABASE_NAME = "database_name";
    
    private static Resources self;
    private String serverAddress;
    private int serverPort;
    private String databaseName;
    private Mongo mongo;

    private Resources() {
        InputStreamReader isr = null;
        try {
            
            URL url = this.getClass().getClassLoader().getResource(FILE);
            isr = new InputStreamReader(url.openStream());
            JSONTokener t  = new JSONTokener(isr);
            JSONObject o = new JSONObject(t);
            
            this.serverAddress = o.getString(SERVER_ADDRESS);
            this.serverPort = o.getInt(SERVER_PORT);
            this.databaseName = o.getString(DATABASE_NAME);
            
            isr.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Resources getInstance() {
        if (Resources.self == null) {
                Resources.self = new Resources();
        }
        return Resources.self;
    }

    public DBCollection getCollection(String collection) {
        return this.mongo.getDB(this.databaseName).getCollection(collection);
    }

    public void connect() {
        if (this.mongo == null) {
            try {
                this.mongo = new Mongo(this.serverAddress, this.serverPort);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void close() {
        this.mongo.close();
        this.mongo = null;
    }
    
    public String getFirstCharacterId() {
        Resources.getInstance().connect();
        //Resources.getInstance().fill();
        BasicDBObject ob = (BasicDBObject) Resources.getInstance().getCollection(Character.COLLECTION).findOne();
        ObjectId id = ob.getObjectId("_id");
        Resources.getInstance().close();
        return id.toString();
    }

    public void fill() throws JSONException {        
        HeiligeSchrift.Gott().Apokalypse();
        HeiligeSchrift.Gott().Entstehung();
    }
}
