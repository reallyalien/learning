package com.ot.netty.dubbo;

import com.ot.netty.dubbo.netty.NettyServer;

public class Provider {

    public static void main(String[] args) {
        NettyServer.start(9999);
    }
}
