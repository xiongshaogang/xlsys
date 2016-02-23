#include <boost/timer.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainTimer()
{
	// cout.unsetf(ios::scientific);
	// 注意在mac os中要手动fix一下，否则输出的使科学计数法
	cout.setf(ios::fixed);
	timer t;
	// 可度量的最大时间
	cout << "max timespan:" << t.elapsed_max()/3600 << "h" << endl;
	// 可度量的最小时间
	cout << "min timespan:" << t.elapsed_min()  << "s" << endl;
	// 已经流逝的时间
	cout << "now time elapsed:" << t.elapsed() << "s" << endl;
	return 0;
}
