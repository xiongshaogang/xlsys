#include <boost/asio.hpp>
#include <boost/system/error_code.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

int mainUDPClient(int argc, const char* argv[])
{
	cout << "client start." << endl;
	io_service ios;
	ip::udp::endpoint send_ep(ip::address::from_string("127.0.0.1"), 6699);
	ip::udp::socket sock(ios);
	sock.open(ip::udp::v4()); // 使用ipve打开socket
	char buf[1];
	sock.send_to(buffer(buf), send_ep); // 向连接端点发送连接数据

	vector<char> v(100, 0);
	ip::udp::endpoint recv_ep;
	sock.receive_from(buffer(v), recv_ep); // 接收数据
	cout << "recv from " << recv_ep.address() << " " << &v[0] << endl;
	return 0;
}
