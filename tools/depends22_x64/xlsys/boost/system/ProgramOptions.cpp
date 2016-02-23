#include <boost/algorithm/string.hpp>
#include <boost/program_options.hpp>
#include <iostream>

using namespace std;
using namespace boost::program_options;

string name_mapper(const string& env_name)
{
	static map<string, string> map;
	map.insert(make_pair("HOME", "home"));
	map.insert(make_pair("USER", "uname"));
	return map[env_name];
}

int mainProgramOptions(int argc, const char* argv[])
{
	options_description opts("demo options");
	// 增加参数，参数用"--"来指定参数名调用,如果有空格或特殊字符，可以用引号把值括起来 比如--filename="read me.txt"
	opts.add_options()("help", "just a help info")("filename", value<string>(), "to find a file")("home", value<string>(), "home dir")("uname", value<string>(), "user's name");
	variables_map vm; // 选项存储map容器
	store(parse_command_line(argc, argv, opts), vm); // 解析存储
	// 解析完成, 实现选项处理逻辑
	if(vm.count("help"))
	{
		cout << opts << endl;
		return 0;
	}
	if(vm.count("filename"))
	{
		cout << "find " << vm["filename"].as<string>() << endl;
	}
	if(vm.empty())
	{
		cout << "no options" << endl;
	}
	// 解析环境变量
	store(parse_environment(opts, name_mapper), vm); // 解析环境变量
	for(auto x : vm)
	{
		cout << x.first << "=" << x.second.as<string>() << endl;
	}
	return 0;
}
