/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.character.Character;
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
    public Response getById(@PathParam("id") String id) throws UnknownHostException {
        Ressources.getInstance().connect();
        Character c = new Character(new ObjectId(id));
        Ressources.getInstance().close();
        return Response.status(200).entity(c.toDBObject().toString()).build();  
    }
    
}
