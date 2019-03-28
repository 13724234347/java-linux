package com.tzh.main;

import java.util.ArrayList;
import java.util.List;

import com.tzh.mongodb.Mongodb;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private static MongoCollection<Document> collection;
	public static void main(String[] args) {
		Mongodb mongodb = new Mongodb();
		//链接单节点mongodb
		new Main().connect();
		//链接mongodb集群
		new Main().setUp();
		//添加文档(相当于数据库的数据)
//		mongodb.insert(collection);
		//查询文档(相当于数据库的数据)
		mongodb.select(collection);
		//修改文档(相当于数据库的数据)
//		mongodb.update(collection);
		//删除文档(相当于数据库的数据)
//		mongodb.delete(collection);
	}
	
	/**
	 * 连接mongodb数据库并选择文档
	 */
	public void connect() {
		try {
			// 连接到 mongodb 服务
			mongoClient = new MongoClient("192.168.1.210", 27017);
			// 连接到数据库
			mongoDatabase = mongoClient.getDatabase("tzh");
			// 选择集合test,相当于mysql当中的表
			collection = mongoDatabase.getCollection("test");
			// 得到服务器ip
			System.out.println("服务器Ip:" + mongoClient.getAddress());
			System.out.println("Connect to database successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /*
	  *  连接mongodb集群 就算任意一个节点挂掉也不会影响应用程序客户端对整个副本集的读写
	  *  */
	 
	public void setUp() {
		try {
			List<ServerAddress> addresses = new ArrayList<ServerAddress>();
			ServerAddress address1 = new ServerAddress("192.168.1.127", 30000);
			ServerAddress address2 = new ServerAddress("192.168.1.130", 30000);
			ServerAddress address3 = new ServerAddress("192.168.1.213", 30000);
			addresses.add(address1);
			addresses.add(address2);
			addresses.add(address3);

			mongoClient = new MongoClient(addresses);
			// 连接到数据库
			mongoDatabase = mongoClient.getDatabase("tzh");
			// 选择集合test,相当于mysql当中的表
			collection = mongoDatabase.getCollection("test");
			System.out.println("Connect to database successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
