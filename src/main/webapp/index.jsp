<%-- 
    Document   : index
    Created on : 6 mai 2013, 20:24:40
    Author     : bruno
--%><%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet Voldemort</title>
    </head>
    <body>
        <h1>Index</h1>
        <h2>Pages web</h2>
        <ul>
            <li><a href="map.jsp">Map</a></li>
            <li><a href="panoplie.jsp">Personnage</a></li>
        </ul>
        <h2>Service web</h2>
        <ul>
            <li><a href="rest/ressources/fill">Fill database</a></li>
            <li><a href="rest/itemmodel/all">Get all item models</a></li>
            <li><a href="rest/charactermodel/all">Get all character models</a></li>
            <li><a href="rest/character/all">Get all characters</a></li>
        </ul>
        <p>Exemple d'utilisation du web service :</p>
        <ul>
            <li>Get all characters : /rest/character/all</li>
            <li>Get character by id : /rest/character/{id}</li>
            <li>Get his inventory : /rest/character/{id}/inventory</li>
            <li>Get his equipment : /rest/character/{id}/equipment</li>
            <li>Get his stats : /rest/character/{id}/stats</li>
            <li>Get all item models : /rest/itemmodel/all</li>
            <li>Get item by id : /rest/itemmodel/{id}</li>
            <li>Get all character models : /rest/charactermodel/all</li>
            <li>Get character model by id : /rest/charactermodel/{id}</li>
            <li>Get a map by id : /rest/map/{id}</li>
            <li>Get a state of a map by id : /rest/map/states/{id}</li>
        </ul>
        
        <h2>BDD</h2>
        <pre>cd /Users/Voldemort/Downloads/mongodb-osx-x86_64-2.4.3/bin</pre>
        <pre>./mongod --dbpath /Users/Voldemort/Downloads/mongodb-osx-x86_64-2.4.3/data</pre>
    </body>
</html>
