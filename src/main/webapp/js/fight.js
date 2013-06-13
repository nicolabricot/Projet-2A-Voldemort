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
        var result = '<div class="row" style="margin-bottom: 15px;"><div class="span4" style="text-align:center;"><p class="well well-small">Fight during ' + data.fight.length + ' phases</p></div>';                
        result += '<div class="span4 pull-right" style="text-align: center;">';
        // fast result of the fight
        if (data.defenser.id != data.fight[data.fight.length-1].winner.id)
            result += '<p class="alert alert-success">You win against ' + data.defenser.name + ' :)</p>';
        else
            result += '<p class="alert alert-error">You lose against ' + data.defenser.name + ' :/</p>';
        result += '</div></div>';
        // add description of each phases
        result
        result += '<table class="table table-striped table-hover">';
        for (i=0; i<data.fight.length; i++) {
            result += '<tr><td>' + (i+1) + '</td><td>' + data.fight[i].description + '</td></tr>';
        }        
        result += '</table>';
        // display the result
        $('#fight').html(result);
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