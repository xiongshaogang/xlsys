#ifndef XLSYS_BOOST_THREAD_BASICATOMIC_H_
#define XLSYS_BOOST_THREAD_BASICATOMIC_H_

#include <boost/thread.hpp>
#include <boost/noncopyable.hpp>
using namespace boost;

template<typename T>
class BasicAtomic : noncopyable
{
	private:
		T n;
		typedef boost::mutex mutex_t;
		mutex_t mu;
	public:
		BasicAtomic(T x = T()) : n(x) {}
		T operator++()
		{
			mutex_t::scoped_lock lock(mu);
			return ++n;
		}
		operator T() // 类型转换操作符定义
		{
			mutex_t::scoped_lock lock(mu);
			return n;
		}
};

typedef BasicAtomic<int> AtomicInt;

#endif /* XLSYS_BOOST_THREAD_BASICATOMIC_H_ */
