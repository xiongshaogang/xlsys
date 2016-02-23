#include <boost/detail/lightweight_mutex.hpp>
#include <iostream>

using namespace std;

/**
 * 超轻量级的互斥量
 */
int mainLightWeightMutex(int argc, const char* argv[])
{
	boost::detail::lightweight_mutex lmu;
	{
		boost::detail::lightweight_mutex::scoped_lock lock(lmu);
		cout << "light weight mutex!" << endl;
	}

	return 0;
}
