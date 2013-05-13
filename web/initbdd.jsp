<%-- 
    Document   : initbdd
    Created on : 11 mai 2013, 06:57:43
    Author     : bruno
--%>

<%@page import="framework.item.Item"%>
<%@page import="framework.character.Armor"%>
<%@page import="framework.item.Item"%>
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
            <li>Items</li>
                <%
                    Ressources res = Ressources.getInstance();
                    res.connect();
                    
                    
                    res.getCollection(CharacterClass.COLLECTION).drop();

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
                    
                    res.getCollection(Item.COLLECTION).drop();
                    
                    Item ig = new Item();
                    ig.setType(Item.GAUNTLET);
                    ig.setName("Gant de base");
                    ig.setDescription("Gant parfait pour un noob.");
                    ig.save();
                    
                    Item ic = new Item();
                    ic.setType(Item.CUIRASS);
                    ic.setName("Cuirass de m***e");
                    ic.setDescription("Cette cuirass n'a aucun effet possitif.");
                    ic.save();
                    
                    Item ia = new Item();
                    ia.setType(Item.ARM);
                    ia.setName("Epée rouillée");
                    ia.setDescription("D'aventages de risque de choper le tetanos que de tuer un adversaire en la manipulant.");
                    ia.save();
                    
                    Item ib = new Item();
                    ib.setType(Item.BAG);
                    ib.setName("Sacoche 6 emplacements");
                    ib.setDescription("Augmente votre inventaire de 6 cases.");
                    ib.save();
                    
                    Item ir = new Item();
                    ir.setType(Item.RING);
                    ir.setName("Anneau de pouvoir");
                    ir.setDescription("Perdu par une étrange créature dans un marais, il est écrit dessus \"Un anneau pour les gouverner tous. Un anneau pour les trouver tous, Un anneau pour les amener tous et dans les ténèbres les lier.\"");
                    ir.save();
                    
                    //cigu.getArmor().setGauntlet(ic);


                    res.close();
                %>
        </ul>
        <a href="index.jsp">return</a>
    </body>
</html>
