package zx.soft.utils.jdk.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExecutorPoolDemo {
	private static AtomicInteger aint = new AtomicInteger(0);

	public static void main(String[] args) {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 1; i <= 1000; i++) {
			System.err.println(i);
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(10000);
						aint.incrementAndGet();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});
		}

		executor.shutdown();
		while (!executor.isTerminated()) {
			System.out.println(executor.getActiveCount());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("运行线程数： " + aint.get());

	}
}
