<%-- 
    Document   : items
    Created on : 13 mai 2013, 05:41:43
    Author     : bruno
--%>

<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="framework.item.Item"%>
<%@page import="com.mongodb.DBCursor"%>
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
        %>



        <p>Items :</p>
 
        <%
            DBCollection coll = res.getCollection(Item.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {      
                BasicDBObject bdbo = (BasicDBObject) cursor.next();
                Item i = new Item(bdbo.getObjectId("_id"));
                StringBuilder str = new StringBuilder();

                str.append("<ul>");
                str.append("<li>Id : " + i.getId() + "</li>");
                str.append("<li>Type : " + i.getType() + "</li>");
                str.append("<li>Name : " + i.getName() + "</li>");
                str.append("<li>Description : " + i.getDescription() + "</li>");
                str.append("</ul>");
                out.println(str.toString());
            }
        %>
        
        
        
        <a href="index.jsp">return</a>
    </body>   
</html>
