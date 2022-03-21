//package com.ot.tools.es;
//
//import com.alibaba.fastjson.JSON;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.index.query.MultiMatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.profile.ProfileShardResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.stereotype.Service;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class EsDocInfoService {
//
//    @Autowired
//    private TransportClient transportClient;
//
//    @Autowired
//    private EsDocInfoRepository esDocInfoRepository;
//
//    public void searchIndex(String index) {
//        try {
//            //查询索引对象
//            SearchRequest searchRequest = new SearchRequest(index);
////            searchRequest.types("doc");
//            SearchResponse response = transportClient.search(searchRequest).get();
//            for (SearchHit hit : response.getHits()) {
//                System.out.println(hit.getSourceAsString());
//            }
//            System.out.println(response.getTook());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void docInfo() {
////        SearchRequest searchRequest = new SearchRequest();
////        SearchSourceBuilder builder = new SearchSourceBuilder();
//        //第一个参数是查询的值，后面的参数是字段名，可以跟多个字段，用逗号隔开
//        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("头痛", "labels^1.5", "intro","special");
//        queryBuilder.tieBreaker(0.3f);
//        queryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
//        SearchResponse response = transportClient.prepareSearch("local_docinfo").setQuery(queryBuilder).get();
//        SearchHits hits = response.getHits();
//        System.out.println("took:" + response.getTook());
//        System.out.println("timeout:" + response.isTimedOut());
//        System.out.println("total:" + hits.getTotalHits());
//        System.out.println("MaxScore:" + hits.getMaxScore());
//        for(SearchHit hit:hits) {
//            System.out.println("score:" + hit.getScore());
//            System.out.println(hit.getSourceAsString());
//            System.out.println();
//            //将获取的值转换成map的形式
////            Map<String, Object> map = hit.getSourceAsMap();
////            for(String key:map.keySet()) {
////                System.out.println(key +" key对应的值为：" +map.get(key));
////            }
//        }
//    }
//    public void docInfo1() {
//        //第一个参数是查询的值，后面的参数是字段名，可以跟多个字段，用逗号隔开
//        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("头痛", "labels^1.5", "intro","special");
//
//        queryBuilder.tieBreaker(0.3f);
//        queryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
//        Page<EsDocInfo> esDocInfos = esDocInfoRepository.search(searchQuery);
//        System.out.println(JSON.toJSONString(esDocInfos));
//        long totalElements = esDocInfos.getTotalElements();
//        System.out.println(totalElements);
//        Iterable<EsDocInfo> search = esDocInfos.getContent();
//        for (EsDocInfo esDocInfo : search) {
//            System.out.println(esDocInfo.getSource());
//            System.out.println(JSON.toJSONString(esDocInfo));
//        }
//    }
//
//    public void docInfo2() {
////        Settings esSetting = Settings.builder().loadFromStream("json/common-setting-new.json", Resources.getResourceAsStream("json/common" + "-setting-new.json")).build();
//        GetIndexTemplatesResponse getIndexTemplatesResponse = transportClient.admin().indices().prepareGetTemplates("").get();
//        List<IndexTemplateMetaData> indexTemplates = getIndexTemplatesResponse.getIndexTemplates();
//    }
//
//}
