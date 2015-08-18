package zx.soft.utils.http;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Test2 {
	public static void main(String[] args) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.baidu.com/");
		CloseableHttpResponse response = null;
		try {
			try {
				response = httpclient.execute(httpget);
				System.err.println(response.getEntity());
			} finally {
				httpclient.close();
				response.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
