package zx.soft.utils.hbase.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TableExport {

	public static class TokenizerMapper extends TableMapper<Text, NullWritable> {

		@Override
		protected void map(ImmutableBytesWritable key, Result value,
				Mapper<ImmutableBytesWritable, Result, Text, NullWritable>.Context context) throws IOException,
				InterruptedException {
			Model model = new Model();
			byte[] ts = value.getValue(Model.T, Model.TS);
			if (ts != null) {
				model.setTimestamp(Bytes.toLong(ts));
			}
			byte[] srcIp = value.getValue(Model.T, Model.SRCIP);
			if (srcIp != null) {
				model.setSrcIp(Bytes.toString(srcIp));
			}
			byte[] desIp = value.getValue(Model.T, Model.DESIP);
			if (desIp != null) {
				model.setDesIp(Bytes.toString(desIp));
			}
			byte[] srcPort = value.getValue(Model.T, Model.SRCPORT);
			if (srcPort != null) {
				model.setSrcPort(Bytes.toInt(srcPort));
			}
			byte[] desPort = value.getValue(Model.T, Model.DESPORT);
			if (desPort != null) {
				model.setDesPort(Bytes.toInt(desPort));
			}
			byte[] protocol = value.getValue(Model.T, Model.PROTOCOL);
			if (protocol != null) {
				model.setProtocol(Bytes.toString(protocol));
			}
			byte[] brief = value.getValue(Model.S, Model.BRIEF);
			if (brief != null) {
				model.setBrief(Bytes.toString(brief));
			}
			byte[] detail = value.getValue(Model.S, Model.DETAIL);
			if (detail != null) {
				model.setDetail(Bytes.toString(detail));
			}
			context.write(new Text(model.toString()), NullWritable.get());
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 1) {
			System.err.println("Usage: wordcount  <out>");
			System.exit(1);
		}
		conf.set("hbase.zookeeper.quorum", "192.168.3.54,192.168.3.55,192.168.3.56,192.168.3.57,192.168.3.58");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(TableExport.class);
		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob("apt_outline", scan, TokenizerMapper.class, Text.class,
				NullWritable.class, job);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		job.setInputFormatClass(TableInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileOutputFormat.setOutputPath(job, new Path(otherArgs[0]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
