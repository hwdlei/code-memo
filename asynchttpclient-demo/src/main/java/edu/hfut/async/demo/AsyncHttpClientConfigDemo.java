package edu.hfut.async.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig.Builder;
import org.asynchttpclient.Response;

public class AsyncHttpClientConfigDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		Builder builder = new DefaultAsyncHttpClientConfig.Builder();
		builder.setCompressionEnforced(true).setMaxConnections(10).setRequestTimeout(20000);
		AsyncHttpClient client = new DefaultAsyncHttpClient(builder.build());
		Response re = client.prepareGet("http://www.example.com/").execute().get();
		System.out.println(re.getResponseBody(Charset.forName("UTF-8")));
		client.close();
	}

}
