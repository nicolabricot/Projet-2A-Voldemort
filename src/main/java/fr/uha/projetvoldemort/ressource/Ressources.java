/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.ressource;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import fr.uha.projetvoldemort.Properties;
import fr.uha.projetvoldemort.character.CharacterModel;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.Panoply;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.item.UnexpectedItemException;
import java.net.UnknownHostException;

/**
 *
 * @author bruno
 */
public final class Ressources {

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
        if (this.mongo == null) {
            this.mongo = new Mongo(this.serverAddress, this.serverPort);
        }
    }

    public void close() {
        this.mongo.close();
        this.mongo = null;
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
        img.getProperties().setDefense(2);
        img.getProperties().setInitiative(1);
        img.getProperties().setRobustness(1);
        img.setDescription("Gant parfait pour un noob.");
        img.save();

        ItemModel imc = new ItemModel(ItemType.CUIRASS);
        imc.setName("Cuirass de m***e");
        imc.getProperties().setInitiative(-5);
        imc.getProperties().setLuck(-10);
        imc.setDescription("Cette cuirasse n'a aucun effet possitif. Mieux vaut ne pas s'en équiper.");
        imc.save();

        ItemModel ima = new ItemModel(ItemType.ARM);
        ima.setName("Epée rouillée");
        ima.getProperties().setAttack(10);
        ima.getProperties().setInitiative(3);
        ima.setDescription("D'aventages de risque de choper le tetanos que de tuer un adversaire en la manipulant.");
        ima.save();

        ItemModel imb = new ItemModel(ItemType.BAG);
        imb.setName("Sacoche 6 emplacements");
        imb.setDescription("Augmente votre inventaire de 6 cases.");
        imb.save();

        ItemModel imr = new ItemModel(ItemType.RING);
        imr.getProperties().setRobustness(10);
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
        
        Properties p = cgu.getProperties();
        p.setAttack(10);
        p.setDefense(15);
        p.setInitiative(30);
        p.setLuck(5);
        p.setRobustness(2);
        
        // Crée les items du personnage
        Item ic = new Item(imc);
        Item ig = new Item(img);
        Item ia = new Item (ima);
        Item ib = new Item (imb);
        Item ir = new Item (imr);

        // Ajoute les items à l'inventaire
        cgu.getInventory().add(ic);
        cgu.getInventory().add(ig);
        cgu.getInventory().add(ia);
        cgu.getInventory().add(ib);

        // Créer une panoplie
        Panoply  pa = cgu.createPanoply();
        pa.setItem(ic);
        pa.setItem(ig);
        pa.setItem(ia);
        
        cgu.setActivePanoply(pa);
        
        cgu.save(); // TODO : régler le bug de l'update
    }
}
