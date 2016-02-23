#include <boost/function.hpp>
#include <boost/bind.hpp>
#include <boost/ref.hpp>
#include <iostream>

using namespace std;
using namespace boost;

static int f(int a, int b)
{
	return a+b;
}

class DemoForFunction
{
	public:
		DemoForFunction()
		{
			cout << "DemoForFunction()" << endl;
		}

		DemoForFunction(const DemoForFunction& dff)
		{
			cout << "DemoForFunction(const DemoForFunction&)" << endl;
		}

		int add(int a, int b)
		{
			return a+b;
		}
		int operator()(int x) const
		{
			return x*x;
		}
};

class DemoForCallBack
{
	private:
		typedef boost::function<void(int)> func_t;
		func_t func;
		int n;
	public:
		DemoForCallBack(int i) : n(i) {}

		template<typename CallBack>
		void accept(CallBack f)
		{
			func = f;
		}

		void run()
		{
			func(n);
		}
};

static void callBackFunc(int i)
{
	cout << "callBackFunc:" << i*2 << endl;
}

int mainFunction(int argc, const char* argv[])
{
	// 存储函数
	boost::function<int(int, int)> func;
	func = f;
	if(func)
	{
		cout << func(10, 20) << endl;
	}
	// 存储成员函数
	boost::function<int(DemoForFunction&, int, int)> func1;
	func1 = bind(&DemoForFunction::add, _1, _2, _3);
	DemoForFunction dff;
	cout << func1(dff, 10, 20) << endl;
	boost::function<int(int, int)> func2;
	func2 = bind(&DemoForFunction::add, &dff, _1, _2);
	cout << func2(10, 20) << endl;
	// 使用bind存储函数对象
	boost::function<int(int)> func3;
	func3 = bind<int>(dff, _1); // 注意这里function使用的是拷贝传参
	cout << func3(10) << endl;
	// 直接存储函数对象
	boost::function<int(int)> func4;
	func4 = dff; // 注意这里是拷贝传参
	cout << func4(10) << endl;
	// 使用ref包装对象来传参
	boost::function<int(int)> func5;
	func5 = boost::cref(dff); // const ref
	cout << func5(10) << endl; // 注意看输出结果，结果之前没有再执行对象的拷贝构造
	// 用于回调
	DemoForCallBack dfcb(10);
	dfcb.accept(callBackFunc); // 接受回调函数
	dfcb.run();
	return 0;
}
