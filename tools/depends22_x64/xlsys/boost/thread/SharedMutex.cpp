#include <boost/thread.hpp>
#include <boost/chrono.hpp>
#include <boost/bind.hpp>
#include <boost/ref.hpp>
#include <iostream>

using namespace std;
using namespace boost;

static boost::mutex io_mu;

class RWData
{
	private:
		int m_x;
		boost::shared_mutex rw_mu;
	public:
		RWData() : m_x(0) {}

		void write()
		{
			boost::unique_lock<shared_mutex> ul(rw_mu);
			++m_x;
		}

		void read(int* x)
		{
			shared_lock<shared_mutex> sl(rw_mu);
			*x = m_x;
		}
};

static void writer(RWData& d)
{
	for(int i=0;i<20;++i)
	{
		this_thread::sleep_for(boost::chrono::microseconds(10));
		d.write();
	}
}

static void reader(RWData& d)
{
	int x;
	for(int i=0;i<10;++i)
	{
		this_thread::sleep_for(boost::chrono::milliseconds(5));
		d.read(&x);
		boost::mutex::scoped_lock lock(io_mu);
		cout << "reader:" << x << endl;
	}
}

int mainSharedMutex(int argc, const char* argv[])
{
	// 用thread_group启用多个线程
	RWData d;
	thread_group pool;
	pool.create_thread(bind(writer, boost::ref(d)));
	pool.create_thread(bind(writer, boost::ref(d)));

	pool.create_thread(bind(reader, boost::ref(d)));
	pool.create_thread(bind(reader, boost::ref(d)));
	pool.create_thread(bind(reader, boost::ref(d)));
	pool.create_thread(bind(reader, boost::ref(d)));

	pool.join_all();
	return 0;
}
