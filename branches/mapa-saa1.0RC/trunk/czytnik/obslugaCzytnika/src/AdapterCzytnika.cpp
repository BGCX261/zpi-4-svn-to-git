/*
 * AdapterCzytnika.cpp
 *
 *  Created on: 10-03-2012
 *      Author: root
 */

#include "../inc/AdapterCzytnika.h"
#include <iostream>
namespace czytnik {

AdapterCzytnika::AdapterCzytnika()
{
	fp_init();
	auto czytniki = fp_discover_devs();
	if(czytniki == NULL)
	{
		std::cout<<"Nie ma czytnika"<<std::endl;
		return;
	}
	//FIXME: Dodac obsluge wiekszej ilosci czytnikow.
	czytnik = fp_dev_open(czytniki[0]);
	fp_dscv_devs_free(czytniki);
}

AdapterCzytnika::~AdapterCzytnika() {
	fp_dev_close(czytnik);
	fp_exit();
}

    img_ptr AdapterCzytnika::pobierzObrazek()
    {
    	fp_print_data* data;
    	fp_img* img;
    	fp_enroll_finger_img(czytnik,&data, &img);
    	img_ptr pointer(new AdapterObrazka(img, std::string("Stub")));
    	fp_print_data_free(data);

    	return pointer;
    }

} /* namespace czytnik */
