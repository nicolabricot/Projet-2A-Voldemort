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
public final class Attributes {

    public static final String ATTRIBUTES = "attributes";
    public static final String LIFE = "life";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String INITIATIVE = "initiative";
    public static final String LUCK = "luck";
    public static final String STRENGTH = "strength";
    public static final String INTELLIGENCE = "intelligence";
    public static final String AGILITY = "agility";
    public static final String STEALTH = "stealth";
    public static final String ABILITY = "ability";
    private int life, attack, defense, initiative, luck, strength, intelligence, agility, stealth, ability;

    public Attributes(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
    }
    
    public Attributes() {
    }

    private void hydrate(BasicDBObject ob) {
        this.life = ob.getInt(LIFE);
        this.attack = ob.getInt(ATTACK);
        this.defense = ob.getInt(DEFENSE);
        this.initiative = ob.getInt(INITIATIVE);
        this.luck = ob.getInt(LUCK);
        
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
        ob.append(STRENGTH, this.strength);
        ob.append(INTELLIGENCE, this.intelligence);
        ob.append(AGILITY, this.agility);
        ob.append(STEALTH, this.stealth);
        ob.append(ABILITY, this.ability);
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
        ob.put(STRENGTH, this.strength);
        ob.put(INTELLIGENCE, this.intelligence);
        ob.put(AGILITY, this.agility);
        ob.put(STEALTH, this.stealth);
        ob.put(ABILITY, this.ability);
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
    
        public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }
    
}
