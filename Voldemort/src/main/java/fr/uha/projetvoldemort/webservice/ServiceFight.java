/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.fight.Fight;
import fr.uha.projetvoldemort.fight.Fight1v1;
import fr.uha.projetvoldemort.fight.FightDemo;
import fr.uha.projetvoldemort.fightreport.FightReport;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bruno
 */
@Path("/fight")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceFight {

    @GET
    public Response getReports() {
        try {
            Resources.getInstance().connect();
            DBCollection coll = Resources.getInstance().getCollection(FightReport.COLLECTION);
            DBCursor cursor = coll.find();
            JSONArray a = new JSONArray();
            while (cursor.hasNext()) {
                ObjectId id = (ObjectId) cursor.next().get("_id");
                a.put(id.toString());
            }
            return Response.status(HttpStatus.OK).entity(a.toString()).build();
        } finally {
            Resources.getInstance().close();
        }
    }

    @GET
    @Path("/{id}")
    public Response fight(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();

            DBCollection coll = Resources.getInstance().getCollection(Character.COLLECTION);
            DBCursor cursor = coll.find();

            Character c1 = new Character((ObjectId) cursor.next().get("_id"));
            Character c2 = new Character((ObjectId) cursor.next().get("_id"));

            Fight f;

            if (Fight1v1.class.getSimpleName().equals(id)) {
                f = new Fight1v1(c1, c2);
            } else if (FightDemo.class.getSimpleName().equals(id)) {
                f = new FightDemo(c1, c2);
            } else {
                f = new FightDemo(c1, c2);
            }
            
            f.AveCaesarMorituriTeSalutant();
            FightReport fr = f.getReport();

            return Response.status(HttpStatus.OK).entity(fr.toJSONObject().toString()).build();
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
    @Path("/report/{id}")
    public Response getReport(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();

            BasicDBObject ob = (BasicDBObject) Resources.getInstance().getCollection(FightReport.COLLECTION).findOne(new ObjectId(id));
            if (ob == null) {
                throw new NotFoundException();
            }

            JSONObject o = new JSONObject();
            o.put("id", ob.getObjectId("_id").toString());            
            o.put("report", new JSONObject(ob.getString("report")));
            
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
