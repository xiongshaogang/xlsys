#include <boost/progress.hpp>
#include <iostream>

using namespace std;
using namespace boost;

int mainPTimer()
{
	// 此类在析构时会自动输出流逝的时间
	progress_timer t;
	return 0;
}
