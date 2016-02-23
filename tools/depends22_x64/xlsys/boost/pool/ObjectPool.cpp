#include <boost/pool/object_pool.hpp>
#include <iostream>

using namespace std;
using namespace boost;

/**
 * 一个示范用的类
 */
class DemoForObjectPool
{
	public:
		int a,b,c;
		DemoForObjectPool(int x=1, int y=2, int z=3) : a(x), b(y), c(z) {cout<<"DemoForObjectPool() at" << this <<endl;}
		~DemoForObjectPool() {cout<<"~DemoForObjectPool() at " << this <<endl;}
};

int mainObjectPool(int argc, const char* argv[])
{
	object_pool<DemoForObjectPool> pl;
	DemoForObjectPool* p = pl.malloc(); // 分配一个原始内存块，该方法不会调用类的构造方法
	cout << "a=" << p->a << " b=" << p->b << " c=" << p->c << endl;

	p = pl.construct(7,8,9);
	cout << "a=" << p->a << " b=" << p->b << " c=" << p->c << endl;

	return 0;
}
