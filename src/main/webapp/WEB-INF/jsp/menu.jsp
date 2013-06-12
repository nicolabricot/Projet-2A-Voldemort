<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String actual = request.getParameter("page");
%>
<%!
    public String menu(String actual, String page) {
        return (page.equals(actual) ? " class=\"active\"" : "");
    }
    
%>        <!-- menu -->
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand" href="./" title="Projet Voldemort"><img src="./static/img/favicon.png" alt="Projet Voldemort"/></a>
                    <ul class="nav">
                        <li<%=menu(actual, "home")%>><a href="./">Home</a></li>
                        <!--<li<%=menu(actual, "map")%>><a href="./map.jsp">Map</a></li>
                        <li<%=menu(actual, "panoply")%>><a href="./panoply.jsp">Panoply</a></li>-->
                    </ul>
                    <ul class="nav pull-right">
                        <li><a href="./dev.jsp" rel="external">API <i class="icon-share-alt"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- /menu -->