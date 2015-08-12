package zx.soft.utils.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtoIntDemo {
	private static AtomicInteger atoInt = new AtomicInteger(0);

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {

				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(atoInt.incrementAndGet());

				}
			}).start();

		}
	}


}

