<%-- 
    Document   : charactermodels
    Created on : 14 mai 2013, 06:31:37
    Author     : bruno
--%>

<%@page import="fr.uha.projetvoldemort.view.CharacterModelView"%>
<%@page import="fr.uha.projetvoldemort.character.CharacterModel"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="com.mongodb.DBCollection"%>
<%@page import="fr.uha.projetvoldemort.ressource.Ressources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet Voldemort</title>
    </head>
    <body>
        <h1>Character models</h1>
        <% 
            Ressources res = Ressources.getInstance();
            res.connect();
            
            DBCollection coll = res.getCollection(CharacterModel.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {
                BasicDBObject ob = (BasicDBObject) cursor.next();
                CharacterModel cm = new CharacterModel(ob.getObjectId("_id"));
                CharacterModelView cmv = new CharacterModelView(cm);
                out.println(cmv);
            }
            
            res.close();
        %>
        <a href="index.jsp">return</a>
    </body>
</html>
