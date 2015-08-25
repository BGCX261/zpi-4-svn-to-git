<?php

require_once 'classes/cGroupTest.php';
require_once 'classes/cUserTest.php';

class Package_AllTests extends PHPUnit_Framework_TestSuite
{

	public static function suite()
	{
		$suite = new Package_AllTests('Package');
		$suite->addTestSuite('Package_classes_cGroupTest');
		$suite->addTestSuite('Package_classes_cUserTest');
		return $suite;
	}
	

	protected function setUp()
	{
		
	}
	

	protected function tearDown()
	{
		
	}
}
?>

