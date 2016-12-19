package edu.hfut.elasticsearch.index;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest.OpType;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.engine.DocumentAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.ExceptionHelper;

public class CrudClients {

	private static final Logger logger = LoggerFactory.getLogger(CrudClients.class);

	private static final Client client = ElasticsearchConnection.getConnection().getClient();

	/**
	 * 覆盖
	 * @return true if the data does not exist, false if the data already exists (based on its id)
	 */
	public static boolean index(String index, String type, String id, String data) {
		IndexResponse response = client.prepareIndex(index, type, id).setSource(data).get();
		return response.isCreated();
	}

	/**
	 * create a new Document
	 * @return true if the data does not exist
	 */
	public static boolean create(String index, String type, String id, String data) {
		try {
			IndexResponse response = client.prepareIndex(index, type, id).setSource(data).setOpType(OpType.CREATE)
							.get();
			System.out.println(response.getHeaders());
		} catch (DocumentAlreadyExistsException e) {
			logger.info(ExceptionHelper.stackTrace(e));
			return false;
		}
		return true;
	}

	public static String get(String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).get();
		if (!response.isExists()) {
			return "";
		}
		return response.getSourceAsString();
	}

	public static <T> T get(String index, String type, String id, Class<T> t) {
		String record = get(index, type, id);
		if (record.isEmpty()) {
			return null;
		}
		return JsonUtils.getObject(record, t);
	}

	/**
	 * 删除一个文档
	 * @return false if the data does not exist, otherwise true
	 */
	public static boolean delete(String index, String type, String id) {
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.isFound();
	}

	/**
	 * update
	 * @return true if the data does not exist, false if the data already exists (based on its id)
	 */
	public static boolean upsert(String index, String type, String id, String data) {
		UpdateResponse response = client.prepareUpdate(index, type, id).setDoc(data).setUpsert(data)
						.setRetryOnConflict(3).get();
		return response.isCreated();
	}

	public static List<String> mget(String index, String type, Iterable<String> ids) {
		MultiGetResponse response = client.prepareMultiGet().add(index, type, ids).get();
		List<String> docs = new ArrayList<>();
		for (MultiGetItemResponse item : response) {
			if (!item.isFailed()) {
				GetResponse getResponse = item.getResponse();
				if (getResponse.isExists()) {
					docs.add(getResponse.getSourceAsString());
				}
			}
		}
		return docs;
	}


	public static void main(String[] args) {
		//		index("cars", "transactions", "125",
		//						"{\"price\":20000,\"color\":\"red\",\"make\":\"honda\",\"sold\":\"2014-11-05\"}");
		//		create("cars", "transactions", "125",
		//						"{\"price\":20000,\"color\":\"red\",\"make\":\"honda\",\"sold\":\"2014-11-05\"}");
		System.out.println(get("cars", "transactions", "125"));
		//		delete("cars", "transactions", "125");
		//		System.out.println(get("cars", "transactions", "125"));
		System.out.println(upsert("cars", "transactions", "125", "{\"price\":20000}"));
	}
}
