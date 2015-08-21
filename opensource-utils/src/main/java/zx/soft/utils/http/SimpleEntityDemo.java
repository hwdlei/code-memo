package zx.soft.utils.http;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class SimpleEntityDemo {
	public static void main(String[] args) {
		StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));

		System.out.println(myEntity.getContentType());
		System.out.println(myEntity.getContentLength());
		try {
			System.out.println(EntityUtils.toString(myEntity));
			System.out.println(EntityUtils.toByteArray(myEntity).length);
			System.out.println(myEntity.getContentEncoding());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
