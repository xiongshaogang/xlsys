#include <boost/utility/result_of.hpp>
#include <iostream>
#include <cmath>

using namespace std;
using namespace boost;

template<typename T, typename T1>
typename boost::result_of<T(T1)>::type call_func(T t, T1 t1)
{
	return t(t1); // 这里的返回值类型是一个不确定的模版类型, 是使用result_of的时候了
}

int mainResultOf(int argc, const char* argv[])
{
	// result_of主要用于范型编程, 功能与auto相似, 但是auto不能用于范型编程
	typedef double (*Func)(double);
	Func func = sqrt;
	auto x = call_func(func, 5.0);
	cout << typeid(x).name() << endl;
	return 0;
}
