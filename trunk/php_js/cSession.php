<?php
class cSession
{
	function start()
	{
		session_name("zalogowany");	
	}
	function set( $name, $value )
	{
		$_SESSION["$name"] = $value;
	}
	function get( $name )
	{
		if ( !isset( $_SESSION["$name"] ) )
			return ( NULL );
		return ( $_SESSION["$name"] );
	}
	function del( $name )
	{
		if ( !isset( $_SESSION["$name"] ) )
			return ( NULL );
		unset( $_SESSION["$name"] );
		session_destroy();
		
		return ( true );
	}
	function version()
	{
		return ( '1.0' );
	}
}
?>

