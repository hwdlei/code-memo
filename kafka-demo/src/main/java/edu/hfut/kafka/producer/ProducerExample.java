package edu.hfut.kafka.producer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hfut.kafka.utils.CheckSumUtils;

public class ProducerExample {
	private static Logger logger = LoggerFactory.getLogger(ProducerExample.class);

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.5.201:9092,192.168.3.10:9092,192.168.3.11:9092");

		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		props.put(ProducerConfig.TIMEOUT_CONFIG, "50000");

		props.put(ProducerConfig.ACKS_CONFIG, "1");

		//		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		long start = System.currentTimeMillis();
		boolean sync = false;
		String topic = "test";
		int i = 100000;
		for (; i < 200000; i++) {
			if (i % 100 == 0) {
				logger.info("i = " + i);
			}
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic,
					CheckSumUtils.getMD5(i + ""));
			if (sync) {
				producer.send(producerRecord).get();
			} else {
				producer.send(producerRecord);
			}
		}
		producer.close();
		logger.info("Push " + i + " 　条数据,耗时为" + (System.currentTimeMillis() - start));
	}
}