package edu.hfut.spark.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.broadcast.Broadcast;

public class BroadcastDemo {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Broadcast collections").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		List<Integer> data = Arrays.asList(1, 2, 5, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 9, 15, 16, 17, 18, 19, 20);
		JavaRDD<Integer> distData = sc.parallelize(data);
		final Broadcast<int[]> broadVar = sc.broadcast(new int[] { 5, 7, 9 });
		JavaRDD<Integer> filters = distData.filter(new Function<Integer, Boolean>() {

			private static final long serialVersionUID = -8353027323351913172L;

			@Override
			public Boolean call(Integer v1) throws Exception {
				int[] values = broadVar.value();
				for (int value : values) {
					if (value == v1) {
						return true;
					}
				}
				return false;
			}
		});
		int totalNum = filters.reduce(new Function2<Integer, Integer, Integer>() {

			private static final long serialVersionUID = -4362964200853886835L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1 + v2;
			}
		});
		System.out.println(totalNum);
	}
}
