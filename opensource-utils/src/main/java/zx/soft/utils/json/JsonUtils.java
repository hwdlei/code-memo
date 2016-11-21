package zx.soft.utils.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import zx.soft.utils.json.User.Gender;
import zx.soft.utils.json.User.Name;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {

	/* Object mapper for serializing and deserializing JSON strings. */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		User u = createUser();
		//		System.out.println(new String(u.getUserImage()));
		System.out.println(getString(u));
		//		User uT = getObject(
		//				"{\"gender\":\"MALE\",\"name\":{\"first\":\"Joe\"},\"userImage\":\"Um05dlltRnlJUT09\",\"verified\":false}",
		//				User.class);
		//		System.out.println(uT);
		//		System.out.println(new String(uT.getUserImage()));
		//		Map<String, Object> userData = OBJECT_MAPPER.readValue(getString(u), Map.class);
		//		User test = new User();
		//		test.setGender(Gender.getG((String) userData.get("gender")));
		//		System.out.println(test.getGender());
		//
		//		treeJson(getString(u));
		//
		//		createJsonNode();
		//
		//		streamApi();
		//
		parseStreamApi(getString(u));
		//
		String json = "[{\"foo\": \"bar\"},{\"foo\": \"biz\"}]";
		parse(json);
		//		jsonTest();

	}

	public static class Foo {

		private String foo;

		public String getFoo() {
			return foo;
		}

		public void setFoo(String foo) {
			this.foo = foo;
		}

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

	public static void streamApi() throws IOException {
		JsonFactory f = new JsonFactory();
		StringWriter sw = new StringWriter();
		JsonGenerator g = f.createGenerator(sw);

		g.writeStartObject();
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		g.writeEndObject(); // for field 'name'
		g.writeStringField("gender", Gender.MALE.toString());
		g.writeBooleanField("verified", false);
		g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
		byte[] binaryData = "123".getBytes();
		g.writeBinary(binaryData);
		g.writeEndObject();
		g.close(); // important: will force flushing of output, close underlying output stream
		System.out.println(sw.toString());
	}

	public static void parseStreamApi(String content) throws JsonParseException, IOException {
		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createParser(content);
		User user = new User();
		JsonToken token = jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
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
				user.setName(name);
			} else if ("gender".equals(fieldname)) {
				user.setGender(User.Gender.valueOf(jp.getText()));
			} else if ("verified".equals(fieldname)) {
				user.setVerified(jp.getCurrentToken() == JsonToken.VALUE_TRUE);
			} else if ("userImage".equals(fieldname)) {
				user.setUserImage(jp.getBinaryValue());
			} else {
				throw new IllegalStateException("Unrecognized field '" + fieldname + "'!");
			}
		}
		jp.close(); // ensure resources get cleaned up timely and properly
	}

	public static void createJsonNode() throws JsonProcessingException {
		ObjectNode userOb = OBJECT_MAPPER.createObjectNode();
		ObjectNode nameOb = userOb.putObject("name");
		nameOb.put("first", "Joe");
		nameOb.put("last", "Sixpack");
		userOb.put("gender", User.Gender.MALE.toString());
		userOb.put("verified", false);
		byte[] imageData = "123".getBytes(); // or wherever it comes from
		userOb.put("userImage", imageData);
		System.out.println(getString(userOb));
	}

	public static void treeJson(String tree) throws JsonProcessingException, IOException {
		ObjectMapper m = new ObjectMapper();
		// can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
		JsonNode rootNode = m.readTree(tree);
		Iterator<String> ite = rootNode.fieldNames();
		while (ite.hasNext()) {
			System.out.println(ite.next());
		}
		JsonNode nameNode = rootNode.path("name");
		String lastName = nameNode.path("last").asText();
		if ("xmler".equalsIgnoreCase(lastName)) {
			((ObjectNode) nameNode).put("last", "Jsoner");
		}
		// and write it out:
		System.out.println(m.writeValueAsString(rootNode));
	}

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static void jsonTest() throws IOException {
		/* Serialize a JacksonBeanExample object to a JSON-formatted string. */
		JacksonBeanExample jacksonBeanExample = new JacksonBeanExample();
		jacksonBeanExample.setIntValue(10);
		String serialization = OBJECT_MAPPER.writeValueAsString(jacksonBeanExample);
		System.out.println(serialization);
		/* Deserialize a JSON-formatted string to a JacksonBeanExample object. */
		ObjectReader objectReader = OBJECT_MAPPER.reader(JacksonBeanExample.class);
		JacksonBeanExample jacksonBean = objectReader.readValue("{\"value\":10}");
		System.out.println(jacksonBean.getIntValue());
	}

	public static String getString(Object o) throws JsonProcessingException {
		return OBJECT_MAPPER.writeValueAsString(o);
	}

	public static <T> T getObject(String s, Class<T> t) throws JsonProcessingException, IOException {
		ObjectReader objectReader = OBJECT_MAPPER.reader(t);
		return objectReader.readValue(s);
	}

	public static User createUser() throws UnsupportedEncodingException {
		User u = new User();
		Name n = new Name();
		n.setFirst("Joe");
		n.setLast("Sixpack");
		u.setGender(Gender.MALE);
		u.setName(n);
		u.setVerified(false);
		u.setUserImage("Rm9vYmFyIQ==".getBytes("UTF-8"));
		return u;
	}

}
