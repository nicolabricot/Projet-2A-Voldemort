/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.item.UnexpectedItemException;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ServiceResources {
    
    @GET
    @Path("/fill")
    public Response fill() {
        try {
            Resources res = Resources.getInstance();
            res.connect();
            res.fill();
            return Response.status(HttpStatus.CREATED).build();
            
        } catch (UnexpectedItemException ex) {
            Logger.getLogger(ServiceResources.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
}
