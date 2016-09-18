package edu.hfut.async.demo;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

public class SampleGet2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException,
	IOException {
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		for (int i = 0; i < 100; i++) {
			Future<Integer> f = asyncHttpClient.prepareGet("http://192.168.32.17:5901/firstpage/0/2016-04-11,13")
					.execute(new AsyncCompletionHandler<Integer>() {

						@Override
						public Integer onCompleted(Response response) throws Exception {
							// Do something with the Response
							System.out.println(response.getStatusCode());
							return response.getStatusCode();
						}

						@Override
						public void onThrowable(Throwable t) {
							// Something wrong happened.
						}
					});
		}

		//		int bodyResponse = f.get(5, TimeUnit.SECONDS);
		//		System.out.println(bodyResponse);
		//		asyncHttpClient.close();
	}

}
