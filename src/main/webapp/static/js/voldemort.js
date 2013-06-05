/*  
 * Author: Nicolas Devenet <nicolas@devenet.info>
 */

// external links
$('a[rel=\"external\"]').click(function() {
  window.open($(this).attr('href'));
  return false;
});