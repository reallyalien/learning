package com.ot.netty.dubbo;

import com.ot.http.dubbo.HelloService;
import com.ot.netty.dubbo.api.AppService;
import com.ot.netty.dubbo.netty.NettyClient;

public class Consumer {
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        AppService service = (AppService) nettyClient.getProxy(AppService.class);
        String hello = service.app("1");
        System.out.println(hello);
    }
}
