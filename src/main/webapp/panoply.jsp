<%-- 
    Document   : panoply
    Created on : 19 mai 2013, 14:22:12
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            li.active {
                background-color: #ccc;
            }
        </style>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <div id="links">
            <ul id="first-links">
                <li><a href="#p">P</a>
                <li><a href="#d">D</a>
                <li><a href="#c">C</a>
            </ul>

            <ul id="second-links">
                <li><a href="#i">I</a>
            </ul>
        </div>
        
        <div id="tabs">
            <div id="first-tabs">
                <div id="p">
                    <p>This is my message</p>
                </div>
                <div id="d">
                    <p>This is my second message</p>
                </div>
                <div id="c">
                    <p>This is my message number 3</p>
                </div>
            </div>
            
            <div id="second-tabs">
                <div id="i">
                    <p>This is my inventory</p>
                </div>
            </div>
        </div>
        
        
        
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script src="js/tabify.js" charset="utf-8"></script>
        <script src="js/panoply.js" charset="utf-8"></script>
    </body>
</html>
