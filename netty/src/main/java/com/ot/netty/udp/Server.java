package com.ot.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * UDP是一种无连接协议，相对于TCP协议而言，简单高效，适用于传输视频、音频等及时性要求高，
 * 但是准确率要求低的数据。Netty对UDP传输数据也进行了封装，实现起来特别简单。
 *
 * 首先是编写服务端启动类：
 */
public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap=new Bootstrap();
        try {

        }catch (Exception e){

        }
    }
}
