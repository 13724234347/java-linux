package com.elastic.common.conn;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

public class ESUtil {
    
    private static final String INDEX_NAME = "test_index";
    private static final String INDEX_TYPE = "text_type";
    
    public static void main(String[] args)throws Exception {
        ESUtil esUtil = new ESUtil();
        esUtil.queryByTerm(INDEX_NAME, INDEX_TYPE);
        //esUtil.deleteDataByQuery(INDEX_NAME, INDEX_TYPE);
      //esUtil.createIndex(INDEX_NAME);
      //esUtil.updateMapping(INDEX_NAME, INDEX_TYPE);
    }
    /**
     * 创建索引
     */
    public void createIndex(String indexName)
    {
        ESConnection.getConnection().admin().indices().prepareCreate(indexName).execute().actionGet();
    }
    
    /**
     * 判断索引是否存在
     */
    public boolean existsIndex(String indexName)
    {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        IndicesExistsResponse response = ESConnection.getConnection().admin().indices().exists(request).actionGet();
        return response.isExists();
    }
    
    /**
     *
     * 创建MAPPING
     */
    public void createMapping(String indexName,String type)throws Exception
    {
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject().startObject(type).startObject("properties")
                                        //5.0以后通过keyword进行不分词设置 
                .startObject("stu_name").field("type","keyword").field("store",true).endObject()
                .startObject("stu_sex").field("type","keyword").field("store",true).endObject()
                .startObject("stu_age").field("type","integer").endObject()
                .startObject("stu_desc").field("type","text").field("analyzer", "ik_max_word").endObject()
                .endObject().endObject().endObject();
        PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(type).source(mapping);
        ESConnection.getConnection().admin().indices().putMapping(mappingRequest).actionGet();// 执行mapping创建
    }
    
    /**
    *
    * 修改MAPPING
    */
   public void updateMapping(String indexName,String type)throws Exception
   {
       XContentBuilder mapping = XContentFactory.jsonBuilder()
               .startObject().startObject(type).startObject("properties")
               .startObject("stu_name").field("type","keyword").field("store",true).endObject()
               .startObject("stu_sex").field("type","keyword").field("store",true).endObject()
               .startObject("stu_age").field("type","integer").endObject()
               .startObject("stu_addr").field("type","text").field("analyzer", "ik_max_word").field("store", true).endObject()
               .startObject("stu_desc").field("type","text").field("analyzer", "ik_max_word").endObject()
               .endObject().endObject().endObject();
       PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(type).source(mapping);
       ESConnection.getConnection().admin().indices().putMapping(mappingRequest).actionGet();// 执行mapping创建
   }
    
    
    /**
     *删除索引 
     */
    public void deleteIndex(String indexName)
    {
        ESConnection.getConnection().admin().indices().prepareDelete(indexName).execute().actionGet();// 删除索引
    }
    
    
    /**
     *插入数据 
     */
    public void insertData(String indexName,String type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stu_name", "高金龙111");
        map.put("stu_age", 24);
        map.put("stu_desc", "我来自祁阳白水镇");
        map.put("stu_addr", "唐子壕");
        
        
        
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("stu_name", "唐尉");
        map1.put("stu_age", 21);
        map1.put("stu_desc", "我来自冷水滩白水镇");
        map1.put("stu_addr", "广东解放路");
        
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("stu_name", "唐建桥");
        map2.put("stu_age", 22);
        map2.put("stu_desc", "我来自山东白水镇");
        map2.put("stu_addr", "山东响马路");
        
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("stu_name", "唐子壕");
        map3.put("stu_age", 18);
        map3.put("stu_desc", "我来自黄阳司");
        map3.put("stu_addr", "冷水滩黄阳司觅香路");
        
        // 索引名字 索引类型
        IndexResponse response = ESConnection.getConnection().prepareIndex(indexName, type).setSource(map, XContentType.JSON).get();
    }
    
    /**
     *
     * 根据ID修改
     */
    public void updateDataById(String indexName,String type)
    {
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("stu_name", "唐子壕");
        map3.put("stu_age", 18);
        map3.put("stu_desc", "我来自黄阳司水口桥");
        map3.put("stu_addr", "冷水滩黄阳司觅香路");

        ESConnection.getConnection().prepareUpdate(indexName, type,"dNX_VmABwYzHwRVwDOvw").setDoc(map3).execute().actionGet();
    }
    
    /**
     * 根据ID删除
     */
    public void deleteDataById(String indexName,String type)
    {
        ESConnection.getConnection().prepareDelete(indexName, type,"dNX_VmABwYzHwRVwDOvw").execute().actionGet();
    }
    
    /**
     * 根据字段查询进行删除
     */
    public void deleteDataByQuery(String indexName,String type)
    {
        
        QueryBuilder queryBuilder = QueryBuilders.termQuery("stu_age", 22);
        
        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(ESConnection.getConnection())
                    .filter(queryBuilder) 
                    .source(indexName)                                  
                    .get();        
        //https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-update.html
    }
    
    
    /**
     *根据ID进行查询 
     */
    public void queryById(String indexName,String type)
    {
        GetResponse responsere  = ESConnection.getConnection().prepareGet(indexName,type ,"jw0yV2ABElZ3oyBRiQFT").execute().actionGet();
        System.out.println(responsere.getSourceAsString());
        //System.out.println(responsere.getSourceAsMap().get("stu_addr"));
    }
    
    /**
     *根据条件进行查询 
     */
    public void queryByTerm(String indexName,String type)
    {
//       QueryBuilder queryBuilder1 = QueryBuilders.termQuery("stu_name", "高金龙");//完全匹配
//       QueryBuilder queryBuilder2 = QueryBuilders.termQuery("stu_addr", "路");//完全匹配
//       
//       
//       QueryBuilder queryBuilder3 = QueryBuilders.termQuery("stu_age", 20);//完全匹配
//       
//       BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//       boolQueryBuilder.should(queryBuilder1);
//       boolQueryBuilder.should(queryBuilder2);
//       //条件之间通过should(or) must(and) mustnot(not)进行关联
//       
//       
//       BoolQueryBuilder abc = QueryBuilders.boolQuery();
//       abc.must(queryBuilder3);
//       abc.must(boolQueryBuilder);
//       select *from aaa where (stu_name = '高金龙'　or stu_addr = '路') and　stu_age =20     
        
        
//        List<String> types = new ArrayList<String>();
//        types.add("高金龙");
//        types.add("唐子壕");
//        QueryBuilder desQuery = QueryBuilders.termsQuery("stu_name", types);//实现in 的效果查询
      
        
//        QueryBuilder desQuery = QueryBuilders.wildcardQuery("stu_name", "*唐*");//通配符查询
        //QueryBuilder desQuery = QueryBuilders.rangeQuery("stu_age").gte(20).to(21);//实现范围查询 (开闭区间)
        //开区间　　是指定    10<a<20     闭区间　　10<=a<=20
        
//        QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("stu_addr", "冷水滩黄阳司觅香路");//把查询的词进行分词后，再与ES数据进行匹配
        
        
        //QueryBuilder queryBuilder1 = QueryBuilders.multiMatchQuery("*唐*", "stu_addr","stu_name");//一个值匹配多个字段
        // select *from user where stu_addr = '唐子壕' or stu_name = '唐子壕'     
        //QueryBuilder queryBuilder1 = QueryBuilders.matchAllQuery();
        
        QueryBuilder queryBuilder2 = QueryBuilders.termQuery("stu_addr", "永州");//完全匹配
        
        HighlightBuilder hiBuilder=new HighlightBuilder();
        hiBuilder.preTags("<h2 style='color:red'>");
        hiBuilder.postTags("</h2>");
        hiBuilder.field("stu_addr");
        
       SearchRequestBuilder searchRequestBuilder = ESConnection.getConnection().prepareSearch(indexName).setTypes(type)
                                                           .highlighter(hiBuilder)
                                                           .setQuery(queryBuilder2);
                                                          // .addSort("stu_age", SortOrder.ASC)//查询所有,后面不需要带条件
                                                           //.setFrom(1) //从指定下标开始向后取值
                                                           //.setSize(2); //取几条数据
                                                           //与mysql limit效果一样
       
       
        // SearchRequestBuilder searchRequestBuilder = ESConnection.getConnection().prepareSearch(indexName).setTypes(type)
        //                                                       .setQuery(queryBuilder);
       
       SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();//得到返回值
       SearchHits searchHits = searchResponse.getHits();//得到总数(总数)
      
       long count = searchHits.getTotalHits();//得到总数的数量
       System.out.println("总数=" +count);
       
       //循环
       for (SearchHit searchHit : searchHits) {
          // Map<String,Object> map = searchHit.getSourceAsMap();
          System.out.println(searchHit.getHighlightFields().get("stu_addr"));
       }
    }
    
    
    
    
    
}
