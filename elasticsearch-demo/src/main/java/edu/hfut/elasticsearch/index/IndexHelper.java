package edu.hfut.elasticsearch.index;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

public class IndexHelper {


	BulkProcessor bulkProcessor = BulkProcessor
					.builder(ElasticsearchConnection.getConnection().getClient(), new BulkProcessor.Listener() {
						@Override
						public void beforeBulk(long executionId, BulkRequest request) {
						}

						@Override
						public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
						}

						@Override
						public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
						}
	}).setBulkActions(10000).setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
					.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
					.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

}
