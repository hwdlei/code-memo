package edu.hfut.spark.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.google.common.collect.Lists;

public class WordCountDemoV2 {

	public static void main(String[] args) {
		String logFile = "hdfs://archtorm:9000/user/donglei/input/wordcount_input"; // Should be some file on your system
		SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> logData = sc.textFile(logFile);
		JavaRDD<String> words = logData.flatMap(new FlatMapFunction<String, String>() {

			@Override
			public Iterable<String> call(String t) throws Exception {
				StringTokenizer itr = new StringTokenizer(t.toString());
				List<String> list = new ArrayList<>();
				while (itr.hasMoreTokens()) {
					list.add(itr.nextToken());
				}
				return Lists.newArrayList(t.split(" "));
			}
		});
		JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				return new Tuple2<String, Integer>(t, 1);
			}
		});

		JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1 + v2;
			}
		});
		JavaPairRDD<String, Integer> sortCounts = counts.sortByKey();
		for (Tuple2<String, Integer> tuples : sortCounts.collect()) {
			System.out.println(tuples._1() + "\t" + tuples._2());
		}

	}

}
