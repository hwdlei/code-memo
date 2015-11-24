package edu.hfut.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSqlDemo {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		DataFrame df = sqlContext
				.read()
//				.json("file:///home/donglei/Desktop/workspace/03Code/myproject/code-memo/spark-demo/src/main/resources/people.json");
				.json("hdfs://archtorm:9000/user/donglei/spark/people.json");
		df.show();
		df.printSchema();
		df.selectExpr("name").show();
		df.select(df.col("name"), df.col("age").plus(1)).show();
		df.filter(df.col("age").gt(21)).show();
		df.groupBy("age").count().show();
	}

}
