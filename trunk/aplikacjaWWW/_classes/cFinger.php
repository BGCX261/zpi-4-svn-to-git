<?php
require_once 'cSQL.php';


class cFinger extends cSQL
{
	// ===  odcisk_palca
	// id				:int(11)
	// hash				:varchar(255)
	// aktywny			:1,0
	// uzytkownik_id	:int(11)
	public function get_rows()
	{
		$this->query("SELECT * FROM `odcisk_palca`");
		$ile=mysql_num_rows($this->wynik);
	
		$ile=($ile/10)+1;
	
		return (int)$ile;
	}
	
	public function add($hash,$status,$user_id)
	{
		// todo: odwolanie do cCHECK sprawdzenie wartosci czy sa prawidlowe
		$this->is_int($status);
		$this->is_int($user_id);
				
		$this->query("INSERT INTO odcisk_palca (`id` ,`hash` ,`aktywny` ,`uzytkownik_id`	)
						VALUES ( NULL , '$hash', '$status', '$user_id'");
		return $this->get_last_id();
		
	}
	public function edit($id,$hash,$status,$user_id)
	{
		//todo: odwolanie do cCHECK
		$this->is_int($id);
		$this->is_int($status);
		$this->is_int($user_id);
		
		
		
		
		
		
		$this->query("UPDATE odcisk_palca SET `hash`='$hash',`aktywny`='$status',`uzytkownik_id`='$user_id' WHERE `id`='$id' ");
								
		
	}
	public function delete($id)
	{
		//todo: cCHECK
		$this->is_int($id);
		
		
		$this->query("DELETE FROM `odcisk_palca` WHERE `uzytkownik_id` = '$id'");
		
	}
	public function read($hash)
	{
		//todo: cCHECK
		
		$this->query("SELECT uzytkownik_id FROM `odcisk_palca` WHERE `hash` = '$hash'");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab=$rzad[uzytkownik_id];
		}
		return $tab;
	}
}