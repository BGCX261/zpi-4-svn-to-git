<?php

require_once 'PHPUnit/Autoload.php';
require_once './Package/AllTests.php';

class AllTests
{

	 public static function suite()
	 {
	 	$suite = new PHPUnit_Framework_TestSuite('Package');
	 	$suite->addTest(Package_AllTests::suite());
	 	return $suite;
	 }
}
?>

