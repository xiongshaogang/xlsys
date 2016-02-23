#define BOOST_THREAD_VERSION 3
#include <boost/thread.hpp>
#include <iostream>

static int fab(int n) // 递归计算菲波拉契数列
{
	if(n==0||n==1)
	{
		return 1;
	}
	return fab(n-1) + fab(n-2);
}

static void fab2(int n, boost::promise<int>* p)
{
	p->set_value(fab(n));
}

int mainPromise(int argc, const char* argv[])
{
	boost::promise<int> p;
	boost::future<int> f = p.get_future();
	boost::thread(fab2, 10, &p);
	f.wait();
	std::cout << f.get() << std::endl;
	return 0;
}
