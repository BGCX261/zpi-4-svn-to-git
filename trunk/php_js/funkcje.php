<?php

/**
  Funkcja tworzy linki do stron: poprzedni 1 2 3 4 5 6 ... 7 8 następny wszystkie
  
  parametry:
    $aktualnie - aktualna strona, jeżeli wartość będzie -1, to znaczy,
                 że mają być wyświetlone wszystkie rekordy i nie wyświetli
                 się "następny" oraz pogrubiony i bez linku "wszystkie"
    $ilosc - ilosc wszystkich rekordów do wyświetlenia
    $na_stronie - ilość na stronie wyników
    $link1 - początek
    $link2 - kotwica
*/

function porcjowanie($aktualnie, $ilosc, $na_stronie, $link1, $link2='')
{
	$stron = ceil($ilosc/$na_stronie);

	if($stron > 1)
	{
		if($aktualnie != 0 and $aktualnie != -1)
		{
			$poprzednia = $aktualnie-$na_stronie;
			$zwroc[] = '<a href="'.$link1.'str='.$poprzednia.$link2.'">poprzednia</a>';
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
					$zwroc[] = ' <a href="'.$link1.'str='.$parametr.$link2.'">'.$i.'</a> ';
				}
			
			$parametr += $na_stronie;
		}
		
		$nastepna = $aktualnie+$na_stronie;
		
		if($parametr != $nastepna)
		{
			if($aktualnie != -1)
				$zwroc[] = '<a href="'.$link1.'str='.$nastepna.$link2.'">następna</a>';
		}
		
		if($aktualnie == -1)
			$zwroc[] = '<b>wszystkie</b>';
		else
			$zwroc[] = '<a href="'.$link1.'all=1'.$link2.'">wszystkie</a>';
		
		$zwroc = implode(' ', $zwroc);
		
		return $zwroc;
	}
	return NULL;
}
?>

