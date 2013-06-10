$(document).ready(function() {

    /*
     * CHARACTER
     */
    var character = $('#view').data('character-id');
    var id = $('#view').data('panoply-id');
    //console.log('character: '+ character, id);

    /*
     * LINKS AND TABS
     */
    // remove click on link tabs
    $('#links a').click(function() {
        return false;
    });

    // reset all links and remove tabs
    $('#links span.btn').click(function() {
        $('#links a').removeClass('disabled').addClass('ready');
        $('#view .tab').html('');
        $(this).addClass('disabled');
    });

    // set ready link to be draggeabled
    $('#links a').addClass('ready');
    $('#links a.ready').draggable({
        revert: 'invalid',
        helper: 'clone',
        addClasses: false,
        cursor: 'move'
    });

    // define comportment for droppable tabs
    $('#view .tab').droppable({
        accept: 'a.ready',
        activeClass: 'active',
        hoverClass: 'accept',
        addClasses: false,
        drop: function(event, ui) {
            var link = ui.helper.attr('href');
            var tab = '#' + $(this).attr('id');
            var old_link = $(tab).data('actual-link');

            //console.log('link:' + link, 'tab:' + tab, old_link);

            // s'il y a deja une view, on la récupère pour l'enlever et on réactive le lien
            if (old_link !== undefined) {
                $('#links a[href=' + old_link + ']').removeClass('disabled').addClass('ready');
                $(tab).html('');
            }
            // maintenant on peut mettre la vue sélectionnée
            $('#links a[href=' + link + ']').addClass('disabled').removeClass('ready');
            $('#view .tab').removeClass('active');
            $(tab).data('actual-link', link);

            // il va falloir faire une requete pour récupérer le contenu, on fait patienter
            //$(tab).append($(link).html());
            $(tab).append('<p class="center loading"><img src="./static/img/loader-length.gif" alt="..." /></p>');

            ui.helper.remove();
            // add remove tab on click
            $('#links span.btn').removeClass('disabled');

            // update drag and drop
            var path_ajax = 'rest/character/' + character + '/panoply/' + id + '/' + link.replace('#', '');
            switch (link.replace('#', '')) {
                case 'inventory':
                    path_ajax = 'rest/character/' + character + '/inventory';
                case 'sustainables':
                case 'consumables':
                case 'degradables':
                    // download content, then design and add it
                    $.ajax({
                        type: 'GET',
                        url: path_ajax,
                        dataType: 'json',
                        success: function(data) {
                            var result = '';
                            for (i = 0; i < data.length; i++)
                                result += '<div class="item ' + data[i].model.type + '"><div class="image-items item-' + data[i].model.image + '"></div></div>';
                            $(tab).html('<div class="' + link.replace('#', '') + '">' + result + '</div>');
                            drag_drop();
                        },
                        error: function(result, state, error) {
                            alert('Ajax failed: ' + error);
                            console.log('result: ' + result);
                            console.log('state: ' + state);
                            console.log('error: ' + error);
                        }
                    });
                    break;

                default:
                    $(tab).html('').append($(link).html());
                    drag_drop();
            }
        }
    });

    /*
     * CHECK ALL DRAG AND DROP
     */
    function drag_drop() {
        inventory();
    }


    /*
     * INVENTORY
     */
    function inventory_drop(event, ui, parent) {
        // get the item
        var item = ui.helper.children();
        // add the item to equipment
        $(parent).html(item);
    }

    function inventory() {
        $('.inventory .item').draggable({
            revert: 'invalid',
            helper: 'clone',
            addClasses: false,
            cursor: 'move'
        });

        // weapon droppable
        $('.permanent .item.weapon').droppable({
            accept: '.inventory .item.weapon',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });

        // gauntlet droppable
        $('.permanent .item.gauntlet').droppable({
            accept: '.inventory .item.gauntlet',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });

        // ring droppable
        $('.permanent .item.ring').droppable({
            accept: '.inventory .item.ring',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });

        // cuirass droppable
        $('.permanent .item.cuirass').droppable({
            accept: '.inventory .item.cuirass',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
    }

});