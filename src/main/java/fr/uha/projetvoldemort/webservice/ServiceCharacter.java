/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uha.projetvoldemort.webservice;

import fr.uha.projetvoldemort.exception.NotAllowedException;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import fr.uha.projetvoldemort.character.Character;
import fr.uha.projetvoldemort.character.Inventory;
import fr.uha.projetvoldemort.exception.NotFoundException;
import fr.uha.projetvoldemort.character.Panoply;
import fr.uha.projetvoldemort.item.Item;
import fr.uha.projetvoldemort.item.ItemCategory;
import fr.uha.projetvoldemort.resource.Resources;
import java.util.Iterator;
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

/**
 *
 * @author bruno
 */
@Path("/character")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ServiceCharacter {

    @GET
    @Path("/all")
    public Response getCharacters() {
        Resources res = Resources.getInstance();
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
    public Response get(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();
            Character c = new Character(new ObjectId(id));
            return Response.status(HttpStatus.OK).entity(c.toJSONObject().toString()).build();
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
    @Path("/{id}/inventory")
    public Response getInventory(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();
            Inventory i = new Character(new ObjectId(id)).getInventory();
            return Response.status(HttpStatus.OK).entity(i.toJSONArray().toString()).build();
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
    @Path("/{id}/inventory/sustainables")
    public Response getInventorySustainables(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemCategory.SUSTAINABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/inventory/consumables")
    public Response getConsumables(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemCategory.CONSUMABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/inventory/degradables")
    public Response getDegradables(@PathParam("id") String id) {
        try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getInventory().getItems(ItemCategory.DEGRADABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/panoply/all")
    public Response getPanoplies(@PathParam("id") String id) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Panoply> it = new Character(new ObjectId(id)).getPanoplies().iterator();
            while (it.hasNext()) {
                a.put(it.next().getId().toString());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
    @GET
    @Path("/{idC}/panoply/{idP}")
    public Response getPanoplyItems(@PathParam("idC") String idC, @PathParam("idP") String idP) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(idC)).getPanoply(new ObjectId(idP)).getItems().iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{idC}/panoply/{idP}/sustainables")
    public Response getPanoplySustainables(@PathParam("idC") String idC, @PathParam("idP") String idP) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(idC)).getPanoply(new ObjectId(idP)).getItems(ItemCategory.SUSTAINABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{idC}/panoply/{idP}/consumables")
    public Response getPanoplyConsumables(@PathParam("idC") String idC, @PathParam("idP") String idP) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(idC)).getPanoply(new ObjectId(idP)).getItems(ItemCategory.CONSUMABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{idC}/panoply/{idP}/degradables")
    public Response getPanoplyDegradables(@PathParam("idC") String idC, @PathParam("idP") String idP) {
                 try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(idC)).getPanoply(new ObjectId(idP)).getItems(ItemCategory.DEGRADABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/panoply/active")
    public Response getActivePanoplyItems(@PathParam("id") String id) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getActivePanoply().getItems().iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/panoply/active/sustainables")
    public Response getActivePanoplySustainables(@PathParam("id") String id) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getActivePanoply().getItems(ItemCategory.SUSTAINABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/panoply/active/consumables")
    public Response getActivePanoplyConsumables(@PathParam("id") String id) {
         try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getActivePanoply().getItems(ItemCategory.CONSUMABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/panoply/active/degradables")
    public Response getActivePanoplyDegradables(@PathParam("id") String id) {
                 try {
            Resources.getInstance().connect();
            JSONArray a = new JSONArray();

            Iterator<Item> it = new Character(new ObjectId(id)).getActivePanoply().getItems(ItemCategory.DEGRADABLE).iterator();
            while (it.hasNext()) {
                a.put(it.next().toJSONObject());
            }

            return Response.status(HttpStatus.OK).entity(a.toString()).build();
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
    @Path("/{id}/statistics")
    public Response getStats(@PathParam("id") String id) {
        return Response.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
    @Path("/{idC}/panoply/{idP}/remove/{idM}")
    public Response removeFromPanoply(@PathParam("idC") String idC, @PathParam("idP") String idP, @PathParam("idM") String idM) {
         try {
            Resources.getInstance().connect();

            Character c = new Character(new ObjectId(idC));
            Item i = c.getInventory().getItem(new ObjectId(idM));
            Panoply p = c.getPanoply(new ObjectId(idP));
            p.remove(i);
            p.save();
 
            return Response.status(HttpStatus.OK).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
    @Path("/{idC}/panoply/active/remove/{idM}")
    public Response removeFromActivePanoply(@PathParam("idC") String idC, @PathParam("idM") String idM) {
         try {
            Resources.getInstance().connect();

            Character c = new Character(new ObjectId(idC));
            Item i = c.getInventory().getItem(new ObjectId(idM));
            Panoply p = c.getActivePanoply();
            p.remove(i);
            p.save();
 
            return Response.status(HttpStatus.OK).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
    @Path("/{idC}/panoply/{idP}/add/{idM}")
    public Response addToPanoply(@PathParam("idC") String idC, @PathParam("idP") String idP, @PathParam("idM") String idM) {
         try {
            Resources.getInstance().connect();

            Character c = new Character(new ObjectId(idC));
            Item i = c.getInventory().getItem(new ObjectId(idM));
            Panoply p = c.getPanoply(new ObjectId(idP));
            p.setItem(i);
            p.save();
 
            return Response.status(HttpStatus.OK).build();
        } catch (NotAllowedException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
    @Path("/{idC}/panoply/active/add/{idM}")
    public Response addtoActivePanoply(@PathParam("idC") String idC, @PathParam("idM") String idM) {
         try {
            Resources.getInstance().connect();

            Character c = new Character(new ObjectId(idC));
            Item i = c.getInventory().getItem(new ObjectId(idM));
            Panoply p = c.getActivePanoply();
            p.setItem(i);
            p.save();
            
            return Response.status(HttpStatus.OK).build();
        } catch (NotAllowedException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } catch (NotFoundException ex) {
            Logger.getLogger(ServiceCharacter.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(HttpStatus.NOT_FOUND).build();
        } finally {
            Resources.getInstance().close();
        }
    }
        
}
