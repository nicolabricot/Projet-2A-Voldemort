/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.Equipment;
import fr.uha.projetvoldemort.character.Inventory;
import fr.uha.projetvoldemort.ressource.RessourceNotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
@Path("/character")
public class ServiceCharacter {
    
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) throws UnknownHostException {
       try {
            Ressources.getInstance().connect();
            Character c = new Character(new ObjectId(id));
            return Response.status(HttpStatus.OK).entity(c.toDBObject().toString()).build();
        }
        catch (RessourceNotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }
    }
    
    @GET
    @Path("/{id}/equipment")
    public Response getEquipment(@PathParam("id") String id) throws UnknownHostException {
        try {
            Ressources.getInstance().connect();
            Equipment e = new Character(new ObjectId(id)).getEquipment();
            return Response.status(HttpStatus.OK).entity(e.toDBObject().toString()).build();
        }
        catch (RessourceNotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }
    }
    
    @GET
    @Path("/{id}/inventory")
    public Response getInventory(@PathParam("id") String id) throws UnknownHostException {
        try {
            Ressources.getInstance().connect();
            Inventory i = new Character(new ObjectId(id)).getInventory();
            return Response.status(HttpStatus.OK).entity(i.toDBObject().toString()).build();
        }
        catch (RessourceNotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }   
    }
    
    @GET
    @Path("/{id}/stats")
    public Response getById(@PathParam("id") String id) throws UnknownHostException {
        return Response.status(HttpStatus.NOT_IMPLEMENTED).build();  
    }
    
}
