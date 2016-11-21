package edu.hfut.elasticsearch.index;

import java.net.InetAddress;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

/**
 * Elasticsearch 连接
 * @author donglei
 * @date: 2016年11月17日 下午4:04:50
 */
public class ElasticsearchConnection {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConnection.class);

	private static ElasticsearchConnection connection;

	private Client client;

	private ElasticsearchConnection() {
		try {
			start();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	public static ElasticsearchConnection getConnection() {
		if (connection == null) {
			connection = new ElasticsearchConnection();
		}
		return connection;
	}

	public void start() throws Exception {
		logger.info("Starting Elasticsearch Client");
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", ElasticsearchConfig.getProps("cluster.name", "elasticsearch")).build();

		TransportClient esClient = TransportClient.builder().settings(settings).build();
		Map<String, String> hosts = Splitter.onPattern("[,]").withKeyValueSeparator(':')
				.split(ElasticsearchConfig.getProps("es.nodes", "localhost:9300"));
		for (Entry<String, String> host : hosts.entrySet()) {
			esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host.getKey()), Integer.parseInt(host.getValue())));
			logger.info(String.format("Added Elasticsearch Node : %s", host));
		}
		client = esClient;
		logger.info("Started Elasticsearch Client");
	}

	public void stop() throws Exception {
		if (this.client != null) {
			this.client.close();
		}
		this.client = null;
	}

	public Client getClient() {
		return client;
	}

}
