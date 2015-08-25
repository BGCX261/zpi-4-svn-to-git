

$(function(){
	$(".del").click(function(){
		var tab = $(this).attr("name");
		var msg = '';
		
		if(tab == 'uzytkownik')
			msg = "Czy napewno usunąć tego użytkownika?";
		else
			msg = "Czy napewno usunąć tę grupę?";
		
		if(confirm(msg))
		{
			$(this).parents(".odd").animate({backgrounColor: "#fbc7c7"}, "fast").animate({ opacity: "hide"}, "slow");
		}
		return false;
	});
});

function add_user(){
	alert('Dodano użytkownika pomyślnie.');
}

function add_group(){
	alert("Dodano grupę pomyślnie.");
}

function zaznaczenie(){
	$('input:checkbox:checked').val(); 
	$('input:checkbox').each(
		function(){
			var check = 0;
			if($(this,':checked').val() == 'on')
				check += 1;
			if(check == 0)
				$('#selectallitems').text('Zaznacz wszystkich użytkowników');
		});
}

