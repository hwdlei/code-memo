package example.avro;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class Bytes2FileTest {

	@Test
	public void test() throws IOException {
		Schema scheme = Schema.create(Schema.Type.BYTES);
		ByteBuffer a1 = ByteBuffer.wrap("asd".getBytes());
		ByteBuffer b1 = ByteBuffer.wrap("bsd".getBytes());

		// Serialize user1, user2 and user3 to disk
		DatumWriter<ByteBuffer> userDatumWriter = new SpecificDatumWriter<ByteBuffer>(ByteBuffer.class);
		DataFileWriter<ByteBuffer> dataFileWriter = new DataFileWriter<ByteBuffer>(userDatumWriter);
		dataFileWriter.create(scheme, new File("users.avro"));
		dataFileWriter.append(a1);
		dataFileWriter.append(b1);
		dataFileWriter.close();

		// Deserialize Users from disk
		DatumReader<ByteBuffer> userDatumReader = new SpecificDatumReader<ByteBuffer>(ByteBuffer.class);
		DataFileReader<ByteBuffer> dataFileReader = new DataFileReader<ByteBuffer>(new File(
				"test_strbbb_hdfs+0+0000000000+0000000002.avro"),
				userDatumReader);
		ByteBuffer user = null;
		while (dataFileReader.hasNext()) {
			// Reuse user object by passing it to next(). This saves us from
			// allocating and garbage collecting many objects for files with
			// many items.
			user = dataFileReader.next(user);
			System.out.println(new String(user.array()));
		}
		dataFileReader.close();
	}

	//	@Test
	public void testGenericRecord() throws IOException {
		Schema scheme = Schema.create(Schema.Type.STRING);
		String a1 = "asd";
		String b1 = "bsd";
		// Serialize user1, user2 and user3 to disk
		DatumWriter<String> userDatumWriter = new SpecificDatumWriter<String>(String.class);
		DataFileWriter<String> dataFileWriter = new DataFileWriter<String>(userDatumWriter);
		dataFileWriter.create(scheme, new File("str.avro"));
		dataFileWriter.append(a1);
		dataFileWriter.append(b1);
		dataFileWriter.close();

		// Deserialize Users from disk
		DatumReader<CharSequence> userDatumReader = new SpecificDatumReader<CharSequence>(CharSequence.class);
		DataFileReader<CharSequence> dataFileReader = new DataFileReader<CharSequence>(new File(
				"test_strmmm_hdfs+0+0000000000+0000000002.avro"),
				userDatumReader);
		CharSequence user = null;
		while (dataFileReader.hasNext()) {
			// Reuse user object by passing it to next(). This saves us from
			// allocating and garbage collecting many objects for files with
			// many items.
			user = dataFileReader.next(user);
			System.out.println(user);
		}
		dataFileReader.close();
	}

}
