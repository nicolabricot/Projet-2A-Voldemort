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
            .statictics {
                padding: 10px;
            }
            .inventory span,
            .consumables span {
                display: block;
                margin: 20px;
                font-size: 20px;
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
                </div>
                <div class="pull-right">
                    <a class="btn btn-success disabled"><i class="icon-ok icon-white"></i> enabled</a>
                </div>
            </div>

            <div id="view" data-character-id="<%= Resources.getInstance().getFirstCharacterId()%>" data-panoply-id="<%= panoply%>">
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
                <div class="sustainables equipment">
                    <div class="item weapon">weapon</div>
                    <div class="item ring">ring</div>
                </div>
            </div>
            <div id="consumables">
                <div class="consumables equipment">
                    <span>Consumable</span>
                    <div class="item">
                        <div class="image-items item-bred"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-ruby"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-diamand"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-papyrus"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-key"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-blue-fiole"></div>
                    </div>
                    <div class="item cuirass">water</div>
                </div>
            </div>
            <div id="degradables">
                <div class="degradables equipment">
                    <div class="item cuirass">cuirass</div>
                </div>
            </div>
            <div id="inventory">
                <div class="inventory">
                    <span>Inventory</span>
                    <div class="item">
                        <div class="image-items item-cuirass"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-rusty-sword"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-bag"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-ring"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-gauntlet"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-bred"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-ruby"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-sword"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-shoes"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-plus-shoes"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-diamand"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-papyrus"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-key"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-metal-cuirass"></div>
                    </div>
                    <div class="item">
                        <div class="image-items item-blue-fiole"></div>
                    </div>
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