/*
 * test.cpp
 *
 *  Created on: 14-03-2012
 *      Author: elistan
 */

#include "../inc/TestyFunkcjonalne.h"

#include <boost/asio.hpp>
#include <sstream>
#include <fstream>

#include "../../obslugaCzytnika/inc/AdapterCzytnika.h"
#include "../../socket/inc/SocketClient.h"

using boost::asio::ip::tcp;
using namespace czytnik;
using namespace std;

bool TestyFunkcjonalne::pobierzOdciskPalca()
{
	AdapterCzytnika ac;
	img_ptr wynik = ac.pobierzObrazek();
	return wynik != 0;
}

bool TestyFunkcjonalne::wyslijStringDoKlienta()
{
	std::string host("127.0.0.1");
	std::string port("15001");
	tcp::iostream s(host, port);
	s<<"Hello world";
	//sc.send(stream);
	cout<<"Czy w kliencie Javy pojawil sie string hello world? (y/n)"<<endl;
	string resp;
	cin >> resp;
	return resp == "y";
}

bool TestyFunkcjonalne::wyslijZapisanyOdciskDoKlienta()
{
	std::string host("127.0.0.1");
	std::string port("15001");
	tcp::iostream s(host, port);
	SocketClient sc(s);
	ifstream file("obrazek.pgm");
	sc.send(file);
	cout<<"Czy klient javy wygenerowal obrazek identyczny z wyslanym? (y/n)"<<endl;
	string resp;
	cin >> resp;
	return resp == "y";
}

bool TestyFunkcjonalne::wyslijZeskanowanyOdciskDoKlienta()
{
	AdapterCzytnika ac;
	img_ptr obr = ac.pobierzObrazek();
	obr->zapiszObrazek("new_generated.pgm");
	obr_ptr obrazek = obr->przygotujDoPrzeslania();
	std::string host("127.0.0.1");
	std::string port("15001");
	tcp::iostream s(host, port);
	SocketClient sc(s);
	sc.send(obrazek->dane);
	cout<<"Czy klient javy wygenerowal obrazek identyczny z zapisanym pod sciezka new_generated.pgm? (y/n)"<<endl;
	string resp;
	cin >> resp;
	return resp == "y";
}
void TestyFunkcjonalne::wyslijObrazek()
{
	AdapterCzytnika ac;
	img_ptr obr = ac.pobierzObrazek();
	obr_ptr obrazek = obr->przygotujDoPrzeslania();
	std::string host("156.17.247.193");
	std::string port("15001");
	tcp::iostream s(host, port);
	SocketClient sc(s);
	sc.send(obrazek->dane);
}
void TestyFunkcjonalne::zeskanujIZapisz(std::string sciezka)
{
	AdapterCzytnika ac;
	img_ptr obr = ac.pobierzObrazek();
	obr->zapiszObrazek(sciezka);
}
void TestyFunkcjonalne::wyslijZapisanyOdciskDoKlienta(std::string sciezka, int id)
{
	std::string host("127.0.0.1");
	std::string port("15001");
	tcp::iostream s(host, port);
	SocketClient sc(s);
	ifstream file(sciezka);
	sc.send(file, id);
}
