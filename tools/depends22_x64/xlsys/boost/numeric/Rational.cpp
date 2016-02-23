#include <boost/rational.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/regex.hpp>
#include <iostream>
#include <cmath>

using std::cout;
using std::endl;
using std::string;
using namespace boost;

int main(int argc, const char* argv[])
{
	rational<int> a; // a=0
	rational<int> b(20); // b=20
	rational<int> c(31415, 10000); // c=3.1415
	a = 3; // 也可直接赋值
	b = 65534;
	c.assign(22,7); // 也可用assign赋值
	b += a;
	c -= a;
	if(c>=0)
	{
		c = c * b;
		++a;
	}
	cout << a << ' ' << b << ' ' << c << endl;
	cout << std::boolalpha << (a==4) << endl;
	// 转换为其他数字类型
	rational<int> r(2718, 1000);
	cout << rational_cast<int>(r) << endl;
	cout << rational_cast<double>(r) << endl;
	// 输出分子和分母
	cout << r.numerator() << ':' << r.denominator() << '=' << rational_cast<double>(r) << endl;
	// 尝试将字符串转成double后再转成有理数
	rational<int64_t> pi;
	string str = "+3.1415926";
	regex reg(R"---((\+|-)?([0-9]+)((\.)([0-9]+))?)---");
	smatch what;
	if(regex_match(str, what, reg))
	{
		for(auto x : what)
		{
			cout << "[" << x << "]";
		}
		cout << endl;
		// 是一个有理数
		if(what.size()==6)
		{
			// 包含小数点, 将整数部份和小数部份拆分开
			int64_t left = lexical_cast<int64_t>(what[2]);
			uint8_t scale = what[5].length();  // 小数点后的精度
			int64_t right = lexical_cast<int64_t>(what[5]);
			int64_t base = pow(10, scale);
			pi.assign(left*base+right, base);
		}
		else
		{
			// 不含小数点, 直接转成整数有理数
			pi = lexical_cast<int64_t>(str);
		}
	}
	// 输出pi
	cout << pi << endl;
	cout.precision(10);
	cout << rational_cast<double>(pi) << endl;
	return 0;
}
