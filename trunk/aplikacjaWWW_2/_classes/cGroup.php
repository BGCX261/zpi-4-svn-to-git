<?php
require_once 'cSQL.php';


class cGroup extends cSQL
{
	// ===  grupa
	// id			:int(11)
	// nazwa		:varchar(20)
	// opis			:text
	// aktywna		:1,0
	public function get_rows()
	{
		$this->query("SELECT * FROM `grupa`");
		$ile=mysql_num_rows($this->wynik);
	
		$ile=($ile/10)+1;
	
		return (int)$ile;
	}
	
	public function add_grupa_user($id,$tab)
	{
		while (list ($key,$val) = @each ($tab)) {
	
			$this->query("INSERT INTO `grupa_uzytkownik` (`grupa_id`, `uzytkownik_id`) VALUES ('$val', '$id');");
		}
	}
	public function del_grupa_user($id)
	{
		$this->query("DELETE FROM `grupa_uzytkownik` WHERE `uzytkownik_id` = $id");
	}
	public function get_grupa_user($id)
	{
		$this->query("SELECT grupa_id FROM grupa_uzytkownik WHERE uzytkownik_id=$id");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[$rzad[grupa_id]]=1;
		}
		return $tab;
	}
	
	public function add_grupa_czytnik($id,$tab)
	{
		while (list ($key,$val) = @each ($tab)) {
		
			$this->query("INSERT INTO `grupa_czytnik` (`grupa_id`, `czytnik_id`) VALUES ('$id', '$val');");
		}
	}
	public function del_grupa_czytnik($id)
	{
		$this->query("DELETE FROM `grupa_czytnik` WHERE `grupa_czytnik`.`grupa_id` = $id");
	}
	public function get_grupa_czytnik($id)
	{
		$this->query("SELECT czytnik_id FROM grupa_czytnik WHERE grupa_id=$id");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[$rzad[czytnik_id]]=1;
		}
		return $tab;
	}
	
	
	
	public function add($nazwa,$opis,$status)
	{
		//todo: cCHECK
		//$this->is_int($status);
				
		$this->query("INSERT INTO grupa (`nazwa` ,`opis` ,`aktywna`)
										VALUES ('$nazwa', '$opis', '$status')");
		return $this->get_last_id();
	}	
	public function edit($id,$nazwa,$opis,$status)
	{
		//todo: odwolanie do cCHECK
		//$this->is_int($id);
		//$this->is_int($status);	
		
		
		$this->query("UPDATE grupa SET `nazwa`='$nazwa',`aktywna`='$status',`opis`='$opis' WHERE `id`='$id' ");
	}
	public function delete($id)
	{
		//todo cCHECK;
		$this->is_int($id);
		
		
		$this->query("DELETE FROM `grupa` WHERE `id` = '$id'");
	}
	public function read($id="0",$page="0")
	{
		if($page>"0")
		{
			$pagemax=20;
			$limit="LIMIT $page,$pagemax";
				
		}
		if($id==0)
			$this->query("SELECT * FROM `grupa` ORDER by aktywna DESC $limit");
		else
			$this->query("SELECT * FROM `grupa` WHERE id='$id'");
		
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad[id],"nazwa"=>$rzad[nazwa],"opis"=>$rzad[opis],"aktywna"=>$rzad[aktywna]);
		}
		return $tab;
	}
	
	public function limit_read($start, $end)
	{
		$this->query("SELECT * FROM grupa LIMIT $start, $end");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad[id],"nazwa"=>$rzad[nazwa],"opis"=>$rzad[opis],"aktywna"=>$rzad[aktywna]);
		}
		return $tab;
	}
	
	public function count()
	{
		$this->query("SELECT COUNT(id) FROM grupa");
		$wynik=$this->wynik;
		$zwroc = mysql_fetch_array($wynik);
		return $zwroc[0];
	}
}
