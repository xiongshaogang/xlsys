#include <boost/date_time/posix_time/posix_time.hpp>
#include <iostream>

using namespace std;
using namespace boost::posix_time;
using namespace boost::gregorian;

int mainPTime(int argc, const char* argv[])
{
	ptime p(date(2010,3,5), hours(1));
	cout << p << endl;

	ptime p1 = time_from_string("2010-3-5 01:00:00");
	ptime p2 = from_iso_string("20100305T010000");
	cout << p1 << endl;
	cout << p2 << endl;

	// 获取当前的时间
	ptime p3 = second_clock::local_time();
	ptime p4 = microsec_clock::universal_time();
	cout << p3 << endl << p4 << endl;

	// 获取ptime中的date和time_duration
	date d = p.date();
	time_duration td = p.time_of_day();
	p += days(10);
	cout << p << endl;
	cout << to_simple_string(p4) << endl;
	cout << to_iso_string(p4) << endl;
	cout << to_iso_extended_string(p4) << endl;

	// 自定义格式化输出
	date_facet* dfacet = new date_facet("%Y年%m月%d日");
	cout.imbue(locale(cout.getloc(), dfacet));
	cout << d << endl;
	time_facet* tfacet = new time_facet("%Y年%m月%d日%H点%M分%S%F秒");
	cout.imbue(locale(cout.getloc(), tfacet));
	cout << p4 << endl;
	return 0;
}
