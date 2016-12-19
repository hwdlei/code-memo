package edu.hfut.elasticsearch.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import zx.soft.utils.codec.URLCodecUtils;

/**
 * Hdfs转存ES
 * @author donglei
 * @date: 2016年11月16日 下午2:44:29
 */
public class Hdfs2ES {

	public static class ReloadMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(NullWritable.get(), value);
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		conf.setBoolean("mapred.map.tasks.speculative.execution", false);
		conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
		conf.setLong("mapreduce.input.fileinputformat.split.minsize", 1000 * 1024 * 1024);
		conf.setLong("mapreduce.map.skip.maxrecords", 100);
		conf.set("es.input.json", "yes");
		conf.set("es.mapping.id", "caseId");
		conf.set("es.write.operation", "upsert");
		//		conf.set("es.batch.size.bytes", "10485760");
		conf.set("es.batch.size.bytes", "4194304");
		conf.set("es.batch.size.entries", "1000");

		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: Main <in> <es.nodes> <es.resource>");
			System.exit(1);
		}
		conf.set("es.nodes", otherArgs[1]);
		String[] resource = otherArgs[2].split("/");
		conf.set("es.resource", resource[0] + "/" + URLCodecUtils.encoder(resource[1], "UTF-8"));
		Job hadoopEsJob = Job.getInstance(conf, "hadoop2esJob");
		hadoopEsJob.setJarByClass(Hdfs2ES.class);

		hadoopEsJob.setMapperClass(ReloadMapper.class);
		hadoopEsJob.setMapOutputKeyClass(NullWritable.class);
		hadoopEsJob.setMapOutputValueClass(Text.class);

		hadoopEsJob.setInputFormatClass(TextInputFormat.class);
		hadoopEsJob.setOutputFormatClass(EsOutputFormat.class);
		FileInputFormat.addInputPath(hadoopEsJob, new Path(otherArgs[0]));

		hadoopEsJob.setNumReduceTasks(0);

		hadoopEsJob.waitForCompletion(true);
		System.out.println("finished!");

	}

}