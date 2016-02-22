package edu.hfut.redis.demo;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterDemo {

	/**
	 * Redis Cluster: (实验阶段： 并没有在生产环境下大规模使用)
	 *  集群信息：
	 *      192.168.6.129:7001(M)
	 *          --192.168.6.129:7002(S)
	 *      192.168.6.129:7003(M)
	 *          --192.168.6.129:7004(S)
	 *      192.168.6.129:7005(M)
	 *          --192.168.6.129:7006(S)
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort("192.168.6.129", 7001));
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		jc.set("cluster", "redis");
		String value = jc.get("cluster");
		System.out.println(value);
		jc.close();
	}

}
