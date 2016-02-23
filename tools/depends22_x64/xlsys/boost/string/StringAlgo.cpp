#include <boost/format.hpp>
#include <boost/algorithm/string.hpp>
#include <iostream>
#include <vector>

using namespace std;
using namespace boost;

int mainStringAlgo(int argc, const char* argv[])
{
	string str = "readme.txt";
	if(ends_with(str, "txt"))
	{
		cout << to_upper_copy(str) + " UPPER" << endl;
	}
	replace_first(str, "readme", "followme");
	cout << str << endl;

	vector<char> v(str.begin(), str.end());
	vector<char> v2 = to_upper_copy(erase_first_copy(v, "txt"));
	for(auto ch : v2)
	{
		cout << ch;
	}
	cout << endl;
	// 判断式的用法
	str = "Power Bomb";
	cout << boolalpha << iends_with(str, "bomb") << endl; // 大小写无关的检测
	cout << "with case : " << ends_with(str, "bomb") << endl; // 大小写敏感的检测
	cout << starts_with(str, "Pow") << endl;
	cout << contains(str, "er") << endl;
	string str2 = to_lower_copy(str);
	cout << iequals(str, str2) << endl;
	string str3("power suit");
	cout << ilexicographical_compare(str, str3) << endl; // 比较
	cout << all(str2.substr(0, 5), is_lower()) << endl; // 检测字串均小写
	// trim
	str = "  samus aran   ";
	cout << trim_copy(str) << endl;
	cout << trim_left_copy(str) << endl;
	trim_right(str);
	cout << str << endl;
	str2 = "2012 Happy new Year!!!";
	cout << trim_left_copy_if(str2, is_digit()) << endl; // 删除左端数字
	cout << trim_right_copy_if(str2, is_punct()) << endl; // 删除右端标点
	cout << trim_copy_if(str2, is_punct()||is_digit()||is_space()) << endl; // 删除两端的标点、数字和空格
	// 查找
	format fmt("|%s|.pos = %d\n");
	str = "Long long ago, there was a king.";
	iterator_range<string::iterator> range;
	range = find_first(str, "long");
	cout << fmt %range % (range.begin()-str.begin());
	range = ifind_first(str, "long"); // 大小写无关查找
	cout << fmt %range % (range.begin()-str.begin());
	range = find_nth(str, "ng", 2); // 查找第三次出现(从0开始计数，所以传入2)
	cout << fmt %range % (range.begin()-str.begin());
	range = find_head(str, 4); // 取前4个字符
	cout << fmt %range % (range.begin()-str.begin());
	range = find_tail(str, 5); // 取末尾5个字符
	cout << fmt %range % (range.begin()-str.begin());
	range = find_first(str, "samus"); // 找不到
	cout << boolalpha << range.empty() << " range state:" << static_cast<bool>(range) << endl;
	// 替换和删除
	str = "Samus beat the monster.\n";
	cout << replace_first_copy(str, "Samus", "samus");
	replace_last(str, "beat", "kill");
	cout << str;
	replace_tail(str, 9, "ridley.\n");
	cout << str;
	cout << ierase_all_copy(str, "samus");
	cout << replace_nth_copy(str, "l", 1, "L");
	cout << erase_tail_copy(str, 8) << endl;
	// 分割
	str = "Samus,Link.Zelda::Mario-Luigi+zelda";
	deque<string> d;
	ifind_all(d, str, "zELDA");
	for(auto x: d)
	{
		cout << "[" << x << "] ";
	}
	cout << endl;

	list<iterator_range<string::iterator> > l;
	split(l, str, is_any_of(",.:-+"));
	for(auto x : l)
	{
		cout << "[" << x << "] ";
	}
	cout << endl;

	l.clear();
	split(l, str, is_any_of(".:-"), token_compress_on); // 默认时token_compress_off(即不把两个连续的分隔符视为一个)，这里手动至成on
	for(auto x : l)
	{
		cout << "[" << x << "] ";
	}
	cout << endl;
	// 合并
	vector<string> v3;
	v3.push_back("Samus");
	v3.push_back("Link");
	v3.push_back("Zelda");
	v3.push_back("Mario");
	cout << join(v3, "+") << endl;
	struct is_contains_a
	{
			bool operator()(const string& x)
			{
				return contains(x, "a");
			}
	};
	cout << join_if(v3, "**", is_contains_a()) << endl;
	return 0;
}
