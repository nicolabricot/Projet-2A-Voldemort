/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.map.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.uha.projetvoldemort.resource.Resources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
@Path("/map")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceMap {
    
    /*
     * States:
     * - closed (= by default): region is closed. Need to finish other opened regions first
     * - opened: region is opened. Quests can be done.
     * - done: region is terminated. All quests were done.
     * - locked: need to pay to open the region.
     */

    @GET
    @Path("/{id_character}")
    public Response getStates(@PathParam("id_character") String idc) {        
        try {
            Resources.getInstance().connect();
            
            Character c = new Character(new ObjectId(idc));
            Map map = new Map("main");
            
            JSONObject o = map.getStates(c);
            
            return Response.status(HttpStatus.OK).entity(o.toString()).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } catch (JSONException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            Resources.getInstance().close();
        }
    }

    @GET
    @Path("/{id_map}/{id_character}")
    public Response getStates(@PathParam("id_map") String idm, @PathParam("id_character") String idc) {
        try {
            Resources.getInstance().connect();
            
            Character c = new Character(new ObjectId(idc));
            Map map = new Map(idm);
            
            JSONObject o = map.getStates(c);
            
            return Response.status(HttpStatus.OK).entity(o.toString()).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } catch (JSONException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            Resources.getInstance().close();
        }
    }
}
