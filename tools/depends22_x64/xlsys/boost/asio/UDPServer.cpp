#include <boost/asio.hpp>
#include <boost/system/error_code.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

int mainUDPServer(int argc, const char* argv[])
{
	cout << "udp server start." << endl;
	io_service ios;
	ip::udp::socket sock(ios, ip::udp::endpoint(ip::udp::v4(), 6699));
	for(;;)
	{
		char buf[1]; // 一个临时用缓冲区
		ip::udp::endpoint ep;
		boost::system::error_code ec;
		sock.receive_from(buffer(buf), ep, 0, ec); // 阻塞等待远程连接
		if(ec&&ec!=error::message_size)
		{
			throw boost::system::system_error(ec);
		}
		cout << "send to " << ep.address() << endl;
		sock.send_to(buffer("hello asio udp"), ep);
	}
	return 0;
}
