#include <boost/uuid/uuid.hpp>
#include <boost/uuid/uuid_generators.hpp>
#include <boost/uuid/uuid_io.hpp>
#include <vector>
#include <iostream>

using namespace std;
using namespace boost::uuids;

int mainUUID(int argc, const char* argv[])
{
	uuid u;
	vector<unsigned char> v(16, 7);
	copy(v.begin(), v.end(), u.begin());
	cout << u << endl;

	// 名字生成器
	// 先指定一个基准UUID
	uuid xlsys = string_generator()("01234567-89ab-cdef-0123-456789abcdef");
	// 创建名字生成器
	name_generator ngen(xlsys);
	// 生成mario的uuid
	uuid u1 = ngen("mario");
	cout << u1 << endl;
	// 生成link的uuid
	uuid u2 = ngen("link");
	cout << u2 << endl;

	// 随机数生成器
	random_generator rgen;
	cout << rgen() << endl;

	// 转换成字符串
	string str = to_string(u);
	cout << str << endl;
	return 0;
}
