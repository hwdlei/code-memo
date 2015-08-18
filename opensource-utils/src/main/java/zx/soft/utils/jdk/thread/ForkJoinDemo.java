package zx.soft.utils.jdk.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ForkJoinDemo {
	public static void main(String[] args) {
		Callable<String> call = new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				return Thread.currentThread().getName();
			}
		};
		FutureTask<String> task = new FutureTask<String>(call);
		new Thread(task).start();
		try {
			System.out.println(task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
