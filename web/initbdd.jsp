<%-- 
    Document   : initbdd
    Created on : 11 mai 2013, 06:57:43
    Author     : bruno
--%>

<%@page import="framework.character.Individual"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="com.mongodb.DBCollection"%>
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
        <h1>Init BDD</h1>

        <ul>
            <li>Character Classes</li>
            <li>Individuals</li>
                <%
                    Ressources res = Ressources.getInstance();
                    res.connect();
                    
                    
                    res.getCollection(CharacterClass.COLLECTION).drop();
                    DBCollection coll = res.getCollection(CharacterClass.COLLECTION);

                    CharacterClass ccb = new CharacterClass();
                    ccb.setName("Brute");
                    ccb.setDescription("Grande défense, petite attaque. (50%, 10%)");
                    ccb.save();

                    CharacterClass ccc = new CharacterClass();
                    ccc.setName("Canaille");
                    ccc.setDescription("Moyenne défense, moyenne attaque. (25%, 25%)");
                    ccc.save();

                    CharacterClass cct = new CharacterClass();
                    cct.setName("Tacticien");
                    cct.setDescription("Petite défense, grande attaque. (10%, 50%)");
                    cct.save();
                    
                    res.getCollection(Individual.COLLECTION).drop();
                    coll = res.getCollection(Individual.COLLECTION);
                    
                    Individual ciga = new Individual();
                    ciga.setName("Gandalf");
                    ciga.setCharacterClass(cct);
                    ciga.save();
                    
                    Individual cigi = new Individual();
                    cigi.setName("Gimli");
                    cigi.setCharacterClass(ccb);
                    cigi.save();
                    
                    Individual cigu = new Individual();
                    cigu.setName("Gurdil");
                    cigu.setCharacterClass(cct);
                    cigu.save();



                    res.close();
                %>
        </ul>
        <a href="index.jsp">return</a>
    </body>
</html>
