#include <boost/xpressive/xpressive_dynamic.hpp>
#include <iostream>

using namespace std;
using namespace boost::xpressive;

int mainXpressive(int argc, const char* argv[])
{
	cregex creg = cregex::compile("a.c");
	cout << boolalpha << regex_match("abc", creg) << endl;
	cout << regex_match("a+c", creg) << endl;
	cout << regex_match("ac", creg) << endl;
	cout << regex_match("abd", creg) << endl;

	// 匹配, regex_match, 必须完全匹配才返回true
	string regStr = R"---(\d{6}(1|2)\d{3}(0|1)\d[0-3]\d\d{3}(X|\d))---"; // 使用c++11的字符串形式
	cout << regStr << endl;
	sregex sreg = sregex::compile(regStr, icase);
	cout << regex_match(string("999555197001019999"), sreg) << endl;

	// 获取子表达式的匹配结果
	regStr = R"---(\d{6}((1|2)\d{3})((0|1)\d)([0-3]\d)(\d{3}(X|\d)))---"; // 每个()中就是一个子表达式
	sreg = sregex::compile(regStr, icase);
	smatch what;
	regex_match(string("999555197001019999"), what, sreg);
	for(auto x : what)
	{
		cout << "[" << x << "]";
	}
	cout << endl;
	cout << "date:" << what[1] << what[3] << what[5] << endl;

	// 查找, regex_search, 只要找到匹配的子串就返回true
	const char* str = "there is a POWER-suit item";
	cregex reg = cregex::compile("(power)-(.{4})", icase);
	cout << regex_search(str, reg) << endl;
	cmatch cwhat;
	regex_search(str, cwhat, reg);
	for(auto x : cwhat)
	{
		cout << "[" << x << "]";
	}
	cout << endl;

	// 替换
	string readme("readme.txt");
	sregex reg1 = sregex::compile("(.*)(me)");
	sregex reg2 = sregex::compile("(t)(.)(t)");
	smatch swhat;
	regex_search(readme, swhat, reg1);
	for(auto x : swhat)
	{
		cout << "[" << x << "]";
	}
	cout << endl;
	regex_search(readme, swhat, reg2);
	for(auto x : swhat)
	{
		cout << "[" << x << "]";
	}
	cout << endl;
	cout << regex_replace(readme, reg1, "manual") << endl; // 替换完后返回一个字符串的拷贝
	cout << regex_replace(readme, reg1, "$1you") << endl; // 将匹配的字符串替换成匹配的子表达式1+you
	cout << regex_replace(readme, reg1, "$&$&") << endl; // $&引用全匹配
	cout << regex_replace(readme, reg2, "$1N$3") << endl;

	readme = regex_replace(readme, reg2, "$1$3");
	cout << readme << endl;
	// 迭代
	string iterStr("Power-bomb, power-suit, pOWER-beam all items\n");
	sreg = sregex::compile("power-(\\w{4})", icase);

	sregex_iterator pos(iterStr.begin(), iterStr.end(), sreg);
	sregex_iterator end;
	while(pos!=end)
	{
		for(auto x : *pos) // operator*返回一个match_results对象
		{
			cout << "[" << x << "]";
		}
		cout << endl;
		++pos;
	}

	// 分词
	str = "*Link*||+Mario+||Zelda!!!||Metroid";
	// 查找所有的单词, 无视标点符号
	reg = cregex::compile("\\w+", icase);
	cregex_token_iterator cpos(str, str+strlen(str), reg);
	while(cpos!=cregex_token_iterator())
	{
		cout << "[" << *cpos << "]";
		++cpos;
	}
	cout << endl;
	// 使用分隔符正则表达式, 分隔符时"||"
	cregex split_reg = cregex::compile("\\|\\|");
	cpos = cregex_token_iterator(str, str+strlen(str), split_reg, -1); // 最后一个参数传入-1时，将把匹配的字符串视为分隔符
	while(cpos!=cregex_token_iterator())
	{
		cout << "[" << *cpos << "]";
		++cpos;
	}
	cout << endl;
	return 0;
}
