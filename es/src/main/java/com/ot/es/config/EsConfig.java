package com.ot.es.config;

import org.elasticsearch.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.stereotype.Component;

@Configuration
public class EsConfig {


    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ElasticsearchConverter converter,ResultMapperCustom resultMapperCustom) {
        ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(client, converter,resultMapperCustom);
        return elasticsearchTemplate;
    }
}
