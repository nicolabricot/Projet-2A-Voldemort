$(document).ready(function() {
    // create raphael map
    var paper = new Raphael(document.getElementById('map'), 520, 560);
    // default values
    var map_info = 'Choose a region to start!';
    var map_closed = '<span class="more">Region is closed. Finish first opened quests!</span>';
    var map_done = '<span class="more">You have already done this region!</span>';
    var map_locked = '<span class="more">This region is locked. You can unlocked it with some money.</span>';
    // html elements
    var title = $('#map-info .title');
    var description = $('#map-info .description');
    var state = $('#map-info .state');
    // data
    var color_stroke = $('#map span.stroke');
    var color_stroke_hover = $('#map span.stroke-hover');
    var color_fill = $('#map span.fill');
    var color_fill_hover = $('#map span.fill-hover');
    var cursor = $('#map span.cursors');
    var icon = $('#map span.icons');

    // change state button
    function updateState(dataState, text) {
        state.html('<span style="background-color:' + color_fill.data('color-' + dataState) + ';"><i class="icon-' + icon.data('icon-' + dataState) + '"></i> ' + text + '</span>');
    }

    // set color and text
    function addAttr(data) {
        var result = {};
        switch (data.type) {
            // opened
            case 'opened':
                result = {
                    fill: color_fill.data('color-opened'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    href: data.link,
                    //'title': data.description,
                    cursor: cursor.data('cursor-opened')
                };
                break;

                // done
            case 'done':
                result = {
                    fill: color_fill.data('color-done'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_done,
                    cursor: cursor.data('cursor-default')
                };
                break;

                // locked
            case 'locked':
                result = {
                    fill: color_fill.data('color-locked'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_locked,
                    cursor: cursor.data('cursor-default')
                };
                break;

                // closed
            default:
                result = {
                    fill: color_fill.data('color-default'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_closed,
                    cursor: cursor.data('cursor-default')
                };
        }
        return result;
    }

    // set color on hover
    function hoverAttr(data) {
        var result = {};
        switch (data.type) {
            // opened
            case 'opened':
                result = {
                    fill: color_fill_hover.data('color-opened'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(data.description);
                title.html(data.title);
                updateState(data.type, data.type);
                break;

                // done
            case 'done':
                result = {
                    fill: color_fill_hover.data('color-done'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_done + (data.description != undefined ? data.description : ''));
                title.html(data.title);
                updateState(data.type, data.type);
                break;

                // locked
            case 'locked':
                result = {
                    fill: color_fill_hover.data('color-locked'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_locked + (data.description != undefined ? data.description : ''));
                title.html(data.title);
                updateState(data.type, data.type);
                break;

                // closed
            default:
                result = {
                    fill: color_fill_hover.data('color-default'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_closed + (data.description != undefined ? data.description : ''));
                updateState('default', 'closed');
                title.html('');

        }
        return result;
    }

    var map_load = $('#map').data('map-load');
    var character = $('#map').data('character-id');
    var path_map = 'static/map/' + map_load + '.json';
    if (map_load === 'main') {
        var path_state = 'rest/map/states/' + character;
    } else {
        var path_state = 'rest/map/states/' + map_load + '/' + character;
    }
    //console.log(path_map, path_state);

    var map = new Array();
    $.ajax({
        type: 'GET',
        url: path_map,
        dataType: 'json',
        success: function(data) {
            $.map(data, function(value, key) {
                map[key] = paper.path(value)
                        .attr(addAttr({'type': null}))
                        .hover(function() {
                    hoverAttr({'type': null});
                })
                        .mouseout(function() {
                    description.html(map_info);
                    title.html('');
                    state.html('');
                });
            });
            ajaxMapDone();
        },
        error: function(result, state, error) {
            $('.notifications').notify({
                type: 'error',
                message: {text: 'Unable to load the map!'}
            }).show();
            console.log('result: ' + result);
            console.log('state: ' + state);
            console.log('error: ' + error);
        }
    });

    function ajaxMapDone() {
        $.ajax({
            type: 'GET',
            url: path_state,
            dataType: 'json',
            success: function(data) {
                //console.log(data);
                ajaxStatesDone(data);
            },
            error: function(result, state, error) {
                $('.notifications').notify({
                type: 'error',
                message: {text: 'Unable to load states!'}
            }).show();
                console.log('result: ' + result);
                console.log('state: ' + state);
                console.log('error: ' + error);
            }
        });
    }

    function ajaxStatesDone(data) {
        $.map(data, function(value, key) {
            map[key.toString()].attr(addAttr(value[0]));
            //console.log(value[0]);
            (function(st, v) {
                st[0].onmouseover = function() {
                    st.animate(hoverAttr(v), 300);
                    paper.safari();
                };
                st[0].onmouseout = function() {
                    st.animate(addAttr(v), 300);
                    paper.safari();
                    title.html('');
                };
            })(map[key], value[0]);
        });
        description.html(map_info);
        title.html('');
        state.html('');
    }

});