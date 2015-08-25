<?php
require_once 'cSQL.php';


class cCzytnik extends cSQL
{
	// ===  czytnik
	// id				:int(11)
	// nazwa			:varchar(255)
	// opis				:varhchar(255)
	// aktywny			:1,0
	public function get_rows()
	{
		$this->query("SELECT * FROM `czytnik`");
		$ile=mysql_num_rows($this->wynik);
	
		$ile=($ile/10)+1;
	
		return (int)$ile;
	}
	
	public function add($nazwa,$opis,$status)
	{
		// todo: odwolanie do cCHECK sprawdzenie wartosci czy sa prawidlowe
		//$this->is_int($status);
						
		$this->query("INSERT INTO czytnik (`id` ,`nazwa` ,`opis` ,`aktywny`	)
						VALUES ( NULL , '$nazwa', '$opis', '$status')");
		return $this->get_last_id();
		
	}
	public function edit($id,$nazwa,$opis,$status)
	{
		//todo: odwolanie do cCHECK
		//$this->is_int($id);
		//$this->is_int($status);
		//$this->is_int($user_id);
		
		
		
		
		
		
		$this->query("UPDATE czytnik SET `nazwa`='$nazwa',`opis`='$opis',`status`='$status' WHERE `id`='$id' ");
								
		
	}
	public function delete($id)
	{
		//todo: cCHECK
		$this->is_int($id);
		
		
		$this->query("DELETE FROM `czytnik` WHERE `id` = '$id'");
		
	}
	public function read()
	{
		//todo: cCHECK
		
		$this->query("SELECT * FROM `czytnik`");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad[id],"nazwa"=>$rzad[nazwa],"opis"=>$rzad[opis],"aktywny"=>$rzad[aktywny]);
		}
		return $tab;
	}
}