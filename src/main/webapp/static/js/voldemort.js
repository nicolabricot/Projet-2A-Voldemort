/*  
 * Author: Nicolas Devenet <nicolas@devenet.info>
 */

// external links
$('a[rel="external"]').click(function() {
    window.open($(this).attr('href'));
    return false;
});

$('a[href="#reset"]').click(function() {
    $.ajax({
        type: 'GET',
        url: './rest/resources/fill',
        success: function(data) {
            $('.notifications').notify({
                message: {text: 'Reset successful!'}
            }).show();
            setTimeout(function() {document.location.href = './';},1250);
        },
        error: function(result, state, error) {
            $('.notifications').notify({
                type: 'error',
                message: {text: 'Impossible to reset the game!'}
            }).show();
            console.log('result: ' + result);
            console.log('state: ' + state);
            console.log('error: ' + error);
        }
    });
    return false;
});