<%-- 
    Document   : characterclasses
    Created on : 11 mai 2013, 06:53:39
    Author     : bruno
--%>

<%@page import="com.mongodb.DBCollection"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="framework.character.CharacterClass"%>
<%@page import="framework.ressource.Ressources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet Voldemort</title>
    </head>
    <body>
        <h1>Character Classes</h1>

        <%
            Ressources res = Ressources.getInstance();
            res.connect();
        %>



        <p>Character Classes :</p>

        <%
            DBCollection coll = res.getCollection(CharacterClass.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {
                BasicDBObject bdbo = (BasicDBObject) cursor.next();
                CharacterClass cc = new CharacterClass(bdbo.getObjectId("_id"));
                StringBuilder str = new StringBuilder();

                str.append("<ul>");
                str.append("<li>Id : " + cc.getId() + "</li>");
                str.append("<li>Name : " + cc.getName() + "</li>");
                str.append("<li>Description: " + cc.getDescription() + "</li>");
                str.append("</ul>");

                out.println(str.toString());
            }


            res.close();
        %>

        <a href="index.jsp">return</a>

    </body>
</html>
