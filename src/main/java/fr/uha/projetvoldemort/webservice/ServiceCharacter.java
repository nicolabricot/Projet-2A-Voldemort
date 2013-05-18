/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.Inventory;
import fr.uha.projetvoldemort.NotFoundException;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemUsage;
import fr.uha.projetvoldemort.ressource.Ressources;
import java.net.UnknownHostException;
import java.util.Iterator;
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
@Path("/character")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceCharacter {
    
    @GET
    @Path("/all")
    public Response getAll() throws UnknownHostException, JSONException {

        Ressources res = Ressources.getInstance();
        res.connect();
        DBCollection coll = res.getCollection(Character.COLLECTION);
        DBCursor cursor = coll.find();
        JSONArray a = new JSONArray();
        while (cursor.hasNext()) {
            ObjectId id = (ObjectId) cursor.next().get("_id");
            a.put(new Character(id).toJSONObject());
        }
        res.close();
        return Response.status(HttpStatus.OK).entity(a.toString()).build();

    }
    
    @GET
    @Path("/ids")
    public Response getIds() throws UnknownHostException, JSONException {

        Ressources res = Ressources.getInstance();
        res.connect();
        DBCollection coll = res.getCollection(Character.COLLECTION);
        DBCursor cursor = coll.find();
        JSONArray a = new JSONArray();
        while (cursor.hasNext()) {
            ObjectId id = (ObjectId) cursor.next().get("_id");
            a.put(id.toString());
        }
        res.close();
        return Response.status(HttpStatus.OK).entity(a.toString()).build();

    }
    
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) throws UnknownHostException, JSONException {
       try {
            Ressources.getInstance().connect();
            Character c = new Character(new ObjectId(id));
            return Response.status(HttpStatus.OK).entity(c.toJSONObject().toString()).build();
        }
        catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }
    }
    
    @GET
    @Path("/{id}/equipment")
    public Response getEquipment(@PathParam("id") String id) throws UnknownHostException, JSONException {
        return Response.status(HttpStatus.NOT_IMPLEMENTED).build();
        /*
        try {
            Ressources.getInstance().connect();
            Panoply e = new Character(new ObjectId(id)).getEquipment();
            return Response.status(HttpStatus.OK).entity(e.toJSONObject().toString()).build();
        }
        catch (RessourceNotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }*/
    }
    
    @GET
    @Path("/{id}/inventory")
    public Response getInventory(@PathParam("id") String id) throws UnknownHostException, JSONException {
        try {
            Ressources.getInstance().connect();
            Inventory i = new Character(new ObjectId(id)).getInventory();
            return Response.status(HttpStatus.OK).entity(i.toJSONArray().toString()).build();
        }
        catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }   
    }
    
    @GET
    @Path("/{id}/sustainables")
    public Response getSustainables(@PathParam("id") String id) throws UnknownHostException, JSONException {
        try {
            Ressources.getInstance().connect();
            JSONArray a = new JSONArray();
            
            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemUsage.SUSTAINABLE).iterator();
            while (it.hasNext())
                a.put(it.next().toJSONObject());

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
        }
        catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }   
    }
    
    @GET
    @Path("/{id}/consumables")
    public Response getConsumables(@PathParam("id") String id) throws UnknownHostException, JSONException {
        try {
            Ressources.getInstance().connect();
            JSONArray a = new JSONArray();
            
            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemUsage.CONSUMABLE).iterator();
            while (it.hasNext())
                a.put(it.next().toJSONObject());

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
        }
        catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }   
    }
    
    @GET
    @Path("/{id}/degradables")
    public Response getDegradables(@PathParam("id") String id) throws UnknownHostException, JSONException {
        try {
            Ressources.getInstance().connect();
            JSONArray a = new JSONArray();
            
            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemUsage.DEGRADABLE).iterator();
            while (it.hasNext())
                a.put(it.next().toJSONObject());

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
        }
        catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Ressources.getInstance().close();
        }   
    }
    
    @GET
    @Path("/{id}/stats")
    public Response getStats(@PathParam("id") String id) throws UnknownHostException {
        return Response.status(HttpStatus.NOT_IMPLEMENTED).build();  
    }
    
}
