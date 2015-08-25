/*
 * SocketClientTest.cpp
 *
 *  Created on: 14-03-2012
 *      Author: elistan
 */

#include <cppunit/extensions/HelperMacros.h>

#include "../inc/SocketClient.h"
#include <sstream>
using namespace czytnik;
using namespace std;
class SocketClientTest: public CppUnit::TestFixture
{

public:
	void testSendStream()
	{
		stringstream stream;
		SocketClient sc(stream);
		stringstream file;
		file << "P5 2 2 255" << std::endl;
		unsigned char a = 0xff, b = 0x00, c = 0x01, d = 0x02;
		file << a<<b<<c<<d;
		sc.send(file);
		string res;
		stream >> res;
		CPPUNIT_ASSERT(res == "P5");
		stream >> res;
		CPPUNIT_ASSERT(res == "2");
		stream >> res;
		CPPUNIT_ASSERT(res == "2");
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("255"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("255"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("0"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("1"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("2"), res);

	}
	void testSendVector()
	{
		vector<vector<unsigned char> > dane;
		vector<unsigned char> wiersz;
		wiersz.push_back(0xff);
		wiersz.push_back(0x00);
		dane.push_back(wiersz);
		wiersz = vector<unsigned char>();
		wiersz.push_back(0x01);
		wiersz.push_back(0x02);
		dane.push_back(wiersz);
		stringstream stream;
		SocketClient sc(stream);
		sc.send(dane);
		string res;
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(string("P2"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(string("2"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(string("2"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("255"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("255"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("0"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("1"), res);
		stream >> res;
		CPPUNIT_ASSERT_EQUAL(std::string("2"), res);
	}

CPPUNIT_TEST_SUITE(SocketClientTest);
		CPPUNIT_TEST(testSendStream);
		CPPUNIT_TEST(testSendVector);
	CPPUNIT_TEST_SUITE_END();
};

