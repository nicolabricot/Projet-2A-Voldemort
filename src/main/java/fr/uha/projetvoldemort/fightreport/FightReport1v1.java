/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fightreport;

import fr.uha.projetvoldemort.fight.Fight;
import fr.uha.projetvoldemort.fight.Fight1v1;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public class FightReport1v1 extends FightReport {
    
    private static String SEED = "seed";
    private static String TYPE = "type";
    private static String ATTACKER = " attacker";
    private static String DEFENSER = "defenser";
    private static String ANIMATION = "animation";
    
    private Fight1v1 fight;
    private JSONArray animation;
    
    public FightReport1v1(Fight1v1 fight) {
            this.fight = fight;
            this.animation = new JSONArray();
    }

    @Override
    public JSONObject getReport() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put(SEED, fight.getSeed());
        ob.put(TYPE, "1v1");
        ob.put(ATTACKER, this.fight.getAttacker().getId().toString());
        ob.put(DEFENSER, this.fight.getDefesner().getId().toString());
        ob.put(ANIMATION, this.animation);
        return ob;
    }
    
}
