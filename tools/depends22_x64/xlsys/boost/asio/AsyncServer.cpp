#include <boost/smart_ptr.hpp>
#include <boost/asio.hpp>
#include <boost/system/error_code.hpp>
#include <boost/bind.hpp>
#include <iostream>

using namespace boost::asio;

class AsyncServer
{
	private :
		io_service& ios;
		ip::tcp::acceptor acceptor;
		typedef boost::shared_ptr<ip::tcp::socket> sock_pt;
	public:
		AsyncServer(io_service& io) : ios(io), acceptor(ios, ip::tcp::endpoint(ip::tcp::v4(), 6688))
		{
			start();
		}
		void start()
		{
			sock_pt sock(new ip::tcp::socket(ios));
			acceptor.async_accept(*sock, boost::bind(&AsyncServer::accept_handler, this, placeholders::error, sock));
		}
		void accept_handler(const boost::system::error_code& ec, sock_pt sock)
		{
			if(ec) return; // 如果检测到错误码, 则返回
			std::cout << "client:" << sock->remote_endpoint().address() << std::endl;
			sock->async_write_some(buffer("hello asio"), boost::bind(&AsyncServer::write_handler, this, placeholders::error));
			start(); // 再次启动异步接受连接
		}
		void write_handler(const boost::system::error_code&)
		{
			std::cout << "send msg complete." << std::endl;
		}
};

int mainAsyncServer(int argc, const char* argv[])
{
	try
	{
		std::cout << "server start." << std::endl;
		io_service ios;

		AsyncServer server(ios);
		ios.run();
	}
	catch(std::exception& e)
	{
		std::cout << e.what() << std::endl;
	}
	return 0;
}
