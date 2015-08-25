$(document).ready(
function(){
	$(".zwiniete").toggle(
	function(){
		img = $(this).prev();
		img.attr('src', 'k2.png');
	},
	function(){
		img = $(this).prev();
			img.attr('src', 'k1.png');	
	});
}); 

