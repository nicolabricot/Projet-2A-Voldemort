$(document).ready(function() {

    /*
     * LINKS AND TABS
     */

    // remove click on link tabs
    $("#links li a").click(function() {
        return false;
    });
    // set ready link to be draggeabled
    $("#links li").addClass("ready");
    $("#links li.ready").draggable({
        revert: "invalid",
        helper: "clone"
    });

    // define comportment for droppable tabs
    $("#view .tab").droppable({
        accept: "li.ready",
        activeClass: "active",
        hoverClass: "accept",
        drop: function(event, ui) {
            var link = ui.helper.children().attr("href");
            var tab = "#" + $(this).attr("id");
            var old_link = $(tab).data("actual-link");

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
            ui.helper.remove();
            // add remove tab on click
            $("#links li a[href=" + link + "]").click(function() {
                $("#links li a[href=" + link + "]").parent().removeClass("disable").addClass("ready");
                $(tab).html("");
            });
        }
    });


    /*
     * INVENTORY
     */
    // weapons draggable
    

});