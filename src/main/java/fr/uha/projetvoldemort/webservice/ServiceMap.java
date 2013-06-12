/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.fight.FightDemo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("/states/{id_character}")
    public Response getStates() {
        String url = "./map.jsp?map=";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"champagne-ardenne\": [{\"type\": \"opened\", \"title\": \"Champagne-Ardenne\", \"link\":\"" + url + "champagne-ardenne\", \"description\": \"Want to drink a glass of Champagne?\"}],");
        sb.append("\"alsace\": [{\"type\": \"opened\", \"title\": \"Alsace\", \"link\": \"" + url + "alsace\", \"description\": \"Welcome in Alsace\"}],");
        sb.append("\"centre\": [{\"type\": \"locked\", \"title\": \"Centre\", \"link\": \"" + url + "alsace\", \"description\": \"Le Centre est une région française, qui regroupe six départements : le Cher, l'Eure-et-Loir, l'Indre, l'Indre-et-Loire, le Loir-et-Cher et le Loiret.\"}],");
        sb.append("\"corse\": [{\"type\": \"done\", \"title\": \"Corse\", \"description\": \"Corse, ma belle Corse, que veux-tu faire par monts et par vaux...\"}],");
        sb.append("\"ile-de-france\": [{\"type\": \"done\", \"title\": \"Île de France\"}]");
        sb.append("}");

        return Response.status(200).entity(sb.toString()).build();
    }

    @GET
    @Path("/states/{id}/{id_character}")
    public Response getStates(@PathParam("id") String id) {
        String pano = "./panoply.jsp?panoply=";
        String combat = "./rest/fight/";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if ("alsace".equals(id)) {
            sb.append("\"bas-rhin\": [{\"type\": \"opened\", \"link\": \"\", \"description\": \"Mulhouse\"}],");
            sb.append("\"haut-rhin\": [{\"type\": \"opened\", \"link\": \"\", \"description\": \"Mulhouse\"}]");
        } else if ("champagne-ardenne".equals(id)) {
            sb.append("\"marne\": [{\"type\": \"closed\", \"link\": \"\", \"description\": \"Strasbourg\"}],");
            sb.append("\"aube\": [{\"type\": \"closed\", \"link\": \"\", \"description\": \"Strasbourg\"}],");
            sb.append("\"ardennes\": [{\"type\": \"opened\", \"title\": \"Combat 1v1\", \"link\": \"" + combat + FightDemo.class.getSimpleName() + "\", \"description\": \"Kill one\"}],");
            sb.append("\"haute-marne\" : [{\"type\": \"opened\", \"title\": \"Panoply\", \"link\": \"" + pano + "active\", \"description\": \"Bla bla bla\"}]");
        } else {
            return Response.status(HttpStatus.NOT_FOUND).build();
        }
        sb.append("}");

        return Response.status(200).entity(sb.toString()).build();
    }
}
