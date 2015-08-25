<?php
require_once 'cSQL.php';


class cLog extends cSQL
{
	// ===  log_aktywnosci
	// id				:int(11)
	// uzytkownik_id	:int(11)
	// klient_id		:int(11)
	// czas				:datetime
	// opis				:varchar(255)
	public function get_rows()
	{
		$this->query("SELECT * FROM `log_aktywnosci`");
		$ile=mysql_num_rows($this->wynik);
	
		$ile=($ile/10)+1;
	
		return (int)$ile;
	}
	
	public function add($opis)
	{
		//todo: cCHECK
		//$this->is_int($user_id);
		//$this->is_int($klient_id);
		//$this->is_time($czas);
		
		
		
	//	$this->query("INSERT INTO log_aktywnosci (`id` ,`uzytkownik_id` ,`czytnik_id` ,`czas`, `opis`)
	//							VALUES ( NULL , '7', '1','2012-03-31 00:00:00','$opis')");
	//	return $this->get_last_id();
				
	}
	
	public function edit()
	{
		//todo: jesli bedzie trzeba		
	}
	
	public function delete()
	{
		//todo: jesli bedzie trzeba	
	}
	
	public function read($page="0")
	{
		$pagemax=20;
		if($page>"0")
		{
				
			$limit="LIMIT $page,$pagemax";
				
		}
		else
		{
			$limit="LIMIT 0,$pagemax";
		}
		
		//todo: pytanie co potrzebujemy i jak grupujemy ? na razie czyta wszystko 
		// narazie wyswietla tylko info czyste z bazy trzeba dorobic podzapytanie do rozpoznawania userow
		$this->query("SELECT * FROM `log_aktywnosci` ORDER BY id DESC $limit");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("czas"=>$rzad[czas],"opis"=> $rzad[opis]);
			
		}
		return $tab;
		
	}
}