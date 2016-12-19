/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.hfut.parquet.avro.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

@RunWith(Parameterized.class)
public class TestReadWrite {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { false }, // use the new converters
						{ true } }; // use the old converters
		return Arrays.asList(data);
	}

	private final boolean compat;
	private final Configuration testConf = new Configuration();

	public TestReadWrite(boolean compat) {
		this.compat = compat;
		this.testConf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, compat);
		testConf.setBoolean("parquet.avro.add-list-element-records", false);
		testConf.setBoolean("parquet.avro.write-old-list-structure", false);
	}

	@Test
	public void testEmptyArray() throws Exception {
		Schema schema = new Schema.Parser().parse(Resources.getResource("array.avsc").openStream());

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file).withSchema(schema)
						.withConf(testConf).build();

		// Write a record with an empty array.
		List<Integer> emptyArray = new ArrayList<>();
		GenericData.Record record = new GenericRecordBuilder(schema).set("myarray", emptyArray).build();
		writer.write(record);
		writer.close();

		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(testConf, file);
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

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file).withSchema(schema)
						.withConf(testConf).build();

		// Write a record with an empty map.
		ImmutableMap emptyMap = new ImmutableMap.Builder<String, Integer>().build();
		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap", emptyMap).build();
		writer.write(record);
		writer.close();

		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(testConf, file);
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

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file).withSchema(schema)
						.withConf(testConf).build();

		// Write a record with a null value
		Map<CharSequence, Integer> map = new HashMap<>();
		map.put(str("thirty-four"), 34);
		map.put(str("eleventy-one"), null);
		map.put(str("one-hundred"), 100);

		GenericData.Record record = new GenericRecordBuilder(schema).set("mymap", map).build();
		writer.write(record);
		writer.close();

		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(testConf, file);
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(map, nextRecord.get("mymap"));
	}

	@Test(expected = RuntimeException.class)
	public void testMapRequiredValueWithNull() throws Exception {
		Schema schema = Schema.createRecord("record1", null, null, false);
		schema.setFields(Lists.newArrayList(
						new Schema.Field("mymap", Schema.createMap(Schema.create(Schema.Type.INT)), null, null)));

		File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
		tmp.deleteOnExit();
		tmp.delete();
		Path file = new Path(tmp.getPath());

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file).withSchema(schema)
						.withConf(testConf).build();

		// Write a record with a null value
		Map<String, Integer> map = new HashMap<>();
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

		ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file).withSchema(schema)
						.withConf(testConf).build();

		// Write a record with a map with Utf8 keys.
		GenericData.Record record = new GenericRecordBuilder(schema)
						.set("mymap", ImmutableMap.of(new Utf8("a"), 1, new Utf8("b"), 2)).build();
		writer.write(record);
		writer.close();

		AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(testConf, file);
		GenericRecord nextRecord = reader.read();

		assertNotNull(nextRecord);
		assertEquals(ImmutableMap.of(str("a"), 1, str("b"), 2), nextRecord.get("mymap"));
	}

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	/**
	 * Return a String or Utf8 depending on whether compatibility is on
	 */
	public CharSequence str(String value) {
		return compat ? value : new Utf8(value);
	}
}
