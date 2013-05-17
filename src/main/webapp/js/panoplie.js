window.onload = function() {
    $("#tabs ul").tabify();
    
    $("#tabs ul li").draggable({
        //axis: "x",
        //containment: "body",
        revert: "invalid",
        stack: "#tabs",
        drag: function(event, ui) {
            $(this).css("color", "red");
        },
        stop: function(event, ui) {
            $(this).css("color", "blue");
        }
    });
    
    $("#tabs ul").droppable({
        accept: "#tabs ul li",
        activeClass: "receptione",
        hoverClass: "accept",
        drop: function(event, ui) {
            alert("droped");
            console.log(ui);
        }              
    });
    
    
};