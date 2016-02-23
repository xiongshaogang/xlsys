#include <boost/pool/pool.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainPool(int argc, const char* argv[])
{
	pool<> pl(sizeof(int)); // 一个可分配int的内存池
	int* p = static_cast<int*>(pl.malloc());
	cout << boolalpha << pl.is_from(p) << endl;
	pl.free(p); // 主动释放内存
	// 连续分配大量内存
	for(int i=0;i<100;++i)
	{
		pl.ordered_malloc(10);
	}
	return 0;
} // 内存池对象析构，所有分配的内存都被释放
