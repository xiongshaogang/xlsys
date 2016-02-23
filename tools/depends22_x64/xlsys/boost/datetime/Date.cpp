#include <boost/date_time/gregorian/gregorian.hpp>
#include <iostream>

using namespace std;
using namespace boost::gregorian;

void printlnDate(const date& d)
{
	cout << to_iso_extended_string(d) << endl;
}

int mainDate(int argc, const char* argv[])
{
	date d1;
	date d2(2000,1,1);
	date d3(2000, Jan, 1);
	date d4(d2);
	date d5 = from_string("2014-11-11");
	date d6(from_string("2012/3/8"));
	date d7 = from_undelimited_string("20011118");


	cout << d1 << endl;
	cout << d2 << endl;
	cout << d3 << endl;
	cout << d4 << endl;
	cout << d5 << endl;
	cout << d6 << endl;
	cout << d7 << endl;

	assert(d1==date(not_a_date_time));
	assert(d3==d4);

	// 获取当天的日期
	cout << day_clock::local_day() << endl; // 本地日期
	cout << day_clock::universal_day() << endl; // UTC日期

	// 利用枚举创建特殊日期
	date d8(neg_infin); // 负无限日期
	date d9(pos_infin); // 正无限日期
	date d10(not_a_date_time); // 无效日期
	date d11(max_date_time); // 最大可能日期
	date d12(min_date_time); // 最小可能日期
	cout << "-----------------" << endl;
	cout << d8 << endl;
	cout << d9 << endl;
	cout << d10 << endl;
	cout << d11 << endl;
	cout << d12 << endl;

	date d(2014,11,11);
	cout << "Year:" << d.year() << "\tMonth:" << d.month() << "\tDay:" << d.day() << endl;
	cout << to_iso_extended_string(d) << endl;
	cout << to_iso_string(d) << endl;

	tm t = to_tm(d);
	cout << t.tm_year << ' ' << t.tm_hour << endl;

	date dt = date_from_tm(t);
	cout << to_iso_string(dt) << endl;

	return 0;
}
