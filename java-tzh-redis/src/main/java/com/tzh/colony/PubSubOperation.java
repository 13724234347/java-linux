package com.tzh.colony;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class PubSubOperation {
	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.1.145",7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.145",7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.145",7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.149",7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.149",7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.149",7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.151",7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.151",7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.151",7002));
		JedisCluster cluster = new JedisCluster(jedisClusterNodes);
		
	}
}

