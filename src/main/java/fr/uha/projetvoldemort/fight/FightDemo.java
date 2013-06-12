/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fight;

import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.CharacterAttribute;
import fr.uha.projetvoldemort.faction.FactionAttribute;
import fr.uha.projetvoldemort.fightreport.FightReport;
import fr.uha.projetvoldemort.fightreport.FightReportDemo;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import fr.uha.projetvoldemort.item.ItemType;
import org.json.JSONException;

/**
 *
 * @author bruno
 */
public class FightDemo extends Fight {

    private Character attacker, defenser;
    private FightReportDemo report;

    public FightDemo(Character attacker, Character defenser) {
        this.attacker = attacker;
        this.defenser = defenser;
        this.report = new FightReportDemo(this);
    }

    public Character getAttacker() {
        return this.attacker;
    }

    public Character getDefenser() {
        return this.defenser;
    }

    private Character[] phaseInitiative() throws JSONException {
        int fate = super.nextRandom(this.attacker.getAttribute(CharacterAttribute.INITIATIVE) + this.defenser.getAttribute(CharacterAttribute.INITIATIVE));
        Character[] c;

        if (fate < this.attacker.getAttribute(CharacterAttribute.INITIATIVE)) {
            c = new Character[]{this.attacker, this.defenser};
        } else {
            c = new Character[]{this.defenser, this.attacker};
        }

        this.report.phaseInitiative(c[0]);
        return c;
    }

    private void phaseInitialThrowing(Character first, Character second) throws JSONException {
        // Si (alea < premier.jetoffensif.déclanchement) alors
        Item of = first.getActivePanoply().getItem(ItemType.OFFENSIVE_THROWING);
        Item def = second.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING);

        int attackOf = of.getAttribute(ItemAttribute.ATTACK);
        attackOf += super.nextRandom(-5, 5);

        int attackDef = def.getAttribute(ItemAttribute.ATTACK);
        attackDef += super.nextRandom(-5, 5);

        if (attackOf > attackDef) {
            int life = second.getAttribute(CharacterAttribute.LIFE);
            life -= (attackOf - attackDef);
            second.setAttribute(CharacterAttribute.LIFE, life);
        } else if (attackDef > attackOf) {
            int life = first.getAttribute(CharacterAttribute.LIFE);
            life -= (attackDef - attackOf);
            first.setAttribute(CharacterAttribute.LIFE, life);
        }

        this.report.phaseInitialThrowing(first, of, attackOf, second, def, attackDef);
    }

    public int phaseAttack(Character player) {
        int attack = player.getAttribute(CharacterAttribute.ATTACK) + player.getActivePanoply().getItem(ItemType.WEAPON).getAttribute(ItemAttribute.ATTACK);
        attack += super.nextRandom(-5, 5);
        return attack;
    }

    private int phaseDefense(Character player) {
        int defense = player.getAttribute(CharacterAttribute.DEFENSE) + player.getActivePanoply().getItem(ItemType.SHIELD).getAttribute(ItemAttribute.DEFENSE);
        defense += super.nextRandom(-5, 5);
        return defense;
    }

    private void phaseDamage(Character first, Character second, int attack, int defense) throws JSONException {
        if (attack > defense) {
            int life = second.getAttribute(CharacterAttribute.LIFE);
            life -= (attack - defense);
            second.setAttribute(CharacterAttribute.LIFE, life);
        }
        this.report.phaseDamage(first, attack, second, defense);
    }

    private void phaseFaction(Character player, Character second, int attack, int defense) throws JSONException {
        // Joueur.faction.coup++
        int hit = player.getFaction().getAttribute(FactionAttribute.HIT);
        hit++;
        player.getFaction().setAttribute(FactionAttribute.HIT, hit);
        
        int delta = 0;
        
        if (attack > defense) {
            switch (player.getFaction().getType()) {
                    case WEREWOLF:
                        delta = super.nextRandom(player.getFaction().getAttribute(FactionAttribute.POWER));
                        // Second.vie -= delta
                        int life = second.getAttribute(CharacterAttribute.LIFE);
                        life -= delta;
                        second.setAttribute(CharacterAttribute.LIFE, life);                        
                        break;
                    // Vampire :
                    case VAMPIRE:
                        delta = super.nextRandom(player.getFaction().getAttribute(FactionAttribute.POWER));
                        // Second.vie -= delta/2
                        life = second.getAttribute(CharacterAttribute.LIFE);
                        life -= delta / 2;
                        second.setAttribute(CharacterAttribute.LIFE, life);
                        // Premier.vie +=delta/2
                        life = player.getAttribute(CharacterAttribute.LIFE);
                        life += delta / 2;
                        player.setAttribute(CharacterAttribute.LIFE, life);
                        break;
                    // Momie
                    case MUMMY:
                        delta = super.nextRandom(player.getFaction().getAttribute(FactionAttribute.POWER));
                        // Premier.vie += delta
                        life = player.getAttribute(CharacterAttribute.LIFE);
                        life += delta;
                        player.setAttribute(CharacterAttribute.LIFE, life);
                        break;
                }
        }
        
        this.report.phaseFaction(player.getFaction(), player, second, delta);
    }

    private Character[] phaseInversion(Character first, Character second) {
        return new Character[]{second, first};
    }

    @Override
    public void AveCaesarMorituriTeSalutant() throws JSONException {
        int loop = 0;

        // Premier,second = Phase initiatives (attaquant, défenseur)
        Character[] c = this.phaseInitiative();
        Character first = c[0];
        Character second = c[1];
        // Phase jets initiaux (premier)
        this.phaseInitialThrowing(first, second);
        do {
            // DégatsAttaque = Phase attaque (premier)
            int attackDamage = this.phaseAttack(first);
            // DégatsDéfense = Phase défense (second)
            int defenseDamage = this.phaseDefense(second);
            // Phase dégats (premier, second, DégatsAttaque, DégatsDéfense)
            this.phaseDamage(first, second, attackDamage, defenseDamage);
            // Phase faction (premier, second, DégatsAttaque, DégatsDéfense)
            this.phaseFaction(first, second, attackDamage, defenseDamage);
            // Phase soins (premier)
            //Premier,second = Phase inversion (premier, second)
            c = this.phaseInversion(first, second);
            first = c[0];
            second = c[1];

            if (++loop == 1000) {
                break;
            }

        } while (!this.attacker.isDead() && !this.defenser.isDead());
        // Retourne victoire ou défaite
        if (!this.attacker.isDead()) {
            
            this.attacker.setLevel(this.attacker.getLevel()+1);
            this.attacker.setAttribute(CharacterAttribute.LIFE, 100);
            this.attacker.save();
            
            this.report.setWinner(this.attacker);
        } else {
            this.defenser.setLevel(this.defenser.getLevel()+1);
            this.defenser.setAttribute(CharacterAttribute.LIFE, 100);
            this.defenser.save();
            this.report.setWinner(this.defenser);
        }

        // Sauvegarde le raport de combat dans la BDD
        this.report.save();
    }

    @Override
    public FightReport getReport() {
        return this.report;
    }
}
