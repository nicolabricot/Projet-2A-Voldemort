<%@page
    import="fr.uha.projetvoldemort.resource.Resources"
    contentType="text/html" pageEncoding="UTF-8"
%><%
    String map = "main";
    if (request.getParameter("map") != null && !request.getParameter("map").isEmpty()) {
        map = request.getParameter("map");
    }
    %><!DOCTYPE html>
    <html lang="fr">
        <head>
            <jsp:include page="/WEB-INF/jsp/head.jsp">
                <jsp:param name="title" value="Map" />
            </jsp:include>
            <style>
                #map-info .state {
                    margin: 10px 0;
                }
                #map-info .state span {
                    border: 1px solid #ccc;
                    padding: 4px 8px;
                }
                #map-info .title {
                    margin: 15px 0 5px;
                    font-size: 16px;
                }
                #map-info .description .more {
                    display: block;
                    margin-bottom: 5px;
                }
            </style>
        </head>

        <body class="page-map">
            <%-- menu --%>
            <jsp:include page="/WEB-INF/jsp/menu.jsp">
                <jsp:param name="page" value="map" />
            </jsp:include>

            <!-- content -->
            <div id="content">
                <div class="container">
                    <div class="row">
                        <div class="span8">
                            <div id="map" data-map-load="<%= map%>" data-character-id="<%= Resources.getInstance().getFirstCharacterId()%>">
                                <span class="stroke" data-color-default="#888"></span>
                                <span class="stroke-hover" data-color-default="#888"></span>
                                <span class="fill" data-color-default="#F8F8F8" data-color-opened="#D9F7FF" data-color-done="#CAFFC9" data-color-locked="#dadada"></span>
                                <span class="fill-hover" data-color-default="#F8F8F8" data-color-opened="#80E9FF" data-color-done="#4DD15E" data-color-locked="#c0c0c0"></span>
                                <span class="cursors" data-cursor-default="not-allowed" data-cursor-opened="pointer"></span>
                                <span class="icons" data-icon-default="ban-circle" data-icon-opened="certificate" data-icon-locked="lock" data-icon-done="ok"></span>
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