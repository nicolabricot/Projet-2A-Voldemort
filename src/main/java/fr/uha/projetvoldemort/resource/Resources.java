/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.resource;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import fr.uha.projetvoldemort.Attributes;
import fr.uha.projetvoldemort.character.CharacterModel;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.Panoply;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.item.ItemUsage;
import fr.uha.projetvoldemort.item.UnexpectedItemException;
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
    
    public String getFirstCharacterId() throws UnexpectedItemException {
        Resources.getInstance().connect();
        Resources.getInstance().fill();
        BasicDBObject ob = (BasicDBObject) Resources.getInstance().getCollection(Character.COLLECTION).findOne();
        ObjectId id = ob.getObjectId("_id");
        Resources.getInstance().close();
        return id.toString();
    }

    public void fill() throws UnexpectedItemException {
        
        // Supprimer les collections
        this.getCollection(CharacterModel.COLLECTION).drop();
        this.getCollection(Character.COLLECTION).drop();
        this.getCollection(ItemModel.COLLECTION).drop();
        this.getCollection(Item.COLLECTION).drop();
        this.getCollection(Panoply.COLLECTION).drop();

        // Crée les modèles de personnages
        CharacterModel cmb = new CharacterModel();
        cmb.setName("Brute");
        cmb.setDescription("Grande défense, petite attaque. (50%, 10%)");
        cmb.save();

        CharacterModel cmc = new CharacterModel();
        cmc.setName("Canaille");
        cmc.setDescription("Moyenne défense, moyenne attaque. (25%, 25%)");
        cmc.save();

        CharacterModel cmt = new CharacterModel();
        cmt.setName("Tacticien");
        cmt.setDescription("Petite défense, grande attaque. (10%, 50%)");
        cmt.save();

        // Crée les modèles d'items
        ItemModel img = new ItemModel(ItemType.GAUNTLET);
        img.setName("Gant de base");
        img.getAttributes().setDefense(2);
        img.getAttributes().setInitiative(1);
        img.getAttributes().setIntelligence(10);
        img.setDescription("Gant parfait pour un noob.");
        img.save();

        ItemModel imc = new ItemModel(ItemType.CUIRASS);
        imc.setName("Cuirass de m***e");
        imc.getAttributes().setInitiative(-5);
        imc.getAttributes().setLuck(-10);
        imc.setDescription("Cette cuirasse n'a aucun effet possitif. Mieux vaut ne pas s'en équiper.");
        imc.save();

        ItemModel ima = new ItemModel(ItemType.ARM);
        ima.setName("Epée rouillée");
        ima.getAttributes().setAttack(10);
        ima.getAttributes().setInitiative(3);
        ima.setDescription("D'aventages de risque de choper le tetanos que de tuer un adversaire en la manipulant.");
        ima.save();

        ItemModel imb = new ItemModel(ItemType.BAG);
        imb.setName("Sacoche 6 emplacements");
        imb.setDescription("Augmente votre inventaire de 6 cases.");
        imb.save();

        ItemModel imr = new ItemModel(ItemType.RING);
        imr.getAttributes().setIntelligence(10);
        imr.setName("Anneau de pouvoir");
        imr.setDescription("Perdu par une étrange créature dans un marais, il est écrit dessus \"Un anneau pour les gouverner tous. Un anneau pour les trouver tous, Un anneau pour les amener tous et dans les ténèbres les lier.\"");
        imr.save();

        /*
        Character cga = new Character(cmt);
        cga.setName("Gandalf");
        cga.save();

        Character cgi = new Character(cmb);
        cgi.setName("Gimli");
        cgi.save();
        */
        
        // Créer un personnage
        Character cgu = new Character(cmc);
        cgu.setName("Gurdil");
        
        Attributes a = cgu.getAttributes();
        a.setAttack(10);
        a.setDefense(15);
        a.setInitiative(30);
        a.setLuck(5);
        a.setIntelligence(2);
        
        // Crée les items du personnage
        Item ic = new Item(imc);
        Item ig = new Item(img);
        Item ia = new Item (ima);
        Item ib = new Item (imb);
        Item ir = new Item (imr);
        
        ic.setUsage(ItemUsage.DEGRADABLE);
        ig.setUsage(ItemUsage.CONSUMABLE);
        ia.setUsage(ItemUsage.SUSTAINABLE);
        ib.setUsage(ItemUsage.SUSTAINABLE);

        // Ajoute les items à l'inventaire
        cgu.getInventory().add(ic);
        cgu.getInventory().add(ig);
        cgu.getInventory().add(ia);
        cgu.getInventory().add(ib);

        // Créer une panoplie
        Panoply  p = cgu.createPanoply();
        p.setItem(ic);
        p.setItem(ig);
        p.setItem(ia);
        
        cgu.setActivePanoply(p);
        
        cgu.save(); // TODO : régler le bug de l'update
    }
}
