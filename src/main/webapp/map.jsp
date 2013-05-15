<%-- 
    Document   : country
    Created on : 15 mai 2013, 16:26:14
    Author     : bruno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
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
</style>
</head>
<body>
  <header>
    <h1>Projet Voldemort</h1>
  </header>

  <div id="map" data-country="france">
  </div>

  <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
  <script src="./raphael.js" charset="utf-8"></script>
  <script src="./map.js" charset="utf-8"></script>
</body>
</html>