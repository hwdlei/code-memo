package edu.hfut.elasticsearch.index;

import java.net.UnknownHostException;

import org.elasticsearch.client.Client;


public class IndexDemo {

	public static void main(String[] args) throws UnknownHostException {
		String json = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";
		ElasticsearchConnection connection = ElasticsearchConnection.getConnection();
		Client client = connection.getClient();

	}

}
