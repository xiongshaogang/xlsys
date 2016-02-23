#include <boost/smart_ptr.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainScopedPtr(int argc, const char* argv[])
{
	scoped_ptr<string> sp(new string("text"));
	cout << *sp << endl;
	cout << sp->size() << endl;
	// sp++; // 错误，scoped_ptr未定义递增操作符
	// scoped_ptr<string> sp2 = sp; // 错误，scoped_ptr不能拷贝构造
	return 0;
}
