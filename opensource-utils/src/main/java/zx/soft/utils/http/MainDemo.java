package zx.soft.utils.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class MainDemo {
	public static void main(String[] args) throws InterruptedException {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

		// URIs to perform GETs on
		String[] urisToGet = { "http://www.aol.com/", "http://www.baidu.com/", "www.slf4j.org/manual.html",
				"http://192.168.3.23:18081/nexus/" };

		// create a thread for each URI
		GetThread[] threads = new GetThread[urisToGet.length];
		for (int i = 0; i < threads.length; i++) {
			HttpGet httpget = new HttpGet(urisToGet[i]);
			threads[i] = new GetThread(httpClient, httpget);
		}

		// start the threads
		for (int j = 0; j < threads.length; j++) {
			threads[j].start();
		}

		// join the threads
		for (int j = 0; j < threads.length; j++) {
			threads[j].join();
		}

	}

	static class GetThread extends Thread {

		private final CloseableHttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;

		public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
			this.httpClient = httpClient;
			this.context = HttpClientContext.create();
			this.httpget = httpget;
		}

		@Override
		public void run() {
			try {
				CloseableHttpResponse response = httpClient.execute(httpget, context);
				try {
					HttpEntity entity = response.getEntity();
					System.out.println(EntityUtils.toString(entity));
				} finally {
					response.close();
				}
			} catch (ClientProtocolException ex) {
				// Handle protocol errors
			} catch (IOException ex) {
				// Handle I/O errors
			}
		}

	}

	public static class IdleConnectionMonitorThread extends Thread {

		private final HttpClientConnectionManager connMgr;
		private volatile boolean shutdown;

		public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}

		@Override
		public void run() {
			try {
				while (!shutdown) {
					synchronized (this) {
						wait(5000);
						// Close expired connections
						connMgr.closeExpiredConnections();
						// Optionally, close connections
						// that have been idle longer than 30 sec
						connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException ex) {
				// terminate
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}

	}
}
