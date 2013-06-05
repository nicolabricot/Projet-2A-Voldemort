<%@page contentType="text/html" pageEncoding="UTF-8" %><%
    String map = "france";
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
        <jsp:include page="/WEB-INF/jsp/menu.jsp" />

        <!-- content -->
        <div id="content">
            <div class="container">
                <div id="map-info"><p></p></div>
                <div id="map" data-map-load="<%= map%>"></div>
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