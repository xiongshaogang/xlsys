#include <boost/format.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainFormat(int argc, const char* argv[])
{
	// format 重载了operator%函数
	cout << format("%s:%d+%d=%d\n") %"sum" %1 %2 %(1+2);

	format fmt("(%1% + %2%) * %2% = %3%\n");
	fmt %2 %5;
	fmt %((2+5)*5);
	cout << fmt.str();

	// 重新定义新的格式化字符串
	fmt.parse("%s") %"Hello world!";
	cout << fmt.str() << endl;

	// 使用clear清空缓冲区
	fmt.clear();
	fmt % "Hello everyone!";
	cout << fmt.str() << endl;
	return 0;
}
