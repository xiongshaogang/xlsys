#include <boost/typeof/typeof.hpp>
#include <vector>
#include <iostream>

using namespace std;
using namespace boost;

vector<string> guessMyReturnType()
{
	vector<string> v(10);
	return std::move(v);	// 转移语义高效返回对象
}


void typeofInCpp11()
{
	decltype(2.0*3) x = 2.0*3;
	auto y = 2+3;

	auto a = new double[20];
	auto p = make_pair(1, "string");

	auto v = guessMyReturnType();
}

int mainTypeOf(int argc, const char* argv[])
{
	BOOST_TYPEOF(2.0*3) x = 2.0*3; // 推导类型为double
	BOOST_AUTO(y, 2+3); // 推导类型为int

	BOOST_AUTO(a, new double[20]); // 推导类型为double*
	BOOST_AUTO(p, make_pair(1, "string")); // 推导类型为pair<int, const char*>

	BOOST_AUTO(v, guessMyReturnType()); // 推导类型为vector<string>

	// 等价于以下c++11标准代码
	typeofInCpp11();

	return 0;
}
