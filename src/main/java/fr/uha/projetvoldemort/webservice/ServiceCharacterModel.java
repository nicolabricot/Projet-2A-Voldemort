/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import fr.uha.projetvoldemort.character.CharacterModel;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.resource.Resources;
import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author bruno
 */
@Path("/charactermodel")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceCharacterModel {

    @GET
    @Path("/all")
    public Response getAll() throws UnknownHostException, JSONException {
        Resources res = Resources.getInstance();
        res.connect();
        DBCollection coll = res.getCollection(CharacterModel.COLLECTION);
        DBCursor cursor = coll.find();
        JSONArray a = new JSONArray();
        while (cursor.hasNext()) {
            ObjectId id = (ObjectId) cursor.next().get("_id");
            a.put(new CharacterModel(id).toJSONObject());
        }
        res.close();
        return Response.status(HttpStatus.OK).entity(a.toString()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) throws UnknownHostException, JSONException {
        try {
            Resources.getInstance().connect();
            CharacterModel cm = new CharacterModel(new ObjectId(id));
            return Response.status(HttpStatus.OK).entity(cm.toJSONObject().toString()).build();
        } catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
}
