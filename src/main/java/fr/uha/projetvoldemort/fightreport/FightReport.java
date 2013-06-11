/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fightreport;

import com.mongodb.BasicDBObject;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.resource.Resources;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public abstract class FightReport {
    
    public static String COLLECTION = "report";
    public static String REPORT = "report";
    public static String ID = "_id";

    private ObjectId id;
    
    public abstract JSONObject toJSONObject() throws JSONException ;
    
    public final void save() throws JSONException {
        BasicDBObject ob = new BasicDBObject();
        ob.append(REPORT, this.toJSONObject().toString());
        Resources.getInstance().getCollection(COLLECTION).save(ob);
        this.id = ob.getObjectId(ID);
        System.out.println("FightReport.save: " + ob);
    }
    
    public ObjectId getId() {
        return this.id;
    }
}
