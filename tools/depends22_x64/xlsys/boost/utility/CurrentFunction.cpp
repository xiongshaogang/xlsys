#include <boost/current_function.hpp>
#include <iostream>

using namespace std;

int mainCurrentFunction(int argc, const char* argv[])
{
	cout << BOOST_CURRENT_FUNCTION << endl;
	return 0;
}
