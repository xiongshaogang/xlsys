#include <boost/random.hpp>
#include <iostream>
#include <ctime>

using namespace std;
using namespace boost;

int mainFakeRandom(int argc, const char* argv[])
{
	boost::mt19937 rng(time(0)); // 以时间为种子创建一个随机数发生器
	cout << mt19937::min() << "<->" << mt19937::max() << endl;
	for(int i=0;i<100;++i)
	{
		cout << rng() << ","; // 产生随机数
	}
	rng.discard(5); // 丢弃5个随机数
	vector<int> vec(10);
	rng.generate(vec.begin(), vec.end()); // 填充值容器
	for(auto i : vec)
	{
		cout << i << endl;
	}
	// 使用随机数分布器
	boost::random::uniform_int_distribution<> ui(0, 255); // 0-255之间的整数
	for(int i=0;i<10;++i)
	{
		cout << ui(rng) << ",";
	}
	cout << endl;
	boost::random::uniform_01<> u01; // 0-1之间的小数
	for(int i=0;i<10;++i)
	{
		cout << u01(rng) << ",";
	}
	cout << endl;
	// 使用变量发生器直接封装随机数发生器和随机数分布器
	boost::random::uniform_smallint<> us(1, 100);
	variate_generator<mt19937&, boost::random::uniform_smallint<> > gen(rng, us);
	for(int i=0;i<10;++i)
	{
		cout << gen() << endl;
	}
	return 0;
}
