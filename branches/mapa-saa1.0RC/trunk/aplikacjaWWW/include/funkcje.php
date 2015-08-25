<?php

require_once '_classes/cGroup.php';
require_once '_configs/config.php';

define('PORCJUJ_GRUPY', '0');
define('PORCJUJ_UZYTKOWNIKOW', '1');

/**
  
  parametry:
    $aktualnie - aktualna strona, jeżeli wartość będzie -1, to znaczy,
                 że mają być wyświetlone wszystkie rekordy i nie wyświetli
                 się "następny" oraz pogrubiony i bez linku "wszystkie"
    $ilosc - ilopsc wszystkich rekordów do wyświetlenia
    $na_stronie - ilość na stronie wyników
    $link1 - początek linku np. 'plik.php?'
    $link2 - kotwica w dokumencie np. '#akapit' - paramtr opcjonalny
*/
function porcjowanie($aktualnie, $ilosc, $na_stronie, $link1, $link2='')
{
	$stron = ceil($ilosc/$na_stronie);

	if($stron > 1)
	{
		if($aktualnie != 0 and $aktualnie != -1)
		{
			$poprzednia = $aktualnie-$na_stronie;
			$zwroc[] = '<a href="'.$link1.'porcja='.$poprzednia.$link2.'">poprzednia</a>';
		}
		
		$parametr=0;
		
		for($i=1; $i <= $stron; $i++)
		{
			$do_tylu = $aktualnie-$na_stronie;
			$do_przodu = $aktualnie+$na_stronie;
			$cofnij = $stron-3;
			
			if($stron > 9 and $i > 3 and $i <= $cofnij and ($parametr < $do_tylu or $parametr > $do_przodu))
			{
				$zwroc[] = '...';
				
				if($parametr < $aktualnie)
				{
					$i = $do_tylu/$na_stronie;
					$parametr = $do_tylu-$na_stronie;
					$cofnij = $stron-2;
					
					if($i == $cofnij)
					{
						$i--;
						$parametr -= $na_stronie;
					}
				}
				else
					{
						$i = $cofnij;
						$parametr = ($cofnij-1)*$na_stronie;
					}
			}
			elseif($parametr == $aktualnie)
			{
				$zwroc[] = '<b>'.$i.'</b>';
			}
			else
				{
					$zwroc[] = ' <a href="'.$link1.'porcja='.$parametr.$link2.'">'.$i.'</a> ';
				}
			
			$parametr += $na_stronie;
		}
		
		$nastepna = $aktualnie+$na_stronie;
		
		if($parametr != $nastepna)
		{
			if($aktualnie != -1)
				$zwroc[] = '<a href="'.$link1.'porcja='.$nastepna.$link2.'">następna</a>';
		}
		
		if($aktualnie == -1)
			$zwroc[] = '<b>wszystkie</b>';
		else
			$zwroc[] = '<a href="'.$link1.'wszystko=1'.$link2.'">wszystkie</a>';
		
		$zwroc = implode(' ', $zwroc);
		
		return $zwroc;
	}
	return NULL;
}

/**
  Funkcja pakuje w tablice potrzebne dane do wyswietlania stronnicowania
  wynikow
  
  zwraca:
    tablice z danymi lub NULL jezeli otrzyma zly drugi parametr
  
  parametry:
    $strona - aktualna strona, jeżeli wartość będzie -1, to znaczy,
                 że mają być wyświetlone wszystkie rekordy i nie wyświetli
                 się "następny" oraz pogrubiony i bez linku "wszystkie"
    $co - jedna ze stalych: PORCJUJ_GRUPY, PORCJUJ_UZYTKOWNIKOW
*/
function porcjuj($strona, $co)
{
	$zwroc = array();
	
	// wybieramy jakie dane dzielimy
	switch($co)
	{
	case PORCJUJ_GRUPY:
		$sql = new cGroup();
		$link = 'index.php?opcja=wyswietl&rodzaj=grupe&';
		break;
	case PORCJUJ_UZYTKOWNIKOW:
		$sql = new cUser();
		$link = 'index.php?opcja=wyswietl&rodzaj=uzytkownikow&';
		break;
	default:
		return NULL;
	}
	
	// pobieramy ilosc rekordow
	$zwroc['rekordow'] = $sql->count();
	
	if($strona >= 0 and $strona % ITEMS_ON_PAGE == 0 and $strona < $zwroc['rekordow'] )
	{
		$zwroc['rekord_n'] = ($strona/ITEMS_ON_PAGE+1)*ITEMS_ON_PAGE;
		
		$zwroc['encje'] = $sql->limit_read($strona, ITEMS_ON_PAGE);
		
		if((int)$zwroc['rekord_n'] > (int)$zwroc['rekordow'])
			$zwroc['rekord_n'] = $zwroc['rekordow'];
		
		$zwroc['porcjowanie'] = porcjowanie($strona, $zwroc['rekordow'], ITEMS_ON_PAGE, $link );
	}
	else
		{
			// wyświetlamy wszystkie rekordy
			$zwroc['porcjowanie'] = porcjowanie($strona, $zwroc['rekordow'], ITEMS_ON_PAGE, $link );
			$zwroc['rekord_n'] = $zwroc['rekordow'];
			$zwroc['encje'] = $sql->read();
		}
	
	return $zwroc;
}

?>

