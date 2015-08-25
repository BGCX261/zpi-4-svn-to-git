/*
 * AdapterCzytnika.h
 *
 *  Created on: 10-03-2012
 *      Author: root
 */

#ifndef ADAPTERCZYTNIKA_H_
#define ADAPTERCZYTNIKA_H_
#include <cstddef>
#include <fprint.h>
#include "AdapterObrazka.h"

namespace czytnik {


class AdapterCzytnika {
	fp_dev* czytnik;
public:
	AdapterCzytnika();
	virtual ~AdapterCzytnika();
	img_ptr pobierzObrazek();
};

} /* namespace czytnik */
#endif /* ADAPTERCZYTNIKA_H_ */
