package com.ot.tomcat.myTomcat;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义的请求
 */
@Data
public class MyRequest {

    private String url;
    private String method;

    public MyRequest(InputStream inputStream) throws IOException {
        String httpRequest = "";
        byte[] httpRequestBytes = new byte[1024];
        int length = 0;
        if ((length = inputStream.read(httpRequestBytes)) > 0) {
            httpRequest = new String(httpRequestBytes, 0, length);
        }
        //HTTP请求协议:首行的内容依次为：请求方法、请求路径以及请求协议及其对应版本号 // GET /index HTTP/1.1
        String httpHead = httpRequest.split("\n")[0]; //取出HTTP请求协议的首行
        System.out.println(httpHead);
        method = httpHead.split("\\s")[0]; //按照空格进行分割，第一个是请求的方法
        url = httpHead.split("\\s")[1]; //按照空格进行分割，第二个是请求的路径
    }
}
