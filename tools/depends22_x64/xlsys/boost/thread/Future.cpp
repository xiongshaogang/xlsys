#define BOOST_THREAD_VERSION 3
#include <boost/bind.hpp>
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

/**
 * 使用future可得到线程的返回结果
 */
int mainFuture(int argc, const char* argv[])
{
	// 声明packaged_task对象, 用模版参数指明返回值类型
	// packaged_task只接受无参函数, 因此需要使用bind
	boost::packaged_task<int> pt(boost::bind(fab, 10));

	// 声明future对象, 接受packaged_task的future值
	// 同样要用模版参数指明返回值类型
	boost::future<int> f = pt.get_future();

	// 启动线程计算, 必须使用boost::move()来转移packaged_task对象
	// 因为package_task是不可拷贝的
	boost::thread(boost::move(pt));
	f.wait();

	if(f.is_ready() && f.has_value())
	{
		std::cout << f.get() << std::endl;
	}
	return 0;
}
