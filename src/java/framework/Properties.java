package framework;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
    
    private static final String LIFE = "life";
    private static final String ATTACK = "attack";
    private static final String DEFENSE = "defense";
    private static final String INITIATIVE = "initiative";
    private static final String LUCK = "luck";
    private static final String ROBUSTNESS = "robustness";
    
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
