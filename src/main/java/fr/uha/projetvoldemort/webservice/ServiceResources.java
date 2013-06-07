/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.resource.Resources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author bruno
 */
@Path("/ressources/")
public class ServiceResources {
    
    @GET
    @Path("/fill")
    public Response fill() {
        try {
            Resources res = Resources.getInstance();
            res.connect();
            res.fill();
            return Response.status(HttpStatus.CREATED).build();
            
        } finally {
            Resources.getInstance().close();
        }
    }
    
}
