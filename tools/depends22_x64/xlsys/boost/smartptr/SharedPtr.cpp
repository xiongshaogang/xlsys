#include <boost/smart_ptr.hpp>
#include <boost/make_shared.hpp>
#include <iostream>
#include <vector>

using namespace std;

int mainSharedPtr(int argc, const char* argv[])
{
	/* shared_ptr如果要强转，需要使用如下几种方式
	* static_pointer_cast<T>()
	* const_pointer_cast<T>()
	* dynamic_pointer_cast<T>()
	*/
	// 使用工厂方法来构造shared_ptr
	boost::shared_ptr<string> sp = boost::make_shared<string>("make_shared"); // 创建string的共享指针
	boost::shared_ptr<vector<int> > spv = boost::make_shared<vector<int> >(10, 2); // 创建vector的共享指针
	cout << sp->size() << endl << spv->size() << endl;
	return 0;
}
