package com.ot.es.config;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义es查询结果处理类
 */
@Component
public class ResultsExtractorParse implements ResultsExtractor<List<Map<String, Object>>> {

    @Override
    public List<Map<String, Object>> extract(SearchResponse response) {
        List<Map<String, Object>> result = new ArrayList<>();
        SearchHits hits = response.getHits();
        System.out.println("============================");
        for (SearchHit hit : hits) {
            Map<String, Object> map = null;
            float score = hit.getScore();
            map = hit.getSourceAsMap();
            map.put("socre", score);
            result.add(map);
        }
        return result;
    }
}
