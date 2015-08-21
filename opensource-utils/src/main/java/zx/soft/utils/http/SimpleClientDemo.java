package zx.soft.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SimpleClientDemo {
	public static void main(String[] args) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("https://www.baidu.com/s?wd=12306");
		try {
			URI uri = new URIBuilder().setScheme("https").setHost("www.baidu.com").setPath("/s")
					.setParameter("wd", "12306").build();
			httpget = new HttpGet(uri);
			System.out.println(httpget.getURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		CloseableHttpResponse response = null;
		InputStream instream = null;
		try {
			try {
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					instream = entity.getContent();
					ByteArrayOutputStream writer = new ByteArrayOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = instream.read(bytes)) > 0) {
						writer.write(bytes, 0, len);
					}
					System.out.println(writer.toString());
					//					long len = entity.getContentLength();
					//					if (len != -1 && len < 2048) {
					//						System.out.println(EntityUtils.toString(entity));
					//					} else {
					//						// Stream content out
					//					}
				}
			} finally {
				// attempt to keep the underlying connection alive by consuming the entity content
				instream.close();
				//  immediately shuts down and discards the connection.
				response.close();
				//  the connection must be shut down
				httpclient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
