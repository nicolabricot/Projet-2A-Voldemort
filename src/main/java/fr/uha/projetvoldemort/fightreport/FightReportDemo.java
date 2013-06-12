/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fightreport;

import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.faction.Faction;
import fr.uha.projetvoldemort.fight.FightDemo;
import fr.uha.projetvoldemort.item.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public class FightReportDemo extends FightReport {

    private static String TYPE = "type";
    private static String ATTACKER = " attacker";
    private static String DEFENSER = "defenser";
    private static String PHASE = "phase";
    private static String PLAYER = "player";
    private static String DESCRIPTION = "description";
    private static String RECORD = "record";
    private JSONArray report;
    private int record;
    private FightDemo fight;

    public FightReportDemo(FightDemo fight) {
        this.fight = fight;
        this.report = new JSONArray();
        this.record = 0;
    }

    public void phaseInitiative(Character c) throws JSONException {

        JSONObject o = new JSONObject();

        o.put(RECORD, record++);
        o.put(PHASE, "phase_initiative");
        o.put(PLAYER, c.toJSONObject(false));

        StringBuilder str = new StringBuilder();
        str.append("Le joueur ");
        str.append(c.getName());
        str.append(" prend l'initiative.");

        o.put(DESCRIPTION, str.toString());

        this.report.put(o);

    }

    public void phaseInitialThrowing(Character first, Item offensiveThrowing, int attack, Character second, Item defensiveThrowing, int defense) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_initial_throwing");

        o.put("first", first.toJSONObject(false));
        o.put("attack", attack);
        o.put("offensive_throwing", offensiveThrowing.toJSONObject());
        
        o.put("second", second.toJSONObject(false));
        o.put("defense", defense);
        o.put("defensive_throwing", defensiveThrowing.toJSONObject());


        StringBuilder str = new StringBuilder();
        if (attack > defense) {
            str.append("Jet offensif [");
            str.append(offensiveThrowing.getModel().getName());
            str.append("] esquive jet defensif [");
            str.append(defensiveThrowing.getModel().getName());
            str.append("] et fait perdre ");
            str.append(attack - defense);
            str.append(" points de vie à ");
            str.append(second.getName());
            str.append(".");
        } else if (defense > attack) {
            str.append("Jet defensif [");
            str.append(defensiveThrowing.getModel().getName());
            str.append("] esquive jet offensif [");
            str.append(offensiveThrowing.getModel().getName());
            str.append("] et fait perdre ");
            str.append(defense - attack);
            str.append(" point de vie à ");
            str.append(first.getName());
            str.append(".");
        } else {
            str.append("Jet offensif [");
            str.append(offensiveThrowing.getModel().getName());
            str.append("] et jet defensif [");
            str.append(defensiveThrowing.getModel().getName());
            str.append("] s'esquivent mutuellement, aucun dégats n'est infligé.");
        }
        o.put(DESCRIPTION, str.toString());

        this.report.put(o);
    }

    public void phaseDamage(Character first, int attack, Character second, int defense) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_damage");

        o.put("first", first.toJSONObject(false));
        o.put("attack", attack);
        o.put("second", second.toJSONObject(false));
        o.put("defense", defense);

        StringBuilder str = new StringBuilder();
        if (attack > defense) {
            str.append(first.getName());
            str.append(" attaque ");
            str.append(second.getName());
            str.append(" et lui fait perdre ");
            str.append(attack - defense);
            str.append(" points de vie.");
        } else {
            str.append(first.getName());
            str.append(" attaque ");
            str.append(second.getName());
            str.append(" qui parvient à se défendre, aucun dégats n'est infligé.");
        }
        o.put(DESCRIPTION, str.toString());

        this.report.put(o);
    }

    public void phaseFaction(Faction faction, Character first, Character second, int delta) throws JSONException {

        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "phase_faction");

        o.put("faction", faction.toJSONObject());
        o.put("first", first.toJSONObject(false));
        o.put("second", second.toJSONObject(false));
        o.put("delta", delta);

        StringBuilder str = new StringBuilder();

        
        switch (faction.getType()) {
            case WEREWOLF:
                str.append(first.getName());
                str.append(" utilise son pouvoir de [");
                str.append(faction.getName());
                str.append("] et fait perdre " );
                str.append(delta);
                str.append(" points de vie ");
                str.append(second.getName());
                str.append(".");
                break;
            // Vampire :
            case VAMPIRE:
                str.append(first.getName());
                str.append(" utilise son pouvoir de [");
                str.append(faction.getName());
                str.append("] et vole " );
                str.append(delta);
                str.append(" points de vie à ");
                str.append(second.getName());
                str.append(".");
                break;
            // Momie
            case MUMMY:
                str.append(first.getName());
                str.append(" utilise son pouvoir de [");
                str.append(faction.getName());
                str.append("] et gagne " );
                str.append(delta);
                str.append(" points de vie.");
                break;
        }

        o.put(DESCRIPTION, str.toString());

        this.report.put(o);

    }

    public void setWinner(Character winner) throws JSONException {
        JSONObject o = new JSONObject();
        o.put(RECORD, record++);
        o.put(PHASE, "end");

        o.put("winner", winner.toJSONObject(false));

        StringBuilder str = new StringBuilder();
        str.append("Le joueur ");
        str.append(winner.getName());
        str.append(" gagne le combat.");
        o.put(DESCRIPTION, str.toString());
        
        this.report.put(o);
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put(TYPE, "demo");
        
        ob.put(ATTACKER, this.fight.getAttacker().toJSONObject());
        ob.put(DEFENSER, this.fight.getDefenser().toJSONObject());

        ob.put("fight", this.report);
        ob.put("id", this.getId());

        return ob;
    }
}
