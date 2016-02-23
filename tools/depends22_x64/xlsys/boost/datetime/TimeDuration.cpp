#include <boost/date_time/posix_time/posix_time.hpp>
#include <iostream>

using namespace std;
using namespace boost::posix_time;

int mainTimeDuration(int argc, const char* argv[])
{
	time_duration td(1, 10, 30, 1000); // 小时，分钟，秒，微妙
	cout << td << endl;
	hours h(1);
	minutes m(10);
	seconds s(30);
	millisec ms(1);

	time_duration td2 = h + m + s + ms;
	cout << td2 << endl;

	cout << boolalpha << (td==td2) << endl;

	return 0;
}
