#include <boost/date_time/gregorian/gregorian.hpp>
#include <iostream>

using namespace std;
using namespace boost::gregorian;

extern void printlnDate(const date&);

int mainDateCaculate(int argc, const char* argv[])
{
	date d1(2000,1,1), d2(2008,8,8);
	cout << d2 - d1 << endl; // 天数
	d1 += days(10);
	printlnDate(d1);
	d1 += months(2);
	printlnDate(d1);
	d1 -= weeks(1);
	printlnDate(d1);
	d2 -= years(7);
	printlnDate(d2);
	return 0;
}
