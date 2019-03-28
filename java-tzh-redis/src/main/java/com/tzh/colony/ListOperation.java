package com.tzh.colony;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


public class ListOperation {
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
		 *String[] mylist={"tzh","lc","zxy","lbj","bz","tw","gjl","lsq","zyh"};
		 * 添加list集合(顺序相反)cluster.lpush("mylist",mylist);
		 * 			 (按照添加顺序)cluster.rpush("mylist",mylist);
		 *
		 * (加在最左边)追加功能cluster.lpushx(键,值)根据该键追加一个值并返回该集合现有几个值(如果没有该键就返回0)
		 * (加在最右边)追加功能cluster.rpushx(键,值)同理
		 * System.err.println(cluster.lpushx("mylist","aaa"));
		 *
		 *删除指定集合cluster.del(键)
		 *System.out.println(cluster.del("mylist"));
		 *
		 *修改
		 * lset(键,下标,值)将下标里原有的值修改成这个括号里的值(不能超出下标否则会报错，可以用llen方法先查一下)
		 * System.out.println(cluster.lset("mylist",8,"tzh"));
		 *
		 * 查询list集合cluster.lrange("mylist",0,-1)
		 * List<String> mylist=cluster.lrange("mylist",0,-1);
		 * System.out.println(mylist);
		 *
		 * 查询集合里指定下标的值cluster.lindex(键,下标)超出下标则返回null
		 * System.err.println(cluster.lindex("mylist",3));
		 *
		 * 查看list集合的长度lengthcluster.llen(键);
		 * Long lenth=cluster.llen("mylist");
		 * System.out.println(lenth)
		 *
		 * 查询该键是否存在，存在返回true，不存在则返回falsecluster.exists(键)
		 * System.out.println(cluster.exists("list"));
		 *
		 *
		 * */

		/*String[] mylist={"tzh","lc","zxy","lbj","bz","tw","gjl","lsq","zyh"};
		cluster.lpush("mylist",mylist);*/
		System.out.println(cluster.lrange("mylist",0,-1));
		//System.err.println(cluster.lpushx("mylist","aaa"));
		//System.out.println(cluster.del("mylist"));
	}

}

