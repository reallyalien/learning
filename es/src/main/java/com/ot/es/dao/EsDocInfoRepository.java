package com.ot.es.dao;

import com.ot.es.entity.EsDocInfo;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsDocInfoRepository extends ElasticsearchRepository<EsDocInfo,Integer> {

}
