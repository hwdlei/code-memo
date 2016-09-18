package edu.hfut.async.demo;

import io.netty.handler.codec.http.HttpHeaders;

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

/**
 * 异步httpclient单例实现
 * @author donglei
 * @date: 2016年4月17日 下午12:16:36
 */
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

	/**
	 * GET 请求
	 * @param url 请求的URL地址
	 * @param queryParams URL请求参数
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
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

	/**
	 * 请求URL
	 * @param url
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	public String get(String url) throws InterruptedException, ExecutionException, TimeoutException {
		return get(url, null);
	}

	/**
	 * asynchttpclient 后台使用线程池维护启动的线程，所以推出时需要关闭
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (!this.asyncHttpClient.isClosed()) {
			this.asyncHttpClient.close();
		}
	}

	/**
	 * post请求
	 * @param url  请求URL地址
	 * @param headers  请求头
	 * @param data   数据块
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	public String post(String url, HttpHeaders headers, String data) throws InterruptedException, ExecutionException,
	TimeoutException {
		RequestBuilder builder = new RequestBuilder("POST");
		builder.setUrl(url);
		builder.setHeaders(headers);
		builder.setBody(new String(data.getBytes(), Charset.forName("UTF-8")));
		Future<String> re = this.asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandler<String>() {

			@Override
			public String onCompleted(Response response) throws Exception {
				return response.getResponseBody(Charset.forName("UTF-8"));
			}
		});
		return re.get(5, TimeUnit.SECONDS);
	}

	/**
	 * post请求
	 * @param url  请求URL
	 * @param headers  请求头
	 * @param data  数据块
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	public String post(String url, Map<String, String> headers, String data) throws InterruptedException,
	ExecutionException, TimeoutException {
		RequestBuilder builder = new RequestBuilder("POST");
		builder.setUrl(url);
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		builder.setBody(data);
		Future<String> re = this.asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandler<String>() {

			@Override
			public String onCompleted(Response response) throws Exception {
				return response.getResponseBody(Charset.forName("UTF-8"));
			}
		});
		return re.get(5, TimeUnit.SECONDS);
	}

}
