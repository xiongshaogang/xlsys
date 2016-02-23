#include <boost/progress.hpp>
#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
using namespace boost;

int mainPDisplay(int argc, const char* argv[])
{
	vector<string> v(100);
	ofstream fs("test.txt");

	progress_display pd(v.size());

	vector<string>::iterator pos;
	for(pos=v.begin();pos!=v.end();++pos)
	{
		fs << *pos << endl;
		++pd;
		sleep(1);
	}

	return 0;
}
