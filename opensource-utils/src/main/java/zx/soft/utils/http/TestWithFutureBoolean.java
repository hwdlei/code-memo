package zx.soft.utils.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;

public class TestWithFutureBoolean {

	public static void main(final String[] args) throws Exception {

		System.out.println("-------------------------------------------------------------------");
		System.out.println("java.version: " + System.getProperty("java.version"));
		System.out.println("java.vendor.url: " + System.getProperty("java.vendor.url"));
		System.out.println("java.vendor: " + System.getProperty("java.vendor"));
		System.out.println("java.home: " + System.getProperty("java.home"));
		System.out.println("os.arch: " + System.getProperty("os.arch"));
		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("os.version: " + System.getProperty("os.version"));
		System.out.println("-------------------------------------------------------------------");

		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 10; i++) {
				Future<Boolean> future = httpclient.execute(HttpAsyncMethods.createGet("http://www.baidu.com/"),
						new MyResponseConsumer(), null);
				Boolean result = future.get();
				System.out.println("result: " + result);
				if (!result) {
					System.out.println("Request failed");
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("Total time (sec):" + ((double) (end - start)) / 1000);
		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}

	static class MyResponseConsumer extends AsyncByteConsumer<Boolean> {

		@Override
		protected void onResponseReceived(final HttpResponse response) {
		}

		@Override
		protected void releaseResources() {
		}

		@Override
		protected Boolean buildResult(final HttpContext context) {
			return Boolean.TRUE;
		}

		@Override
		protected void onByteReceived(ByteBuffer arg0, IOControl arg1) throws IOException {
			// TODO Auto-generated method stub

		}

	}

}