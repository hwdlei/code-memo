package edu.hfut.spark.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

public class AccumulatorDemo {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("AccumulatorDemo").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> data = Arrays.asList(1, 2, 5, 3, 4, 5);
		JavaRDD<Integer> distData = sc.parallelize(data);
		// 内置支持整型
		Accumulator<Integer> accu = sc.accumulator(0);
		distData.foreach(new VoidFunction<Integer>() {

			@Override
			public void call(Integer t) throws Exception {
				accu.add(t);
			}
		});
		System.out.println(accu.value());
		// 累加器保证执行  lazy transformation
		Accumulator<String> sAccu = sc.accumulator("", new StringAccumulatorParam());
		distData.map(new Function<Integer, String>() {

			@Override
			public String call(Integer v1) throws Exception {
				sAccu.add(v1 + "");
				return v1 + "";
			}
		}).foreach(new VoidFunction<String>() {

			@Override
			public void call(String t) throws Exception {
			}
		});

		JavaRDD<String> distStr = sc.parallelize(Arrays.asList("1", "2", "3", "4", "5"));
		//		Accumulator<String> sAccu = sc.accumulator("", new StringAccumulatorParam());
		distStr.foreach(new VoidFunction<String>() {

			@Override
			public void call(String t) throws Exception {
				sAccu.add(t);
			}
		});
		System.out.println(sAccu.value());
		sc.stop();
	}
}
