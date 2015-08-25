/*
 * main.cpp
 *
 *  Created on: 08-03-2012
 *      Author: elistan
 */
#include <iostream>
#include <boost/asio.hpp>
#include <fstream>
#include <sstream>
#include "../inc/TestyFunkcjonalne.h"
using boost::asio::ip::tcp;
int main(int argc, char** argv)
{
	if (argc > 1)
	{
		TestyFunkcjonalne test;
		std::string sciezka(argv[1]);
		int id=1;
		if(argc > 2)
			id = atoi(argv[2]);
		test.wyslijZapisanyOdciskDoKlienta(sciezka, id);
	}
	else
	{
		/*TestyFunkcjonalne test;
		 std::cout<<"Wynik = "<<test.uruchomWszystkie()<<std::endl;
		 */
		TestyFunkcjonalne test;
		for (int i = 0; i < 5; i++)
		{
			std::cout << "Przeciagnij palec po czytniku" << std::endl;
			std::stringstream stream;
			stream << "obrazek" << i << ".pgm";
			std::string nazwa;
			stream >> nazwa;
			test.zeskanujIZapisz(nazwa);
		}
	}
	return 0;
}

