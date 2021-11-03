package com.ot.netty.dubbo.api;

public class AppServiceImpl implements AppService {
    @Override
    public String app(String msg) {
        return "api->" + msg;
    }
}
