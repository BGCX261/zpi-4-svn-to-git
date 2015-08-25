/* funkcje pomocnicze */

$(function(){
	$(".del").click(function(){
		var del_id = $(this).attr("id");
		var tab = $(this).attr("name");
		var info = 'id=' + del_id + "&tab=" + tab;
		var msg = '';
		
		if(tab == 'uzytkownik')
			msg = "Czy napewno usunąć tego użytkownika?";
		else
			msg = "Czy napewno usunąć tę grupę?";
		
		if(confirm(msg))
		{
			$.ajax({
				type: "POST",
				url: "delete.php",
				data: info,
				success: function(){
				},
				statusCode: {
					404: function(){
						alert('page not found');
					}
				}
			});
			$(this).parents(".odd").animate({backgrounColor: "#fbc7c7"}, "fast").animate({ opacity: "hide"}, "slow");
		}
		return false;
	});
});

function delete_items(){
	var ids = ''; // ID zaznaczonych elementow oddzielonymi przecinkami
	var items = 0; // ilosc zaznaczonych rekordow
	var tab = $('.del').attr('name');
	$('input:checkbox', '.odd').each(
	function(){
		if($(this).attr('checked')=='checked'){
			ids += $(this).attr('id') + ',';
			items++;
		}
	});
	
	if(items==0)
		return false;
	
	// usun ostatni przecinek i sklep dane
	ids = 'ids=' + ids.substr(0, ids.length-1) + '&tab=' + tab;
	
	if(confirm('Czy chcesz usunąć ' + items + ' zaznaczonych rekordów?')) {
		$.ajax({
			type: "POST",
			url: "delete.php",
			data: ids,
			success: function(){
				
			},
			statusCode: {
				404: function(){
					alert('page not found');
				}
			}
		});
		
		$('input:checkbox', '.odd').each(
		function(){
		if($(this).attr('checked')=='checked'){
			$(this).parents(".odd").animate({backgrounColor: "#fbc7c7"}, "fast").animate({ opacity: "hide"}, "slow");
		}
	});
		
	}
	
	
	
	return true;
}

/*$(*/function add_user(){
	/*$('.add').click(function(){*/
		var imie = $("#name").val();
		var nazwisko = $("#surname").val();
		var opis = $("#description").val();
		var grupa = $("#group").val();
		/*var tab = $(this).attr("name");*/
		var info = "name=" + imie + "&surname=" + nazwisko + "&desc=" + opis + "&group=" + grupa + "&tab=uzytkownik";
		
		$.ajax({
			type: "POST",
			url: "add.php",
			data: info,
			success: function(){
				alert('Dodano użytkownika pomyślnie.');
				// ukrywamy formularz
				$('.od').hide(); 
				// czyscimy pola formularza
				$(':input', '.od').each(
				function(){
					if($(this).attr('type')!='submit')
						$(this).val('');
				});
				// oswierzamy strone
				location.reload();
			}
		});
		
		/*return false;
	});*/
}/*);*/

function add_group(){
	var nazwa = $('#name').val();
	var opis = $('#description').val();
	var info = "name=" + nazwa + "&desc=" + opis + "&tab=grupa";
	
	$.ajax({
		type: "POST",
		url: "add.php",
		data: info,
		success: function(){
			alert("Dodano grupę pomyślnie.");
			$('.od').hide();
			$(':input', '.od').each(
			function(){
				if($(this).attr('type')!='submit')
					$(this).val('');
			});
			location.reload();
		}
	});
}

/*function zaznaczenie(){
	$('input:checkbox:checked').val(); 
	$('input:checkbox').each(
		function(){
			var check = 0;
			if($(this,':checked').attr('checked') == 'checked')
				check += 1;
			if(check == 0)
				$('#selectallitems').text('Zaznacz wszystkich użytkowników');
		});
}*/

