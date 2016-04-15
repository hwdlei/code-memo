package edu.hfut.async.demo;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

/**
 *
 * @author donglei
 * @date: 2016年4月15日 下午4:29:51
 */
public class RequestBuilderDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		RequestBuilder builder = new RequestBuilder("GET");
		// http://192.168.32.16:8912/insights?trueUser=34232a86c69edcf1e86f3caefcbed9d6
		Request request = builder.setUrl("http://192.168.32.16:8912/insights")
				.addQueryParam("trueUser", "34232a86c69edcf1e86f3caefcbed9d6")
				.build();
		AsyncHttpClient client = new DefaultAsyncHttpClient();
		Response re = client.executeRequest(request).get();
		System.out.println(re.getResponseBody(Charset.forName("UTF-8")));

	}
}
