<%-- 
    Document   : index
    Created on : 6 mai 2013, 20:24:40
    Author     : bruno
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="fr">
    <head>
        <jsp:include page="/WEB-INF/jsp/head.jsp" />
    </head>
    <body class="page-home">
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="home" />
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

        <%-- scripts --%>
        <jsp:include page="/WEB-INF/jsp/script.jsp" />
    </body>
</html>
