$(document).ready(function() {
    $("#tabs ul").tabify();
    
    $("li.reception a").click(function() {
       return false; 
    });
    
    $("#tabs ul li").draggable({
        axis: "x",
        revert: "invalid",
        stack: "#tabs",
        drag: function(event, ui) {
            $(this).css("color", "red");
        },
        stop: function(event, ui) {
            $(this).css("color", "blue");
        }
    });
    
    $("li.reception").droppable({
        accept: "#tabs ul li",
        activeClass: "receptione",
        hoverClass: "accept",
        drop: function(event, ui) {
            var tab = ui.helper.children().attr("href").replace("-tab", "");
            console.log(tab);
            var current = $(this).children().attr("href").replace("tab", "column");
            console.log(current);
            $(current).append($(tab));
            $(this).before(ui.helper.css("left", 0));
        }              
    });
    
});