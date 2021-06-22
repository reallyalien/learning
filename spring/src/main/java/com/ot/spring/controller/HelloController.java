package com.ot.spring.controller;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Controller
@Scope("prototype")
/**
 * singleton:单例 容器初始化就创建好
 * prototype：多例 需要的时候才创建
 * request：一个请求创建一个
 * session：
 * application：servletContext
 * websocket：
 */

public class HelloController {

    private int index;

    private static int staticIndex;

    public String doRequest() {
        System.out.println("成员变量：" + (index++) + ",静态变量：" + staticIndex++);
        return null;
    }

    @PostConstruct
    public void init(){

    }
}
