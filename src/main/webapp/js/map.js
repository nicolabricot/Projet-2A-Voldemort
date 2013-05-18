$(document).ready(function() {
    var paper = new Raphael(document.getElementById('map'), 520, 560);

    var colorStroke = {
        "default": "#888",
        "opened": "#888",
        "done": "#888",
        "hover": "#888"
    };
    var colorFill = {
        "default": "#fff",
        "opened": "#47FF75",
        "done": "#cdcdcd"
    };
    var colorFillHover = {
        "default": "#eaeaea",
        "opened": "#3ACC60",
        "done": "#888"
    };

    // set color and text
    function addAttr(data) {
        var result = {};
        switch (data.type) {
            // opened
            case "opened":
                result = {
                    fill: colorFill["opened"],
                    stroke: colorStroke["opened"],
                    "stroke-width": 1,
                    "stroke-linejoin": "round",
                    href: "./map.jsp?map=" + data.link,
                    "title": data.title,
                    cursor: "pointer"
                };
                break;

                // done
            case "done":
                result = {
                    fill: colorFill["done"],
                    stroke: colorStroke["done"],
                    "stroke-width": 1,
                    "stroke-linejoin": "round",
                    "title": "You have already done this region",
                    cursor: "not-allowed"
                };
                break;

                // closed
            default:
                result = {
                    fill: colorFill["default"],
                    stroke: colorStroke["default"],
                    "stroke-width": 1,
                    "stroke-linejoin": "round",
                    "title": "This region is closed. Finish first opened quests",
                    cursor: "not-allowed"
                };
        }
        $("#map-info p").html(map_info);
        return result;
    }

    // set color on hover
    function hoverAttr(data) {
        var result = {};
        switch (data.type) {
            // opened
            case "opened":
                result = {
                    fill: colorFillHover["opened"],
                    stroke: colorStroke["hover"]
                };
                $("#map-info p").html(data.title);
                break;

                // done
            case "done":
                result = {
                    fill: colorFillHover["done"],
                    stroke: colorStroke["hover"]
                };
                $("#map-info p").html("You have already done this region");
                break;

                // closed
            default:
                result = {
                    fill: colorFillHover["default"],
                    stroke: colorStroke["hover"]
                };
                $("#map-info p").html("This region is closed. Finish first opened quests");
        }
        return result;
    }

    var map_info = "Survolez une zone pour avoir plus d’informations";

    var map_load = $("#map").data("map-load");
    var map = new Array();
    $.ajax({
        type: "GET",
        url: "rest/map/" + map_load,
        dataType: "json",
        success: function(data) {
            $.map(data, function(value, key) {
                map[key] = paper.path(value)
                        .attr(addAttr({"type": null}))
                        .hover(function() {
                    hoverAttr({"type": null});
                })
                        .mouseout(function() {
                    $("#map-info p").html(map_info);
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
            type: "GET",
            url: "rest/map/states/" + map_load,
            dataType: "json",
            success: function(data) {
                ajaxStatesDone(data);
            },
            error: function(result, state, error) {
                alert('Oups !');
                console.log('result: ' + result);
                console.log('state: ' + state);
                console.log('error: ' + error);
            }
        });
    }

    function ajaxStatesDone(data) {
        $.map(data, function(value, key) {
            map[key.toString()].attr(addAttr(value[0]));
            (function(st, v) {
                st[0].onmouseover = function() {
                    st.animate(hoverAttr(v), 300);
                    paper.safari();
                };
                st[0].onmouseout = function() {
                    st.animate(addAttr(v), 300);
                    paper.safari();
                };
            })(map[key], value[0]);
        });
        $("#map-info p").html(map_info);
    }

});