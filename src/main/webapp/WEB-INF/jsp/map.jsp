<%-- 
    Document   : data-map
    Created on : 12 juin 2013, 16:10:02
    Author     : Nicolas Devenet <nicolas@devenet.info>
--%>
<%@page import="fr.uha.projetvoldemort.resource.Resources"%>
<div class="row">
    <div class="span8">
        <div id="map" data-map-load="<%= request.getParameter("map")%>" data-character-id="<%= Resources.getInstance().getFirstCharacter().getId().toString()%>">
            <span class="stroke" data-color-default="#888"></span>
            <span class="stroke-hover" data-color-default="#888"></span>
            <span class="fill" data-color-default="#F8F8F8" data-color-opened="#D9F7FF" data-color-done="#CAFFC9" data-color-locked="#dadada"></span>
            <span class="fill-hover" data-color-default="#F8F8F8" data-color-opened="#80E9FF" data-color-done="#4DD15E" data-color-locked="#c0c0c0"></span>
            <span class="cursors" data-cursor-default="not-allowed" data-cursor-opened="pointer"></span>
            <span class="icons" data-icon-default="ban-circle" data-icon-opened="certificate" data-icon-locked="lock" data-icon-done="ok"></span>
        </div>
    </div>
    <div class="span4" id="map-info">
        <div class="state"></div>
        <div class="title"></div>
        <div class="description"></div>
    </div>
</div>

<!-- notifications -->
<div class="notifications top-right"></div>