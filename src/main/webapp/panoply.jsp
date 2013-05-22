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
                background-color: #ccc;
            }
            #links li.disable a {
                background-color: #eee;
                cursor: default;
            }
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
        </style>
    </head>
    <body>
        <h1>Panoply</h1>
        
        <ul id="links">
            <li><a href="#p">P</a></li>
            <li><a href="#c">C</a></li>
            <li><a href="#d">D</a></li>
            <li><a href="#i">I</a></li>
            <li><a href="#s">S</a></li>
        </ul>
        
        <div id="view">
            <div id="first-tab" class="tab">
                
            </div>
            <div id="second-tab" class="tab">
                
            </div>
            <div id="third-tab" class="tab">
                
            </div>
        </div>
        
        <div style="display:none;">
            <div id="p">
                <p>This is the p table</p>
                <p>Here we can some details about this or that...</p>
            </div>
            <div id="c">
                this is the c table
            </div>
            <div id="d">
                this is the d table
            </div>
            <div id="i">
                this is the i table
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
