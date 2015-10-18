package edu.hfut.kafka.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerGroupExample {
	private static Logger logger = LoggerFactory.getLogger(ConsumerGroupExample.class);
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;
	private AtomicLong count;

	public ConsumerGroupExample(String a_zookeeper, String a_groupId, String a_topic) {
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper, a_groupId));
		this.topic = a_topic;
		this.count = new AtomicLong();
	}

	public void shutdown() {
		if (consumer != null) {
			consumer.shutdown();
		}
		if (executor != null) {
			executor.shutdown();
		}
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(1000);
				logger.info("wait Executors to shutdown");
			} catch (InterruptedException e) {
				logger.info(e.getMessage());
			}
		}
	}

	public void run(int a_numThreads) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(a_numThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		// now launch all the threads
		//
		executor = Executors.newFixedThreadPool(a_numThreads);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams) {
			executor.submit(new ConsumerRunnable(stream, threadNumber, count));
			threadNumber++;
		}
	}

	private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", a_zookeeper);
		props.put("group.id", a_groupId);
		props.put("zookeeper.session.timeout.ms", "1000");
		props.put("zookeeper.sync.time.ms", "2000");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);
	}

	public static void main(String[] args) {
		String zooKeeper = "192.168.5.201:2124,192.168.3.10:2124,192.168.3.11:2124";
		String groupId = "sentiment";
		String topic = "sentiment-cache";
		int threads = Integer.parseInt("10");

		ConsumerGroupExample example = new ConsumerGroupExample(zooKeeper, groupId, topic);
		example.run(threads);
		//		example.shutdown();
		while (true) {
			logger.info("pull " + example.count.get() + " Records");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
	}
}