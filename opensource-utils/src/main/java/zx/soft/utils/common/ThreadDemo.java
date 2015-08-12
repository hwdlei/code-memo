package zx.soft.utils.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
	public static void main(String[] args) {
		Callable<String> call = new Callable<String>() {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
