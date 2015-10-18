package edu.hfut.kafka.producer;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerRunnable implements Runnable {
	private Logger logger = LoggerFactory.getLogger(ProducerExample.class);

	private KafkaProducer<String, String> producer;
	private int base;
	private int gap;
	private String topic;
	private boolean sync;
	private CountDownLatch latch;

	public ProducerRunnable(KafkaProducer<String, String> producer, String topic, boolean sync, int base, int gap, CountDownLatch latch) {
		this.producer = producer;
		this.topic = topic;
		this.base = base;
		this.gap = gap;
		this.sync = sync;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Callback callback = new MyCallback();
			for (int i = 0; i < gap; i++) {
				if (i % 1000 == 0) {
					logger.info("i = " + i);
				}
				ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, (base + i)
						+ "");
				if (sync) {
					try {
						producer.send(producerRecord).get();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					producer.send(producerRecord);
					//					try {
					//						Thread.sleep(10);
					//					} catch (InterruptedException e) {
					//						e.printStackTrace();
					//					}
				}
			}
		} finally {
			this.latch.countDown();
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

