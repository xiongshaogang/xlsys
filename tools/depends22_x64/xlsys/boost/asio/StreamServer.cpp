#include <boost/asio.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

int mainStreamServer(int argc, const char* argv[])
{
	io_service ios;
	ip::tcp::endpoint ep(ip::tcp::v4(), 6688);
	ip::tcp::acceptor acceptor(ios, ep);
	for(;;)
	{
		ip::tcp::iostream tcp_stream;
		acceptor.accept(*tcp_stream.rdbuf());
		tcp_stream << "hello tcp stream";
	}
	return 0;
}
