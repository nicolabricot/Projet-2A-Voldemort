<%-- 
    Document   : panoply
    Created on : 22 mai 2013, 11:30:20
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%><%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panoply</title>
        <style>
            /* links */
            #links {
                list-style: none;
                padding: 0;
                margin: 20px 0;
            }
            #links li {
                display: inline-block;
            }
            #links a {
                float: left;
                padding: 10px 15px;
                text-decoration: none;
                /*background-color: #ccc;*/
                color: #000;
                margin-bottom: 0;
            }
            #links li.ready a {
                background-color: #eee;
                cursor: move;
            }
            #links li.disable a {
                background-color: #ccc;
                cursor: n-resize;
            }
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
            /* item */
            .item {
                display: inline-block;
                width: 75px;
                height: 75px;
                margin: 10px 0 10px 10px;
                overflow: hidden;
                line-height: 75px;
                text-align: center;
                border: 1px solid grey;
            }
           .item img {
                width: 60px;
                height: 60px;
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
    <body>
        <h1>Panoply</h1>
        
        <ul id="links">
            <li><a href="#permanent">Permanent equipment</a></li>
            <li><a href="#inventory">Inventory</a></li>
            <li><a href="#c">C</a></li>
            <li><a href="#d">D</a></li>
            <li><a href="#s">S</a></li>
        </ul>
        
        <div id="view" data-character-id="519ce8b7c02662c571349537">
            <div id="first-tab" class="tab">
                
            </div>
            <div id="second-tab" class="tab">
                
            </div>
            <div id="third-tab" class="tab">
                
            </div>
        </div>
        
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
            <div id="c">
                this is the c table
            </div>
            <div id="d">
                this is the d table
            </div>
            <div id="inventory">
                <div class="inventory">
                    <div class="item weapon">
                        <img src="static/img/arc-1.png" alt="arc-1" />
                    </div>
                    <div class="item weapon">
                        <img src="static/img/arc-2.png" alt="arc-2" />
                    </div>
                    <div class="item helmet">
                        <img src="static/img/casque-1.png" alt="casque-1" />
                    </div>
                    <div class="item weapon">
                        <img src="static/img/couteau-1.png" alt="couteau-1" />
                    </div>
                    <div class="item weapon">
                        <img src="static/img/couteau-2.png" alt="couteau-1" />
                    </div>
                </div>
            </div>
            <div id="s">
                this is the s table
            </div>
        </div>
        
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src="js/panoply.js" charset="utf-8"></script>
    </body>
</html>
