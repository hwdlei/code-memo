package edu.hfut.elasticsearch.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.GenericOptionsParser;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import zx.soft.utils.codec.URLCodecUtils;

public class Hbase2ES {

	public static class TokenizerMapper extends TableMapper<NullWritable, Text> {

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
						Mapper<ImmutableBytesWritable, Result, NullWritable, Text>.Context context)
						throws IOException, InterruptedException {

			for (Cell cell : value.listCells()) {
				byte[] column = CellUtil.cloneQualifier(cell);
				byte[] columnValue = CellUtil.cloneValue(cell);
			}
			context.write(NullWritable.get(), new Text());
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: Hbase2ES <hbase.table> <es.nodes> <es.resource>");
			System.exit(1);
		}

		conf.setBoolean("mapred.map.tasks.speculative.execution", false);
		conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
		conf.setLong("mapreduce.input.fileinputformat.split.minsize", 1000 * 1024 * 1024);
		conf.setLong("mapreduce.map.skip.maxrecords", 100);
		conf.set("es.input.json", "yes");
		conf.set("es.mapping.id", "caseId");
		//		conf.set("es.batch.size.bytes", "10485760");
		conf.set("es.batch.size.bytes", "4194304");
		conf.set("es.batch.size.entries", "1000");
		conf.set("hbase.zookeeper.quorum", "192.168.3.54,192.168.3.55,192.168.3.56,192.168.3.57,192.168.3.58");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("es.nodes", otherArgs[1]);
		String[] resource = otherArgs[2].split("/");
		conf.set("es.resource", resource[0] + "/" + URLCodecUtils.encoder(resource[1], "UTF-8"));

		Job job = Job.getInstance(conf, "Hbase2ES");
		job.setJarByClass(Hbase2ES.class);

		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob(otherArgs[0], scan, TokenizerMapper.class, NullWritable.class, Text.class,
						job);

		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Text.class);

		job.setInputFormatClass(TableInputFormat.class);
		job.setOutputFormatClass(EsOutputFormat.class);

		job.setNumReduceTasks(0);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}