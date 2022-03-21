package com.ot.tools.es;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;

import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EsClient2 {

    private RestClient client;

    /**
     * 测试连接
     *
     * @throws IOException
     */
    @Before
    public void connection() throws IOException {
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("192.168.1.40", 9200, "http"));
        Header[] defaultHeaders = {new BasicHeader("content-type", "application/json")};
        clientBuilder.setDefaultHeaders(defaultHeaders);
        client = clientBuilder.build();
    }

    /**
     * 查看索引
     */
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("local_docinfo");
        BasicHeader basicHeader = new BasicHeader("content-type", "application/json");
        Response response = client.performRequest("GET", "http://192.168.1.40:9200/_cat/indices?v", basicHeader);
        InputStream inputStream = response.getEntity().getContent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            outStream.write(buff, 0, rc);
        }
        //合并之后的字节数组
        byte[] merge = outStream.toByteArray();
        String s = new String(merge);
        System.out.println(s);
    }

    /**
     * 查看索引
     */
    @Test
    public void post() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("local_docinfo");
        BasicHeader basicHeader = new BasicHeader("content-type", "application/json");
        String params = "{\n" +
                "    \"query\":{\n" +
                "           \"multi_match\" : {\n" +
                "              \"query\" : \"头痛\",\n" +
                "              \"type\" : \"best_fields\",\n" +
                "              \"tie_breaker\" : 0.3,\n" +
                "              \"fields\" : [ \"labels^1.5\", \"intro\", \"special\" ]\n" +
                "           }\n" +
                "        \n" +
                "    }\n" +
                "    \n" +
                "}";
        ByteArrayInputStream stream = new ByteArrayInputStream(params.getBytes());
        BasicHttpEntity httpEntity = new BasicHttpEntity();
        httpEntity.setContent(stream);
        httpEntity.setContentLength(params.getBytes().length);
        Response response = client.performRequest("POST", "http://192.168.1.40:9200/local_docinfo/doc/_search?pretty", basicHeader);
        InputStream inputStream = response.getEntity().getContent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            outStream.write(buff, 0, rc);
        }
        byte[] merge = outStream.toByteArray();
        String s = new String(merge);
        System.out.println("============================");
        System.out.println(s);
    }

    private DocInfoDao docInfoDao;





}
