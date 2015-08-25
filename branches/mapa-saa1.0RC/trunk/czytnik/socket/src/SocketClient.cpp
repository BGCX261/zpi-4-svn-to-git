/*
 * SocketClient.cpp
 *
 *  Created on: 10-03-2012
 *      Author: elistan
 */

#include "../inc/SocketClient.h"
#include <fstream>
#include <sstream>
namespace czytnik
{

SocketClient::SocketClient(iostream_t& stream) :
		stream(stream)
{

}

SocketClient::~SocketClient()
{
	// TODO Auto-generated destructor stub
}
void SocketClient::send(std::istream& file, int idCzytnika)
{
	std::string line;
	getline(file, line);
	std::stringstream sstream(line);
	std::string ignore;
	sstream>>ignore;
	int height, width;
	sstream >> width;
	sstream >> height;
	stream<<idCzytnika<<std::endl;
	stream<<line<<std::endl;
	getline(file, line);
	for(unsigned int i = 0 ; i < line.length(); i++)
	{
		stream<<(int)((unsigned char)line[i])<<" ";
		if((i+1)%width == 0)
			stream<<std::endl;
	}
	std::string eof("EOF");
	stream << eof;
}
} /* namespace czytnik */
