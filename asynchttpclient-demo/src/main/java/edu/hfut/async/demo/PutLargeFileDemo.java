package edu.hfut.async.demo;

import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.request.body.Body;
import org.asynchttpclient.request.body.generator.BodyGenerator;

/**
 *  zero copy in memory concept
 * @author donglei
 * @date: 2016年4月15日 下午4:50:50
 */
public class PutLargeFileDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		RequestBuilder builder = new RequestBuilder("PUT");
		Request request = builder.setUrl("http://")
				.addHeader("name", "value")
				//	     .setBody(new File("myUpload.avi"))
				.setBody(new BodyGenerator() {

					@Override
					public Body createBody() {
						return null;
					}
				})
				.build();
		AsyncHttpClient client = new DefaultAsyncHttpClient();
		client.executeRequest(request).get();
	}

}
