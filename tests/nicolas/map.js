window.onload = function() {
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
  function addAttr(name) {
    var result = {};
    switch ($(name).data("map-type")) {
      // opened
      case "opened":
        result = {
          fill: colorFill["opened"],
          stroke: colorStroke["opened"],
          "stroke-width": 1,
          "stroke-linejoin": "round",
          href: "/map/" + $(name).data("map-link"),
          "title": $(name).data("map-title")
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
          cursor: "help"
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
    return result;
  }

  // set color on hover
  function hoverAttr(name) {
    var result = {};
    switch ($(name).data("map-type")) {
      // opened
      case "opened":
        result = {
          fill: colorFillHover["opened"],
          stroke: colorStroke["hover"]
        };
        break;

      // done
      case "done":
        result = { 
          fill: colorFillHover["done"],
          stroke: colorStroke["hover"] 
        };
        break;

      // closed
      default:
        result = {
          fill: colorFillHover["default"],
          stroke: colorStroke["hover"] 
        };
    }
    return result;
  }

  var map = new Array();
  $.ajax({
    type: "GET",
    url: "file:///Users/nicolas/Documents/Programmation/github/Projet-2A-Voldemort/tests/nicolas/france.json",
    dataType: "json",
    success: function(data){
      console.log(data);
      console.log('ok');
    },
    error: function(result, state, error) {
      alert('Oups !');
      console.log('result: '+result);
      console.log('state: '+state);
      console.log('error: '+error);
    }
  });

  // set hover behaviour
  var current = null;
  for (var state in map) {
    map[state].attr(addAttr("."+state.toString()));
    (function (st, state) {
      st[0].onmouseover = function() {
        st.animate(hoverAttr("."+state.toString()), 300);
        paper.safari();
      };
      st[0].onmouseout = function() {
        st.animate(addAttr("."+state.toString()), 300);
        paper.safari();
      };
    })(map[state], state);
  }

}