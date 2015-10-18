package edu.hfut.kafka.consumer;

import java.util.concurrent.atomic.AtomicLong;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ConsumerRunnable.class);
	private KafkaStream m_stream;
	private int m_threadNumber;
	private AtomicLong count;

	public ConsumerRunnable(KafkaStream a_stream, int a_threadNumber, AtomicLong count) {
		m_threadNumber = a_threadNumber;
		m_stream = a_stream;
		this.count = count;
	}

	@Override
	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while (it.hasNext()) {
			logger.info("Thread " + m_threadNumber + ": " + new String(it.next().message()));
			count.incrementAndGet();
		}
		logger.info("Shutting down Thread: " + m_threadNumber);
	}
}