#include <boost/asio.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

int mainAddress(int argc, const char* argv[])
{
	ip::address addr;
	addr = addr.from_string("127.0.0.1");
	cout << boolalpha << addr.is_v4() << endl;
	cout << addr.to_string() << endl;
	addr = addr.from_string("ab::12:34:56");
	cout << addr.is_v6() << endl;

	addr = addr.from_string("127.0.0.1");
	ip::tcp::endpoint ep(addr, 6688); // 创建端点对象
	cout << ep.address() << ":" << ep.port() << endl;
 	return 0;
}
