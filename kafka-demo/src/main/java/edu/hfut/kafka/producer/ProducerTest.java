package edu.hfut.kafka.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 代码来自：　https://issues.apache.org/jira/browse/KAFKA-1642
 * @author donglei
 */
public class ProducerTest {

	static int numberTh = 200;
	static CountDownLatch latch = new CountDownLatch(200);

	public static void main(String[] args) throws IOException, InterruptedException {

		Properties prop = new Properties();
		InputStream propFile = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("kafkaproducer.properties");

		String topic = "test";
		prop.load(propFile);
		System.out.println("Property: " + prop.toString());
		StringBuilder builder = new StringBuilder(1024);
		int msgLenth = 256;
		for (int i = 0; i < msgLenth; i++) {
			builder.append("a");
		}

		int numberOfProducer = 4;
		Producer[] producer = new Producer[numberOfProducer];

		for (int i = 0; i < producer.length; i++) {
			producer[i] = new KafkaProducer(prop);
		}
		ExecutorService service = new ThreadPoolExecutor(numberTh, numberTh, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(numberTh * 2));

		for (int i = 0; i < numberTh; i++) {
			service.execute(new MyProducer(producer, 100000, builder.toString(), topic));
		}
		latch.await();

		System.out.println("All Producers done...!");
		for (int i = 0; i < producer.length; i++) {
			producer[i].close();
		}
		service.shutdownNow();
		System.out.println("All done...!");

	}

	static class MyProducer implements Runnable {

		Producer[] producer;
		long maxloops;
		String msg;
		String topic;

		MyProducer(Producer[] list, long maxloops, String msg, String topic) {
			this.producer = list;
			this.maxloops = maxloops;
			this.msg = msg;
			this.topic = topic;
		}

		@Override
		public void run() {
			ProducerRecord record = new ProducerRecord(topic, msg.toString().getBytes());
			Callback callBack = new MyCallback();
			try {
				for (long j = 0; j < maxloops; j++) {
					try {
						for (int i = 0; i < producer.length; i++) {
							producer[i].send(record, callBack);
						}
						Thread.sleep(10);
					} catch (Throwable th) {
						System.err.println("FATAL ");
						th.printStackTrace();
					}
				}

			} finally {
				latch.countDown();
			}
		}
	}

	static class MyCallback implements Callback {
		@Override
		public void onCompletion(RecordMetadata metadata, Exception exception) {
			if (exception != null) {
				System.err.println("Msg dropped..!");
				exception.printStackTrace();
			}

		}
	}

}