<?php
require_once 'cSQL.php';


class cUser extends cSQL
{
	// ===  uzytkownik
	// id			:int(11)
	// imie			:varchar(20)
	// nazwisko		:varchar(30)
	// opis			:text
	// aktywny		:1,0
	public function add($imie,$nazwisko,$opis,$status)
	{
		//todo: cCHECK
		$this->is_int($status);
		
		
		$this->query("INSERT INTO `uzytkownik` (`imie` ,`nazwisko` ,`opis` ,`aktywny`)	
						VALUES ('$imie', '$nazwisko', '$opis', '$status')");
		return $this->get_last_id();
	}
	public function edit($id,$imie,$nazwisko,$opis,$status)
	{
		//todo: cCHECK
		//$this->is_int($id);
		//$this->is_int($status);
		
		
		
		
		$this->query("UPDATE `uzytkownik` SET `imie` = '$imie',`nazwisko` = '$nazwisko',`opis` = '$opis',
						`aktywny` = '$status' WHERE `id` =$id");
		
		
	}
	public function delete($id)
	{
		//todo: cCHECK
		$this->is_int($id);
		
		
		//$this->query("DELETE FROM `uzytkownik` WHERE `uzytkownik_id` = '$id'");
		$this->query("DELETE FROM `uzytkownik` WHERE `id` = '$id'");
	}
	
	public function read($id=0)
	{
		if($id==0)
			$this->query("SELECT * FROM `uzytkownik`");
		else
			$this->query("SELECT * FROM `uzytkownik` WHERE id='$id'");
		$tab = array();
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad['id'],"imie"=>$rzad['imie'],"nazwisko"=>$rzad['nazwisko'],"aktywny"=>$rzad['aktywny'],"opis"=>"$rzad[opis]");
		}
		return $tab;
	}
	
	public function limit_read($start, $end)
	{
		$this->query("SELECT * FROM uzytkownik LIMIT $start, $end");
		$tab = array();
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad['id'],"imie"=>$rzad['imie'],"nazwisko"=>$rzad['nazwisko'],"aktywny"=>$rzad['aktywny'],"opis"=>"$rzad[opis]");
		}
		return $tab;
	}
	
	public function count()
	{
		$this->query("SELECT COUNT(id) FROM uzytkownik");
		$wynik=$this->wynik;
		$zwroc = mysql_fetch_array($wynik);
		return (int)$zwroc[0];
	}
	public function get_rows()
	{
		$this->query("SELECT * FROM `uzytkownik`");
		$ile=mysql_num_rows($this->wynik);
	
		$ile=($ile/10)+1;
	
		return (int)$ile;
	}
	
}
