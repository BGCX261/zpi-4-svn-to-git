/*
 * TestyFunkcjonalne.h
 *
 *  Created on: 14-03-2012
 *      Author: elistan
 */

#ifndef TESTYFUNKCJONALNE_H_
#define TESTYFUNKCJONALNE_H_
#include <string>
class TestyFunkcjonalne
{
public:
	bool pobierzOdciskPalca();
	bool wyslijStringDoKlienta();
	bool wyslijZapisanyOdciskDoKlienta();
	bool wyslijZeskanowanyOdciskDoKlienta();
	void wyslijZapisanyOdciskDoKlienta(std::string, int id = 1);
	bool uruchomWszystkie()
	{
		bool result = true;
		result = result && pobierzOdciskPalca();
		result = result && wyslijStringDoKlienta();
		result = result && wyslijZapisanyOdciskDoKlienta();
		result = result && wyslijZeskanowanyOdciskDoKlienta();
		return result;
	}
	void wyslijObrazek();
	void zeskanujIZapisz(std::string);
};


#endif /* TESTYFUNKCJONALNE_H_ */
