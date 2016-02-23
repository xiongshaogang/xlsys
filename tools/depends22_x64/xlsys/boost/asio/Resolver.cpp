#include <boost/asio.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/system/error_code.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

static void resolve_connect(ip::tcp::socket& sock, const char* name, int port)
{
	ip::tcp::resolver rlv(sock.get_io_service()); // 创建resolver对象
	ip::tcp::resolver::query qry(name, boost::lexical_cast<string>(port)); // 创建query对象
	// 使用resolver开始迭代端点
	ip::tcp::resolver::iterator iter = rlv.resolve(qry);
	ip::tcp::resolver::iterator end;
	boost::system::error_code ec = error::host_not_found;
	for(;ec&&iter!=end;++iter)
	{
		sock.close();
		cout << "try " << iter->endpoint().address() << endl;
		sock.connect(*iter, ec); // 尝试连接端点
	}
	if(ec)
	{
		cout << "can't connect." << endl;
		throw boost::system::system_error(ec);
	}
	cout << "connect success." << endl;
}

/**
 * 通过域名直接解析来访问
 */
int mainResolver(int argc, const char* argv[])
{
	try
	{
		io_service ios;
		ip::tcp::socket sock(ios);
		resolve_connect(sock, "www.boost.org", 80);
		ios.run();
	}
	catch(std::exception& e)
	{
		cout << e.what() << endl;
	}
	return 0;
}
