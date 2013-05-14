<%-- 
    Document   : characterindividuals
    Created on : 11 mai 2013, 07:34:15
    Author     : bruno
--%>

<%@page import="framework.view.CharacterView"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="framework.character.Character"%>
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
        <h1>Characters</h1>

        <%
            Ressources res = Ressources.getInstance();
            res.connect();
            DBCollection coll = res.getCollection(Character.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {
                BasicDBObject ob = (BasicDBObject) cursor.next();
                Character c = new Character(ob.getObjectId("_id"));
                out.println(new CharacterView(c));
            }
            res.close();
        %>
        <a href="index.jsp">return</a>
    </body>
</html>
