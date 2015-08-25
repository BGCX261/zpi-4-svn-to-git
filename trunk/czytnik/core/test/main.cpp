
#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/ui/text/TestRunner.h>
#include "../../obslugaCzytnika/test/AdapterObrazkaTest.cpp"
#include "../../socket/test/SocketClientTest.cpp"
int main(int argc, char **argv)
{
	CppUnit::TextUi::TestRunner runner;

	runner.addTest(AdapterObrazkaTest::suite());
	runner.addTest(SocketClientTest::suite());
	runner.run();

	return 0;
}
