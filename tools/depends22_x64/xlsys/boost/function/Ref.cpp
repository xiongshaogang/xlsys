#include <boost/ref.hpp>
#include <iostream>

using namespace std;

// 函数模版没有定义对值的引用
template<typename T>
T selfIncrease(T t)
{
	return ++t;
}

/**
 * ref的作用就是总是可以安全的用在泛型代码中，不用关心对象的包装特性，总能够正确的操作对象
 */
int mainRef(int argc, const char* argv[])
{
	int i = 10;
	cout << selfIncrease(i) << endl; // 这里因为函数模版的参数不是引用，所以没有改变i的值
	cout << "i=" << i << endl;
	cout << selfIncrease(ref(i)) << endl; // 在模版参数不支持引用的情况下，直接使用ref()来把对象封装传入
	cout << "i=" << i << endl;
	int& j = i;
	cout << selfIncrease(j) << endl; // 由于是值传递，即T t = j; 所以即使将i的引用j传入也不会改变i的值
	cout << "i=" << i << endl;
	return 0;
}
