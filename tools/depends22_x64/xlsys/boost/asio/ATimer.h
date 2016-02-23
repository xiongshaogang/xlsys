#ifndef XLSYS_BOOST_ASIO_ATIMER_H_
#define XLSYS_BOOST_ASIO_ATIMER_H_

#include <boost/function.hpp>
#include <boost/bind.hpp>
#include <boost/ref.hpp>

using namespace boost::asio;

class ATimer
{
	private:
		int count, count_max;
		boost::function<void()> f;
		deadline_timer t;
	public:
		template<typename F>
		ATimer(io_service& ios, int x, F func) : f(func), count_max(x), count(0), t(ios, boost::posix_time::millisec(500))
		{
			t.async_wait(boost::bind(&ATimer::call_func, this, placeholders::error));
		}

		void call_func(const boost::system::error_code&)
		{
			if(count>=count_max) return;
			++count;
			f();
			t.expires_at(t.expires_at() + boost::posix_time::millisec(500));
			t.async_wait(bind(&ATimer::call_func, this, placeholders::error));
		}
};



#endif /* XLSYS_BOOST_ASIO_ATIMER_H_ */
