package edu.hfut.redis.demo;

import redis.clients.jedis.Jedis;

/**
 *
 * @author donglei
 * @date: 2016年2月14日 下午3:46:55
 */
public class JedisDemo {


	/**
	 * 单点Redis实例，192.168.6.129 7000
	 * Jedis不可以在多线程中共享
	 * @param args
	 */

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.6.129", 7000);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
		jedis.close();
	}

}
