#include <boost/asio.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

int maiStreamClient(int argc, const char* argv[])
{
	for(int i=0;i<5;++i)
	{
		ip::tcp::iostream tcp_stream("127.0.0.1", "6688");
		string str;
		getline(tcp_stream, str); // 从tcp流中读取一行数据
		cout << str << endl;
	}
	return 0;
}
