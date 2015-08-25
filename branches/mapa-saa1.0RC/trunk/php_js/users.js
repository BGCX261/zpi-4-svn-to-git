
$(document).ready(function(){
	// ukrywam formularz o klasie `od` - dadawania użytkownika lub grupy
	$('.od').hide();
	
	// funkcja toggle() przy kliknięciu parzystym wywołuje fukcję pierwsza
	// a przy kliknięciu nieparzystym funkcję drugą
	$('#selectallitems').toggle(
	// funkcja pierwsza
	function(){
		// zaznaczam wszystkie elementy klasy `checkbox`
		$('.checkbox').attr('checked', true);
		// zmieniam na treść linku w menu
		$(this).text('Odznacz wszystkich użytkowników');
	},
	// funkcja druga
	function(){
		// odznaczam wszyskie elementy input klasy checkbox
		$('.checkbox').attr('checked', false);
		// zmieniam na napis linka w menu
		$(this).text('Zaznacz wszystkich użytkowników');
	})
	
	// zdarzenie klikniecia na link o klasie `additem` (prawe menu - dodaj nowego(ą) użytkownika(grupę))
	$('#additem').click(
	function(){
		// odkrywam formularz
		$('.od').show();
	});
	
	$('.hide_form').click(
	function(){
		$('.od').hide();
		$(':input', '.od').each(
		function(){
			$(this).val('');
		});
	});
	
	
}); // ready()


