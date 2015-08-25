<?php
require_once 'cSession.php';
require_once "_configs/config.php";

class cSQL extends cSession
{
	private $hand=NULL;
	private $login="";
	private $password="";
	private $db="";
	private $host="";
	private $ile_zapytan=NULL;
	protected $wynik;
	
	
	
	public function __construct()
	{
		$this->login=CONF_DB_LOGIN;
		$this->password=CONF_DB_PASS;
		$this->db=CONF_DB_NAME;
		$this->host=CONF_DB_HOST;
		$this->hand=mysql_connect($this->host,$this->login,$this->password, $this->db) or die('cSQL: Nie mo¿na siê polaczyc : ' . mysql_error());
		mysql_select_db($this->db);
		$query = "SET CHARSET latin2";
		$wynik = mysql_query($query);
	}
	public function query($sql)
	{
		
		if($this->hand != NULL)
		{
			$this->ile_zapytan++;
			$this->wynik = mysql_query($sql, $this->hand) or die("cSQL: Problem z zapytaniem - ".$sql." ".mysql_error());
			
			return $this->wynik;
		}
		
		return false;
		
	}
	public function close()
	{
		if($this->hand != NULL)
		{
			mysql_close($this->hand) or die("cSQL: B£AD".mysql_error());
			$this->hand = NULL;
		}
	}
	public function get_last_id()
	{
		if($this->hand != NULL)
		{
			return mysql_insert_id($this->hand);
		}
	}

	public function set_login($login)
	{
		$this->login=$login;
	}
	public function set_password($pass)
	{
		$this->password=$pass;
	}
	public function set_db($db)
	{
		$this->db=$db;
	}
	public function set_host($host)
	{
		$this->host=$host;
	}
}
?>
