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
                            var result = '<h4>' + link.replace('#', '') + '</h4>';
                            var items = [];
                            // get all elements
                            for (i = 0; i < data.length; i++) {
                                items.push(data[i].model.type);
                                result += '<div class="item ' + (link.replace('#', '') != 'inventory' ? 'item-removable ' : '') + data[i].model.type + '" data-item-id="' + data[i].id + '"><div class="image-items item-' + data[i].model.image + '"></div></div>';
                            }
                            // get layout and compare if the item is already used
                            var layout = layouts[link.replace('#', '')];
                            if (layout != undefined) {
                                for (i = 0; i < layout.length; i++) {
                                    // if item not exists, add droppable case
                                    if ($.inArray(layout[i], items) == -1) {
                                        result += '<div class="item item-droppable ' + layout[i] + '">' + layout[i] + '</div>';
                                    }
                                    else {
                                        items.splice(items.indexOf(layout[i]), 1);
                                    }
                                }
                            }
                            // if it's inventory add the trash
                            if (link.replace('#', '') == 'inventory') {
                                result += '<div class="item item-trash"><i class="icon-trash"></i></div>';
                            }
                            // display the result
                            $(tab).html('<div class="' + link.replace('#', '') + '">' + result + '</div>');
                            drag_drop();
                        },
                        error: function(result, state, error) {
                            $('.notifications').notify({
                                type: 'error',
                                message: {text: 'Impossible to load informations!'}
                            }).show();
                            console.log('result: ' + result);
                            console.log('state: ' + state);
                            console.log('error: ' + error);
                        }
                    });
                    break;

                case 'statistics':
                    path_ajax = 'rest/character/' + $('#view').data('character-id') + '/statistics';
                    console.log(path_ajax);
                    $.ajax({
                        type: 'GET',
                        url: path_ajax,
                        dataType: 'json',
                        success: function(data) {
                            var result = '<h4>' + link.replace('#', '') + '</h4>';
                            result += '<dl class="dl-horizontal">';
                            result += '<dt>Name</dt><dd>' + data.name + '</dd>';
                            result += '<dt>Class</dt><dd>' + data.class + '</dd>';
                            result += '<dt>Faction</dt><dd>' + data.faction + '</dd>';
                            result += '</dl>';
                            result += '<dl class="dl-horizontal">';
                            for(var key in data.attributes) {
                                result += '<dt>' + key + '</dt>';
                                result += '<dd>' + data.attributes[key] + '</dd>';
                            }
                            result += '</dl>';
                            // display the result
                            $(tab).html('<div class="' + link.replace('#', '') + '">' + result + '</div>');
                        },
                        error: function(result, state, error) {
                            $('.notifications').notify({
                                type: 'error',
                                message: {text: 'Impossible to load informations!'}
                            }).show();
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
        inventory2panoply();
        panoply2inventory();
    }


    /*
     * INVENTORY TO PANOPLY
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
                // add the item to equipment
                $(parent).html(item);
                $(parent).removeClass('item-droppable');
                $(parent).attr('data-item-id', item_id);
                $(parent).droppable('destroy');
                $(parent).addClass('item-removable');
                drag_drop();
                $('.notifications').notify({
                    message: {html: '<i class="icon-ok"></i> Item added to panoply!'}
                }).show();
            },
            error: function(result, state, error) {
                $('.notifications').notify({
                    type: 'error',
                    message: {html: '<i class="icon-ban-circle"></i> Impossible to add item!'}
                }).show();
                console.log('result: ' + result);
                console.log('state: ' + state);
                console.log('error: ' + error);
            }
        });
    }

    function inventory2panoply() {
        $('.inventory .item').draggable({
            revert: 'invalid',
            helper: 'clone',
            addClasses: false,
            cursor: 'move'
        });
        $('.inventory .item.item-trash').draggable('destroy');

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

        // other_sustainable droppable
        $('.sustainables .item-droppable.other_sustainable').droppable({
            accept: '.inventory .item.other_sustainable',
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

        // other_consumable droppable
        $('.consumables .item-droppable.other_consumable').droppable({
            accept: '.inventory .item.other_consumable',
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

    /*
     * PANOPLY TO INVENTORY
     */
    function panoply_drop(event, ui) {
        // get the item
        var item_id = ui.helper.data('item-id');
        var item = $('.item-removable[data-item-id="' + item_id + '"]');
        // request ajax to save the transfer
        var path_ajax = 'rest/character/' + character + '/panoply/' + id + '/remove/' + item_id;
        $.ajax({
            type: 'GET',
            url: path_ajax,
            success: function(data) {
                // remove the item to equipment
                item.html(item.removeClass('item-removable').attr('class').replace('item', ''));
                item.addClass('item-droppable');
                drag_drop();
                $('.notifications').notify({
                    type: 'alert',
                    message: {html: '<i class="icon-trash"></i> Item removed from panoply!'}
                }).show();
            },
            error: function(result, state, error) {
                $('.notifications').notify({
                    type: 'error',
                    message: {text: 'Impossible to remove item!'}
                }).show();
                console.log('result: ' + result);
                console.log('state: ' + state);
                console.log('error: ' + error);
            }
        });
    }

    function panoply2inventory() {
        $('.item-removable').draggable({
            revert: 'invalid',
            helper: 'clone',
            addClasses: false,
            cursor: 'move'
        });
        $('.item-trash').droppable({
            accept: '.item-removable',
            activeClass: 'active',
            hoverClass: 'accept',
            addClasses: false,
            drop: function(event, ui) {
                panoply_drop(event, ui);
            }
        });

    }

});