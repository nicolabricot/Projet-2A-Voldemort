/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.item.UnexpectedItemException;
import fr.uha.projetvoldemort.ressource.Ressources;
import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author bruno
 */
@Path("/ressources/")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceRessources {
    
    @GET
    @Path("/fill")
    public Response fill() throws UnknownHostException {
        try {
            Ressources res = Ressources.getInstance();
            res.connect();
            res.fill();
            return Response.status(HttpStatus.CREATED).build();
            
        } catch (UnexpectedItemException e) {
            return Response.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } finally {
            Ressources.getInstance().close();
        }
    }
    
}
