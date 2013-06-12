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
                    <jsp:include page="/WEB-INF/jsp/map.jsp">
                        <jsp:param name="map" value="main" />
                    </jsp:include>
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
