#include <boost/filesystem.hpp>
#include <iostream>

using namespace boost::filesystem;
using namespace std;

int mainFileSystem(int argc, const char* argv[])
{
	namespace fs = boost::filesystem;
	path ptest = "toDel";
	if(exists(ptest))
	{
		if(fs::is_empty(ptest)) // 如果是目录, 则目录中无文件时返回true; 如果为文件, 则文件长度为0时返回true
		{
			remove(ptest);
		}
		else
		{
			remove_all(ptest); // 可递归删除
		}
	}
	cout << boolalpha << exists(ptest) << endl;
	create_directory(ptest); // 创建目录
	copy_file("test.txt", ptest/"a.txt"); // 拷贝文件
	rename(ptest/"a.txt", ptest/"b.txt"); // 改名
	create_directories(ptest/"sub_dir1"/"sub_dir2"); // 一次创建多个目录
	// 迭代目录
	directory_iterator pos("/Users/Lewis/Work/boost_1_57_0");
	directory_iterator end;
	for(;pos!=end;++pos)
	{
		cout << (*pos).path() << endl;
	}
	// 使用递归目录迭代器, 比直接用递归要快很多
	typedef recursive_directory_iterator RdIter;
	RdIter rend;
	RdIter rpos(ptest);
	for(;rpos!=rend;++rpos)
	{
		cout << "level" << rpos.level() << " : " << system_complete(*rpos) << endl;
	}
	return 0;
}
