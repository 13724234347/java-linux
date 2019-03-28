package com.tzh.colony;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class StringOperation {
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
		/*
		 * set(键，值)设值
		 * cluster.set("name","tzh");
		 *
		 *
		 * get(键)取指定键的值
		 * System.out.println(cluster.get(name));
		 *
		 * 判读该键存不存在，存在就返回true，不存在就返回false
		 * System.out.println(cluster.exists("myphone"));
		 *
		 * 追加，并返回追加后字符串的长度
		 * System.out.println(cluster.append("myphone","-13724234347"))
		 *
		 * 将 key中储存的数字值减一（只对数字有效）
		 * cluster.decr(键);
		 * 将 key中储存的数字值增一（只对数字有效）
		 * cluster.incr(键);
		 * 将key中储存的值减去括号里的数字（）（只对数字有效）
		 * 如果对不存在的键，默认会创建这个不存在的键并默认数值为0然后进行这个操作
		 * cluster.decrby(键,数字)
		 *
		 * */
		//System.out.println(cluster.exists("myphone"));
		//System.out.println(cluster.append("myphone","xiaomi5X"));
		//System.out.println(cluster.append("myphone","-13724234347"));
		System.out.println(cluster.get("myphone"));
	}
}
