#include <boost/smart_ptr.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainScopedArray(int argc, const char* argv[])
{
	scoped_array<int> sa(new int[100]);
	sa[0] = 10;
	// *(sa+1) = 20; // 错误用法，不能通过编译

	fill_n(&sa[0], 100, 5); // 可以使用标准库算法赋值数据

	for(int i=0;i<100;i++)
	{
		cout << "sa[" << i << "]=" << sa[i] << endl;
	}
	return 0;
}
