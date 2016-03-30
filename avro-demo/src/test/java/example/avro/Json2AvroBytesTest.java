package example.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;

/**
 * AvroTest   AVRO不适合
 * @author donglei
 * @date: 2016年3月14日 下午3:53:48
 */
public class Json2AvroBytesTest {

	private static final String JSON = "{" + "\"name\":\"Frank\"," + "\"age\":47" + "}";
	private static final Schema SCHEMA = new Schema.Parser()
	.parse("{ \"type\":\"record\", \"namespace\":\"foo\", \"name\":\"Person\", \"fields\":[ { \"name\":\"name\", \"type\":\"string\" }, { \"name\":\"age\", \"type\":\"int\" } ] }");

	public static void main(String[] args) throws IOException {
		byte[] data = jsonToAvro(JSON, SCHEMA);

		String jsonString = avroToJson(data, SCHEMA);
		System.out.println(jsonString);
	}

	/**
	 * Convert JSON to avro binary array.
	 *
	 * @param json
	 * @param schema
	 * @return
	 * @throws IOException
	 */
	public static byte[] jsonToAvro(String json, Schema schema) throws IOException {
		DatumReader<Object> reader = new GenericDatumReader<>(schema);
		GenericDatumWriter<Object> writer = new GenericDatumWriter<>(schema);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Decoder decoder = DecoderFactory.get().jsonDecoder(schema, json);
		Encoder encoder = EncoderFactory.get().binaryEncoder(output, null);
		Object datum = reader.read(null, decoder);
		writer.write(datum, encoder);
		encoder.flush();
		return output.toByteArray();
	}

	/**
	 * Convert Avro binary byte array back to JSON String.
	 *
	 * @param avro
	 * @param schema
	 * @return
	 * @throws IOException
	 */
	public static String avroToJson(byte[] avro, Schema schema) throws IOException {
		boolean pretty = false;
		GenericDatumReader<Object> reader = new GenericDatumReader<>(schema);
		DatumWriter<Object> writer = new GenericDatumWriter<>(schema);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JsonEncoder encoder = EncoderFactory.get().jsonEncoder(schema, output, pretty);
		Decoder decoder = DecoderFactory.get().binaryDecoder(avro, null);
		Object datum = reader.read(null, decoder);
		writer.write(datum, encoder);
		encoder.flush();
		output.flush();
		return new String(output.toByteArray(), "UTF-8");
	}
}