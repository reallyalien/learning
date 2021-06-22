package com.ot.http.dubbo;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return msg + "http dubbo";
    }
}
