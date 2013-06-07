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
        Resources.getInstance().fill();
        BasicDBObject ob = (BasicDBObject) Resources.getInstance().getCollection(Character.COLLECTION).findOne();
        ObjectId id = ob.getObjectId("_id");
        Resources.getInstance().close();
        return id.toString();
    }

    public void fill() {
        
        // Supprimer les collections
        this.getCollection(CharacterModel.COLLECTION).drop();
        this.getCollection(Character.COLLECTION).drop();
        this.getCollection(ItemModel.COLLECTION).drop();
        this.getCollection(Item.COLLECTION).drop();
        this.getCollection(Panoply.COLLECTION).drop();
        this.getCollection(Faction.COLLECTION).drop();
        
        // Crée les factions
        Faction fw = new Faction(FactionType.WEREWOLF);
        fw.setName("Warewolf");
        fw.setDescription("cf. doc.");
        fw.save();
        
        Faction fv = new Faction(FactionType.VAMPIRE);
        fv.setName("Vampire");
        fv.setDescription("cf. doc.");
        fv.save();
        
        Faction fm = new Faction(FactionType.MUMMY);
        fm.setName("Mummy");
        fm.setDescription("cf. doc.");
        fm.save();
        

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
        ItemModel img = new ItemModel(ItemCategory.CONSUMABLE, ItemType.GAUNTLET);
        img.setName("Gant de base");
        img.setDescription("Gant parfait pour un noob.");
        img.setImage("gauntlet");
        img.save();

        ItemModel imc = new ItemModel(ItemCategory.DEGRADABLE, ItemType.CUIRASS);
        imc.setName("Cuirasse de m***e");
        imc.setDescription("Cette cuirasse n'a aucun effet possitif. Mieux vaut ne pas s'en équiper.");
        imc.setImage("cuirass");
        imc.save();

        ItemModel ima = new ItemModel(ItemCategory.SUSTAINABLE, ItemType.WEAPON);
        ima.setName("Epée rouillée");
        ima.setDescription("D'aventages de risque de choper le tetanos que de tuer un adversaire en la manipulant.");
        ima.setImage("rusty-sword");
        ima.save();

        ItemModel imb = new ItemModel(ItemCategory.SUSTAINABLE, ItemType.BAG);
        imb.setName("Sacoche 6 emplacements");
        imb.setDescription("Augmente votre inventaire de 6 cases.");
        imb.setImage("bag");
        imb.save();

        ItemModel imr = new ItemModel(ItemCategory.SUSTAINABLE, ItemType.RING);
        imr.setName("Anneau de pouvoir");
        imr.setDescription("Perdu par une étrange créature dans un marais, il est écrit dessus \"Un anneau pour les gouverner tous. Un anneau pour les trouver tous, Un anneau pour les amener tous et dans les ténèbres les lier.\"");
        imr.setImage("ring");
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
        cgu.setFaction(fw);
        cgu.setAttribute(CharacterAttribute.ATTACK, 10);
        cgu.setAttribute(CharacterAttribute.DEFENSE, 15);
        cgu.setAttribute(CharacterAttribute.INITIATIVE, 30);
        cgu.setAttribute(CharacterAttribute.LUCK, 5);
        
        // Crée les items du personnage
        Item ic = new Item(imc);
        ic.setAttribute(ItemAttribute.CLASS, 10);
        ic.setAttribute(ItemAttribute.DEFENSE, 30);
        Item ig = new Item(img);
        ig.setAttribute(ItemAttribute.CLASS, 2);
        ig.setAttribute(ItemAttribute.DEFENSE, 3);
        Item ia = new Item (ima);
        ia.setAttribute(ItemAttribute.CLASS, 3);
        ia.setAttribute(ItemAttribute.ATTACK, 2);
        Item ib = new Item (imb);
        Item ir = new Item (imr);
        ir.setAttribute(ItemAttribute.CLASS, 3);
        ir.setAttribute(ItemAttribute.LUCK, 5);
        
        // Ajoute les items à l'inventaire
        cgu.getInventory().add(ic);
        cgu.getInventory().add(ig);
        cgu.getInventory().add(ia);
        cgu.getInventory().add(ib);
        cgu.getInventory().add(ir);

        // Créer une panoplie
        Panoply  p = cgu.createPanoply();
        p.setItem(ic);
        p.setItem(ig);
        p.setItem(ia);
        
        cgu.setActivePanoply(p);
        
        cgu.save();
    }
}
