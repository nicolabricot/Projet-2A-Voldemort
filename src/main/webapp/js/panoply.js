$(document).ready(function() {

    $("#links li a").click(function() {
        return false;
    });
    $("#links li").addClass("ready");
    //$("#links li:first").addClass("disable");

    $("#links li.ready").hover(function() {
        $(this).draggable({
            revert: "invalid",
            helper: "clone"
        });
    });

    /*
    $("#links li.disable").hover(function() {
        $(this).draggable("destroy");
    });
    */

    $("#view .tab").droppable({
        accept: "li.ready",
        activeClass: "active",
        hoverClass: "accept",
        drop: function(event, ui) {
            var link = ui.helper.children().attr("href");
            var tab = "#" + $(this).attr("id");
            var old_link = $(tab).data("actual-link");
            //console.log(link);
            //console.log(tab);
            //console.log(old_link);

            // s'il y a deja une view, on la récupère pour l'enlever et on réactive le lien
            if (old_link !== undefined) {
                $("#links li a[href=" + old_link + "]").parent().removeClass("disable").addClass("ready");
                $(tab).html("");
            }
            // maintenant on peut mettre la vue sélectionnée
            $("#links li a[href=" + link + "]").parent().addClass("disable").removeClass("ready");
            $("#view .tab").removeClass("active");
            $(tab).data("actual-link", link);
            $(tab).append($(link).html());
        }
    });

});