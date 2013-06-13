<%@page import="fr.uha.projetvoldemort.resource.Resources" contentType="text/html" pageEncoding="UTF-8"%><%

    String panoply = "";
    if (request.getParameter("panoply") != null && !request.getParameter("panoply").isEmpty()) {
        panoply = request.getParameter("panoply");
    }

    if (panoply.isEmpty() || panoply.contentEquals("")) {
        response.sendRedirect(Resources.PUBLIC_URL_HOME);
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
            .statictics {
                padding: 10px;
            }
            h4 {
                margin-left: 10px;
                text-transform: capitalize;
            }
        </style>
    </head>

    <body class="page-panoply">
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp">
            <jsp:param name="page" value="panoply" />
        </jsp:include>

        <!-- content -->
        <div id="content">

            <div class="container">
                <div id="links" class="pull-left"> 
                    <div class="btn-group">
                        <a href="#sustainables" class="btn">Permanent</a>
                        <a href="#consumables" class="btn">Consumable</a>
                        <a href="#degradables" class="btn">Degradable</a>
                    </div>
                    <a href="#inventory" class="btn">Inventory</a>
                    <a href="#statistics" class="btn">Statistics</a>
                    <span class="btn disabled"><i class="icon-remove"></i></span>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success disabled"><i class="icon-ok icon-white"></i> enabled</a>
                </div>
            </div>

            <div id="view" data-character-id="<%= Resources.getInstance().getFirstCharacter().getId().toString()%>" data-panoply-id="<%= panoply%>">
                <div class="row-fluid">
                    <div id="first-tab" class="tab span4"></div>
                    <div id="second-tab" class="tab span4"></div>
                    <div id="third-tab" class="tab span4"></div>
                </div>
            </div>
        </div>
        <!-- /content -->


        <!-- deviendra de l'ajax -->
        <div style="display:none; clear:both;" id="layouts">
            <div id="sustainables" class="equipment">
                <div class="sustainables equipment">
                    <div class="data" data-items='[
                         "bag",
                         "weapon_modifier",
                         "shield_modifier",
                         "other_sustainable",
                         "other_sustainable"
                         ]'></div>
                </div>
            </div>
            <div id="consumables">
                <div class="consumables equipment">
                    <div class="data" data-items='[
                         "projectile",
                         "offensive_throwing",
                         "defensive_throwing",
                         "other_consumable",
                         "other_consumable",
                         "other_consumable"
                         ]'></div>
                </div>
            </div>
            <div id="degradables">
                <div class="degradables equipment">
                    <div class="data" data-items='[
                         "cuirass",
                         "gauntlet",
                         "weapon",
                         "shield",
                         "shoes"
                         ]'></div>
                </div>
            </div>
            <div id="inventory">
                <div class="inventory">
                </div>
            </div>
            <div id="statistics">
                <div class="statictics">
                    <div class="alert">
                        <strong>Warning!</strong> Not yet implemented.
                    </div>
                </div>
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