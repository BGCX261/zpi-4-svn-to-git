/*
 * AdapterObrazka.cpp
 *
 *  Created on: 10-03-2012
 *      Author: root
 */

#include "../inc/AdapterObrazka.h"
namespace czytnik
{

AdapterObrazka::AdapterObrazka(fp_img* obrazek, std::string nazwa):idCzytnika(nazwa)
{
	this->obrazek = obrazek;
}

AdapterObrazka::~AdapterObrazka()
{
#ifndef UNIT_TEST
	fp_img_free(obrazek);
#endif
}
obr_ptr AdapterObrazka::przygotujDoPrzeslania()
{
	unsigned char* data = fp_img_get_data(obrazek);
	int width = fp_img_get_width(obrazek);
	int height = fp_img_get_height(obrazek);
	return przygotujDoPrzeslania(data, width, height);
}
obr_ptr AdapterObrazka::przygotujDoPrzeslania(unsigned char * data, int width, int height){
	obr_ptr res(new dane_obrazka());
	res->id_czytnika = idCzytnika;
	for(int i = 0 ; i < height ; i++)
	{
		std::vector<unsigned char> chars;
		for(int j = 0 ; j < width ; j++){
			chars.push_back(data[i*width+j]);
		}
		res->dane.push_back(chars);
	}
	return res;
}
} /* namespace czytnik */
