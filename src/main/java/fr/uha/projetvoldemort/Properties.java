package fr.uha.projetvoldemort;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bruno
 */
public final class Properties {

    public static final String PROPERTIES = "properties";
    public static final String LIFE = "life";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String INITIATIVE = "initiative";
    public static final String LUCK = "luck";
    public static final String ROBUSTNESS = "robustness";
    private int life, attack, defense, initiative, luck, robustness;

    public Properties(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }

    public Properties() {
    }

    private void hydrate(BasicDBObject ob) {
        this.life = ob.getInt(LIFE);
        this.attack = ob.getInt(ATTACK);
        this.defense = ob.getInt(DEFENSE);
        this.initiative = ob.getInt(INITIATIVE);
        this.luck = ob.getInt(LUCK);
        this.robustness = ob.getInt(ROBUSTNESS);
    }

    /**
     * Obtient un objet Mongo déstiné à être enregistré dans la base de données.
     *
     * @return l'objet Mongo
     */
    public DBObject toDBObject() {
        BasicDBObject ob = new BasicDBObject();
        ob.append(LIFE, this.life);
        ob.append(ATTACK, this.attack);
        ob.append(DEFENSE, this.defense);
        ob.append(INITIATIVE, this.initiative);
        ob.append(LUCK, this.luck);
        ob.append(ROBUSTNESS, this.robustness);
        return ob;
    }

    /**
     * Obtient un objet JSON déstiné à être envoyé par le sevice web.
     *
     * @return l'objet JSON
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject ob = new JSONObject();
        ob.put(LIFE, this.life);
        ob.put(ATTACK, this.attack);
        ob.put(DEFENSE, this.defense);
        ob.put(INITIATIVE, this.initiative);
        ob.put(LUCK, this.luck);
        ob.put(ROBUSTNESS, this.robustness);
        return ob;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getRobustness() {
        return robustness;
    }

    public void setRobustness(int robustness) {
        this.robustness = robustness;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
