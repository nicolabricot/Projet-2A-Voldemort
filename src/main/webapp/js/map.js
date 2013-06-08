$(document).ready(function() {
    // create raphael map
    var paper = new Raphael(document.getElementById('map'), 520, 560);
    // default values
    var map_info = 'Choose a region to start!';
    var map_closed = 'Region is closed. Finish first opened quests!';
    var map_done = 'You have already done this region!';
    var map_locked = 'This region is locked. You can unlocked it with some money.';
    // html elements
    var title = $('#map-info .title');
    var description = $('#map-info .description');
    var state = $('#map-info .state');
    // data
    var color_stroke = $('#map span.stroke');
    var color_stroke_hover = $('#map span.stroke-hover');
    var color_fill = $('#map span.fill');
    var color_fill_hover = $('#map span.fill-hover');
    var cursor = $('#map span.cursor');

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
                    cursor: 'pointer'
                };
                title.html(data.title);
                break;

            // done
            case 'done':
                result = {
                    fill: color_fill.data('color-done'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_done,
                    cursor: 'not-allowed'
                };
                title.html(data.title);
                break;
            
            // locked
            case 'locked':
                result = {
                    fill: color_fill.data('color-locked'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_locked,
                    cursor: 'not-allowed'
                };
                title.html(data.title);
                break;

            // closed
            default:
                result = {
                    fill: color_fill.data('color-default'),
                    stroke: color_stroke.data('color-default'),
                    'stroke-width': 1,
                    'stroke-linejoin': 'round',
                    //'title': map_closed,
                    cursor: 'not-allowed'
                };
                title.html('');
        }
        description.html(map_info);
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
                break;
                
            // done
            case 'done':
                result = {
                    fill: color_fill_hover.data('color-done'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_done);
                title.html(data.title);
                break;
                
            // locked
            case 'locked':
                result = {
                    fill: color_fill_hover.data('color-locked'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_locked + '<br />' + data.description);
                title.html(data.title);
                break;

            // closed
            default:
                result = {
                    fill: color_fill_hover.data('color-default'),
                    stroke: color_stroke_hover.data('color-default')
                };
                description.html(map_closed);
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
                });
            });
            ajaxMapDone();
        },
        error: function(result, state, error) {
            alert('Error! Unable to load the map.');
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
                alert('Error! Unable to load states.');
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
    }

});