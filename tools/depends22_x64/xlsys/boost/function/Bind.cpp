#include <boost/bind.hpp>
#include <iostream>

using namespace std;
using namespace boost;

static int f(int a, int b)
{
	return a+b;
}

static int g(int a, int b, int c)
{
	return a+b*c;
}

typedef int (*f_type)(int, int);
typedef int (*g_type)(int, int, int);

struct demoForBind
{
	public:
		int f(int a, int b)
		{
			return a + b;
		}
};

/**
 * bind的意义在于可以把传参的调用变成无参的调用
 */
int mainBind(int argc, const char* argv[])
{
	// 使用bind来调用函数
	cout << bind(f,1,2)() << endl;
	cout << bind(g,1,2,3)() << endl;
	// 使用占位符
	int x = 1, y = 2, z = 3;
	cout << bind(f, _1, 9)(x) << endl;
	cout << bind(f, _1, _2)(x, y) << endl;
	cout << bind(g, _3, _2, _1)(x, y, z) << endl;
	// 绑定函数指针
	f_type pf = f;
	g_type pg = g;
	cout << bind(pf, _1, 9)(x) << endl;
	cout << bind(pg, _3, _2, _1)(x, y, z) << endl;
	// 绑定类的成员函数
	demoForBind a, & ra = a;
	demoForBind* p = &a;
	cout << bind(&demoForBind::f, a, _1, 20)(10) << endl;
	cout << bind(&demoForBind::f, ra, _2, _1)(10, 20) << endl;
	cout << bind(&demoForBind::f, p, _1, _2)(10, 20) << endl;
	return 0;
}
