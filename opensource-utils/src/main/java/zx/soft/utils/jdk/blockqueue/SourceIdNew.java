package zx.soft.utils.jdk.blockqueue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceIdNew {
	private static Logger logger = LoggerFactory.getLogger(SourceId.class);

	private static BlockingQueue<String> usefuls = new LinkedBlockingQueue<String>(3);
	private static HashMap<String, Long> unusefuls = new HashMap<String, Long>();
	private static Map<String, Integer> counts = new HashMap<String, Integer>();

	public static String getId() {
		synchronized (usefuls) {
			String id = null;
			try {
				id = usefuls.take();
				usefuls.offer(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
		}
	}

	public static void addId(String id) {
		synchronized (usefuls) {
			usefuls.offer(id);
		}
	}

	public static void removeId(String id) {
		synchronized (usefuls) {
			String tmp = null;
			while (!id.equals(tmp)) {
				if (tmp != null) {
					usefuls.offer(tmp);
				}
				try {
					tmp = usefuls.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Long> getUnuseId() {
		synchronized (unusefuls) {
			return (Map<String, Long>) unusefuls.clone();
		}
	}

	public static void addUnUseId(String id) {
		synchronized (unusefuls) {
			unusefuls.put(id, System.currentTimeMillis());

		}
	}

	public static void removeUnUseId(String id) {
		synchronized (unusefuls) {
			Iterator<String> iterator = usefuls.iterator();
			while (iterator.hasNext()) {
				String tmp = iterator.next();
				if (tmp.equals(id)) {
					iterator.remove();
				}
			}

		}
	}

	public static void oneCount(String id) {
		synchronized (counts) {
			if (counts.containsKey(id)) {
				counts.put(id, counts.get(id) + 1);
			} else {
				counts.put(id, 1);
			}
		}
	}

	public static int getCount(String id) {
		synchronized (counts) {
			return counts.get(id);
		}
	}

}
