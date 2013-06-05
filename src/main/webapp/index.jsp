<%-- 
    Document   : index
    Created on : 5 juin 2013, 01:28:38
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%><%

    request.setAttribute("page", "home");

%><%@page contentType="text/html" pageEncoding="UTF-8" %><%--
--%><!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <title>Projet Voldemort &middot; MMOHTML5</title>
        <meta name="description" content="" />
        <meta name="keywords" content=""/>
        <meta name="author" content="Nicolas Devenet & Bruno Muller" />
        <meta name="robots" content="noindex, noarchive" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="shortcut icon"    type="image/x-icon" href="./static/img/favicon.ico" />
        <link rel="icon"             type="image/png"    href="./static/img/favicon.ico" />
        <!--[if lt IE 9]><script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
        <link rel="stylesheet" href="./static/css/bootstrap.min.css" media="screen" />
        <link rel="stylesheet" href="./static/css/bootstrap-responsive.min.css" media="screen" />
        <link rel="stylesheet" href="./static/css/voldemort.css" media="screen" />
    </head>

    <body>
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="${ page }" />
        </jsp:include>

        <!-- content -->
        <div id="content">
            <div class="container">
                <p class="center">
                    <img src="./static/img/loader.gif" alt="..." />
                    <br />work in progress
                </p>
            </div>
        </div>
        <!-- /content -->

        <!-- footer -->
        <!-- /footer -->

        <script src="//code.jquery.com/jquery-latest.js"></script>
        <script src="./static/js/bootstrap.min.js"></script>
        <script src="./static/js/voldemort.js"></script>

    </body>
</html>