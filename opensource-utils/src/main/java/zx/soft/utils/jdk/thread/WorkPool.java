package zx.soft.utils.jdk.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkPool {

	public static void main(String args[]) throws InterruptedException {
		//creating the ThreadPoolExecutor
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		//submit work to the thread pool
		for (int i = 0; i < 10; i++) {
			executorPool.execute(new WorkerThread("cmd" + i));
		}

		//shut down the pool
		executorPool.shutdown();
		//shut down the monitor thread
		while (!executorPool.isTerminated()) {

		}
		System.out.println("Finished all threads");
	}
}