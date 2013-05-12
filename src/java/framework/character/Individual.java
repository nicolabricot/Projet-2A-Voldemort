/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.character;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import framework.ressource.Ressources;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
public final class Individual  {
    
    public static final String COLLECTION = "character_individual";
    
    private static final String ID = "_id";
    private static final String CLASS_ID = "class_id";
    private static final String NAME = "name";
    private static final String LIFE = "life";
    private static final String ATTACK = "attack";
    private static final String DEFENSE = "defense";
    private static final String INITIATIVE = "initiative";
    private static final String LUCK = "luck";
    private static final String ROBUSTNESS = "robustness";
    private static final String ARMOR = "armor";
    
    private ObjectId id;
    private String name;

    private int life, attack, defense, initiative, luck, robustness;
    private Armor armor;
    private CharacterClass characterClass;

    public Individual(ObjectId oid) {                
        Ressources res = Ressources.getInstance();
        BasicDBObject ob = (BasicDBObject) res.getCollection(COLLECTION).findOne(oid);
        this.hydrate(ob);
    }
    
    public Individual(DBObject ob) {
        this.hydrate((BasicDBObject) ob);
        this.armor = new Armor();
    }
    
    private void hydrate(BasicDBObject ob) {
        this.id = ob.getObjectId(ID);
        this.name = ob.getString(NAME);
        this.life = ob.getInt(LIFE);
        this.attack = ob.getInt(ATTACK);
        this.defense = ob.getInt(DEFENSE);
        this.initiative = ob.getInt(INITIATIVE);
        this.luck = ob.getInt(LUCK);
        this.robustness = ob.getInt(ROBUSTNESS);
        // this.armor = new Armor((BasicDBObject) ob.get(ARMOR));
        this.characterClass = new CharacterClass(ob.getObjectId(CLASS_ID));
    }  
    
    public BasicDBObject toBasicDBObject() {
        BasicDBObject ob = new BasicDBObject();
        
        if (this.id != null) ob.append(ID, this.id);
        ob.append(NAME, this.name);
        ob.append(LIFE, this.life);
        ob.append(ATTACK, this.attack);
        ob.append(DEFENSE, this.defense);
        ob.append(INITIATIVE, this.initiative);
        ob.append(LUCK, this.luck);
        ob.append(ROBUSTNESS, this.robustness);
        // ob.append(ARMOR, this.armor.toBasicDBObject());
        ob.append(CLASS_ID, this.characterClass.getId());
        
        return ob;
    }
    
    public void save() {
        BasicDBObject ob = this.toBasicDBObject();
        Ressources.getInstance().getCollection(COLLECTION).insert(ob);
        this.id = ob.getObjectId(ID);
    }
    
    public Individual() {
    }
    
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
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

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
