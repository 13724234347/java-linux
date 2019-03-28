package com.tzh.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tzh.main.Main;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class Mongodb{
	
	Main main = new Main();
	/**
	 * 插入数据
	 */
	public void insert(MongoCollection<Document> collection) {
		try {
			main.connect();// 连接Mongodb服务器
			//一次插入多条文档
			Map<String, Object> objMap = new HashMap<String, Object>();
			objMap.put("name", "洋葱");
			objMap.put("sex", "男");
			objMap.put("age", 18);
			objMap.put("addr", "中国深圳");
			Document document = new Document(objMap);
			
			Map<String, Object> objMap1 = new HashMap<String, Object>();
			objMap1.put("name", "liucong");
			objMap1.put("sex", "男");
			objMap1.put("age", 20);
			objMap1.put("addr", "中国深圳");
			Document document1 = new Document(objMap1);
			
			Document document2 = new Document("name", "刘聪").  
			         append("sex","男").  
			         append("age",19).  
			         append("addr","中国深圳");  
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			documents.add(document1);
			documents.add(document2);
			collection.insertMany(documents);
			System.out.println("插入成功");
			
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查询文档(相当于数据库的数据)
	 */
	public void select(MongoCollection<Document> collection) {
		try {
			main.connect();// 连接Mongodb服务器
			FindIterable<Document> findIterable = collection.find(Filters.eq("name", "liucong"));//Filters.eq("条件")条件查询,去掉条件查询就是查询所有
			MongoCursor<Document> mongoCursor = findIterable.iterator();
				while (mongoCursor.hasNext()) {
					System.out.println(mongoCursor.next());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改文档(相当于数据库的数据)
	 */
	public void update(MongoCollection<Document> collection) {
		try {
			main.connect();// 连接Mongodb服务器
			// 将name="洋葱"修改成name="IBM"
			collection.updateMany(Filters.eq("name", "洋葱"), new Document("$set", new Document("name", "高金龙")));
			//将name="洋葱"的文档name的值更改为"高金龙"
			// 修改之后遍历
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除文档
	 */
	public void delete(MongoCollection<Document> collection) {
		try {
			main.connect();// 连接Mongodb服务器
			// 删除符合条件的第一个文档
//			collection.deleteOne(Filters.eq("sex", "男"));
			// 删除符合添加的所有文档
			 collection.deleteMany(Filters.eq("sex", "男"));
			// 删除之后遍历
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
