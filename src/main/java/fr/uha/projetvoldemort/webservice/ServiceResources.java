/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.resource.Resources;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.json.JSONException;

/**
 *
 * @author bruno
 */
@Path("/resources/")
public class ServiceResources {
    
    @GET
    @Path("/fill")
    public Response fill() {
        try {
            Resources res = Resources.getInstance();
            res.connect();
            res.fill();
            return Response.status(HttpStatus.CREATED).build();
            
        } catch (JSONException ex) {
            Logger.getLogger(ServiceResources.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
}
