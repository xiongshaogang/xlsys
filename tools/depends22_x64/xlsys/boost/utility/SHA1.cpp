#include <boost/uuid/sha1.hpp>
#include <iostream>

using namespace std;
using namespace boost::uuids::detail;

int mainSHA1(int argc, const char* argv[])
{
	sha1 sha;
	const char* szMsg = "a short message";
	sha.process_byte(0x10);
	sha.process_bytes(szMsg, strlen(szMsg));
	sha.process_block(szMsg, szMsg + strlen(szMsg));

	unsigned int digest[5];
	sha.get_digest(digest);
	for(int i=0;i<5;++i)
	{
		cout << hex << digest[i];
	}
	return 0;
}
