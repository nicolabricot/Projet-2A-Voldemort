/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fight;

import fr.uha.projetvoldemort.fightreport.FightReport1v1;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.CharacterAttribute;
import fr.uha.projetvoldemort.faction.FactionAttribute;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemAttribute;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.item.ItemType;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONArray;

/**
 *
 * @author bruno
 */
public class Fight1v1 extends Fight {

    private Character attacker, defenser;
    private FightReport1v1 report;

    public Fight1v1(long seed, Character attacker, Character defenser) {
        super(seed);
        this.attacker = attacker;
        this.defenser = defenser;
        this.report = new FightReport1v1(this);
    }

    public Fight1v1(Character attacker, Character defenser) {
        super(new Date().getTime());
        this.attacker = attacker;
        this.defenser = defenser;
        this.report = new FightReport1v1(this);
    }

    public Character getAttacker() {
        return this.attacker;
    }

    public Character getDefesner() {
        return this.defenser;
    }

    private Character[] phaseInitiative() {
        int fate = super.nextRandom(this.attacker.getAttribute(CharacterAttribute.INITIATIVE) + this.defenser.getAttribute(CharacterAttribute.INITIATIVE));
        if (fate < this.attacker.getAttribute(CharacterAttribute.INITIATIVE)) {
            return new Character[]{this.attacker, this.defenser};
        } else {
            return new Character[]{this.defenser, this.attacker};
        }
    }

    private void phaseProjectile(Character first, Character second) {
        /* Dans cette phase, il faut décompter les projectiles utilisés pour le combat.
         * Le principe est simple, une unité pour l'intégralité du combat que l'on gagne,
         * perd, agit ou pas c est sans importance.
         */
        first.getInventory().remove(first.getActivePanoply().getItem(ItemType.PROJECTILE));
        second.getInventory().remove(second.getActivePanoply().getItem(ItemType.PROJECTILE));
    }

    private void phaseInitialThrowing(Character first, Character second) {
        // Alea = random (100)
        int fate = super.nextRandom(100);
        // Si (alea < premier.jetoffensif.déclanchement) alors
        Item of = first.getActivePanoply().getItem(ItemType.OFFENSIVE_THROWING);
        if (fate < of.getAttribute(ItemAttribute.TRIGGER)) {
            // Retire le jetoffensif de la panoplie du premier
            first.getActivePanoply().remove(of);
            // Alea = random (100)
            fate = super.nextRandom(100);
            // Si (alea < second.jetdéfensif.déclanchement) alors
            Item def = second.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING);
            if (fate < def.getAttribute(ItemAttribute.TRIGGER)) {
                // Retire le jetdéfensif de la panoplie du second
                second.getActivePanoply().remove(def);
                // fraction = jetOffensif.effet - jetdéfensif.effet
                int fraction = of.getAttribute(ItemAttribute.EFFECT) - def.getAttribute(ItemAttribute.EFFECT);
                // si (fraction > 0)
                if (fraction > 0) {
                    // Animation jetoffensif partiellement contré par défensif
                    //Second.vie *= (100-fraction)
                    int life = second.getAttribute(CharacterAttribute.LIFE);
                    life *= (100 - fraction);
                    second.setAttribute(CharacterAttribute.LIFE, life);
                } // sinon
                else {
                    // Animation jetoffensif totalement contré par défensif
                }

            } // sinon
            else {
                // Animation jetoffensif vainqueur
                // Second.vie *= (100-jetOffensif.effet)
                int life = second.getAttribute(CharacterAttribute.LIFE);
                life *= (100 - of.getAttribute(ItemAttribute.EFFECT));
                second.setAttribute(CharacterAttribute.LIFE, life);
            }
        }
    }

    public int phaseAttack(Character player) {
        // il faut vérifier si le joueur est assez habile pour viser l'adversaire.
        // Aléa = random( 100)
        int fate = super.nextRandom(100);
        // Si (aléa < joueur.habileté) alors
        if (fate < player.getAttribute(CharacterAttribute.ABILITY)) {
            // OK pour calculer le reste de la formule
            // Dégats = joueur.attribut[classe]
            int damage = player.getAttribute(CharacterAttribute.CLASS)
                    // + joueur.arme.attribut[classe]
                    + player.getActivePanoply().getItem(ItemType.WEAPON).getAttribute(ItemAttribute.CLASS)
                    // * (100+joueur.modif.attribut[classe]+joueur.modif.amé.attribut[classe])/100
                    * (100 + player.getActivePanoply().getItem(ItemType.WEAPON_MODIFIER).getAttribute(ItemAttribute.CLASS)
                    + player.getActivePanoply().getItem(ItemType.WEAPON_MODIFIER).getAmelioration().getAttribute(ItemAttribute.CLASS)) / 100;
            // + ∑(joueur.équip.attribut[classe]*(100+joueur.équip.amé.attribut[classe])/100)	
            Iterator<Item> itSustainables = player.getActivePanoply().getItems(ItemCategory.SUSTAINABLE).iterator();
            while (itSustainables.hasNext()) {
                Item item = itSustainables.next();
                damage += item.getAttribute(ItemAttribute.CLASS)
                        * (100 + item.getAmelioration().getAttribute(ItemAttribute.CLASS) / 100);
            }


            // Dégats *= joueur.projectile.attribut[classe]	
            damage *= player.getActivePanoply().getItem(ItemType.PROJECTILE).getAttribute(ItemAttribute.CLASS);

            // coup critique
            // Aléa = random( 100)
            fate = super.nextRandom(100);
            // Si (aléa < joueur.chance) alors
            if (fate < player.getAttribute(CharacterAttribute.LUCK)) {
                // Dégats *= ( 2 + (projectile.multCC+∑( joueur.stimulant.multCC)/100))
                damage *= (2 + player.getActivePanoply().getItem(ItemType.PROJECTILE).getAttribute(ItemAttribute.CRITICAL_HIT_MODIFIER)
                        + super.sum(player.getActivePanoply().getItems(ItemType.STIMULANT), ItemAttribute.CRITICAL_HIT_MODIFIER) / 100);
            }

            // normaliser en fonction de la classe du joueur
            // Dégats *= ( 100 - joueur.attaque + ∑( joueur.stimulant. attaque)) / 100
            damage *= (100 - player.getAttribute(CharacterAttribute.ATTACK)
                    + super.sum(player.getActivePanoply().getItems(ItemType.STIMULANT), ItemAttribute.ATTACK)) / 100;

            return damage;
        } else {
            // Dégats = 0 // echec du tir
            return 0;
        }
    }

    private int phaseDefense(Character player) {
        // Aléa = random( 100)
        int fate = super.nextRandom(100);
        if (fate < player.getAttribute(CharacterAttribute.DODGE)) {
            // OK pour calculer le reste de la formule
            // Dégats = joueur.attribut[classe]	
            int damage = player.getAttribute(CharacterAttribute.CLASS)
                    // joueur.bouclier.attribut[classe]
                    + player.getActivePanoply().getItem(ItemType.SHIELD).getAttribute(ItemAttribute.CLASS)
                    // *(100+joueur.modif.attribut[classe]+joueur.modif.amé.attribut[classe])/100
                    * (100 + player.getActivePanoply().getItem(ItemType.SHIELD_MODIFIER).getAttribute(ItemAttribute.CLASS)
                    + player.getActivePanoply().getItem(ItemType.SHIELD_MODIFIER).getAmelioration().getAttribute(ItemAttribute.CLASS)) / 100;
            // + ∑(joueur.équip.attribut[classe]*(100+joueur.équip.amé.attribut[classe])/100)
            Iterator<Item> itSustainables = player.getActivePanoply().getItems(ItemCategory.SUSTAINABLE).iterator();
            while (itSustainables.hasNext()) {
                Item item = itSustainables.next();
                damage += item.getAttribute(ItemAttribute.CLASS)
                        * (100 + item.getAmelioration().getAttribute(ItemAttribute.CLASS) / 100);
            }

            // normaliser en fonction de la classe du joueur
            // Dégats *= ( 100 - joueur.défense + ∑( joueur.stimulant.défense)) / 100
            damage *= (100 - player.getAttribute(CharacterAttribute.DEFENSE)
                    + super.sum(player.getActivePanoply().getItems(ItemType.STIMULANT), ItemAttribute.DEFENSE)) / 100;

            return damage;

        } else {
            // Dégats = 0 // esquive du tir
            return 0;
        }
    }

    private void phaseDamage(Character fist, Character second, int attackDamage, int defenseDamage) {
        // Si (dégât_attaque == 0) alors
        if (attackDamage == 0) {
            // Animation d'un tir rate
        } // Sinon si (dégats_défense == 0) alors
        else if (defenseDamage == 0) {
            // Animation d'un tir esquive
        } // Sinon si (dégât_attaque < dégats_défense) alors
        else if (attackDamage < defenseDamage) {
            // Animation d'un tir encaisse
        } else {
            // Animation d'un tir normal
            // Second.vie -= (dégât_attaque - dégats_défense)
            int life = second.getAttribute(CharacterAttribute.LIFE);
            life -= (attackDamage - defenseDamage);
            second.setAttribute(CharacterAttribute.LIFE, life);
        }
    }

    private void phaseFaction(Character player, Character second, int attackDamage, int defenseDamage) {
        // Joueur.faction.coup++
        int hit = player.getFaction().getAttribute(FactionAttribute.HIT);
        hit++;
        player.getFaction().setAttribute(FactionAttribute.HIT, hit);
        // Si (dégât_attaque == 0) alors
        if (attackDamage == 0) {
            // rien
        } // Sinon si (dégats_défense == 0) alors
        else if (defenseDamage == 0) {
            // rien
        } // Sinon si (dégât_attaque < dégats_défense) alors
        else if (attackDamage < defenseDamage) {
            // rien
        } else {
            // Aléa = random( 100)
            int fate = super.nextRandom(100);
            // Si (aléa < joueur.faction.chance) alors
            if (fate < player.getFaction().getAttribute(FactionAttribute.LUCK)) {
                // on va appliquer les calculs
                // Selon la faction est
                switch (player.getFaction().getType()) {
                    // Lycantrope :
                    case WEREWOLF:
                        // Delta = dégât_attaque * faction.pouvoir/100
                        int delta = attackDamage * player.getFaction().getAttribute(FactionAttribute.POWER) / 100;
                        // Second.vie -= delta
                        int life = second.getAttribute(CharacterAttribute.LIFE);
                        life -= delta;
                        second.setAttribute(CharacterAttribute.LIFE, life);
                        // Animation d'un lycantrope
                        break;
                    // Vampire :
                    case VAMPIRE:
                        // Delta = dégât_attaque * faction.pouvoir/100
                        delta = attackDamage * player.getFaction().getAttribute(FactionAttribute.POWER)/100;
                        // Second.vie -= delta/2
                        life = second.getAttribute(CharacterAttribute.LIFE);
                        life -= delta/2;
                        second.setAttribute(CharacterAttribute.LIFE, life);
                        // Premier.vie +=delta/2
                        life = player.getAttribute(CharacterAttribute.LIFE);
                        life += delta / 2;
                        player.setAttribute(CharacterAttribute.LIFE, life);
                        // Animation d'un vampire
                        break;
                    // Momie
                    case MUMMY:
                        // Delta = dégât_attaque * faction.pouvoir/100
                        delta = attackDamage * player.getFaction().getAttribute(FactionAttribute.POWER)/100;
                        // Premier.vie += delta
                        life = player.getAttribute(CharacterAttribute.LIFE);
                        life += delta;
                        player.setAttribute(CharacterAttribute.LIFE, life);
                        // Animation d'une mommie
                        break;
                }
            }


        }
   }

    private void phaseOffensiveThrowing(Character player, Character opponent) {
        // Aléa = random(100)
        int fate = super.nextRandom(100);
        // Si (aléa < joueur.jetcombats.chance) alors
        if (fate < player.getActivePanoply().getItem(ItemType.OFFENSIVE_THROWING).getAttribute(ItemAttribute.LUCK)) {
            // retire un élément jetcombats
            player.getInventory().remove(player.getActivePanoply().getItem(ItemType.OFFENSIVE_THROWING));
            // dégatsOffensif = joueur.jetcombats.attaque
            int offensiveDamage = player.getActivePanoply().getItem(ItemType.OFFENSIVE_THROWING).getAttribute(ItemAttribute.ATTACK);
            int defensiveDamage;
            // si adversaire.jetdéfenses.actif > 0 alors
            if (opponent.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.ACTIVE) > 0) {
                // dégatsDéfensif = 0
                defensiveDamage = 0;
            } else {
                // dégatsDéfensif = adversaire.jetdéfenses.défense
                defensiveDamage = opponent.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.DEFENSE);
            }
            // si (dégatsOffensif > dégatsDéfensif) alors
            if (offensiveDamage > defensiveDamage) {
                // animation d'un jet jetcombats gagnant
                // adversaire.vie -= dégatsDéfensif
                int life = opponent.getAttribute(CharacterAttribute.LIFE);
                life -= defensiveDamage;
                opponent.setAttribute(CharacterAttribute.LIFE, life);
            } else {
                // animation d'un jet jetcombats gaspille
            }
        }
    }

    private void phaseDefensiveThrowing(Character player) {
        // Si joueur.jetdefenses.actif = 0 alors
        if (player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.ACTIVE) > 0) {
            // Aléa = random(100)
            int fate = super.nextRandom(100);
            // Si (aléa < joueur.jetdefenses.chance) alors
            if (fate < player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.LUCK)) {
                // joueur.jetdefenses.actif = joueur.jetdefenses.durée
                int duration = player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.DURATION);
                player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).setAttribute(ItemAttribute.ACTIVE, duration);
                // retire un élément jetdefenses
                player.getInventory().remove(player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING));
                //animation d'un jet défense
            }
        } else {
            // --joueur.jetdefenses.actif
            int active = player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).getAttribute(ItemAttribute.ACTIVE);
            active--;
            player.getActivePanoply().getItem(ItemType.DEFENSIVE_THROWING).setAttribute(ItemAttribute.ACTIVE, active);
        }

    }

    private void phaseCare(Character player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Si soins[tranche].nombre > 0 alors

        // Retire un élément dans la tranche
        // Vie *= (100 + soins[tranche].quota)/100
        // fin si
    }

    private Character[] phaseInversion(Character first, Character second) {
        return new Character[]{second, first};
    }

    private Character forThoseAboutToDieWeSaluteYou() {
        // Premier,second = Phase initiatives (attaquant, défenseur)
        Character[] c = this.phaseInitiative();
        Character first = c[0];
        Character second = c[1];
        // Phase projectiles (premier, second)
        this.phaseProjectile(first, second);
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
            // Phase jets combats (premier)
            this.phaseOffensiveThrowing(first, second);
            // Phase jets défenses (premier)
            this.phaseDefensiveThrowing(first);
            // Phase soins (premier)
            this.phaseCare(first);
            //Premier,second = Phase inversion (premier, second)
            c = this.phaseInversion(first, second);
            first = c[0];
            second = c[1];

        } while (!this.attacker.isDead() && !this.defenser.isDead());
        // Retourne victoire ou défaite
        if (!this.attacker.isDead()) {
            return this.attacker;
        } else {
            return this.defenser;
        }
    }

    @Override
    public JSONArray getReport() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }
}
