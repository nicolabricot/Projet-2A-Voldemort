/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.resource;

import fr.uha.projetvoldemort.character.CharacterModel;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.CharacterAttribute;
import fr.uha.projetvoldemort.character.Panoply;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.faction.FactionAttribute;
import fr.uha.projetvoldemort.faction.FactionType;
import fr.uha.projetvoldemort.fightreport.FightReport;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.item.ItemType;
import fr.uha.projetvoldemort.map.Map;
import fr.uha.projetvoldemort.map.MapType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author bruno
 */
public class HeiligeSchrift {

    private static HeiligeSchrift Ich;
    private static String HEILIGE_SCHRIFT = "fr/uha/projetvoldemort/resource/HeiligeSchrift.json";
    private JSONObject heiligeSchrift;
    private EnumMap<FactionType, Faction> factions;
    private HashMap<String, CharacterModel> characterModels;
    private HashMap<String, ItemModel> itemModels;
    
    private HeiligeSchrift() {

        this.factions = new EnumMap<FactionType, Faction>(FactionType.class);
        this.characterModels = new HashMap<String, CharacterModel>();
        this.itemModels = new HashMap<String, ItemModel>();

        try {
            URL url = this.getClass().getClassLoader().getResource(HEILIGE_SCHRIFT);
            InputStreamReader isr = new InputStreamReader(url.openStream());
            JSONTokener t = new JSONTokener(isr);
            this.heiligeSchrift = new JSONObject(t);

            isr.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static HeiligeSchrift Gott() {
        if (Ich == null) {
            Ich = new HeiligeSchrift();
        }

        return Ich;
    }

    public void Apokalypse() {
        // Supprimer les collections
        Resources.getInstance().getCollection(CharacterModel.COLLECTION).drop();
        Resources.getInstance().getCollection(fr.uha.projetvoldemort.character.Character.COLLECTION).drop();
        Resources.getInstance().getCollection(ItemModel.COLLECTION).drop();
        Resources.getInstance().getCollection(Item.COLLECTION).drop();
        Resources.getInstance().getCollection(Panoply.COLLECTION).drop();
        Resources.getInstance().getCollection(Faction.COLLECTION).drop();
        Resources.getInstance().getCollection(FightReport.COLLECTION).drop();
        Resources.getInstance().getCollection(Map.COLLECTION).drop();
    }

    public void Entstehung() throws JSONException {

        this.DerErsteTag(); // Crée les factions
        this.DerZweiteTag(); // Crée les modèles de personnages
        this.DerDritteTag(); // Crée les modèles d'items
        this.DerVierteTag(); // Crée les personnages
        this.DerFünfteTag(); // Crée les maps

    }

    public void DerErsteTag() throws JSONException {
        // Crée les factions

        for (FactionType val : FactionType.values()) {
            Faction f = new Faction(val);
            f.setName(val.toString() + "_name");
            f.setDescription(val.toString() + "_description");
            f.setAttribute(FactionAttribute.HIT, 0);
            f.setAttribute(FactionAttribute.POWER, 5);
            f.save();

            this.factions.put(f.getType(), f);
        }
    }

    public void DerZweiteTag() throws JSONException {
        // Crée les modèles de personnages

        JSONArray a = this.heiligeSchrift.getJSONArray("character_models");
        for (int i = 0; i < a.length(); i++) {
            String str = a.getString(i);

            CharacterModel cm = new CharacterModel();
            cm.setName(str + "_name");
            cm.setDescription(str + "_description");
            cm.save();

            this.characterModels.put(str, cm);
        }

    }

    public void DerDritteTag() throws JSONException {
        // Crée les modèles d'items

        JSONArray a = this.heiligeSchrift.getJSONArray("item_models");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);


            ItemModel im = new ItemModel(ItemCategory.fromString(o.getString("category")), ItemType.fromString(o.getString("type")));
            String str = o.getString("name");

            im.setName(str + "_name");
            im.setDescription(str + "_description");
            im.setImage(o.getString("image"));
            im.save();

            this.itemModels.put(str, im);
        }
    }

    public void DerVierteTag() throws JSONException {
        // Crée les personnages

        JSONArray a = this.heiligeSchrift.getJSONArray("characters");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(0); //i);

            Character c = new Character(this.characterModels.get(o.getString("model")));
            c.setName(o.getString("name") + "_" +String.valueOf(i));
            c.setFaction(this.factions.get(FactionType.fromString(o.getString("faction"))));

            JSONObject att = o.getJSONObject("attributes");
            Iterator it = att.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                c.setAttribute(CharacterAttribute.fromString(key), att.getInt(key));
            }

            // Créer une panoplie
            Panoply p = c.createPanoply();
            c.setActivePanoply(p);

            // Ajoute les items à l'inventaire
            JSONArray inv = o.getJSONArray("inventory");
            for (int j = 0; j < inv.length(); j++) {
                JSONObject ob = inv.getJSONObject(j);

                Item item = new Item(this.itemModels.get(ob.getString("model")));

                if (ob.has("attributes")) {

                    att = ob.getJSONObject("attributes");
                    it = att.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        item.setAttribute(ItemAttribute.fromString(key), att.getInt(key));
                    }
                }
                c.getInventory().add(item);
                p.setItem(item); // Si on veut que les items soient ajoutés à la panoplie
            }

            c.setLevel(2);
            
            c.save();


        }
    }
    
    public void DerFünfteTag() {
        // Crée les maps
        Map m1 = new Map("main", MapType.MAP, 0);
        m1.setTitle("France");
        m1.setDescription("Carte de la France");
        
 
        
        Map m11 = new Map("champagne-ardenne", MapType.MAP, 2);
        m11.setTitle("Champagne-Ardenne");
        m11.setDescription("Want to drink a glass of Champagne?");
        m1.add(m11);
        
        Map m12 = new Map("alsace", MapType.MAP, 2);
        m12.setTitle("Alsace");
        m12.setDescription("Welcome in Alsace");
        m1.add(m12);
        
        Map m13 = new Map("centre", MapType.MAP, 1);
        m13.setTitle("Centre");
        m13.setDescription("Le Centre est une région française, qui regroupe six départements : le Cher, l'Eure-et-Loir, l'Indre, l'Indre-et-Loire, le Loir-et-Cher et le Loiret.");
        m1.add(m13);
        
        Map m14 = new Map("corse", MapType.MAP, 1);
        m14.setTitle("Corse");
        m14.setDescription("Corse, ma belle Corse, que veux-tu faire par monts et par vaux...");
        m1.add(m14);
        
        Map m15 = new Map("ile-de-france", MapType.MAP, 6);
        m15.setTitle("Île de France");
        m1.add(m15);

        
        Map m121 = new Map("bas-rhin", MapType.PANOPLY, 3);
        m121.setTitle("Panoplie");
        m121.setTitle("Panoplie du Bas-Rhin.");
        m12.add(m121);
        
        Map m122 = new Map("haut-rhin", MapType.FIGHT, 4);
        m122.setTitle("Combat 1v1");
        m122.setDescription("Engagez vous dans la lutte contre les Germains dans la fôret.");
        m12.add(m122);
        
        
        
        Map m111 = new Map("marne", MapType.FIGHT, 2);
        m111.setTitle("Combat");
        m111.setDescription("Combat 1v1 sur les champs de batailles de la Marne.");
        m11.add(m111);
        
        Map m112 = new Map("aube", MapType.PANOPLY, 2);
        m112.setTitle("Panoplie");
        m112.setDescription("Panoplie de l'Aube");
        m11.add(m112);
        
        Map m113 = new Map("ardennes", MapType.FIGHT, 3);
        m113.setTitle("Combat 1v1");
        m113.setDescription("Participez à un combat 1v1 dans la bataille des Ardennes.");
        m11.add(m113);
        
        Map m114 = new Map("haute-marne", MapType.PANOPLY, 3);
        m114.setTitle("Panoplie");
        m114.setDescription("Panoplie de la Haute-Marne");
        m11.add(m114);
        
        m1.save(); // sauvegarde tout    
    }
}
