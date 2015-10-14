package edu.hfut.kafka.producer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * An example using the new java client Producer for Kafka 0.8.2
 *
 * @author Cameron Gregory, http://www.bloke.com/
 */
public class TestProducer {
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

		boolean sync = true;
		String topic = "kafkatopic";
		String key = "mykey";
		String value = "myvalue";
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, key, value);
		if (sync) {
			producer.send(producerRecord).get();
		} else {
			producer.send(producerRecord);
		}
		producer.close();
	}
}