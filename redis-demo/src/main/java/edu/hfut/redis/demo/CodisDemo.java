package edu.hfut.redis.demo;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;

public class CodisDemo {

	public static void main(String[] args) {
		//		singleProxy();
		codisHa();
	}

	/**
	 * Codis代理实现了原先Redis的协议，所以连接到 Codis Proxy 和连接原生的 Redis Server 没有明显的区别
	 */
	public static void singleProxy() {
		Jedis jedis = new Jedis("192.168.6.129", 19000);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
		jedis.close();
	}

	/**
	 * Use a round robin policy to balance load to multiple codis proxies.
	 * Detect proxy online and offline automatically.
	 */
	public static void codisHa() {
		JedisResourcePool jedisPool = RoundRobinJedisPool.create().curatorClient("192.168.6.129:2181", 30000)
				.zkProxyDir("/zk/codis/db_test/proxy").build();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set("foo", "foobar");
			String value = jedis.get("foo");
			System.out.println(value);
		}
	}

}
