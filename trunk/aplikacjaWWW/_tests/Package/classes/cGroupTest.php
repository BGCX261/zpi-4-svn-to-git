<?php

class Package_classes_cGroupTest extends PHPUnit_Framework_TestCase
{
	private $_cgroup;
	
	protected function setUp()
	{
		$this->_cgroup = new cGroup;
	}
	
	protected function tearDown()
	{
		$this->_cgroup->close();
		unset($this->_cgroup);
	}
	

	public function testAdd()
	{
		$name_group = 'Grupa Test '.rand();
		$actual = $this->_cgroup->add($name_group, 'Grupa testowa', 1);
		$excepted = $this->_cgroup->get_last_id();
		$this->assertEquals($excepted, $actual, 'Blad dodania grupy!');
	}
	

	public function testRead()
	{
		$id = 1;
		$this->assertEquals(1, count($this->_cgroup->read($id)), 'Blad odczytu grupy!');
	}
	

	public function testLimitRead()
	{
		$start_limit = 1;
		$stop_limit = 5;
		$this->assertNotNull($this->_cgroup->limit_read($start_limit, $stop_limit),
			'Blad odczytu grupy z limitem!');
	}
	

	/*public function testCount()
	{
		$this->assertType('int', $this->_cgroup->count());
	}*/
	

	/*public function testGetRows()
	{
		$this->assertType('int', $this->_cgroup->get_rows());
	}*/
}
?>

