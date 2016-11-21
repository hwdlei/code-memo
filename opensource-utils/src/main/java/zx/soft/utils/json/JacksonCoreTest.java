package zx.soft.utils.json;

import java.io.IOException;

import zx.soft.utils.json.JsonUtils.Foo;
import zx.soft.utils.json.User.Name;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonCoreTest {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static void main(String[] args) throws IOException {
		String a = "{\"gender\":\"MALE\",\"name\":{\"first\":\"Joe\",\"last\":\"Sixpack\"},\"userImage\":\"Um05dlltRnlJUT09\",\"verified\":false}";
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createParser(a);
		JsonToken token = jp.nextToken();
		JsonToken token1 = jp.nextToken();
		JsonToken token2 = jp.nextToken();
		JsonToken token3 = jp.nextToken();
		JsonToken token4 = jp.nextToken();
		JsonToken token5 = jp.nextToken();
		JsonToken token6 = jp.nextToken();
		JsonToken token7 = jp.nextToken();
		JsonToken token8 = jp.nextToken();
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String fieldname = jp.getCurrentName();
			jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
			if ("name".equals(fieldname)) { // contains an object
				Name name = new Name();
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String namefield = jp.getCurrentName();
					jp.nextToken(); // move to value
					if ("first".equals(namefield)) {
						name.setFirst(jp.getText());
					} else if ("last".equals(namefield)) {
						name.setLast(jp.getText());
					} else {
						throw new IllegalStateException("Unrecognized field '" + fieldname + "'!");
					}
				}
			} else if ("gender".equals(fieldname)) {
			} else if ("verified".equals(fieldname)) {
			} else if ("userImage".equals(fieldname)) {
			} else {
				throw new IllegalStateException("Unrecognized field '" + fieldname + "'!");
			}
		}
		jp.close(); // ensure resources get cleaned up timely and properly
	}

	public static void parse(String json) throws JsonParseException, JsonMappingException, IOException {
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createParser(json);
		// advance stream to START_ARRAY first:
		jp.nextToken();
		// and then each time, advance to opening START_OBJECT
		while (jp.nextToken() == JsonToken.START_OBJECT) {
			Foo foobar = OBJECT_MAPPER.readValue(jp, Foo.class);
			foobar.getFoo();
			// process
			// after binding, stream points to closing END_OBJECT
		}
	}

}
