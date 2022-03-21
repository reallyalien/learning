package com.ot.es;

import com.ot.es.service.EsDocInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@SpringBootTest
public class Test1 {


    @Autowired
    private EsDocInfoService esDocInfoService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void searchIndex(){
        esDocInfoService.searchIndex("local_docinfo");
    }

    @Test
    public void templateTest(){
       esDocInfoService.elasticsearchTemplateTest();
    }

    @Test
    public void docinfo2(){
        esDocInfoService.docInfo2();
    }
}
