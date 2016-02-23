#include <boost/function.hpp>
#include <boost/ref.hpp>
#include <boost/thread.hpp>
#include <boost/chrono.hpp>
#include <boost/bind.hpp>
#include <iostream>
#include "BasicAtomic.h"

using namespace std;
using namespace boost;

static boost::mutex io_mu;

static void printing(AtomicInt& x, const string& str)
{
	for(int i=0;i<5;++i)
	{
		boost::mutex::scoped_lock lock(io_mu); // 锁定io流操作
		cout << str << ++x << endl;
	}
}

int mainThread(int argc, const char* argv[])
{
	AtomicInt x;

	// 使用临时thread对象启动线程
	thread(printing, boost::ref(x), "hello");
	thread(printing, boost::ref(x), "boost");
	this_thread::sleep_for(boost::chrono::seconds(2));
	// 使用join来等待线程结束
	AtomicInt x1;
	thread t1(printing, boost::ref(x1), "hello");
	thread t2(printing, boost::ref(x1), "boost");
	t1.try_join_for(boost::chrono::seconds(1)); // 最多等待1秒然后返回
	t2.join(); // 等待线程结束再返回
	// 配合bind表达式使用
	thread t3(bind(printing, boost::ref(x1), "thread"));
	// 配合function来使用
	boost::function<void ()> f = bind(printing, boost::ref(x1), "mutex");
	thread t4(f);
	t3.join();
	t4.join();
	// 输出当前计算机可并行的线程数量
	cout << thread::hardware_concurrency() << endl;
	cout << this_thread::get_id() << endl;
	this_thread::yield(); // 当前线程放弃时间片
	return 0;
}
