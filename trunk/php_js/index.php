<?php
session_start();

header('Content-type: text/html; charset=UTF-8');

include_once "_plugins/smarty/Smarty.class.php";
include_once "_configs/config.php";

include_once "include/funkcje.php";

function __autoload($class_name) 
{
	require_once '_classes/'.$class_name . '.php';
}

$sesja = new cSession();
$sesja->start();
$sesja->set('login', 'Administrator');

$sql = new cSQL(CONF_DB_HOST, CONF_DB_LOGIN, CONF_DB_PASS, CONF_DB_NAME);

//  WYBIERAM ODPOWIEDNIA STRONĘ 

// sprawdz czy czy podano paramatr `page` poprzez GET
if(isset($_GET['page']))
{
	// sprawdz czy wybrano grupy (grupy.html)
	if(strcmp($_GET['page'], 'groups') == 0)
	{
		// pobieram ilość rekordów z tabeli grupa
		$q = "SELECT COUNT(id) FROM grupa";
		$sql->query($q);
		$rekordow = $sql->get_rows();
		
		// zmienne pomocnicze przy stronnicowaniu
		if(isset($_GET['str']))
		{
			if($_GET['str'] > 0 and $_GET['str'] % ITEMS_ON_PAGE == 0 and
			$_GET['str'] < $rekordow[0] )
				$strona = (int)$_GET['str'];
			else
				$strona = 0;
		}
		else
			$strona = 0;
		
		if(isset($_GET['all']))
		{
			// wyświetlam wszystkie rekordy posortowana wg nazwy
			$sql->query("SELECT * FROM grupa ORDER BY nazwa");
			// funkcja pochodzi z include/fukcje.php - opis w tym pliku
			$porcjowanie = porcjowanie(-1, $rekordow[0], ITEMS_ON_PAGE, 'index.php?page=groups&' );
			$rekord_n = $rekordow[0];
		}
		else
			{
				// pobieram wyiki na stronę
				$sql->query("SELECT * FROM grupa ORDER BY nazwa LIMIT $strona,".ITEMS_ON_PAGE);
				$porcjowanie = porcjowanie($strona, $rekordow[0], ITEMS_ON_PAGE, 'index.php?page=groups&' );
				// numer grupy wyświetlanych rekordów
				$rekord_n = ($strona/ITEMS_ON_PAGE+1)*ITEMS_ON_PAGE;
				
				if($rekord_n > $rekordow[0])
					$rekord_n = $rekordow[0];
			}
			
		$groups = array();
		
		// tworze drugie polaczenie i bedziemy pobierac ile jest uzytkownikow w danej grupie
		$sql2 = new cSQL(CONF_DB_HOST, CONF_DB_LOGIN, CONF_DB_PASS, CONF_DB_NAME);
		
		// pobieram wszystkie rekordy z tabeli grupa
		while($encja = $sql->get_rows())
		{
			// zamieniam z wartoaści boolowskiej na bardziej ludzka :)
			if($encja['aktywna'] == '1')
				$encja['aktywna'] = 'aktywna';
			else
				$encja['aktywna'] = 'nieaktywna';
			
			// pobieram ilosc uzytkownikow z danej grupy
			$q = "SELECT COUNT(*) AS `uzytkownicy` FROM grupa_uzytkownik WHERE grupa_id=$encja[id]";
			$sql2->query($q);
			$num = $sql2->get_rows();
			// dodaje licbe uzytkownikow
			$encja['uzytkownikow'] = $num['uzytkownicy'];
			// dodaje elementy do tablicy groups
			$groups[] = $encja;
		}
		
		$smarty->assign("title","Grupy");
		$smarty->assign("grupy", $groups);
		$smarty->assign("login", $sesja->get('login'));
		$smarty->assign("porcjowanie", $porcjowanie);
		$smarty->assign("rekordow", $rekordow[0]);
		$smarty->assign("strona", ($strona/ITEMS_ON_PAGE+1));
		$smarty->assign("rekord_n", $rekord_n);
		
		$smarty->display('grupy.tpl');
	}
	else
		if(strcmp($_GET['page'], 'users') == 0)
		{
			// pobieram ilosc uzytkownikow w tabeli (w widoku)
			$q = "SELECT COUNT(id) FROM v_uzytkownicy";
			$sql->query($q);
			$rekordow = $sql->get_rows();
			
			// zmienne pomocnicze przy stronnicowaniu
			if(isset($_GET['str']))
			{
				if($_GET['str'] > 0 and ($_GET['str'] % ITEMS_ON_PAGE) == 0 and
				$_GET['str'] < $rekordow[0] )
					$strona = $_GET['str'];
				else
					$strona = 0;
			}
			else
				$strona = 0;
			
			if(isset($_GET['all']))
			{
				// pobieram wszystkie rekordy
				$sql->query("SELECT * FROM v_uzytkownicy ORDER BY nazwisko,imie");
				$porcjowanie = porcjowanie(-1, $rekordow[0], ITEMS_ON_PAGE, 'index.php?page=users&' );
				$rekord_n = $rekordow[0];
			}
			else
				{
					// pobieram wyiki na stronę
					$sql->query("SELECT * FROM v_uzytkownicy ORDER BY nazwisko,imie LIMIT $strona,".ITEMS_ON_PAGE);
					$porcjowanie = porcjowanie($strona, $rekordow[0], ITEMS_ON_PAGE, 'index.php?page=users&' );
					$rekord_n = ($strona/ITEMS_ON_PAGE+1)*ITEMS_ON_PAGE;
				
					if($rekord_n > $rekordow[0])
						$rekord_n = $rekordow[0];
				}
			
			$users = array();
			$groups = array();
			
			while($encja = $sql->get_rows())
			{
				// zamieniam z wartoaści boolowskiej na bardziej ludzka :)
				if($encja['status'] == '1')
					$encja['status'] = 'aktywny';
				else
					$encja['status'] = 'nieaktywny';
	
				// dodaje elementy do tablicy users
				$users[] = $encja;
			}
			
			// pobieram wszyskie grupy, aby wypelnic element select podczas dodawania
			// nowego uzytkownika
			$sql->query("SELECT id,nazwa FROM grupa");
			
			while($encja = $sql->get_rows())
			{
				// dodajem elementy do tablicy groups
				$groups[] = $encja;
			}
			
			$smarty->assign("title","Użytkownicy");
			$smarty->assign("uzytkownicy", $users);
			$smarty->assign("grupy", $groups);
			$smarty->assign("login", $sesja->get('login'));
			$smarty->assign("porcjowanie", $porcjowanie);
			$smarty->assign("rekordow", $rekordow[0]);
			$smarty->assign("strona", ($strona/ITEMS_ON_PAGE+1));
			$smarty->assign("rekord_n", $rekord_n);
			
			$smarty->display('uzytkownicy.tpl');
		}
		else
			if(strcmp($_GET['page'], 'customers') == 0)
			{
				$smarty->assign("title","Klienci");
				
				$smarty->display('klienci.tpl');
			}
}
else // nie podano parametru `page` wyswitlam stronę główną
	{
		$smarty->assign("title", "Index");

		$smarty->display('index.tpl');
	}

$sql->close();
?>

