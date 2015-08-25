<?php

class Package_classes_cUserTest extends PHPUnit_Framework_TestCase
{
	private $_cuser;
	
	protected function setUp()
	{
		$this->_cuser = new cUser;
	}
	
	protected function tearDown()
	{
		$this->_cuser->close();
		unset($this->_cuser);
	}
	

	public function testAdd()
	{
		$actual = $this->_cuser->add('Stanislaw', 'Testowy', 'Opis', 1);
		$excepted = $this->_cuser->get_last_id();
		$this->assertEquals($excepted, $actual, 'Blad dodawania uzytkownika!');
	}
	

	public function testRead()
	{
		$id = 11; 
		$this->assertEquals(1, count($this->_cuser->read($id)), 'Blad odczytu uzytkownika!');
	}

	public function testLimitRead()
	{
		$start_limit = 1;
		$stop_limit = 5;
		$this->assertNotNull($this->_cuser->limit_read($start_limit, $stop_limit),
			'Blad odczytu uzytkownika z limitem!');
	}
	

	/*public function testCount()
	{
		$this->assertType('int', $this->_cuser->count());
	}*/
	

	/*public function testGetRows()
	{
		$this->assertType('int', $this->_cuser->get_rows());
	}*/
}
?>

