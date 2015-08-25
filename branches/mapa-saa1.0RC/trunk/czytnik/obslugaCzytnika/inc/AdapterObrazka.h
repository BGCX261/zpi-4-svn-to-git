/*
 * AdapterObrazka.h
 *
 *  Created on: 10-03-2012
 *      Author: root
 */

#ifndef ADAPTEROBRAZKA_H_
#define ADAPTEROBRAZKA_H_

#include <cstddef>
#include <fprint.h>
#include "boost/shared_ptr.hpp"
#include "daneObrazka.h"
namespace czytnik
{
class AdapterObrazka;
typedef boost::shared_ptr<AdapterObrazka> img_ptr;
class AdapterObrazka
{
	fp_img* obrazek;
	std::string idCzytnika;

public:
	AdapterObrazka(fp_img*, std::string);
	virtual ~AdapterObrazka();
	void zapiszObrazek(std::string sciezka)
	{
		fp_img_save_to_file(obrazek, const_cast<char*>(sciezka.c_str()));
	}
	obr_ptr przygotujDoPrzeslania(unsigned char *data,int,int);
	obr_ptr przygotujDoPrzeslania();
#ifdef UNIT_TEST
	friend class AdapterObrazkaTest;
#endif
};
} /* namespace czytnik */
#endif /* ADAPTEROBRAZKA_H_ */
