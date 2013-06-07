/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.fightreport;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
public abstract class FightReport {
    
    public abstract JSONObject getReport() throws JSONException ;
}
