<%@page contentType="text/html" pageEncoding="UTF-8" %><%--
--%><!DOCTYPE html>
<html lang="fr">
    <head>
        <jsp:include page="/WEB-INF/jsp/head.jsp">
            <jsp:param name="title" value="Panoply" />
        </jsp:include>
        <link rel="stylesheet" href="./static/css/items.css" media="screen" />
        <style>
            /* view */
            #view {
            }
            .tab {
                border: 1px solid grey;
                width: 32%;
                margin-right: 5px;
                float: left;
                min-height: 400px;
            }
            .tab.active {
                border-color: green;
            }
            .tab.accept {
                background-color: green;
            }
            /* inventory */
            .item {
                display: inline-block;
                width: 40px;
                height: 40px;
                margin: 10px 0 10px 10px;
                overflow: hidden;
                line-height: 40px;
                border: 1px solid grey;
                vertical-align: middle;
            }
            .item.active {
                border: 1px solid green;
            }
            .item.accept {
                background-color: green;
            }
        </style>
    </head>

    <body class="page-panoply">
        <%-- menu --%>
        <jsp:include page="/WEB-INF/jsp/menu.jsp" />

        <!-- content -->
        <div id="content">

            <div id="links"> 
                <div class="btn-group">
                    <a href="#permanent" class="btn">Permanent<br />equipment</a>
                    <a href="#consumable" class="btn">Consumable<br />equipment</a>
                    <a href="#degradable" class="btn">Degradable<br />equipment</a>
                </div>
                <a href="#inventory" class="btn">My<br />inventory</a>
                <a href="#stats" class="btn">My<br />statistics</a>
            </div>

            <div id="view" data-character-id="519ce8b7c02662c571349537">
                <div id="first-tab" class="tab"></div>
                <div id="second-tab" class="tab"></div>
                <div id="third-tab" class="tab"></div>
            </div>
        </div>
        <!-- /content -->


        <!-- deviendra de l'ajax -->
        <div style="display:none; clear:both;">
            <div id="permanent">
                <div class="permanent equipment">
                    <div class="item weapon">
                        weapon
                    </div>
                    <div class="item helmet">
                        helmet
                    </div>
                </div>
            </div>
            <div id="consumable">
                this is the c table
            </div>
            <div id="degradable">
                this is the d table
            </div>
            <div id="inventory">
                <div class="inventory">
                    <div class="item gauntlet">
                                <div class="image-items item-gauntlet"></div>
                    </div>
                    <div class="item defense">
                                <div class="image-items item-cuirass"></div>
        
                        
                    </div>
                    <div class="item weapon">
                        <div class="image-items item-rusty-sword"></div>
        
                    </div>
                    <div class="item bag">
                        <div class="image-items item-bag"></div>
        
                    </div>
                    <div class="item ring">
                        <div class="image-items item-ring"></div>
                    </div>
                </div>
            </div>
            <div id="stats">
                this is the s table
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