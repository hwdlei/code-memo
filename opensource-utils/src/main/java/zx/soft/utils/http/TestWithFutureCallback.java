package zx.soft.utils.http;

import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.BasicHttpContext;

public class TestWithFutureCallback {

	public static void main(String args[]) {

		System.out.println("-------------------------------------------------------------------");
		System.out.println("java.version: " + System.getProperty("java.version"));
		System.out.println("java.vendor.url: " + System.getProperty("java.vendor.url"));
		System.out.println("java.vendor: " + System.getProperty("java.vendor"));
		System.out.println("java.home: " + System.getProperty("java.home"));
		System.out.println("os.arch: " + System.getProperty("os.arch"));
		System.out.println("os.name: " + System.getProperty("os.name"));
		System.out.println("os.version: " + System.getProperty("os.version"));
		System.out.println("-------------------------------------------------------------------");

		CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
		client.start();

		try {
			client.execute(new HttpGet("http://www.baidu.com"), new BasicHttpContext(),
					new FutureCallback<HttpResponse>() {

						@Override
						public void failed(Exception ex) {
							System.out.println("failed");
						}

						@Override
						public void completed(HttpResponse result) {
							System.out.println("Result: " + result);

						}

						@Override
						public void cancelled() {
							System.out.println("cancelled");
						}
					}).get();

			client.execute(new HttpGet("http://www.apple.com"), new BasicHttpContext(),
					new FutureCallback<HttpResponse>() {

						@Override
						public void failed(Exception ex) {
							System.out.println("failed");
						}

						@Override
						public void completed(HttpResponse result) {
							System.out.println("Result: " + result);

						}

						@Override
						public void cancelled() {
							System.out.println("cancelled");
						}
					}).get();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			/*
			 * This should execute 5 requests asynchronously and receive responses in the FutureCallback handler.
			 *
			 * If the `f.get()` line is invoked, only the first will work, no further requests will execute
			 * (or sometimes if I leave it sitting for minutes I see a second request go through).
			 *
			 * If the `f.get()` line is commented out, then all 5 correctly execute and return.
			 *
			 * It is expected that if someone calls `f.get()` in this loop it should cause the requests
			 * to happen sequentially, but they should still work.
			 */
			for (int i = 0; i < 5; i++) {
				System.out.println("execute request");
				Future<HttpResponse> f = client.execute(new HttpGet("http://www.baidu.com"), new BasicHttpContext(),
						new FutureCallback<HttpResponse>() {

							@Override
							public void failed(Exception ex) {
								System.out.println("failed");
							}

							@Override
							public void completed(HttpResponse result) {
								System.out.println("Result: " + result);

							}

							@Override
							public void cancelled() {
								System.out.println("cancelled");
							}
						});
				try {
					f.get();// commenting out this line makes the 5 requests succeed
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("after f.get()");

			}

			// wait for all async tasks to complete (when f.get is commented out)

		} finally {
			try {
				Thread.sleep(2000);
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}