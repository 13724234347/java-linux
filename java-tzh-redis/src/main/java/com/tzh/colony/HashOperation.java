package com.tzh.colony;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class HashOperation {
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
		 *将哈希表key中的域的值设为value ： cluster.hset(自定义key,自定义域名,value);可以覆盖这个域
		 * cluster.hset("website","google","www.google.com");key或者域名不存在将返回null
		 *将哈希表key中的域的值设为value(不可以覆盖该域)
		 * System.out.println(cluster.hsetnx("nosql","key-value-store","test"));;
		 *
		 *将哈希表的key设多个域（可覆盖）使用map
		 * Map<String,String> hash=new HashMap<>();
		 * hash.put("baidu","www.baidu.com");
		 * hash.put("google","www.google.com");
		 * hash.put("fox","www.fox.com");
		 * hash.put("xiaomi","www.xiaomi.com");
		 * cluster.hmset("website", hash);
		 *
		 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
		 * String[] fields={多个域名};
		 * cluster.hdel(键,fields);
		 *
		 *
		 *查询该key中的所有域和该域的信息
		 * System.out.println(cluster.hgetAll("website"));;
		 *
		 *  查看哈希表 key中域的数量
		 *  System.out.printl(cluster.hlen("key"));
		 *
		 *  查看哈希表 key 中所有域的值
		 *  System.out.println(cluster.hvals("website"));;
		 *
		 *  查询key里的指定域名（可查询多个）如果没有改域名就返回null
		 *  String[] fields={"baidu","fox","aaaa"};将所有域名放进一个数组
		 * System.out.println(cluster.hmget(key,fields));;
		 *
		 *获取哈希表key中的域的值 cluster.hget(指定的key,指定的域名)
		 * cluster.hget("website","google");
		 *
		 * 查看哈希表 key中给定域field是否存在。
		 * cluster.hexists(键,域名)；
		 *
		 *
		 *获取哈希表key中所有域和该域的值cluster.hgetall(key);
		 */
		/*Map<String,String> hash=new HashMap<>();
		hash.put("baidu","www.baidu.com");
		hash.put("google","www.google.com");
		hash.put("fox","www.fox.com");
		hash.put("xiaomi","www.xiaomi.com");
		cluster.hmset("website", hash);
		System.out.println(cluster.hgetAll("website"));;
//		*/
//		String[] fields={"baidu","fox","aaaa"};
//		System.out.println(cluster.hmget("website", fields));;
		//System.out.println(cluster.hget("nosql","key-value-store"));
		cluster.hdel("website","baidu");
		System.out.println(cluster.hvals("website"));;

	}


}
