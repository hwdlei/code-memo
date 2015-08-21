package zx.soft.utils.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonDemo {
	public static void main(String[] args) {
		BuilderFluentObjectToJson flent = new BuilderFluentObjectToJson.Builder().withName("123").withAge(10).build();
		System.out.println(flent);
		try {
			System.out.println(JsonUtils.getString(flent));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(JsonUtils.getObject("{\"name\":\"123\",\"age\":10}", BuilderFluentObjectToJson.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
