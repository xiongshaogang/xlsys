#include <boost/filesystem.hpp>
#include <iostream>

using namespace std;
using namespace boost::filesystem;

int mainPath(int argc, const char* argv[])
{
	path p1("/usr/local/lib");
	cout << p1 << endl;
	path p2("/");
	p2 /= "etc"; // 使用operator/=追加路径, 会自动添加路径分隔符
	string filename("xinetd.conf");
	p2.append(filename.begin(), filename.end()); // 使用append追加路径, 也会自动添加路径分隔符
	cout << p2 << endl;
	path p3("/");
	p3 += "etc"; // 不会添加路径分隔符
	p3.concat("xinetd.conf"); // 不会添加路径分隔符
	cout << p3 << endl;
	// 获取绝对路径
	path p4("conf.xml");
	cout << system_complete(p4) << endl;
	// 路径处理
	cout << p4.string() << endl; // 返回std::string
	cout << p4.parent_path() << endl;
	cout << p4.stem() << endl;
	cout << p4.filename() << endl;
	cout << p4.extension() << endl;
	path p5;
	if(p4.is_absolute())
	{
		p5 = p4;
	}
	else
	{
		p5 = system_complete(p4);
	}
	cout << p5.root_name() << endl;
	cout << p5.root_directory() << endl;
	cout << p5.root_path() << endl;
	cout << boolalpha << p5.has_root_name() << endl;
	cout << boolalpha << p5.has_root_path() << endl;
	cout << boolalpha << p5.has_parent_path() << endl;
	cout << p5.replace_extension() << endl; // 变更文件的扩展名, 会修改path的值
	cout << p5.replace_extension("hxx") << endl;
	cout << p5 << endl;
	cout << p5.remove_filename() << endl; // 删除文件名, 会修改path的值
	// 使用迭代器分解路径
	for(const path& x : p5)
	{
		cout << "[" << x << "]";
	}
	cout << endl;
	// 测试路径状态
	cout << (status(p5).type()==character_file) << endl; // 是否是一个字符设备文件
	cout << (status(p5).type()==directory_file) << endl; // 是否是一个目录
	cout << (status(p5).type()==regular_file) << endl; // 是否是一个常规文件
	// 访问权限
	cout << ((status(p5).permissions()&owner_exe)==owner_exe) << endl; // 是否拥有执行权限
	// 也可以使用谓词来进行判断
	cout << exists(p5) << endl;
	cout << is_directory(p5) << endl;
	// 文件属性
	cout << initial_path() << endl; // 输出初始路径
	cout << current_path() << endl; // 输出当前路径
	cout << file_size(p4) << endl;
	time_t t = last_write_time(p4); // 获取最后修改时间
	cout << t << endl;
	last_write_time(p4, time(0)); // 更新修改时间
	// 获取磁盘空间分配情况
	const int GBYTES = 1000*1000*1000;
	space_info si = space(p5);
	cout << si.capacity/GBYTES << endl;
	cout << si.available/GBYTES << endl;
	cout << si.free/GBYTES << endl;
	return 0;
}
