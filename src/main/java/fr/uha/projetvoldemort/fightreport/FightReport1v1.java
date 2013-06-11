/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fightreport;

import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.fight.Fight1v1;
import fr.uha.projetvoldemort.item.Item;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public class FightReport1v1 extends FightReport {

    private static String TYPE = "type";
    private static String ATTACKER = " attacker";
    private static String DEFENSER = "defenser";
    private static String PHASE = "phase";
    private static String PLAYER = "player";
    private static String DESCRIPTION = "description";
    private static String RECORD = "record";

    private JSONArray report;
    private int record;
    private Fight1v1 fight;

    public FightReport1v1(Fight1v1 fight) {
        this.fight = fight;
        this.report = new JSONArray();
        this.record = 0;
    }

    public void phaseInitiative(Character c) throws JSONException {

        JSONObject o = new JSONObject();

        o.put(RECORD, record++);
        o.put(PHASE, "phase_initiative");
        o.put(PLAYER, c.getName());

        StringBuilder str = new StringBuilder();
        str.append("Player ");
        str.append(c.getName());
        str.append(" takes initiative.");

        o.put(DESCRIPTION, str.toString());

        this.report.put(o);

    }

    public void phaseProjectile(Character c, Item i) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_projectile");
        o.put(PLAYER, c.toJSONObject(false).put("projectile", i.toJSONObject()));

        StringBuilder str = new StringBuilder();
        str.append("Player ");
        str.append(c.getName());
        str.append(" uses ");
        str.append(i.getModel().getName());
        str.append(".");

        o.put(DESCRIPTION, str.toString());

        this.report.put(o);

    }

    public void phaseInitialThrowing(Character first, Item offensiveThrowing, Character second, Item defensiveThrowing, String description) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_initial_throwing");

        o.put("first", first.toJSONObject(false).put("offensive_throwing", offensiveThrowing.toJSONObject()));
        o.put("second", second.toJSONObject(false).put("defensive_throwing", defensiveThrowing.toJSONObject()));

        o.put(DESCRIPTION, description);

        this.report.put(o);
    }

    public void phaseDamage(Character first, Character second, int attackDamage, int defenseDamage, String description) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_damage");

        o.put("first", first.toJSONObject(false));
        o.put("attack_damage", attackDamage);
        o.put("second", second.toJSONObject(false));
        o.put("defense_damage", defenseDamage);

        o.put(DESCRIPTION, description);

        this.report.put(o);
    }

    public void phaseFaction(Faction faction, Character first, Character second, String description) throws JSONException {
        
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_faction");

        o.put("faction", faction.toJSONObject());
        
        o.put("first", first.toJSONObject(false));
        o.put("second", second.toJSONObject(false));

        o.put(DESCRIPTION, description);

        this.report.put(o);
 
    }

    public void phaseOffensiveThrowing(Character first, Character second, Item offensiveThrowing, Item defensiveThrowing, String description) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_offensive_throwing");
        
        o.put("first", first.toJSONObject(false).put("offensive_throwing", offensiveThrowing.toJSONObject()));
        o.put("second", second.toJSONObject(false).put("defensive_throwing", defensiveThrowing.toJSONObject()));

        o.put(DESCRIPTION, description);

        this.report.put(o);
    }
    
    public void phaseDefensiveThrowing(Character player, Item def, String description) throws JSONException {
        
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_defensive_throwing");
        
        o.put("player", player.toJSONObject(false).put("defensive_throwing", def.toJSONObject()));


        o.put(DESCRIPTION, description);

        this.report.put(o);
    }
    
    public void setWinner(Character winner) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "end");
        
        o.put("winner", winner.toJSONObject(false));

        this.report.put(o);
    }

    
    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put(TYPE, "1v1");
        //ob.put(ATTACKER, this.fight.getAttacker().toJSONObject());
        //ob.put(DEFENSER, this.fight.getDefesner().toJSONObject());
        
        ob.put("fight", this.report);
        
        return ob;
    }
}
