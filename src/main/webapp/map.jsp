<%@page import="fr.uha.projetvoldemort.resource.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %><%
    String map = "full";
    if (request.getParameter("map") != null && !request.getParameter("map").isEmpty()) {
        map = request.getParameter("map");
    }
%><%--
--%><!DOCTYPE html>
<html lang="fr">
    <head>
        <jsp:include page="/WEB-INF/jsp/head.jsp">
            <jsp:param name="title" value="Map" />
        </jsp:include>
    </head>

    <body class="page-map">
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="map" />
        </jsp:include>

        <!-- content -->
        <div id="content">
            <div class="container">
                <div id="map-info"><p></p></div>
                <div id="map" data-map-load="<%= map%>" data-character-id="<%= Resources.getInstance().getFirstCharacterId() %>"></div>
            </div>
        </div>
        <!-- /content -->

        <!-- footer -->
        <!-- /footer -->

        <%-- scripts --%>
        <jsp:include page="/WEB-INF/jsp/script.jsp" />
        <script src="./static/js/raphael.js" charset="utf-8"></script>
        <script src="./js/map.js" charset="utf-8"></script>
    </body>
</html>