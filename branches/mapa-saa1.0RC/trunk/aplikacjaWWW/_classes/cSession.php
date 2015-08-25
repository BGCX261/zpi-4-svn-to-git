<?php
require_once 'cCheck.php';

class cSession extends cCheck
{
	public function start()
	{
		session_name("mapa_saa");	
		session_start();
	}
	public function set( $name, $value )
	{
		$_SESSION["$name"] = $value;
	}
	public function get( $name )
	{
		if ( !isset( $_SESSION["$name"] ) )
			return ( NULL );
		return ( $_SESSION["$name"] );
	}
	public function del( $name )
	{
		if ( !isset( $_SESSION["$name"] ) )
			return ( NULL );
		unset( $_SESSION["$name"] );
		
		
		return ( true );
	}
	public function close()
	{
		session_destroy();
	}
	
    public function version()
	{
	  return ( '1.1' );
	}

}
?>