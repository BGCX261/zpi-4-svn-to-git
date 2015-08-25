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
		
		
		
		
		
		
		$this->query("UPDATE czytnik SET `nazwa`='$nazwa',`opis`='$opis',`aktywny`='$status' WHERE `id`='$id' ");
		
								
		
	}
	public function delete($id)
	{
		//todo: cCHECK
		$this->is_int($id);
		
		
		$this->query("DELETE FROM `czytnik` WHERE `id` = '$id'");
		
	}
	public function read($id="0",$page="0")
	{
		//todo: cCHECK
		
		if($page>"0")
		{
			$pagemax=20;
			$limit="LIMIT $page,$pagemax";
				
		}
		if($id==0)
		{
			$this->query("SELECT * FROM `czytnik` ORDER by aktywny DESC $limit");
			
		}
		else
		{
			$this->query("SELECT * FROM `czytnik` WHERE id='$id'");
			
		}
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad[id],"nazwa"=>$rzad[nazwa],"opis"=>$rzad[opis],"aktywny"=>$rzad[aktywny]);
		}
		return $tab;
	}
	
	public function add_czytnik_user($id,$tab)
	{
		while (list ($key,$val) = @each ($tab)) {
	
			$this->query("
			
			INSERT INTO `test`.`czytnik_uzytkownik` (`czytnik_id`, `uzytkownik_id`, `blacklist`) VALUES ('$val', '$id', '0');");
			//echo "INSERT INTO `czytnik_uzytkownik` (`czytnik_id`, `uzytkownik_id`) VALUES ('$val', '$id');<br/>";
		}
	}
	public function del_czytnik_user($id)
	{
		$this->query("DELETE FROM `czytnik_uzytkownik` WHERE `uzytkownik_id` = '$id'");
		//echo "DELETE FROM `czytnik_uzytkownik` WHERE `uzytkownik_id` = '$id'<br/>";
	}
	
	public function get_czytnik_user($id)
	{
		$this->query("SELECT czytnik_id FROM czytnik_uzytkownik WHERE uzytkownik_id='$id'");
		//echo "SELECT czytnik_id FROM czytnik_uzytkownik WHERE uzytkownik_id='$id'<br/>;";
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[$rzad[czytnik_id]]=1;
		}
		return $tab;
	}
	
	
	
	
	
	
}