#include <boost/serialization/singleton.hpp>
#include <iostream>

using namespace std;
using boost::serialization::singleton;

class MySingleton : public singleton<MySingleton>
{
	private :
		int a, b;
	public :
		// 单例要求有默认的构造函数，构造和析构不抛异常
		MySingleton() throw() : a(0), b(0) {}
		~MySingleton() throw() {}
		void print()
		{
			cout << a << ':' << b << endl;
		}
};

int mainSingleton(int argc, const char* argv[])
{
	MySingleton::get_mutable_instance().print();
	return 0;
}
