#include <boost/smart_ptr.hpp>
#include <boost/asio.hpp>
#include <boost/system/error_code.hpp>
#include <boost/bind.hpp>
#include <vector>
#include <iostream>

using namespace boost::asio;

class AsyncClient
{
	private:
		io_service& ios;
		ip::tcp::endpoint ep;
		typedef boost::shared_ptr<ip::tcp::socket> sock_pt;
	public:
		AsyncClient(io_service& io) : ios(io), ep(ip::address::from_string("127.0.0.1"), 6688)
		{
			start();
		}
		void start()
		{
			sock_pt sock(new ip::tcp::socket(ios));
			sock->async_connect(ep, boost::bind(&AsyncClient::conn_handler, this, placeholders::error, sock));
			// 超时处理
			deadline_timer t(ios, boost::posix_time::seconds(5)); // 5秒后超时
			t.async_wait(boost::bind(&AsyncClient::time_expired, this, placeholders::error, sock));
		}
		void conn_handler(const boost::system::error_code& ec, sock_pt sock)
		{
			if(ec) return;
			std::cout << "recive from " << sock->remote_endpoint().address() << std::endl;
			boost::shared_ptr<std::vector<char> > str(new std::vector<char>(100, 0)); // 建立接收数据的缓冲区
			sock->async_read_some(buffer(*str), boost::bind(&AsyncClient::read_handler, this, placeholders::error, str)); // 异步读取数据
			start(); // 再次启动异步连接
		}
		void read_handler(const boost::system::error_code& ec, boost::shared_ptr<std::vector<char> > str)
		{
			if(ec) return;
			std::cout << &(*str)[0] << std::endl; // 输出接收到的数据
		}
		void time_expired(const boost::system::error_code& ec, sock_pt sock)
		{
			std::cout << "time expired" << std::endl;
			sock->close();
		}
};

int mainAsyncClient(int argc, const char* argv[])
{
	try
	{
		std::cout << "client start." << std::endl;
		io_service ios;
		AsyncClient client(ios);
		ios.run();
	}
	catch(std::exception& e)
	{
		std::cout << e.what() << std::endl;
	}
	return 0;
}
