#include <boost/thread.hpp>
#include <iostream>

static int g_count;

static void init_count()
{
	std::cout << "should call once." << std::endl;
	g_count = 0;
}

static boost::once_flag of;

static void call_func()
{
	call_once(of, init_count);
}

int mainCallOnce(int argc, const char* argv[])
{
	(boost::thread(call_func)); // 必须用括号括住，否则编译器会认为是boost::thread call_func;以为是一个空的thread声明
	(boost::thread(call_func));
	(boost::thread(call_func));
	boost::this_thread::sleep_for(boost::chrono::seconds(1));
	return 0;
}
