<%@page
    import="fr.uha.projetvoldemort.resource.Resources"
    contentType="text/html" pageEncoding="UTF-8"%><%
    String map = "main";
    if (request.getParameter("map") != null && !request.getParameter("map").isEmpty()) {
        map = request.getParameter("map");
    }
    %><!DOCTYPE html>
<html lang="fr">
    <head>
        <jsp:include page="/WEB-INF/jsp/head.jsp" />
    </head>
    <body class="page-map">

        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="home" />
        </jsp:include>

        <!-- content -->
        <div id="content">
            <div class="container">
                <div class="row">
                    <div class="span8">
                        <div id="map" data-map-load="main" data-character-id="<%= Resources.getInstance().getFirstCharacter().getId().toString()%>">
                            <jsp:include page="/WEB-INF/jsp/data-map.jsp" />
                        </div>
                    </div>
                    <div class="span4" id="map-info">
                        <div class="state"></div>
                        <div class="title"></div>
                        <div class="description"></div>
                    </div>
                </div>
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
