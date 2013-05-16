/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import fr.uha.projetvoldemort.item.ItemModel;
import fr.uha.projetvoldemort.ressource.RessourceNotFoundException;
import fr.uha.projetvoldemort.ressource.Ressources;
import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;

/**
 *
 * @author bruno
 */
@Path("/itemmodel/")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceItemModel {

    @GET
    @Path("/all")
    public Response getAll() throws UnknownHostException {
        Ressources res = Ressources.getInstance();
        res.connect();
        DBCollection coll = res.getCollection(ItemModel.COLLECTION);
        DBCursor cursor = coll.find();
        BasicDBList l = new BasicDBList();
        while (cursor.hasNext()) {
            l.add(cursor.next());
        }
        res.close();
        return Response.status(HttpStatus.OK).entity(l.toString()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) throws UnknownHostException {
        try {
            Ressources.getInstance().connect();
            ItemModel im = new ItemModel(new ObjectId(id));
            return Response.status(HttpStatus.OK).entity(im.toDBObject().toString()).build();
        } catch (RessourceNotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }
    }
}
