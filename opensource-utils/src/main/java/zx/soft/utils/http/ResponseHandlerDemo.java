package zx.soft.utils.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseHandlerDemo {
	public static void main(String[] args) {
		StringEntity entity = new StringEntity("范冰冰，张丰毅，张钧甯，张庭，张馨予太子李弘一心思慕媚娘的外甥女敏月，"
				+ "但敏月却表示自己爱的人是雉奴。太子师计划挑拨李弘和武后的关系，" + "于是他命太子妃将义阳和宣城未死的消息告知李弘。李弘借义阳和宣城未死一事弹劾媚娘，" + "得知此事的媚娘将李弘禁足于东宫之",
				ContentType.create("application/json", "UTF-8"));
		entity.setChunked(true);
		HttpPost httppost = new HttpPost("http://192.168.32.16:10000/origin/keywords");
		httppost.setEntity(entity);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ResponseHandler<List<String>> rh = new ResponseHandler<List<String>>() {

			@Override
			public List<String> handleResponse(final HttpResponse response) throws IOException {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException("Response contains no content");
				}
				Gson gson = new GsonBuilder().create();
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(entity.getContent(), charset);
				return gson.fromJson(reader, List.class);
			}
		};
		try {
			List<String> keys = httpclient.execute(httppost, rh);
			System.out.println(keys);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
