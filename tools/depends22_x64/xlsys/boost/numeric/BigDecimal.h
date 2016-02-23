#ifndef XLSYS_MATH_BIGDECIMAL_H_
#define XLSYS_MATH_BIGDECIMAL_H_

#include <boost/cstdint.hpp>
#include <boost/rational.hpp>
#include <boost/regex.hpp>
#include <iostream>

using boost::regex;
using boost::rational;
using boost::intmax_t;
using boost::int32_t;
using boost::int64_t;
using boost::uint8_t;
using std::string;
using std::ostream;
using std::istream;

namespace xlsys
{
	class BigDecimal
	{
		private:
			/**
			 * 用来匹配有理数的正则表达式
			 */
			static regex reg;
			/**
			 * 内部真实数据存储
			 */
			rational<intmax_t> ratio;
			/**
			 * 转换成文本时的精度
			 */
			uint8_t scale;
		private:
			/**
			 * 通过字符串直接赋值
			 */
			void assignWithStr(const string&);
			/**
			 * 通过double直接赋值
			 */
			void assignWithDouble(const double, uint8_t);
			BigDecimal(const rational<intmax_t>&, uint8_t =6);
			BigDecimal(const rational<intmax_t>&&, uint8_t =6);
		public:
			BigDecimal();
			BigDecimal(const BigDecimal&);
			BigDecimal(const BigDecimal&&);
			BigDecimal(const char*);
			BigDecimal(const string&);
			BigDecimal(const double, uint8_t =6);
			BigDecimal(intmax_t);

			uint8_t getScale() const;
			void setScale(uint8_t);

			int32_t intValue() const;
			int64_t longValue() const;
			float floatValue() const;
			double doubleValue() const;

			BigDecimal& operator=(const BigDecimal&);
			BigDecimal& operator=(const BigDecimal&&);
			BigDecimal operator+(const BigDecimal&) const;
			BigDecimal operator-(const BigDecimal&) const;
			BigDecimal operator*(const BigDecimal&) const;
			BigDecimal operator/(const BigDecimal&) const;
			BigDecimal& operator+=(const BigDecimal&);
			BigDecimal& operator-=(const BigDecimal&);
			BigDecimal& operator*=(const BigDecimal&);
			BigDecimal& operator/=(const BigDecimal&);
			BigDecimal& operator++();
			BigDecimal operator++(int);
			BigDecimal& operator--();
			BigDecimal operator--(int);
			bool operator<(const BigDecimal&) const;
			bool operator>(const BigDecimal&) const;
			bool operator<=(const BigDecimal&) const;
			bool operator>=(const BigDecimal&) const;
			bool operator==(const BigDecimal&) const;
			bool operator!=(const BigDecimal&) const;
			bool operator!() const;
			operator bool() const;

			friend ostream& operator<<(ostream&, const BigDecimal&);
			friend istream& operator>>(istream&, BigDecimal&);
	};
}

#endif /* XLSYS_MATH_BIGDECIMAL_H_ */
