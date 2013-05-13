/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.ressource;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.net.UnknownHostException;

/**
 *
 * @author bruno
 */
public class Ressources {

    private static Ressources self;

    private String serverAddress;
    private int serverPort;
    private String databaseName;
    
    private Mongo mongo;

    private Ressources() {
        this.serverAddress = "localhost";
        this.serverPort = 27017;
        this.databaseName = "voldemort";
    }

    public static Ressources getInstance() {
        if (Ressources.self == null) {
            Ressources.self = new Ressources();
        }
        return Ressources.self;
    }

    public DBCollection getCollection(String collection) {
        return this.mongo.getDB(this.databaseName).getCollection(collection);
    }
    
    public void connect() throws UnknownHostException {
        if (this.mongo == null)
            this.mongo = new Mongo(this.serverAddress, this.serverPort);
    }
    
    public void close() {
        this.mongo.close();
        this.mongo = null;
    }
    
}
