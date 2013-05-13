<%-- 
    Document   : characterindividuals
    Created on : 11 mai 2013, 07:34:15
    Author     : bruno
--%>

<%@page import="framework.item.Item"%>
<%@page import="framework.character.Armor"%>
<%@page import="framework.item.ItemType"%>
<%@page import="framework.character.CharacterClass"%>
<%@page import="framework.character.Individual"%>
<%@page import="com.mongodb.BasicDBObject"%>
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
        <h1>Character Individuals</h1>

        <%
            Ressources res = Ressources.getInstance();
            res.connect();
        %>



        <p>Individuals :</p>

        <%
            DBCollection coll = res.getCollection(Individual.COLLECTION);
            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {
                BasicDBObject bdbo = (BasicDBObject) cursor.next();
                Individual i = new Individual(bdbo.getObjectId("_id"));
                StringBuilder str = new StringBuilder();

                str.append("<ul>");
                str.append("<li>" + bdbo + "</li>");
                str.append("<li>Id : " + i.getId() + "</li>");
                str.append("<li>Name : " + i.getName() + "</li>");
                str.append("<li>Life: " + i.getLife() + "</li>");
                str.append("<li>Attack: " + i.getAttack() + "</li>");
                str.append("<li>Defense: " + i.getDefense() + "</li>");
                str.append("<li>Initiative: " + i.getInitiative() + "</li>");
                str.append("<li>Robustness: " + i.getRobustness() + "</li>");
                str.append("<li>Luck: " + i.getLuck() + "</li>");
                str.append("<li>Class:</li>");
                
                    str.append("<ul>");
                    CharacterClass cc = i.getCharacterClass();
                    str.append("<li>Id: " + cc.getId() + "</li>");
                    str.append("<li>Name: " + cc.getName() + "</li>");
                    str.append("<li>Description: " + cc.getDescription() + "</li>");
                    str.append("</ul>");
                
                str.append("<li>Armor:</li>");
                
                Armor a = i.getArmor();
                    str.append("<ul>");
                    
                    for(ItemType it : ItemType.values()) {
                        if (!it.isArmor()) continue;
                         
                        Item t = a.get(it);
                        
                        if (t == null) continue;
                        
                        str.append("<li>" + t.getType().toString() + "</li>");
                            str.append("<ul>");
                            str.append("<li>Id: " + t.getId() + "</li>");
                            str.append("<li>Name: " + t.getName() + "</li>");
                            str.append("<li>Description: " + t.getDescription() + "</li>");
                            str.append("</ul>");
                       
                    }
                    str.append("</ul>");
                    
                    str.append("<li>Arm:</li>");
                        Item arm = i.getArm();
                        if (arm != null) {
                            str.append("<ul>");
                            str.append("<li>Id: " + arm.getId() + "</li>");
                            str.append("<li>Name: " + arm.getName() + "</li>");
                            str.append("<li>Description: " + arm.getDescription() + "</li>");
                            str.append("</ul>");
                        }
                    
                str.append("</ul>");

                out.println(str.toString());


            }


            res.close();
        %>

        <a href="index.jsp">return</a>
    </body>
</html>
