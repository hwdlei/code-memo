package zx.soft.utils.jdk.blockqueue;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
	public static void main(String[] args) {
		SourceIdNew.addId("id1");
		SourceIdNew.addId("id2");
		SourceIdNew.addId("id3");

		new Thread(new ScanRunnable()).start();

		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.DAYS, queue);
		for (int i = 0; i < 45000; i++) {
			executor.execute(new MyRunnable());
		}
		executor.shutdown();
	}
}

class ScanRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ScanRunnable.class);
	public void run() {
		while (true) {
			Map<String, Long> ids = SourceIdNew.getUnuseId();
			logger.info("Scan Start" + ids);
			for (Entry<String, Long> id : ids.entrySet()) {
				String key = id.getKey();
				long value = id.getValue();
				if (System.currentTimeMillis() - value > 10 * 60 * 1000) {
					SourceIdNew.addId(key);
					SourceIdNew.removeUnUseId(key);
				}
			}
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
