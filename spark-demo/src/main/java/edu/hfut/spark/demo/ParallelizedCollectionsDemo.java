package edu.hfut.spark.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

public class ParallelizedCollectionsDemo {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("parallelized collections").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6);
		JavaRDD<Integer> distData = sc.parallelize(data);
		String totalNum = distData.map(new Function<Integer, String>() {

			@Override
			public String call(Integer v1) throws Exception {
				// TODO Auto-generated method stub
				return v1 + "";
			}
		}).reduce(new Function2<String, String, String>() {

			@Override
			public String call(String arg0, String arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		System.out.println(totalNum);
	}

}
