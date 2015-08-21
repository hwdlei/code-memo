package zx.soft.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SimplePostDemo {

	public static void main(String[] args) {
		StringEntity entity = new StringEntity(
				"\"范冰冰，张丰毅，张钧甯，张庭，张馨予太子李弘一心思慕媚娘的外甥女敏月，" + "但敏月却表示自己爱的人是雉奴。太子师计划挑拨李弘和武后的关系，"
						+ "于是他命太子妃将义阳和宣城未死的消息告知李弘。李弘借义阳和宣城未死一事弹劾媚娘，" + "得知此事的媚娘将李弘禁足于东宫之\"",
				ContentType.create("application/json", "UTF-8"));
		entity.setChunked(true);
		HttpPost httppost = new HttpPost("http://192.168.32.16:10000/origin/keywords");
		httppost.setEntity(entity);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream instream = null;
		try {
			try {
				response = httpclient.execute(httppost);
				HttpEntity res = response.getEntity();
				if (res != null) {
					instream = res.getContent();
					ByteArrayOutputStream writer = new ByteArrayOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = instream.read(bytes)) > 0) {
						writer.write(bytes, 0, len);
					}
					System.out.println(writer.toString());
				}
			} finally {
				// attempt to keep the underlying connection alive by consuming the entity content
				instream.close();
				//  immediately shuts down and discards the connection.
				response.close();
				httpclient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
