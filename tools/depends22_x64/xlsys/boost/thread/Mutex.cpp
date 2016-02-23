#include <boost/thread.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainMutex(int argc, const char* argv[])
{
	// 直接使用互斥量
	boost::mutex mu;
	try
	{
		mu.lock();
		cout << "some operations" << endl;
		mu.unlock();
	}
	catch(...)
	{
		mu.unlock();
	}
	// 使用lock_guard来包装mutex, 自动锁定和解锁
	{
		boost::mutex::scoped_lock lock(mu); // 这里自动锁定互斥量
		cout << "some operations" << endl;
	} // 这里自动释放互斥量
	return 0;
}
