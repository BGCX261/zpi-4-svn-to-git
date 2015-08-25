/*
 * SocketClient.h
 *
 *  Created on: 10-03-2012
 *      Author: elistan
 */

#ifndef SOCKETCLIENT_H_
#define SOCKETCLIENT_H_
#include <vector>
#include <iostream>
#ifndef UNIT_TEST
#include <boost/asio.hpp>
typedef boost::asio::ip::tcp::iostream iostream_t;
#else
#include <sstream>
typedef std::stringstream iostream_t;
#endif

namespace czytnik
{
class SocketClient
{
	iostream_t &stream;
public:
	SocketClient(iostream_t &);
	virtual ~SocketClient();
	void send(std::istream&, int idCzytnika = 1);
	void send(std::vector<std::vector<unsigned char> > &data, int idCzytnika = 1)
	{
		stream << idCzytnika<< std::endl;
		stream << "P2 "<<data[0].size()<<" "<<data.size()<<" 255"<<std::endl;
		for(auto start = data.begin() ; start != data.end() ; ++start)
		{
			for(auto start2 = start->begin() ; start2 != start->end() ; ++start2){
				stream <<(int) *start2<<" ";
			}
			stream << std::endl;
		}
		stream<<"EOF"<<std::endl;
	}

};

} /* namespace czytnik */
#endif /* SOCKETCLIENT_H_ */
