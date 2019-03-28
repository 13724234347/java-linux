package com.elastic.common.conn;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * mapping 是种规范,假定在mapping中设置规定类型,那么插入数据时-只能-插入规定的类型(mapping是可以改变类型的)
 * https://blog.csdn.net/lvhong84/article/details/23936697
 * 
 * @author zxy
 *
 * 2018年6月22日 下午3:06:06
 *
 */
public class EsClient {

    TransportClient client = null;

    public static void main(String[] args) throws Exception {
        EsClient es = new EsClient(); // 构造方法中连接linux
//        es.CreateIndex();
//        es.insert();
//        es.query();
//        es.update();
//        es.deleteData();
//        es.deleteInde();
//        es.delete();

    }

    /**
     * 查询数据
     * @author zxy
     * 
     * 2018年6月22日 下午3:44:40
     * 
     * @throws Exception
     */
    public void query()throws Exception {
        /**
         * 根据ID查询
         * 索引名 _index
         * 类型  _type
         * ID _id
         */
//        GetResponse responsere  = client.prepareGet("邹想云", "zxy","9ztMJGQBcQ_qQNWIIPrg").execute().actionGet();
//        System.out.println(responsere.getSourceAsString());
        
    	/**
    	 * 分词查询
    	 */
//    	String content = "我是中国共产党";
//    	AnalyzeResponse response = client.admin().indices()
//    							.prepareAnalyze(content)//内容
//    								.setAnalyzer("ik_max_word")//指定分词器
//    									.execute().actionGet();//执行
//    	List<AnalyzeToken> tokens = response.getTokens();
//    	for (AnalyzeToken analyzeToken : tokens) {
//			System.err.println(analyzeToken.getTerm());
//		}
        
        /**
         * 根据条件查询 (普通匹配查询)
         * 属性名
         * 属性内容 (分词存入时,关键字会屏蔽查询不出来)
         * 索引名
         * 类型
         */
		QueryStringQueryBuilder queryBuilder1 = new QueryStringQueryBuilder("百事履历");
        queryBuilder1.analyzer("ik_max_word");	//ik_smart
        queryBuilder1.field("today_member");
    	
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("today_member", "百事履历");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("邹想云").setTypes("zxy").setQuery(queryBuilder1);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        int count =new Long(searchHits.getTotalHits()).intValue();
        System.out.println("总数 = " +count);
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }
    
   /**
    * 判断索引是否存在,删除索引
    * @author zxy
    * 
    * 2018年6月22日 下午2:48:07
    *
    */
    public void delete() {
        /**
         * 判断索引是否存在
         */
        IndicesExistsRequest request = new IndicesExistsRequest("zxy");
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
        if (response.isExists()) {
            client.admin().indices().prepareDelete("zxy").execute().actionGet();// 删除索引
        }
    }

   /**
    * 插入数据
    * @author zxy
    * 
    * 2018年6月22日 下午2:47:59
    *
    */
    public void insert() {
        Map<String, String> json = new HashMap<String, String>();
        json.put("name", "zxy-un"); // 设置属性
        json.put("today_member", "可口可乐百事履历色我要吃鸭脖!"); // 设置属性

        										  // 索引名字      索引类型
        IndexResponse response = client.prepareIndex("邹想云", "zxy").setSource(json, XContentType.JSON).get();
    }

    /**
     * 修改数据
     * @author zxy
     * 
     * 2018年6月22日 下午2:47:54
     *
     */
    public void update() {
        Map<String, String> json = new HashMap<String, String>();
        json.put("name", "东毒西邪");
        json.put("today_member", "我要吃鸭脖!");

        // 索引名字 索引类型
        // 前面是修改的索引名,类型,对应的ID 需要修改后的数据
        client.prepareUpdate("邹想云", "zxy", "BTuAJGQBcQ_qQNWIpfv0").setDoc(json).execute().actionGet();
    }

   /**
     * 删除数据
     * @author zxy
     * 
     * 2018年6月22日 下午3:23:16
     *
     */
    public void deleteData() {
        client.prepareDelete("邹想云", "zxy", "-TtOJGQBcQ_qQNWITfr3").execute().actionGet();
    }
    
   /**
    * 删除索引
    * @author zxy
    * 
    * 2018年6月22日 下午3:24:30
    *
    */
    public void deleteInde() {
    	DeleteIndexResponse dResponse = client.admin().indices().prepareDelete("邹想云")
                .execute().actionGet();
    }

    /**
     * 创建索引(包括添加MAPPING)
     * @author zxy
     * 
     * 2018年6月22日 下午2:48:26
     * 
     * @throws Exception
     */
    public void CreateIndex() throws Exception {

        IndicesExistsRequest request = new IndicesExistsRequest("邹想云"); // 索引名
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet(); // 判断索引是否存在,存在就跳过,不存在就创建
        if (response.isExists()) {
            return;
        }

        client.admin().indices().prepareCreate("邹想云").execute().actionGet();// 创建一个索引,这个索引是为空(类型与MAPPING都为空)

        // 根据已存在的索引创建类型与对应的MAPPING
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                // 类型(可以随便自己定义) //属性,规定为这样写
                .startObject().startObject("zxy").startObject("properties") // 开始
                // 这里向MAPPING添加一个字段
                // 字段名 类型 分词 是否存储源数据
                .startObject("today_member").field("type", "text").field("analyzer", "ik_max_word").field("store", true).endObject() // 普通字段
                .startObject("name").field("type", "text").endObject()
                .endObject().endObject().endObject(); // 结束

        // 把MAPPING加入到对应的索引中
        PutMappingRequest mappingRequest = Requests.putMappingRequest("邹想云").type("zxy").source(mapping); // 索引名  类型
        client.admin().indices().putMapping(mappingRequest).actionGet();// 执行mapping创建

        // .startObject("plan_intro") //嵌套对象字段
        // .startObject("properties")
        // .startObject("item").field("type", "text").endObject()
        // .startObject("content").field("type", "text").endObject()
        // .endObject()
        // .endObject()
        // 一个字段有一个startobject到endobject

        // 多字段 ： 这个意思，我理解，就是一个字段有多个类型，如下这个，既有一个analyzer = id，又有一个no_analyzed
        // 可以用于全文检索，还可以做精确查找。

    }

    /**
     * 集群连接
     * @author zxy
     * 
     * 2018年6月22日 下午2:48:35
     *
     */
    public EsClient() {
        try {
        	// https://blog.csdn.net/ljc2008110/article/details/48630609
            Settings settings = Settings.builder().put("client.transport.sniff", true) // 不用手动设置集群里所有集群的ip到连接客户端,它会自动帮你添加,并且自动发现新加入集群的机器
                    .put("cluster.name", "my-application").build(); // 修改集群名

            client = new PreBuiltTransportClient(settings) // 集群连接
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.160"), 9300))
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.162"), 9300)); // java 连接端口为 9300
            
//            client = new PreBuiltTransportClient(Settings.EMPTY) // 单节点连接 (参数意为: 不使用集群连接 设置为空)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.196"), 9300));

            // GetResponse response = client.prepareGet("gaojinlong", "bbb",
            // "2").execute().actionGet();
            // 输出结果
            // System.out.println(response.getSourceAsString());

        } catch (Exception ex) {
        } finally {

        }
    }

    /**
     * 集群连接
     * @author zxy
     * 
     * 2018年6月22日 下午2:48:44
     * 
     * @param a
     */
    public EsClient(String a) {
        try {

            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.160"), 9300));

            GetResponse response = client.prepareGet("gaojinlong", "bbb", "2").execute().actionGet();
            // 输出结果
            System.out.println(response.getSourceAsString());

        } catch (Exception ex) {
            client.close();
        } finally {

        }
    }

    /**
     * 连接
     * @author zxy
     * 
     * 2018年6月22日 下午2:48:50
     * 
     * @return
     */
    public TransportClient getConnection() {

        if (client == null) {
            synchronized (EsClient.class) {
                if (client == null) {
                    new EsClient();
                }
            }
        }
        return client;

    }

}