<%-- 
    Document   : index
    Created on : 6 mai 2013, 20:24:40
    Author     : bruno
--%>
<%@page import="fr.uha.projetvoldemort.resource.Resources"%>
<%@page import="java.net.InetAddress"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = Resources.getInstance().getFirstCharacterId();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet Voldemort</title>
    </head>
    <body>
        <h2>Pages web</h2>
        <ul>
            <li><a href="map.jsp">Map</a> (<a href="old-map.jsp">archive</a>)</li>
            <li><a href="panoply.jsp">Panoply</a> (<a href="old-panoply.jsp">archive</a>)</li>
        </ul>
        <h2>Service web</h2>
        <ul>
            <li><a href="rest/ressources/fill">Fill database</a></li>
            <li><a href="rest/itemmodel/all">Get all item models</a></li>
            <li><a href="rest/charactermodel/all">Get all character models</a></li>
        </ul>
        
        <h4>Character</h4>
        <ul>
            <li>Get characters id : <a href="./rest/character">/rest/character</a></li>
            <li>Get character by id : <a href="./rest/character/<%= id %>">/rest/character/{id}</a></li>
            <li>Get his inventory : <a href="./rest/character/<%= id %>/inventory">/rest/character/{id}/inventory</a></li>
            <li>Get his sustainable inventory : <a href="./rest/character/<%= id %>/inventory/sustainables">/rest/character/{id}/inventory/sustainables</a></li>
            <li>Get his consumable inventory : <a href="./rest/character/<%= id %>/inventory/consumables">/rest/character/{id}/inventory/consumables</a></li>
            <li>Get his degradable inventory : <a href="./rest/character/<%= id %>/inventory/degradables">/rest/character/{id}/inventory/degradables</a></li>
        </ul>
        
        <h4>Panoply</h4>
        <ul>
            <li>Get all panoplies : <a href="./rest/character/<%= id %>/panoply">/rest/character/{id}/panoply</a></li>
            <li>Get a whole panoply : <a href="./rest/character/<%= id %>/panoply/active">/rest/character/{id}/panoply/{id}</a></li>
            <li>Get a sustainable panoply : <a href="./rest/character/<%= id %>/panoply/active/sustainables">/rest/character/{id}/panoply/{id}/sustainables</a></li>
            <li>Get a consumable panoply : <a href="./rest/character/<%= id %>/panoply/active/consumables">/rest/character/{id}/panoply/{id}/consumables</a></li>
            <li>Get a degradable panoply : <a href="./rest/character/<%= id %>/panoply/active/degradables">/rest/character/{id}/panoply/{id}/degradables</a></li>
             <li>Get active panoply : <a href="./rest/character/<%= id %>/panoply/active">/rest/character/{id}/panoply/active</a></li>
            <li>Get active sustainable panoply : <a href="./rest/character/<%= id %>/panoply/active/sustainables">/rest/character/{id}/panoply/active/sustainables</a></li>
            <li>Get active consumable panoply : <a href="./rest/character/<%= id %>/panoply/active/consumables">/rest/character/{id}/panoply/active/consumables</a></li>
            <li>Get active degradable panoply : <a href="./rest/character/<%= id %>/panoply/active/degradables">/rest/character/{id}/panoply/active/degradables</a></li>
        </ul>
        
        <h4>Statistics</h4>
        <ul>
            <li>Get statistics : <a href="./rest/character/<%= id %>/statistics">/rest/character/{id}/statistics</a></li>
        </ul>
        
        <h4>Maps</h4>
        <ul>
            <li>Get France map : <a href="./rest/map">/rest/map</a></li>
            <li>Get a region map : <a href="./rest/map/dep51">/rest/map/{id}</a></li>
            <li>Get states for France map : <a href="./rest/map/states/<%= id %>">/rest/map/states/{id_character}</a></li>
            <li>Get states for France map : <a href="./rest/map/state/dep51/<%= id %>">/rest/map/state/{id}/{id_character}</a></li>
        </ul>

        <h2>BDD</h2>
        <pre>cd /Users/Voldemort/Downloads/mongodb-osx-x86_64-2.4.3/bin
./mongod --dbpath /Users/Voldemort/Downloads/mongodb-osx-x86_64-2.4.3/data</pre>
        
        <h2>Server</h2>
        <ul>
            <li>Hostname : <%= InetAddress.getLocalHost().getHostName() %></li>
            <li>IP : <%= InetAddress.getLocalHost().getHostAddress() %></li>
            <li>App Server : <%=getServletConfig().getServletContext().getServerInfo()%></li>
            <li>JVM : <%= System.getProperty("java.version")%> - <%=System.getProperty("java.vendor")%></li>
            <li>Java home : <%= System.getProperty("java.home")%></li>
            <li>OS : <%= System.getProperty("os.name")%> - <%= System.getProperty("os.version")%></li>
            <li>Architecture : <%= System.getProperty("os.arch")%></li>
        </ul>
        
        <h2>Images</h2>
        <div>
            <img src="./static/media/items.png" alt="items"/>
        </div>
        
    </body>
    <script>
      var a = document.querySelectorAll("li a");
      for (var i=0; i<a.length; i++)
        a[i].setAttribute('target', '_blank');
    </script>
</html>
