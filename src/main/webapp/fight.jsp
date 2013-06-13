<%@page import="fr.uha.projetvoldemort.resource.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%
    
    String map = "";
    if (request.getParameter("from") != null && !request.getParameter("from").isEmpty())
        map = request.getParameter("from");
    
    if (map.contentEquals(""))
        map = "main";

%><!--
--><!DOCTYPE html>
    <html lang="fr">
        <head>
            <jsp:include page="/WEB-INF/jsp/head.jsp" />
            <link rel="stylesheet" href="./static/css/items.css" media="screen" />
            <style>
                #rapport-link {
                    cursor: pointer;
                    margin: 20px 0;
                }
            </style>
        </head>
        <body class="page-fight">

            <%-- menu --%>
            <jsp:include page="/WEB-INF/jsp/menu.jsp">
                <jsp:param name="page" value="fight" />
            </jsp:include>

            <!-- content -->
            <div id="content">
                <div class="container">
                    <div id="fight" data-character-id="<%= Resources.getInstance().getFirstCharacter().getId().toString()%>" data-map-id="<%= map%>">
                        
                    </div>
                </div>
            </div>
            <!-- /content -->

            <!-- footer -->
            <!-- /footer -->

            <%-- scripts --%>
            <jsp:include page="/WEB-INF/jsp/script.jsp" />
            <script src="./js/fight.js" charset="utf-8"></script>
        </body>
    </html>
