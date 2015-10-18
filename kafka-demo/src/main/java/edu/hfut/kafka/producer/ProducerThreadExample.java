package edu.hfut.kafka.producer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerThreadExample {
	private static Logger logger = LoggerFactory.getLogger(ProducerThreadExample.class);

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		int numOfThread = 10;
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
		String topic = "sentiment-cache";

		ExecutorService executor = Executors.newFixedThreadPool(numOfThread);
		CountDownLatch latch = new CountDownLatch(numOfThread);
		int gap = 1000000;
		for (int i = 0; i < 10; i++) {
			executor.submit(new ProducerRunnable(producer, topic, sync, i * gap, gap, latch));
		}
		latch.await();
		executor.shutdown();
		producer.close();
		logger.info("Push " + (10 * gap) + " 　条数据,耗时为" + (System.currentTimeMillis() - start));
	}
}