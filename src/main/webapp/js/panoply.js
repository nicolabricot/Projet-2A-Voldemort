$(document).ready(function() {

    /*
     * LINKS AND TABS
     */
    
    // remove click on link tabs
    $("#links a").click(function() {
        return false;
    });
    // set ready link to be draggeabled
    $("#links a").addClass("ready");
    $("#links a.ready").draggable({
        revert: "invalid",
        helper: "clone",
        addClasses: false
    });

    // define comportment for droppable tabs
    $("#view .tab").droppable({
        accept: "a.ready",
        activeClass: "active",
        hoverClass: "accept",
        addClasses: false,
        drop: function(event, ui) {
            var link = ui.helper.attr("href");
            var tab = "#" + $(this).attr("id");
            var old_link = $(tab).data("actual-link");
            
            console.log(link, tab, old_link);

            // s'il y a deja une view, on la récupère pour l'enlever et on réactive le lien
            if (old_link !== undefined) {
                $("#links a[href=" + old_link + "]").removeClass("disabled").addClass("ready");
                $(tab).html("");
            }
            // maintenant on peut mettre la vue sélectionnée
            $("#links a[href=" + link + "]").addClass("disabled").removeClass("ready");
            $("#view .tab").removeClass("active");
            $(tab).data("actual-link", link);
            $(tab).append($(link).html());
            ui.helper.remove();
            // add remove tab on click
            $("#links a[href=" + link + "]").click(function() {
                $("#links a[href=" + link + "]").removeClass("disabled").addClass("ready");
                $(tab).html("");
            });
            // weapon
            weapon();
        }
    });


    /*
     * INVENTORY
     */
    // weapons draggable
    function weapon() {
        $(".inventory .item").draggable({
            revert: "invalid",
            helper: "clone",
            cursor: "move"
        });

        $(".permanent .item.weapon").droppable({
            accept: ".inventory .item.weapon",
            activeClass: "active",
            hoverClass: "accept",
            drop: function(event, ui) {
                var img = ui.helper.children();
                var perm = $(this);
                console.log(img);
                console.log(perm);
            }
        });
    }

});