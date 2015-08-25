<?php
class cSQL
{

	protected $user = '';
	protected $pass = '';
	protected $host = '';
	protected $name = '';
	protected $connect = NULL;
	protected $result = NULL;
	
	public function __construct($host, $user, $password, $database)
	{
		$this->host = $host;
		$this->user = $user;
		$this->pass = $password;
		$this->name = $database;
		
		$this->connect=mysql_connect($this->host, $this->user, $this->pass)
			or die('cSQL: Nie mo¿na siê po³¹czyæ: ' . mysql_error());
		mysql_select_db($this->name, $this->connect)
			or die("cSQL: Nie mogê wybraæ bazy danych: $this->name");
		

		mysql_query("SET CHARACTER SET utf8", $this->connect);
		mysql_query("SET collation_connection = utf8_polish_ci", $this->connect);
	}
	

	public function open()
	{
		if($this->connect == NULL)
		{
			$this->connect = mysql_connect($this->host, $this->user, $this->pass)
				or die('cSQL: Nie mo¿na siê po³¹czyæ: ' . mysql_error());
			mysql_set_charset('utf8', $this->connect);
			$this->result = mysql_select_db($this->name, $this->connect)
				or die("cSQL: Nie mogê wybraæ bazy danych: $this->name");
			
			return $this->result;
		}
		
		return false;
	} # open()

	public function query($query)
	{
		if($this->connect != NULL)
		{
			$this->result = mysql_query($query, $this->connect)
				or die("cSQL: Problem z zapytaniem - ".$query." ".mysql_error());
			return $this->result;
		}
		
		return false;
	} 

	public function get_num()
	{
		return mysql_num_rows($this->result);
	}

	public function close()
	{
		if($this->connect != NULL)
		{
			mysql_close($this->connect)
				or die("cSQL: B³¹d podczas roz³¹czania siê z serwerem MySQL".mysql_error());
			$this->connect = NULL;
		}
	}

	public function get_rows()
	{
		return mysql_fetch_array($this->result, MYSQL_BOTH);
	}
	
	public function get_id()
	{
		return mysql_insert_id($this->connect);
	}
	
	public function set_user($user)
	{
		$this->user = $user;
	}
	
	public function set_password($password)
	{
		$this->pass = $password;
	}
	
	public function set_host($host)
	{
		$this->host = $host;
	}
	
	public function set_database($database)
	{
		$this->name = $database;
	}
}
?>

