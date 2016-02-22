package edu.hfut.redis.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 *
 * @author donglei
 * @date: 2016年2月19日 上午11:56:23
 */
public class JedisShardsDemo {

	/**
	 * Redis Replication
	 *      192.168.6.129:7001(M - W)
	 *          --192.168.6.129:7002(S - R)
	 *          --192.168.6.129:7003(S - R)
	 *      192.168.6.129:7004(M - W)
	 *          --192.168.6.129:7005(S - R)
	 *          --192.168.6.129:7006(S - R)
	 * @param args
	 */

	public static void main(String[] args) {
		poolDemo();

	}

	public static void shardDemo() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo si = new JedisShardInfo("192.168.6.129", 7001);
		si.setPassword("donglei");
		shards.add(si);
		si = new JedisShardInfo("192.168.6.129", 7004);
		si.setPassword("donglei");
		shards.add(si);

		ShardedJedis jedis = new ShardedJedis(shards);

		jedis.set("a", "foo");
		System.out.println(jedis.get("a"));
		jedis.close();
	}

	public static void poolDemo() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo si = new JedisShardInfo("192.168.6.129", 7001);
		si.setPassword("donglei");
		shards.add(si);
		si = new JedisShardInfo("192.168.6.129", 7004);
		si.setPassword("donglei");
		shards.add(si);
		ShardedJedisPool pool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
		ShardedJedis jedis = pool.getResource();
		jedis.set("a", "foo");
		jedis.close();
		ShardedJedis jedis2 = pool.getResource();
		jedis.set("z", "bar");
		jedis2.close();
		ShardedJedis jedis3 = pool.getResource();
		System.out.println(jedis3.get("z"));
		pool.destroy();
	}

}
