package edu.hfut.parquet.avro;

import static edu.hfut.parquet.avro.utils.AvroTestUtil.optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericFixed;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.util.Utf8;
import org.apache.commons.compress.utils.Charsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

public class UserTest {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { false }, // use the new converters
				{ true } }; // use the old converters
		return Arrays.asList(data);
	}

	private boolean compat;
	private final Configuration testConf = new Configuration();

	@Before
	public void beforeTest() {
		this.compat = compat;
		this.testConf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, compat);
		testConf.setBoolean("parquet.avro.add-list-element-records", false);
		testConf.setBoolean("parquet.avro.write-old-list-structure", false);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyArray() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("array.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		// Write a record with an empty array.
		List<Integer> emptyArray = new ArrayList<Integer>();
		GenericData.Record record = new GenericRecordBuilder(schema).set("myarray", emptyArray).build();
		writer.write(record);
		writer.close();

		ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord> builder(file).withConf(testConf).build();
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(emptyArray, nextRecord.get("myarray"));
	}

	@Test
	public void testEmptyMap() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("map.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		// Write a record with an empty map.
		ImmutableMap<String,Integer> emptyMap = new ImmutableMap.Builder<String, Integer>().build();
		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap", emptyMap).build();
		writer.write(record);
		writer.close();

		ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(file).withConf(testConf).build();
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(emptyMap, nextRecord.get("mymap"));
	}

	@Test
	public void testMapWithNulls() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("map_with_nulls.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		// Write a record with a null value
		Map<CharSequence, Integer> map = new HashMap<CharSequence, Integer>();
		map.put(str("thirty-four"), 34);
		map.put(str("eleventy-one"), null);
		map.put(str("one-hundred"), 100);

		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap", map).build();
		writer.write(record);
		writer.close();

		ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(file).withConf(testConf).build();
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(map, nextRecord.get("mymap"));
	}

	@Test(expected = RuntimeException.class)
	public void testMapRequiredValueWithNull() throws Exception {
		Schema schema = Schema.createRecord("record1", null, null, false);
		schema.setFields(Lists.newArrayList(new Schema.Field("mymap", Schema.createMap(Schema.create(Schema.Type.INT)),
				null, null)));

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		// Write a record with a null value
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("thirty-four", 34);
		map.put("eleventy-one", null);
		map.put("one-hundred", 100);

		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap", map).build();
		writer.write(record);
	}

	@Test
	public void testMapWithUtf8Key() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("map.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		// Write a record with a map with Utf8 keys.
		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap",
				ImmutableMap.of(new Utf8("a"), 1, new Utf8("b"), 2)).build();
		writer.write(record);
		writer.close();

		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<GenericRecord>(testConf, file);
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(ImmutableMap.of(str("a"), 1, str("b"), 2), nextRecord.get("mymap"));
	}

	@Test
	public void testAll() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("all.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord> builder(file).withSchema(schema)
				.withConf(testConf).build();

		GenericData.Record nestedRecord = new GenericRecordBuilder(schema.getField("mynestedrecord").schema()).set(
				"mynestedint", 1).build();

		List<Integer> integerArray = Arrays.asList(1, 2, 3);
		GenericData.Array<Integer> genericIntegerArray = new GenericData.Array<Integer>(Schema.createArray(Schema
				.create(Schema.Type.INT)), integerArray);

		GenericFixed genericFixed = new GenericData.Fixed(Schema.createFixed("fixed", null, null, 1),
				new byte[] { (byte) 65 });

		List<Integer> emptyArray = new ArrayList<Integer>();
		ImmutableMap<String,Integer> emptyMap = new ImmutableMap.Builder<String, Integer>().build();

		Schema arrayOfOptionalIntegers = Schema.createArray(optional(Schema.create(Schema.Type.INT)));
		GenericData.Array<Integer> genericIntegerArrayWithNulls = new GenericData.Array<Integer>(
				arrayOfOptionalIntegers, Arrays.asList(1, null, 2, null, 3));

		GenericData.Record record = new GenericRecordBuilder(schema).set("mynull", null).set("myboolean", true)
				.set("myint", 1).set("mylong", 2L).set("myfloat", 3.1f).set("mydouble", 4.1)
				.set("mybytes", ByteBuffer.wrap("hello".getBytes(Charsets.UTF_8))).set("mystring", "hello")
				.set("mynestedrecord", nestedRecord).set("myenum", "a").set("myarray", genericIntegerArray)
				.set("myemptyarray", emptyArray).set("myoptionalarray", genericIntegerArray)
				.set("myarrayofoptional", genericIntegerArrayWithNulls).set("mymap", ImmutableMap.of("a", 1, "b", 2))
				.set("myemptymap", emptyMap).set("myfixed", genericFixed).build();

		writer.write(record);
		writer.close();

		ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(file).withConf(testConf).build();
		GenericRecord nextRecord = reader.read();

		Object expectedEnumSymbol = compat ? "a" : new GenericData.EnumSymbol(schema.getField("myenum").schema(), "a");

		assertNotNull(nextRecord);
		assertEquals(null, nextRecord.get("mynull"));
		assertEquals(true, nextRecord.get("myboolean"));
		assertEquals(1, nextRecord.get("myint"));
		assertEquals(2L, nextRecord.get("mylong"));
		assertEquals(3.1f, nextRecord.get("myfloat"));
		assertEquals(4.1, nextRecord.get("mydouble"));
		assertEquals(ByteBuffer.wrap("hello".getBytes(Charsets.UTF_8)), nextRecord.get("mybytes"));
		assertEquals(str("hello"), nextRecord.get("mystring"));
		assertEquals(expectedEnumSymbol, nextRecord.get("myenum"));
		assertEquals(nestedRecord, nextRecord.get("mynestedrecord"));
		assertEquals(integerArray, nextRecord.get("myarray"));
		assertEquals(emptyArray, nextRecord.get("myemptyarray"));
		assertEquals(integerArray, nextRecord.get("myoptionalarray"));
		assertEquals(genericIntegerArrayWithNulls, nextRecord.get("myarrayofoptional"));
		assertEquals(ImmutableMap.of(str("a"), 1, str("b"), 2), nextRecord.get("mymap"));
		assertEquals(emptyMap, nextRecord.get("myemptymap"));
		assertEquals(genericFixed, nextRecord.get("myfixed"));
	}

	/**
	 * Return a String or Utf8 depending on whether compatibility is on
	 */
	public CharSequence str(String value) {
		return compat ? value : new Utf8(value);
	}
}
