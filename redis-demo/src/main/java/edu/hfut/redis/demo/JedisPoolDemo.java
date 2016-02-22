package edu.hfut.redis.demo;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author donglei
 * @date: 2016年2月14日 下午4:08:26
 */
public class JedisPoolDemo {

	/**
	 * 单点Redis实例， 192.168.6.129:6379
	 * @param args
	 */


	public static void main(String[] args) {
		demo1();
	}

	public static void demo1() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.6.129");
		/// Jedis implements Closable. Hence, the jedis instance will be auto-closed after the last statement.
		try (Jedis jedis = pool.getResource()) {
			/// ... do stuff here ... for example
			jedis.set("foo", "bar2");
			String foobar = jedis.get("foo");
			System.out.println(foobar);
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike");
			Set<String> sose = jedis.zrange("sose", 0, -1);
			System.out.println(sose);
		}
		/// ... when closing your application:
		pool.destroy();
	}

	public static void demo2() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.6.129");
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			/// ... do stuff here ... for example
			jedis.set("foo", "bar");
			String foobar = jedis.get("foo");
			jedis.zadd("sose", 0, "car"); jedis.zadd("sose", 0, "bike");
			Set<String> sose = jedis.zrange("sose", 0, -1);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		/// ... when closing your application:
		pool.destroy();
	}

}
