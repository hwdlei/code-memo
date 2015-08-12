package zx.soft.utils.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceId {
	private static Logger logger = LoggerFactory.getLogger(SourceId.class);
	private static List<String> usefuls = new LinkedList<String>();
	private static HashMap<String, Long> unusefuls = new HashMap<String, Long>();
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	private static Map<String, Integer> counts = new HashMap<String, Integer>();

	public static String getId() {
		synchronized (lock1) {
			while (usefuls.isEmpty()) {
				logger.info("usefuls is empty");
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String id = usefuls.remove(0);
			usefuls.add(id);
			return id;
		}
	}

	public static void addId(String id) {
		synchronized (lock1) {

			usefuls.add(id);
		}
	}

	public static void removeId(String id) {
		synchronized (lock1) {
			Iterator<String> iterator = usefuls.iterator();
			while (iterator.hasNext()) {
				String tmp = iterator.next();
				if (tmp.equals(id)) {
					iterator.remove();
				}
			}

		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Long> getUnuseId() {
		synchronized (lock2) {
			return (Map<String, Long>) unusefuls.clone();
		}
	}

	public static void addUnUseId(String id) {
		synchronized (lock2) {
			unusefuls.put(id, System.currentTimeMillis());

		}
	}

	public static void removeUnUseId(String id) {
		synchronized (lock2) {
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
