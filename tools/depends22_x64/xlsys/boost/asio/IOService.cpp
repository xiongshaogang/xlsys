#include <boost/asio.hpp>
#include <boost/system/error_code.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include <iostream>

using namespace std;
using namespace boost::asio;

static void print(const boost::system::error_code&)
{
	cout << "hello asio" << endl;
}

int mainIOService(int argc, const char* argv[])
{
	// 同步定时器
	io_service ios;
	deadline_timer t(ios, boost::posix_time::seconds(2)); // 两秒钟后定时器终止
	cout << t.expires_at() << endl; // 查看终止的绝对时间
	t.wait();
	cout << "hello asio" << endl;

	// 异步定时器
	deadline_timer t2(ios, boost::posix_time::seconds(2));
	t2.async_wait(print); // 异步等待, 传入回调函数, 立即返回
	cout << "it show before t expired." << endl;
	ios.run(); // 很重要, 异步IO必须, 等待异步操作完成, 当异步操作完成时io_service从操作系统获取执行结果, 调用完成处理函数
	return 0;
}
