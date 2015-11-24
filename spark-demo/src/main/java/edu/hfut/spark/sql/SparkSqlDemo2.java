package edu.hfut.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSqlDemo2 {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		DataFrame df = sqlContext.read().load("hdfs://archtorm:9000/user/donglei/spark/users.parquet");
		df.show();
		df.printSchema();
		df.select("name", "favorite_color").write()
				.save("hdfs://archtorm:9000/user/donglei/spark/Users_select.parquet");
	}

}
