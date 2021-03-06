/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.examples.streaming;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * Counts words cumulatively in UTF8 encoded, '\n' delimited text received from the network every
 * second starting with initial value of word count.
 * Usage: JavaStatefulNetworkWordCount <hostname> <port>
 * <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive
 * data.
 * <p>
 * To run this on your local machine, you need to first run a Netcat server
 * `$ nc -lk 9999`
 * and then run the example
 * `$ bin/run-example
 * org.apache.spark.examples.streaming.JavaStatefulNetworkWordCount localhost 9999`
 */
public class JavaStatefulNetworkWordCount {

	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) {

		// Update the cumulative count function
		final Function2<List<Tuple2<Integer, Integer>>, Optional<Integer>, Optional<Integer>> updateFunction = new Function2<List<Tuple2<Integer, Integer>>, Optional<Integer>, Optional<Integer>>() {
			@Override
			public Optional<Integer> call(List<Tuple2<Integer, Integer>> values, Optional<Integer> state) {
				Integer newSum = state.or(0);
				for (Tuple2<Integer, Integer> value : values) {
					newSum += value._1;
				}
				return Optional.of(newSum);
			}
		};

		// Create the context with a 1 second batch size
		SparkConf sparkConf = new SparkConf().setAppName("JavaStatefulNetworkWordCount").setMaster("local[4]");
		JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
		ssc.checkpoint(".");

		// Initial RDD input to updateStateByKey
		@SuppressWarnings("unchecked")
		List<Tuple2<String, Integer>> tuples = Arrays.asList(new Tuple2<String, Integer>("hello", 1),
				new Tuple2<String, Integer>("world", 1));
		final JavaPairRDD<String, Integer> initialRDD = ssc.sparkContext().parallelizePairs(tuples);

		JavaReceiverInputDStream<String> lines = ssc.socketTextStream("localhost", 9999,
				StorageLevels.MEMORY_AND_DISK_SER_2);

		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterable<String> call(String x) {
				return Lists.newArrayList(SPACE.split(x));
			}
		});

		JavaPairDStream<String, Integer> wordsDstream = words.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String s) {
				return new Tuple2<String, Integer>(s, 1);
			}
		});

		JavaPairDStream<String, Tuple2<Integer, Integer>> wordsDstream2 = wordsDstream
				.transformToPair(new Function<JavaPairRDD<String, Integer>, JavaPairRDD<String, Tuple2<Integer, Integer>>>() {

					@Override
					public JavaPairRDD<String, Tuple2<Integer, Integer>> call(JavaPairRDD<String, Integer> v1)
							throws Exception {
						return v1.join(initialRDD);
					}
				});

		// This will give a Dstream made of state (which is the cumulative count of the words)
		JavaPairDStream<String, Integer> stateDstream = wordsDstream2.updateStateByKey(updateFunction,
				new HashPartitioner(ssc.sparkContext().defaultParallelism()), initialRDD);

		stateDstream.print();
		ssc.start();
		ssc.awaitTermination();
	}
}
