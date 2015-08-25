/*
 * AdapterCzytnikaTest.cpp
 *
 *  Created on: 13-03-2012
 *      Author: elistan
 */
#include <cppunit/extensions/HelperMacros.h>

#include "../inc/AdapterObrazka.h"
using namespace czytnik;
class AdapterObrazkaTest: public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( AdapterObrazkaTest );
		CPPUNIT_TEST( testPrzygotujDane );
	CPPUNIT_TEST_SUITE_END();
public:
	void testPrzygotujDane()
	{
		fp_img* im;
		std::string nazwa("nazwa");
		AdapterObrazka ao(im, nazwa);
		unsigned char dane []=
		{ 0, 0, 255, 1, 2, 245 };
		obr_ptr result = ao.przygotujDoPrzeslania(dane, 2, 3);

		CPPUNIT_ASSERT_EQUAL(nazwa, result->id_czytnika);
		auto linie = result->dane.begin();
		auto znak = linie->begin();
		CPPUNIT_ASSERT(*znak == 0);
		znak++;
		CPPUNIT_ASSERT(*znak == 0);
		znak++;
		CPPUNIT_ASSERT(znak == linie->end());
		linie++;
		znak = linie->begin();
		CPPUNIT_ASSERT(*znak == 255);
		znak++;
		CPPUNIT_ASSERT(*znak == 1);
		znak++;
		CPPUNIT_ASSERT(znak == linie->end());
		linie++;
		znak = linie->begin();
		CPPUNIT_ASSERT(*znak == 2);
		znak++;
		CPPUNIT_ASSERT(*znak == 245);
		znak++;
		CPPUNIT_ASSERT(znak == linie->end());
		linie++;
		CPPUNIT_ASSERT(linie == result->dane.end());
	}

};

