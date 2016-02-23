#include <boost/random.hpp>
#include <boost/random/random_device.hpp>
#include <iostream>

using namespace std;

int mainRealRandom(int argc, const char* argv[])
{
	boost::random::random_device rng;
	for(int i=0;i<10;++i)
	{
		cout << rng() << ",";
	}
	cout << endl;

	// 配合分布器
	boost::uniform_real<> ur(1.0, 2.0);
	for(int i=0;i<10;++i)
	{
		cout << ur(rng) << ",";
	}
	cout << endl;
	// 配合变量发生器
	boost::variate_generator<boost::random::random_device&, boost::uniform_smallint<> >gen(rng, boost::uniform_smallint<>(0, 255));
	for(int i=0;i<10;++i)
	{
		cout << gen() << ",";
	}
	cout << endl;
	return 0;
}
