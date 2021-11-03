package com.ot.netty.dubbo.netty;

import com.ot.netty.dubbo.constant.ProtocolHeader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NettyClient {

    private static final DefaultEventLoopGroup executors = new DefaultEventLoopGroup(1);

    private NettyClientHandler clientHandler = null;

    public Object getProxy(Class clazz) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String sendMsg = args[0].toString();
                clientHandler.setParam(ProtocolHeader.HEADER + sendMsg);
                Future future = executors.submit(clientHandler);
                return future.get();
            }
        });
    }

    public NettyClient() {
        clientHandler = new NettyClientHandler();
        NioEventLoopGroup client = null;
        try {
            client = new NioEventLoopGroup(1);
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    pipeline.addLast(clientHandler);
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();
            System.out.println("客户端启动完成");
        } catch (Exception e) {

        }
    }
}
