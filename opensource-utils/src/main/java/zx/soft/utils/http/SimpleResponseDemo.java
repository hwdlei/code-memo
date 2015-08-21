package zx.soft.utils.http;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpResponse;

public class SimpleResponseDemo {
	public static void main(String[] args) {

		HttpResponse response1 = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		System.out.println(response1.getProtocolVersion());
		System.out.println(response1.getStatusLine().getStatusCode());
		System.out.println(response1.getStatusLine().getReasonPhrase());
		System.out.println(response1.getStatusLine().toString());

		HttpResponse response2 = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response2.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response2.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
		Header h1 = response2.getFirstHeader("Set-Cookie");
		System.out.println(h1);
		Header h2 = response2.getLastHeader("Set-Cookie");
		System.out.println(h2);
		Header[] hs = response2.getHeaders("Set-Cookie");
		System.out.println(hs.length);


		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

		HeaderIterator it = response.headerIterator("Set-Cookie");

		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
