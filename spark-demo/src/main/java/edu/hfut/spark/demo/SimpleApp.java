package edu.hfut.spark.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class SimpleApp {
	public static void main(String[] args) {
		String logFile = "hdfs://archtorm:9000/user/donglei/README.md"; // Should be some file on your system
		SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> logData = sc.textFile(logFile).cache();

		long numAs = logData.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String s) {
				return s.contains("a");
			}
		}).count();

		long numBs = logData.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String s) {
				return s.contains("b");
			}
		}).count();

		System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
		// spark不支持在一个程序中并发运行两个context
		sc.stop();
	}
}