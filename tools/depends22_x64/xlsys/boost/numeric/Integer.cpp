#include <boost/cstdint.hpp>
#include <boost/integer_traits.hpp>
#include <boost/integer.hpp>
#include <boost/format.hpp>
#include <typeinfo>
#include <limits>
#include <iostream>

using std::cout;
using std::endl;
using namespace boost;

int mainInteger(int argc, const char* argv[])
{
	cout << integer_traits<int>::const_max << endl;
	cout << integer_traits<bool>::const_min << endl;
	cout << integer_traits<long>::is_signed << endl;
	// 使用标准整数类型
	uint8_t u8;
	int_fast16_t i16; // 最快的有符号16位整数
	int_least32_t i32; // 至少有32位的有符号整数
	uintmax_t um; // 编译器支持的最大无符号整数类型
	u8 = 255;
	i16 = 32000;
	i32 = i16;
	um = u8+i16+i32;
	cout << typeid(u8).name() << ':' << sizeof(u8) << " v=" << (short)u8 << endl;
	cout << typeid(i16).name() << ':' << sizeof(i16) << " v=" << i16 << endl;
	cout << typeid(i32).name() << ':' << sizeof(i32) << " v=" << i32 << endl;
	cout << typeid(um).name() << ':' << sizeof(um) << " v=" << um << endl;
	cout << (short)std::numeric_limits<int8_t>::max() << endl;
	cout << std::numeric_limits<uint_least16_t>::max() << endl;
	cout << std::numeric_limits<int_fast32_t>::max() << endl;
	cout << std::numeric_limits<intmax_t>::min() << endl;
	// 使用整数类型模版
	int_fast_t<char>::fast a; // char类型的最快类型
	cout << typeid(a).name() << endl;
	int_fast_t<int>::fast b; // int类型的最快类型
	cout << typeid(b).name() << endl;
	int_fast_t<boost::uint16_t>::fast c; // uint16类型的最快类型
	cout << typeid(c).name() << endl;

	format fmt("type:%s, size=%dbit\n");

	uint_t<15>::fast a1; // 可容纳15位的无符号最快整数
	cout << fmt % typeid(a1).name() % (sizeof(a1)*8);

	int_max_value_t<32700>::fast b1; // 可处理最大值位32700的最快整数
	cout << fmt % typeid(b1).name() % (sizeof(b1)*8);

	int_min_value_t<-33000>::fast c1; // 可处理最小值位-33000的最快整数
	cout << fmt % typeid(c1).name() % (sizeof(c1)*8);

	uint_value_t<33000>::fast d1; // 可处理最大值位33000的最快无符号整数
	cout << fmt % typeid(d1).name() % (sizeof(d1)*8);
	return 0;
}
