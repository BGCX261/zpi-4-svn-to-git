
$(document).ready(function(){

	$('.o').hide();
	
	$('#selectallitems').toggle(

	function(){
		$('.checkbox').attr('checked', true);
		// zmiana napisu
		var value = $(this).text().replace('Zaznacz', 'Odznacz');
		$(this).text(value);
	},

	function(){
		$('.checkbox').attr('checked', false);
		// zmiana napisu
		var value = $(this).text().replace('Odznacz', 'Zaznacz');
		$(this).text(value);
	})
	
	$('#additem').click(
	function(){
		$('.o').show();
	});
	
	$('.hide_form').click(
	function(){
		$('.o').hide();
		$(':input', '.o').each(
		function(){
				if($(this).attr('type')!='submit')
					$(this).val('');
		});
	});
	
	var divTable = $(".table");
	
	if(divTable.height() > 435)
	{
		divTable.height(435);
		divTable.addClass("belka");
	}
	else
		divTable.removeClass("belka");
	
	/*$('.checkbox').click(
	function(){
		$('.checkbox').each(
		function(){
			var check = 0;
			if($(this).val() == 'on')
				check += 1;
			if(check == 0)
				$('#selectallitems').text('Zaznacz wszystkich użytkowników');
		});
	});*/
	
}); // ready()

