#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <iostream>

using namespace std;
using namespace boost::property_tree;

int mainPropertyTree(int argc, const char* argv[])
{
	// 读取xml, 像标准容器，提供begin()和end()迭代器
	ptree pt;
	read_xml("conf.xml", pt);
	cout << pt.get<string>("conf.<xmlcomment>") << endl;
	cout << pt.get<long>("conf.theme.<xmlattr>.id") << endl;
	cout << pt.get<string>("conf.theme") << endl;
	cout << pt.get<string>("conf.clock_style.<xmlattr>.name") << endl;
	cout << pt.get<int>("conf.clock_style") << endl;
	cout << pt.get("conf.no_prop", 100) << endl; // 如果不存在则返回整数100, 此时类型可被推导出来, 所以不需要使用模版参数来指定
	cout << pt.get<string>("conf.urls.<xmlcomment>") << endl;
	auto child = pt.get_child("conf.urls");
	for(auto pos = child.begin();pos!=child.end();++pos)
	{
		cout << pos->second.get_value<string>() << ","; // pos的secode成员时子节点自身
	}
	cout << endl;
	for(auto& x : child)
	{
		cout << x.second.data() << ",";
	}
	cout << endl;
	// 写入xml
	pt.put("conf.theme", "Matrix Reloaded");
	pt.put("conf.clock_style", 12);
	pt.put("conf.gui", 0);
	pt.add("conf.urls.url", "http://www.url4.org");
	write_xml(cout, pt);
	return 0;
}
