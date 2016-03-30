package example.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

/**
 * AvroTest   AVRO不适合
 * @author donglei
 * @date: 2016年3月14日 下午3:53:48
 */
public class Bytes2AvroDataTest {

	private static final Schema SCHEMA = Schema.create(Schema.Type.BYTES);

	public static void main(String[] args) throws IOException {
		byte[] origin = "123".getBytes();
		byte[] data = bytesToAvro(origin, SCHEMA);

		byte[] originData = avroToBytes(data, SCHEMA);

		System.out.println();
	}

	/**
	 * Convert JSON to avro binary array.
	 *
	 * @param data
	 * @param schema
	 * @return
	 * @throws IOException
	 */
	public static byte[] bytesToAvro(byte[] data, Schema schema) throws IOException {
		GenericDatumWriter<Object> writer = new GenericDatumWriter<>(schema);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Encoder encoder = EncoderFactory.get().directBinaryEncoder(output, null);
		writer.write(ByteBuffer.wrap(data), encoder);
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
	public static byte[] avroToBytes(byte[] avro, Schema schema) throws IOException {
		GenericDatumReader<Object> reader = new GenericDatumReader<>(schema);
		Decoder decoder = DecoderFactory.get().binaryDecoder(avro, null);
		Object datum = reader.read(null, decoder);
		return ((ByteBuffer) datum).array();
	}
}