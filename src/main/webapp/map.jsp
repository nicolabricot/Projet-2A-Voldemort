<%-- 
    Document   : country
    Created on : 15 mai 2013, 16:26:14
    Author     : bruno
--%><%@
    page contentType="text/html" pageEncoding="UTF-8"
%><%
    String map = "france";
    if(request.getParameter("map") != null && ! request.getParameter("map").isEmpty()){
        map = request.getParameter("map");
    }  
%><!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Voldemort</title>
        <style type="text/css">
          body {
            width: 800px;
            margin: auto;
          }
          header h1 {
            text-align: center;
          }
          #map {
            width: 520px;
            height: 560px;
            margin: auto;
            border: 1px solid #aaa;
          }
          #map span {
            display: none;
            visibility: hidden;
          }
          #map-info {
              text-align: center;
          }
        </style>
    </head>
<body>
  <header>
    <h1>Projet Voldemort</h1>
  </header>
  
  <div id="map-info">
      <p></p>
  </div>
  <div id="map" data-map-load="<%= map %>">
  </div>
    
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
  <script src="js/raphael.js" charset="utf-8"></script>
  <script src="js/map.js" charset="utf-8"></script>

</body>
</html>