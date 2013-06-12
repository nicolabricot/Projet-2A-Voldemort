<%-- 
    Document   : head
    Created on : 5 juin 2013, 12:11:20
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  String title = "Projet Voldemort &middot; MMOHTML5";
  if (request.getParameter("title") != null)
      title = request.getParameter("title") + " &middot; " + title;
  
%>        <meta charset="UTF-8" />
        <title><%= title %></title>
        <meta name="description" content="" />
        <meta name="keywords" content=""/>
        <meta name="author" content="Nicolas Devenet & Bruno Muller" />
        <meta name="robots" content="noindex, noarchive" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="shortcut icon"    type="image/x-icon" href="./static/img/favicon.ico" />
        <link rel="icon"             type="image/png"    href="./static/img/favicon.ico" />
        <!--[if lt IE 9]><script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
        <link rel="stylesheet" href="./static/css/bootstrap.min.css" media="screen" />
        <!--<link rel="stylesheet" href="./static/css/bootstrap-responsive.min.css" media="screen" />-->
        <link rel="stylesheet" href="./static/css/voldemort.css" media="screen" />