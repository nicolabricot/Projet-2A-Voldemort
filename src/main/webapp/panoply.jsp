<%@page import="fr.uha.projetvoldemort.resource.Resources" contentType="text/html" pageEncoding="UTF-8"%><%

    String panoply = "active";
    if (request.getParameter("panoply") != null && !request.getParameter("panoply").isEmpty()) {
        panoply = request.getParameter("panoply");
        //request.getRequestDispatcher("panoply.jsp").forward(request, response);
    }

%><%--
--%><!DOCTYPE html>
<html lang="fr">
    <head>
        <jsp:include page="/WEB-INF/jsp/head.jsp">
            <jsp:param name="title" value="Panoply" />
        </jsp:include>
        <link rel="stylesheet" href="./static/css/items.css" media="screen" />
        <style>

        </style>
    </head>

    <body class="page-panoply">
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="panoply" />
        </jsp:include>

        <!-- content -->
        <div id="content">

            <div id="links" class="container"> 
                <div class="btn-group">
                    <a href="#sustainables" class="btn">Permanent</a>
                    <a href="#consumables" class="btn">Consumable</a>
                    <a href="#degradables" class="btn">Degradable</a>
                </div>
                <a href="#inventory" class="btn">Inventory</a>
                <a href="#stats" class="btn">Statistics</a>
            </div>

            <div id="view" data-character-id="<%= Resources.getInstance().getFirstCharacterId() %>" data-panoply-id="<%= panoply %>">
                <div class="row-fluid">
                    <div id="first-tab" class="tab span4"></div>
                    <div id="second-tab" class="tab span4"></div>
                    <div id="third-tab" class="tab span4"></div>
                </div>
            </div>
        </div>
        <!-- /content -->


        <!-- deviendra de l'ajax -->
        <div style="display:none; clear:both;">
            <div id="sustainables" class="equipment">
                <div class="permanent equipment">
                    <div class="item weapon">weapon</div>
                    <div class="item cuirass">cuirass</div>
                    <div class="item gauntlet">gauntlet</div>
                    <div class="item ring">ring</div>
                </div>
            </div>
            <div id="consumable">
                this is the c table
            </div>
            <div id="degradable">
                this is the d table
            </div>
            <div id="stats">
                Player statistiques
            </div>
        </div>



        <!-- footer -->
        <!-- /footer -->

        <%-- scripts --%>
        <jsp:include page="/WEB-INF/jsp/script.jsp" />
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src="./js/panoply.js" charset="utf-8"></script>
    </body>
</html>