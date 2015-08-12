package zx.soft.utils.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(MyRunnable.class);

	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = SourceIdNew.getId();
		SourceIdNew.oneCount(id);
		int count = SourceIdNew.getCount(id);
		logger.info("得到sourceID：" + id + " ; count:" + count);
		if (count > 10000) {
			SourceIdNew.removeId(id);
			SourceIdNew.addUnUseId(id);
		}

	}

}
