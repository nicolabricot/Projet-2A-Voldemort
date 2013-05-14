<%-- 
    Document   : items
    Created on : 13 mai 2013, 05:41:43
    Author     : bruno
--%>

<%@page import="framework.view.ItemModelView"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="framework.item.ItemModel"%>
<%@page import="com.mongodb.DBCollection"%>
<%@page import="framework.ressource.Ressources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>Projet Voldemort</title>
    </head>
    <body>
        <h1>Items</h1> 
        <%
            Ressources res = Ressources.getInstance();
            res.connect();
            DBCollection coll = res.getCollection(ItemModel.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {      
                BasicDBObject bdbo = (BasicDBObject) cursor.next();
                ItemModel im = new ItemModel(bdbo.getObjectId("_id"));
                ItemModelView imv = new ItemModelView(im);
                out.println(imv);
            }
            
            res.close();
        %>
        <a href="index.jsp">return</a>
    </body>   
</html>
