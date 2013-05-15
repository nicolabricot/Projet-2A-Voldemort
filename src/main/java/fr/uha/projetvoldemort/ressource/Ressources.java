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
        this.getCollection(CharacterModel.COLLECTION).drop();
        this.getCollection(Character.COLLECTION).drop();
        this.getCollection(ItemModel.COLLECTION).drop();

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

        ItemModel img = new ItemModel();
        img.setType(ItemType.GAUNTLET);
        img.setName("Gant de base");
        img.getProperties().setDefense(2);
        img.getProperties().setInitiative(1);
        img.getProperties().setRobustness(1);
        img.setDescription("Gant parfait pour un noob.");
        img.save();

        ItemModel imc = new ItemModel();
        imc.setType(ItemType.CUIRASS);
        imc.setName("Cuirass de m***e");
        imc.getProperties().setInitiative(-5);
        imc.getProperties().setLuck(-10);
        imc.setDescription("Cette cuirasse n'a aucun effet possitif. Mieux vaut ne pas s'en équiper.");
        imc.save();

        ItemModel ima = new ItemModel();
        ima.setType(ItemType.ARM);
        ima.setName("Epée rouillée");
        ima.getProperties().setAttack(10);
        ima.getProperties().setInitiative(3);
        ima.setDescription("D'aventages de risque de choper le tetanos que de tuer un adversaire en la manipulant.");
        ima.save();

        ItemModel imb = new ItemModel();
        imb.setType(ItemType.BAG);
        imb.setName("Sacoche 6 emplacements");
        imb.setDescription("Augmente votre inventaire de 6 cases.");
        imb.save();

        ItemModel imr = new ItemModel();
        imr.setType(ItemType.RING);
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
        
        Character cgu = new Character(cmc);
        cgu.setName("Gurdil");
        Properties p = cgu.getProperties();
        p.setAttack(10);
        p.setDefense(15);
        p.setInitiative(30);
        p.setLuck(5);
        p.setRobustness(2);
        cgu.getEquipment().setItem(new Item(imc));
        cgu.getEquipment().setItem(new Item(img));
        cgu.getEquipment().setItem(new Item(ima));
        cgu.getInventory().add(new Item(imb));
        cgu.getInventory().add(new Item(imr));
        cgu.save(); // TODO : régler le bug de l'update
    }
}
