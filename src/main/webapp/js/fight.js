/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var path_ajax = './rest/fight/FightDemo';

$.ajax({
    type: 'GET',
    url: path_ajax,
    dataType: 'json',
    success: function(data) {
        var result = '';
        // add synthese
        result += '<div class="row" style="margin-bottom: 15px;"><div class="span4" style="text-align:center;"><p class="well well-small">Fight during ' + data.fight.length + ' phases</p></div>';
        // add link to return to map
        result += '<div class="span4" style="text-align:center; margin-top: 5px;"><a class="btn " href="./map.jsp?map=' + $('#fight').data('map-id') + '">Next step <i class="icon-share-alt"></i></a></div>'
        // fast result of the fight
        result += '<div class="span4 pull-right" style="text-align: center;">';
        if (data.defenser.id != data.fight[data.fight.length - 1].winner.id)
            result += '<p class="alert alert-success">You win against ' + data.defenser.name + ' :)</p>';
        else
            result += '<p class="alert alert-error">You lose against ' + data.defenser.name + ' :/</p>';
        result += '</div></div>';
                // add animation
        result += '<div class="center"><img src="./static/media/fight-' + (data.defenser.id != data.fight[data.fight.length - 1].winner.id ? 'win' : 'lose') + '.gif" alt="fight animation" /></div>';
        // add description of each phases
        result += '<div id="rapport-link"></div>';
        result += '<table class="table table-striped table-hover" id="rapport">';
        for (i = 0; i < data.fight.length; i++) {
            result += '<tr><td>' + (i + 1) + '</td><td>' + data.fight[i].description + '</td></tr>';
        }
        result += '</table>';
        // display the result
        $('#fight').html(result);

        function toggleDisplay() {
            if (display) {
                $('#rapport').show();
                $('#rapport-link').html("<i class=\"icon-minus-sign\"></i> Hide report");
            }
            else {
                $('#rapport').hide();
                $('#rapport-link').html("<i class=\"icon-plus-sign\"></i> Display report");
            }
            display = !display;
        }

        var display = false;
        toggleDisplay();
        $('#rapport-link').click(function() {
            toggleDisplay();
        });
    },
    error: function(result, state, error) {
        $('.notifications').notify({
            type: 'error',
            message: {text: 'Impossible to load the fight!'}
        }).show();
        console.log('result: ' + result);
        console.log('state: ' + state);
        console.log('error: ' + error);
    }
});