package edu.hfut.async.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

public class SampleGet1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		Future<Response> f = asyncHttpClient.prepareGet("http://www.example.com/").execute();
		Response r = f.get();
		System.out.println(r.getResponseBody(Charset.forName("UTF-8")));
		asyncHttpClient.close();
	}

}
