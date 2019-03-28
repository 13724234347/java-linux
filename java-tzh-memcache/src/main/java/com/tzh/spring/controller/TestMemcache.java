package com.tzh.spring.controller;


import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.SocketUtils;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import cn.yr.entities.User;
public class TestMemcache {
   private MemcachedClient memCachedClient;
    
   @Before
    public void beforeTest(){
        
        ApplicationContext atx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        memCachedClient = (MemcachedClient)atx.getBean(MemcachedClient.class);
    }  
    /**
     * 添加并查询
     */
    @Test
    public void TestMem(){
    	//memCachedClient.set("YY", 0, "123456789");
    	System.out.println("取值:"+memCachedClient.get("BB"));
//    	System.out.println(memCachedClient.get("a1"));
//    	System.out.println(memCachedClient.get("a2"));
//    	System.out.println(memCachedClient.get("a3"));
    }
    
    /**
     * set 操作
     * 
     * 如果set的key已经存在，该命令可以更新该key所对应的原来的数据，也就是实现更新的作用。
     */
    @Test
    public void set()
    {
    	User user = new User();
    	user.setId(1);
    	user.setAccount("lxm");
    	user.setPasswords("123");
    	user.setTel("13652047751");
    	
    	
    	memCachedClient.set("user", 0, user);
    	System.out.println("取值 --------->>"+memCachedClient.get("user"));
    	
    	user.setTel("18244767133");
    	memCachedClient.set("user", 0, user);
    	System.out.println("取值 --------->>"+memCachedClient.get("user"));
    	
    }
    /**
     * add 操作
     * 如果 add 的 key 已经存在，则不会更新数据(过期的 key 会更新)，之前的值将仍然保持相同，并且您将获得响应 NOT_STORED
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Test
    public void add() throws Exception
    {
    	OperationFuture<Boolean> issave = memCachedClient.add("add1", 0, "All Of Me");
    			
    	System.out.println("add status ["+issave.get()+"] 取值--->>"+memCachedClient.get("add"));
    	
    	OperationFuture<Boolean> isvalue = memCachedClient.add("add1", 0, "All Of Your");
    	System.out.println("add status ["+isvalue.get()+"] 取值--->>"+memCachedClient.get("add"));
    }
    /**
     * replace 操作
     * 
     * Memcached replace 命令用于替换已存在的 key(键) 的 value(数据值)。
     * 如果 key 不存在，则替换失败，并且您将获得响应 NOT_STORED。
     */
    @Test
    public void replace() throws Exception
    {
    	OperationFuture<Boolean> issave = memCachedClient.add("A", 0, "All Of Me");
    	System.out.println("add status ["+issave.get()+"] 取值--->>"+memCachedClient.get("A"));
    	
    	OperationFuture<Boolean> isvalue = memCachedClient.replace("A",0, "All Of She");
    	System.out.println("add status ["+isvalue.get()+"] 取值--->>"+memCachedClient.get("A"));
    }
    /**
     * append 操作
     * 
     * 用于向已存在 key(键) 的 value(数据值) 后面追加数据 。
     * 
     * 对象不能追加
     */
    @Test
    public void append()
    {
//    	User user = new User();
//    	user.setId(2);
//    	user.setAccount("wxm");
//    	user.setPasswords("3306");
//    	user.setTel("15112361715");
    	OperationFuture<Boolean> issave = memCachedClient.set("append3",0, "周星驰");
    	System.out.println("add status ["+issave+"] 取值--->>"+memCachedClient.get("append3"));
    	
//    	User user1 = new User();
//    	user1.setId(3);
//    	user1.setAccount("王涛");
//    	user1.setPasswords("8080");
//    	user1.setTel("17825651423");
    	OperationFuture<Boolean> isvalue = memCachedClient.append("append3", "喜剧之王");
    	System.out.println("add status ["+isvalue+"] 取值--->>"+memCachedClient.get("append3"));
    }
   /**
    * prepend 操作
    * 
    * 向前追加值
    */
    @Test
    public void prepend() throws Exception
    {
    	OperationFuture<Boolean> issave = memCachedClient.set("prepend1",0, "李小龙");
    	System.out.println("add status ["+issave.get()+"] 取值--->>"+memCachedClient.get("prepend1"));
    	
    	OperationFuture<Boolean> isvalue = memCachedClient.prepend("prepend1", "功夫之王-");
    	System.out.println("add status ["+isvalue.get()+"] 取值--->>"+memCachedClient.get("prepend1"));
    }
    /**
     * 删除key
     *
     */
    @Test
    public void delete() throws Exception
    {
    	OperationFuture<Boolean> isdelete = memCachedClient.delete("Y");
    	System.out.println("删除的key ："+isdelete.getKey()+ " 是否操作成功 : "+isdelete.get());
    }
    /**
     * 自增自减
     * 
     * Memcached incr 与 decr 命令用于对已存在的 key(键) 的数字值进行自增或自减操作。
     */
    @Test
    public void auto()
    {
//    	String key = "Y";
//    	memCachedClient.set(key, 0,"10");
//    	System.out.println("最开始的number的值 :  "+memCachedClient.get(key));
//    	for (int i = 5; i < 100; i++) {
//    		memCachedClient.incr(key, i);
//        	System.out.println("增长之后的值---->> :  "+memCachedClient.get(key));
//		}
    	
    	String key2 = "P";
    	memCachedClient.set(key2, 0,"100");
    	System.out.println("最开始的的值 :  "+memCachedClient.get(key2));
    	for (int i = 5; i < 10; i++) {
    		memCachedClient.decr(key2, i);
        	System.out.println("增长之后的值======:  "+memCachedClient.get(key2));
		}
    	
    }
    
}