package com.tzh.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;
/**
 * 
 * @ClassName:  ConnMemcached   
 * @Description:TODO
 * @date:   2018年11月21日 下午3:03:49   
 *    
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved.
 */
public class ConnMemcached {
	private MemcachedClient mcc;

	public static void main(String[] args) {
		ConnMemcached memcachedJava = new ConnMemcached();
		memcachedJava.setUp();
		//memcachedJava.addData();
		// memcachedJava.setData();
		// memcachedJava.delete();
		memcachedJava.select();
		//memcachedJava.query();
	}

	/**
	 * 查询主服务器和备用服务器的数据
	 * @throws IOException 
	 */
	public void query(){
		try {
			for (int i = 10001; i < 10006; i++) {
				mcc = new MemcachedClient(new InetSocketAddress("192.168.1.8", i));
				System.out.println("服务器:" + i + "值也是:" + mcc.get("a1"));
				System.out.println("服务器:" + i + "值也是:" + mcc.get("a2"));
				System.out.println("服务器:" + i + "值也是:" + mcc.get("a3"));
			}
			//mcc.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		
//		try {
//			mcc = new MemcachedClient(new InetSocketAddress("192.168.1.8",10240 ));
//			System.out.println("服务器取到的值："+mcc.get("add"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	/**
	 * 连接memcached的代理服务器10240
	 */
	public void setUp() {
		try {
			// 本地连接 Memcached 服务
			//mcc = new MemcachedClient(new InetSocketAddress("192.168.1.8",10240 ));
			mcc = new MemcachedClient(new InetSocketAddress("192.168.1.8",12000 ));
			System.out.println("Connection to server sucessful.");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 添加
	 */
	@SuppressWarnings("rawtypes")
	public void addData() {
		try {
			User user = new User();
			user.setName("Kimi");
			user.setAge(10);
			user.setSex("女");
			Future fo = mcc.add("add", 0, user);
			System.out.println("缓存状态:" + fo.get());
			mcc.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修改操作
	 */
	@SuppressWarnings("rawtypes")
	public void setData() {
		try {
			User user = new User();
			user.setName("锤子");
			user.setSex("男");
			user.setAge(18);
			// 存储数据
			Future fo = mcc.set("add", 0, user);// 10(秒)表示缓存时间,10秒之后数据消失
			// 查看存储状态
			System.out.println("缓存状态:" + fo.get());
			// 输出值
			System.out.println("缓存数据User:" + mcc.get("add"));
			mcc.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		try {
			mcc.delete("add");
			mcc.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询
	 */
	public void select() {
		try {
			System.out.println(mcc.get("YY"));
			mcc.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}