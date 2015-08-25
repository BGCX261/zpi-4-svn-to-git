/*
 * daneObrazka.h
 *
 *  Created on: 13-03-2012
 *      Author: elistan
 */

#ifndef DANEOBRAZKA_H_
#define DANEOBRAZKA_H_
#include <vector>
#include <boost/shared_ptr.hpp>



namespace czytnik
{
struct dane_obrazka
{
	std::string id_czytnika;
	std::vector<std::vector<unsigned char> > dane;
};
typedef boost::shared_ptr<dane_obrazka> obr_ptr;
}

#endif /* DANEOBRAZKA_H_ */
