#include <boost/asio.hpp>
#include <iostream>
#include <vector>
#include <boost/ref.hpp>
#include "ATimer.h"

using namespace std;
using namespace boost::asio;

static void client(io_service& ios)
try	// function-try块
{
	cout << "client start." << endl;

	ip::tcp::socket sock(ios);
	ip::tcp::endpoint ep(ip::address::from_string("127.0.0.1"), 6688);
	sock.connect(ep);

	vector<char> str(100, 0);
	sock.read_some(buffer(str));
	cout << "recive from " << sock.remote_endpoint().address() << &str[0] << endl;
}
catch(std::exception& e)
{
	cout << e.what() << endl;
}

int mainSyncClient(int argc, const char* argv[])
{
	io_service ios;
	ATimer at(ios, 5, bind(client, boost::ref(ios)));
	ios.run();
	return 0;
}
