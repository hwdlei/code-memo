package edu.hfut.async.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig.Builder;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

public class AsyncHttpClientSingleton {

	private AsyncHttpClient asyncHttpClient;

	private static final AsyncHttpClientSingleton singleton = new AsyncHttpClientSingleton();

	private AsyncHttpClientSingleton() {
		Builder builder = new DefaultAsyncHttpClientConfig.Builder();
		builder.setCompressionEnforced(true).setMaxConnections(60).setMaxConnectionsPerHost(10).setRequestTimeout(5000);
		this.asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
	}

	public static AsyncHttpClientSingleton getInstance() {
		return singleton;
	}

	public String get(String url, Map<String, String> queryParams) throws InterruptedException, ExecutionException,
	TimeoutException {
		RequestBuilder builder = new RequestBuilder("GET");
		builder.setUrl(url);
		if (queryParams != null) {
			for (Entry<String, String> params : queryParams.entrySet()) {
				builder.addQueryParam(params.getKey(), params.getValue());
			}
		}
		Future<String> re = this.asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandler<String>() {

			@Override
			public String onCompleted(Response response) throws Exception {
				return response.getResponseBody(Charset.forName("UTF-8"));
			}
		});
		return re.get(5, TimeUnit.SECONDS);
	}

	public String get(String url) throws InterruptedException, ExecutionException, TimeoutException {
		return get(url, null);
	}

	public void close() throws IOException {
		if (!this.asyncHttpClient.isClosed()) {
			this.asyncHttpClient.close();
		}
	}

}
