#include <boost/noncopyable.hpp>
#include <iostream>

using namespace std;
using namespace boost;

class CannotCopy : private boost::noncopyable
{

};

int mainNonCopyable(int argc, const char* argv[])
{
	CannotCopy d1;
	// CannotCopy d2(d1); // 编译出错
	CannotCopy d3;
	// d3 = d1; // 编译出错
	return 0;
}
