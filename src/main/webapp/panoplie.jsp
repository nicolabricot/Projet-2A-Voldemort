<%-- 
    Document   : perso
    Created on : 17 mai 2013, 14:05:32
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%><%@
    page contentType="text/html" pageEncoding="UTF-8"
%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>  
        <style type="text/css">
            #tabs ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }
            #tabs li {
                display: inline-block;
                margin-bottom: -4px;
            }
            #tabs a {
                float: left;
                padding: 10px 15px;
                text-decoration: none;
                background-color: #ccc;
                color: #000;
                margin-bottom: 0;
            }
            #tabs li.active a {
                background-color: #eee;
            }
            .tab {
                margin-top: 0;
                padding: 10px;
                background-color: #eee;
                overflow: hidden;
            }
            .tab h2 {
                margin-top: 15px;
            }
            .column {
                width: 32%;
                float: left;
                margin-right: 1%;
            }
            .column + .column + .column {
                margin-right: 0;
            }
            #tabs li.reception a {
                cursor: default;
                background-color: #fff;
            }
            .receptione {
                border: 1px solid red;
            }
            .accept {
                border: 1px solid green;
            }
        </style>
    </head>
    <body>
        <h1>Voldemort</h1>
        
        <div id="tabs">
            <div class="column">
                <ul id="first-tab">
                    <li class="active"><a href="#p">P</a></li><!--
                    --><li><a href="#c">C</a></li><!--
                    --><li><a href="#d">D</a></li><!--
                    --><li class="reception"><a href="#first">&nbsp;</a></li>
                </ul>
            </div>
            <div class="column">
                <ul id="second-tab">
                    <li class="active"><a href="#i">I</a></li><!--
                    --><li class="reception"><a href="#second">&nbsp;</a></li>
                </ul>
            </div>
            <div class="column">
                <ul id="third-tab">
                    <li class="active"><a href="#s">S</a></li><!--
                    --><li class="reception"><a href="#third">&nbsp;</a></li>
                </ul>
            </div>
        </div>
        
        <div id="columns">
            <div id="first-column" class="column">
                <div id="p" class="tab">
                    <h2>Équipements pérennes</h2>
                    <p>This is the content of my first tab</p>
                </div>

                <div id="c" class="tab">
                    <h2>Équipements consommables</h2>
                    <p>This is the content of my second tab</p>
                </div>

                <div id="d" class="tab">
                    <h2>Équipements dégradables</h2>
                    <p>This is the content of my third tab</p>
                </div>
            </div>
            
            <div id="second-column" class="column">
                <div id="i" class="tab">
                    <h2>Inventaire</h2>
                    <p>This is the content of my fourth tab</p>
                </div>   
            </div>
            
            <div id="third-column" class="column">
                <div id="s" class="tab">
                    <h2>Statistiques</h2>
                    <p>This is the content of my fifth tab</p>
                </div>
            </div>
        </div>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src="js/tabify.js" charset="utf-8"></script>
        <script src="js/panoplie.js" charset="utf-8"></script>
    </body>
</html>
