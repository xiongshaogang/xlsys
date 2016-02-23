#include <boost/lexical_cast.hpp>
#include <iostream>

using namespace std;
using namespace boost;

class MyToString
{
	private :
		int a, b;
	public :
		MyToString() throw() : a(10), b(20) {}
		friend ostream& operator<<(ostream& os, const MyToString& r)
		{
			return os << r.a << r.b;
		}
};

int mainLexcialCast(int argc, const char* argv[])
{
	int x = lexical_cast<int>("100");
	long y = lexical_cast<long>("2000");
	float pai = lexical_cast<float>("3.1415936");
	double e = lexical_cast<double>("2.71828");;
	cout << x << y << pai << e << endl;

	string str = lexical_cast<string>(456);
	cout << str << endl;

	cout << lexical_cast<string>(0.168) << endl;
	cout << lexical_cast<string>(0x10) << endl;

	cout << boolalpha << lexical_cast<bool>("1") << endl;

	// 只要实现了operator<<方法，就可以使用lexical_cast将自定义类转换成string
	str = lexical_cast<string>(MyToString());
	cout << str << endl;
	return 0;
}
