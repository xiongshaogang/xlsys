#include <boost/asio.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

/**
 * 同步socket处理服务端
 */
int mainSyncServer(int argc, const char* argv[])
{
	try
	{
		cout << "server start." << endl;
		io_service ios;
		ip::tcp::acceptor acceptor(ios, ip::tcp::endpoint(ip::tcp::v4(), 6688)); // 接受6688端口
		cout << acceptor.local_endpoint().address() << endl;

		while(true)
		{
			ip::tcp::socket sock(ios);
			acceptor.accept(sock); // 阻塞等待socket连接

			cout << "client:";
			cout << sock.remote_endpoint().address() << endl;
			sock.write_some(buffer("hello asio")); // 发送数据
		}
	}
	catch(std::exception& e)
	{
		cout << e.what() << endl;
	}
	return 0;
}
