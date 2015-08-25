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
	
	public function add($nazwa,$opis,$status)
	{
		//todo: cCHECK
		$this->is_int($status);
				
		$this->query("INSERT INTO grupa (`nazwa` ,`opis` ,`aktywna`)
										VALUES ('$nazwa', '$opis', '$status')");
		return $this->get_last_id();
	}	
	public function edit($id,$nazwa,$opis,$status)
	{
		//todo: odwolanie do cCHECK
		$this->is_int($id);
		$this->is_int($status);	
		
		
		$this->query("UPDATE grupy SET `nazwa`='$nazwa',`aktywna`='$status',`opis`='$opis' WHERE `id`='$id' ");
	}
	public function delete($id)
	{
		//todo cCHECK;
		$this->is_int($id);
		
		
		$this->query("DELETE FROM `grupa` WHERE `id` = '$id'");
	}
	public function read($id=0)
	{
		if($id==0)
			$this->query("SELECT * FROM `grupa`");
		else
			$this->query("SELECT * FROM `grupa` WHERE id='$id'");
		$tab = array();
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad['id'],"nazwa"=>$rzad['nazwa'],"opis"=>$rzad['opis'],"aktywna"=>$rzad['aktywna']);
		}
		return $tab;
	}
	
	public function limit_read($start, $end)
	{
		$this->query("SELECT * FROM grupa LIMIT $start, $end");
		$wynik=$this->wynik;
		while($rzad = mysql_fetch_array($wynik))
		{
			$tab[]=array("id"=>$rzad['id'],"nazwa"=>$rzad['nazwa'],"opis"=>$rzad['opis'],"aktywna"=>$rzad['aktywna']);
		}
		return $tab;
	}
	
	public function count()
	{
		$this->query("SELECT COUNT(id) FROM grupa");
		$wynik=$this->wynik;
		$zwroc = mysql_fetch_array($wynik);
		return (int)$zwroc[0];
	}
}
