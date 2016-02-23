#include <boost/thread.hpp>
#include <stack>
#include <iostream>

using namespace std;
using namespace boost;

static boost::mutex io_mu;

class Buffer
{
	private:
		boost::mutex mu;
		condition_variable_any cond_put; // 写入条件变量
		condition_variable_any cond_get; // 读取条件变量
		stack<int> stk; // 缓冲区
		int un_read, capacity;
		bool is_full()
		{
			return un_read == capacity;
		}
		bool is_empty()
		{
			return un_read == 0;
		}
	public:
		Buffer(size_t n) : un_read(0), capacity(n) {}

		void put(int x)
		{
			{
				boost::mutex::scoped_lock lock(mu);
				while(is_full())
				{
					{
						boost::mutex::scoped_lock lock(io_mu);
						cout << "full waiting... " << endl;
					}
					cond_put.wait(mu);
				}
				stk.push(x);
				++un_read;
			}
			cond_get.notify_one(); // 通知可以读取数据
		}

		void get(int* x)
		{
			{
				boost::mutex::scoped_lock lock(mu);
				while(is_empty())
				{
					{
						boost::mutex::scoped_lock lock(io_mu);
						cout << "empty waiting... " << endl;
					}
					cond_get.wait(mu);
				}
				--un_read;
				*x = stk.top();
				stk.pop();
			}
			cond_put.notify_one();
		}
};

static Buffer buf(5);

static void producer(int n)
{
	for(int i=0;i<n;++i)
	{
		{
			boost::mutex::scoped_lock lock(io_mu);
			cout << "put " << i << endl;
		}
		buf.put(i);
	}
}

static void consumer(int n)
{
	int x;
	for(int i=0;i<n;++i)
	{
		buf.get(&x);
		boost::mutex::scoped_lock lock(io_mu);
		cout << "get " << x << endl;
	}
}

int mainConditionVariable(int argc, const char* argv[])
{
	thread t1(producer, 20);
	thread t2(consumer, 10);
	thread t3(consumer, 10);

	t1.join();
	t2.join();
	t3.join();
	return 0;
}
