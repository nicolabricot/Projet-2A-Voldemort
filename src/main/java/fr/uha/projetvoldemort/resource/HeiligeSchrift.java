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
import fr.uha.projetvoldemort.faction.FactionType;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.item.ItemType;
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

            //isr.close(); ou pas...
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
    }

    public void Entstehung() throws JSONException {

        this.DerErsteTag(); // Crée les factions
        this.DerZweiteTag(); // Crée les modèles de personnages
        this.DerDritteTag(); // Crée les modèles d'items
        this.DerVierteTag(); // Crée les personnages

    }

    public void DerErsteTag() throws JSONException {
        // Crée les factions

        JSONArray a = this.heiligeSchrift.getJSONArray("factions");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);
            Faction f = new Faction(FactionType.fromString(o.getString("faction_type")));
            f.setName(o.getString("name"));
            f.setDescription(o.getString("description"));
            f.save();

            this.factions.put(f.getType(), f);
        }
    }

    public void DerZweiteTag() throws JSONException {
        // Crée les modèles de personnages

        JSONArray a = this.heiligeSchrift.getJSONArray("character_models");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);

            CharacterModel cm = new CharacterModel();
            cm.setName(o.getString("name"));
            cm.setDescription(o.getString("description"));
            cm.save();

            this.characterModels.put(cm.getName(), cm);
        }

    }

    public void DerDritteTag() throws JSONException {
        // Crée les modèles d'items

        JSONArray a = this.heiligeSchrift.getJSONArray("item_models");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);


            ItemModel im = new ItemModel(ItemCategory.fromString(o.getString("category")), ItemType.fromString(o.getString("type")));
            im.setName(o.getString("name"));
            im.setDescription(o.getString("description"));
            im.setImage(o.getString("image"));
            im.save();

            this.itemModels.put(im.getName(), im);
        }
    }

    public void DerVierteTag() throws JSONException {
        // Crée les personnages

        JSONArray a = this.heiligeSchrift.getJSONArray("characters");
        for (int i = 0; i < a.length(); i++) {
            JSONObject o = a.getJSONObject(i);

            Character c = new Character(this.characterModels.get(o.getString("model")));
            c.setName(o.getString("name"));
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

            c.save();


        }
    }
}
