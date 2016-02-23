#include <boost/cstdint.hpp>
#include <boost/rational.hpp>
#include <boost/lexical_cast.hpp>
#include <boost/regex.hpp>
#include <cmath>
#include <iostream>
#include <sstream>
#include "BigDecimal.h"

using namespace boost;
using boost::intmax_t;
using boost::int32_t;
using boost::int64_t;
using boost::uint8_t;
using std::string;
using std::ostream;
using std::istream;
using std::ostringstream;
using std::ios;
using std::move;
using std::max;

namespace xlsys
{
	regex BigDecimal::reg(R"---((\+|-)?([0-9]+)((\.)([0-9]+))?)---");

	void BigDecimal::assignWithStr(const string& str)
	{
		smatch what;
		if(regex_match(str, what, reg))
		{
			// 是一个有理数
			if(what.size()==6)
			{
				// 包含小数点, 将整数部份和小数部份拆分开
				intmax_t left = lexical_cast<intmax_t>(what[1]+what[2]);
				scale = what[5].length();  // 小数点后的精度
				intmax_t right = lexical_cast<int64_t>(what[5]);
				intmax_t base = pow(10, scale);
				ratio.assign(left*base+right, base);
			}
			else
			{
				// 不含小数点, 直接转成整数有理数
				ratio = lexical_cast<intmax_t>(str);
			}
		}
	}

	void BigDecimal::assignWithDouble(const double d, uint8_t scale)
	{
		ostringstream oss;
		oss.setf(ios::fixed, ios::floatfield);
		oss.precision(scale);
		oss << d;
		assignWithStr(oss.str());
	}

	BigDecimal::BigDecimal(const rational<intmax_t>& r, uint8_t scale) : ratio(r), scale(scale) {}

	BigDecimal::BigDecimal(const rational<intmax_t>&& r, uint8_t scale) : ratio(move(r)), scale(scale) {}

	BigDecimal::BigDecimal() : ratio(), scale() {}

	BigDecimal::BigDecimal(const BigDecimal& r) : ratio(r.ratio), scale(r.scale) {}

	BigDecimal::BigDecimal(const BigDecimal&& r) : ratio(move(r.ratio)), scale(r.scale) {}

	BigDecimal::BigDecimal(const char* str) : ratio(), scale()
	{
		assignWithStr(str);
	}

	BigDecimal::BigDecimal(const string& str) : ratio(), scale()
	{
		assignWithStr(str);
	}

	BigDecimal::BigDecimal(const double d, uint8_t scale)
	{
		assignWithDouble(d, scale);
	}

	BigDecimal::BigDecimal(intmax_t t) : ratio(t), scale() {}

	uint8_t BigDecimal::getScale() const
	{
		return scale;
	}

	void BigDecimal::setScale(uint8_t scale)
	{
		this->scale = scale;
	}

	int32_t BigDecimal::intValue() const
	{
		return rational_cast<int32_t>(ratio);
	}

	int64_t BigDecimal::longValue() const
	{
		return rational_cast<int64_t>(ratio);
	}

	float BigDecimal::floatValue() const
	{
		return rational_cast<float>(ratio);
	}

	double BigDecimal::doubleValue() const
	{
		return rational_cast<double>(ratio);
	}

	BigDecimal& BigDecimal::operator=(const BigDecimal& r)
	{
		ratio = r.ratio;
		scale = r.scale;
		return *this;
	}

	BigDecimal& BigDecimal::operator=(const BigDecimal&& r)
	{
		ratio = move(r.ratio);
		scale = r.scale;
		return *this;
	}

	BigDecimal BigDecimal::operator+(const BigDecimal& r) const
	{
		return BigDecimal(ratio+r.ratio, max(scale, r.scale));
	}

	BigDecimal BigDecimal::operator-(const BigDecimal& r) const
	{
		return BigDecimal(ratio-r.ratio, max(scale, r.scale));
	}

	BigDecimal BigDecimal::operator*(const BigDecimal& r) const
	{
		return BigDecimal(ratio*r.ratio, scale+r.scale);
	}

	BigDecimal BigDecimal::operator/(const BigDecimal& r) const
	{
		rational<intmax_t> result = ratio/r.ratio;
		int scale = lexical_cast<string>(result.denominator()).length();
		return BigDecimal(result, scale);
	}

	BigDecimal& BigDecimal::operator+=(const BigDecimal& r)
	{
		ratio += r.ratio;
		this->scale = max(scale, r.scale);
		return *this;
	}

	BigDecimal& BigDecimal::operator-=(const BigDecimal& r)
	{
		ratio -= r.ratio;
		this->scale = max(scale, r.scale);
		return *this;
	}

	BigDecimal& BigDecimal::operator*=(const BigDecimal& r)
	{
		ratio *= r.ratio;
		scale += r.scale;
		return *this;
	}

	BigDecimal& BigDecimal::operator/=(const BigDecimal& r)
	{
		ratio /= r.ratio;
		scale = lexical_cast<string>(ratio.denominator()).length();
		return *this;
	}

	BigDecimal& BigDecimal::operator++()
	{
		++ratio;
		return *this;
	}

	BigDecimal BigDecimal::operator++(int)
	{
		return move(BigDecimal(ratio++));
	}

	BigDecimal& BigDecimal::operator--()
	{
		--ratio;
		return *this;
	}

	BigDecimal BigDecimal::operator--(int)
	{
		return move(BigDecimal(ratio--));
	}

	bool BigDecimal::operator<(const BigDecimal& r) const
	{
		return ratio<r.ratio;
	}

	bool BigDecimal::operator>(const BigDecimal& r) const
	{
		return ratio>r.ratio;
	}

	bool BigDecimal::operator<=(const BigDecimal& r) const
	{
		return ratio<=r.ratio;
	}

	bool BigDecimal::operator>=(const BigDecimal& r) const
	{
		return ratio>=r.ratio;
	}

	bool BigDecimal::operator==(const BigDecimal& r) const
	{
		return ratio==r.ratio;
	}

	bool BigDecimal::operator!=(const BigDecimal& r) const
	{
		return ratio!=r.ratio;
	}

	bool BigDecimal::operator!() const
	{
		return !ratio;
	}

	BigDecimal::operator bool() const
	{
		return static_cast<bool>(ratio);
	}

	ostream& operator<<(ostream& os, const BigDecimal& r)
	{
		os << r.ratio << ' ';
		if(r.ratio.denominator()==1)
		{
			// 整数, 直接输出
			os << r.ratio.numerator();
		}
		else
		{
			// 小数, 指定精度按小数形式输出
			auto oldFlag = os.setf(ios::fixed, ios::floatfield);
			auto oldPrecision = os.precision(r.scale);
			os << rational_cast<double>(r.ratio);
			os.setf(oldFlag);
			os.precision(oldPrecision);
		}
		return os;
	}

	istream& operator>>(istream& is, BigDecimal& r)
	{
		double d;
		is >> d;
		r = d;
		return is;
	}
}
