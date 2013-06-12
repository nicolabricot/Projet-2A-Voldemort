$(document).ready(function() {

    /*
     * CHARACTER
     */
    var character = $('#view').data('character-id');
    var id = $('#view').data('panoply-id');
    //console.log('character: '+ character, id);

    /*
     * LAYOUTS
     */
    var layouts = [];
    layouts['sustainables'] = $('#layouts .sustainables .data').data('items');
    layouts['degradables'] = $('#layouts .degradables .data').data('items');
    layouts['consumables'] = $('#layouts .consumables .data').data('items');

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
                            var items = [];
                            // get all elements
                            for (i = 0; i < data.length; i++) {
                                items.push(data[i].model.type);
                                result += '<div class="item ' + data[i].model.type + '" data-item-id="' + data[i].id + '"><div class="image-items item-' + data[i].model.image + '"></div></div>';
                            }
                            // get layout and compare if the item is already used
                            var layout = layouts[link.replace('#', '')];
                            if (layout != undefined) {
                                layout.reverse();
                                for (i = layout.length - 1; i >= 0; i--) {
                                    // if item not exists, add droppable case
                                    if ($.inArray(layout[i], items) == -1) {
                                        result += '<div class="item item-droppable ' + layout[i] + '">' + layout[i] + '</div>';
                                    }
                                }
                            }
                            // display the result
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
        var item_id = ui.helper.data('item-id');
        // request ajax to save the transfer
        var path_ajax = 'rest/character/' + character + '/panoply/' + id + '/add/' + item_id;
        $.ajax({
            type: 'GET',
            url: path_ajax,
            success: function(data) {
                //console.log('Item added to panoply!');
            },
            error: function(result, state, error) {
                alert('Ajax failed: ' + error);
                console.log('result: ' + result);
                console.log('state: ' + state);
                console.log('error: ' + error);
            }
        });
        // add the item to equipment
        $(parent).html(item);
        $(parent).removeClass('item-droppable');
        $(parent).droppable('disable');
    }

    function inventory() {
        $('.inventory .item').draggable({
            revert: 'invalid',
            helper: 'clone',
            addClasses: false,
            cursor: 'move'
        });

        // bag droppable
        $('.sustainables .item-droppable.bag').droppable({
            accept: '.inventory .item.bag',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // weapon-modifier droppable
        $('.sustainables .item-droppable.weapon_modifier').droppable({
            accept: '.inventory .item.weapon_modifier',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // shield-modifier droppable
        $('.sustainables .item-droppable.shield_modifier').droppable({
            accept: '.inventory .item.shield_modifier',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // other droppable
        $('.sustainables .item-droppable.other').droppable({
            accept: '.inventory .item.other',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // projectile droppable
        $('.consumables .item-droppable.projectile').droppable({
            accept: '.inventory .item.projectile',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // offensive_throwing droppable
        $('.consumables .item-droppable.offensive_throwing').droppable({
            accept: '.inventory .item.offensive_throwing',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // defensive_throwing droppable
        $('.consumables .item-droppable.defensive_throwing').droppable({
            accept: '.inventory .item.defensive_throwing',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // other droppable
        $('.consumables .item-droppable.other').droppable({
            accept: '.inventory .item.other',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // cuirass droppable
        $('.degradables .item-droppable.cuirass').droppable({
            accept: '.inventory .item.cuirass',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // gauntlet droppable
        $('.degradables .item-droppable.gauntlet').droppable({
            accept: '.inventory .item.gauntlet',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });

        // weapon droppable
        $('.degradables .item-droppable.weapon').droppable({
            accept: '.inventory .item.weapon',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // shield droppable
        $('.degradables .item-droppable.shield').droppable({
            accept: '.inventory .item.shield',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });
        
        // shoes droppable
        $('.degradables .item-droppable.shoes').droppable({
            accept: '.inventory .item.shoes',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                inventory_drop(event, ui, this);
            }
        });


    }

});