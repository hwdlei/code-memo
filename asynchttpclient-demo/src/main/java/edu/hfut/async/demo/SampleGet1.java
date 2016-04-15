package edu.hfut.async.demo;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class SampleGet1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException,
			TimeoutException {
		//		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		//		Future<Response> f = asyncHttpClient.prepareGet("http://www.example.com/").execute();
		//		Response r = f.get();
		//		System.out.println(r.getResponseBody(Charset.forName("UTF-8")));
		//		asyncHttpClient.close();
		AsyncHttpClientSingleton singleton = AsyncHttpClientSingleton.getInstance();
		System.out.println(singleton.get("http://www.example.com/"));
		singleton.close();

	}

}
