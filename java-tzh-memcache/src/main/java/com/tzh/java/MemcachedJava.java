package com.tzh.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

/**
 * @Title: MemcachedJava.java
 * @Package memcached
 * @Description: TODO
 * @date: 2018年11月21日 下午3:11:58
 */
public class MemcachedJava {
	private static MemcachedClient mcc;
	static {
		try {
			mcc = new MemcachedClient(new InetSocketAddress("192.168.1.8", 12000));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection to server sucessful.");
	}

	public static void main(String[] args) {
			MemcachedJava java =  new MemcachedJava();
			java.query();
			//java.set();
			//java.add();
			//java.replace();
			//java.prepend();
	}
	public void query()
	{
		System.out.println(mcc.get("a1"));
	}

	/**
	 * set 操作
	 */
	public void set() {
		try {
			// 存储数据
			Future fo = mcc.set("runoob", 900, "Free Education");
			// 查看存储状态
			System.out.println("set status:" + fo.get());
			// 输出值
			System.out.println("runoob value in cache - 输出值 :" + mcc.get("runoob"));
			// 关闭连接
			mcc.shutdown();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * add 操作实例
	 */
	public void add()
	{
		 try{
	         // 添加数据
	         Future fo = mcc.set("runoob", 900, "Free Education");
	 
	         // 打印状态
	         System.out.println("set status:" + fo.get());
	 
	         // 输出
	         System.out.println("runoob value in cache -输出值： " + mcc.get("runoob"));
	 
	         // 添加
	         fo = mcc.add("runoob", 900, "memcached");
	 
	         // 打印状态
	         System.out.println("add status:" + fo.get());
	 
	         // 添加新key
	         fo = mcc.add("codingground", 900, "All Free Compilers");
	 
	         // 打印状态
	         System.out.println("add status:" + fo.get());
	         
	         // 输出
	         System.out.println("codingground value in cache - " + mcc.get("codingground"));
	 
	         // 关闭连接
	         mcc.shutdown();
	         
	      }catch(Exception ex){
	         System.out.println(ex.getMessage());
	      }
	}
	
	/**
	 * replace 操作实例
	 */
	public void replace(){
		 try {
	         // 添加第一个 key=》value 对
	         Future fo = mcc.set("runoob", 900, "Free Education");
	 
	         // 输出执行 add 方法后的状态
	         System.out.println("add status:" + fo.get());
	 
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("runoob"));
	 
	         // 添加新的 key
	         fo = mcc.replace("runoob", 900, "Largest Tutorials' Library");
	 
	         // 输出执行 set 方法后的状态
	         System.out.println("replace status:" + fo.get());
	 
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("runoob"));
	 
	         // 关闭连接
	         mcc.shutdown();
	         
	      }catch(Exception ex){
	         System.out.println( ex.getMessage() );
	      }
	}
	
	/**
	 * append 操作实例
	 */
	public void append()
	{
		try{
	         // 添加数据
	         Future fo = mcc.set("runoob", 900, "Free Education");
	 
	         // 输出执行 set 方法后的状态
	         System.out.println("set status:" + fo.get());
	 
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("runoob"));
	 
	         // 对存在的key进行数据添加操作
	         fo = mcc.append(900, "runoob", " for All");
	 
	         // 输出执行 set 方法后的状态
	         System.out.println("append status:" + fo.get());
	         
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("codingground"));
	 
	         // 关闭连接
	         mcc.shutdown();
	         
	      }catch(Exception ex) {
	         System.out.println(ex.getMessage());
	      }
	}
	/**
	 * prepend 操作实例
	 */
	public void prepend()
	{
		try{
	         // 添加数据
	         Future fo = mcc.set("runoob", 900, "Education for All");
	 
	         // 输出执行 set 方法后的状态
	         System.out.println("set status:" + fo.get());
	 
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("runoob"));
	 
	         // 对存在的key进行数据添加操作
	         fo = mcc.prepend(900, "runoob", "Free ");
	 
	         // 输出执行 set 方法后的状态
	         System.out.println("prepend status:" + fo.get());
	         
	         // 获取键对应的值
	         System.out.println("runoob value in cache - " + mcc.get("codingground"));
	 
	         // 关闭连接
	         mcc.shutdown();
	         
	      }catch(Exception ex) {
	         System.out.println(ex.getMessage());
	      }
	}
}
