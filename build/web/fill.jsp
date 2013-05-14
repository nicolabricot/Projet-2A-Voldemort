<%-- 
    Document   : fill
    Created on : 14 mai 2013, 06:17:24
    Author     : bruno
--%>

<%@page import="framework.ressource.Ressources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projet Voldemort</title>
    </head>
    <body>
        <h1>Fill database</h1>
        <p>Nothing to show you</p>
        <% 
            Ressources res = Ressources.getInstance();
            res.connect();
            res.fill();
            res.close();
        %>
        <a href="index.jsp">return</a>
    </body>
</html>
