package com.ot.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建2个group，线程组
        //boosGroup只处理连接请求
        //workGroup真正与客户端进行交互io请求
        //2个都是无限循环
        //bossGroup和workGroup含有的子线程默认是cpu的核数*2
        DefaultEventExecutorGroup group=new DefaultEventExecutorGroup(10);
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //创建服务器端启动器,
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置启动参数
            serverBootstrap
                    .group(bossGroup, workGroup)//设置2个线程组
                    .channel(NioServerSocketChannel.class) //设置NioServerSocketChannel作为服务器通道实现
                    //服务端一个个接受客户端的请求，多个客户端来的时候，将不能处理的客户端请求放入队列当中，就是这里指定的参数
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置整个连接保持长连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道对象（匿名对象）
                        @Override
                        //向pipeline设置处理器
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            System.out.println("客户对应的channel的hashCode："+ch.hashCode());
                            //可以使用一个集合去管理所有的channel
                            ChannelPipeline pipeline = ch.pipeline();//返回channel关联的pipeline
                            pipeline.addLast(group, null, new NettyServerHandler());//加入管道，如果业务会影响
                        }
                    });//给workGroup的某一个eventLoop的对应的管道设置处理器
            System.out.println("服务器准备好....");
            //绑定一个端口，并且同步处理，生成一个channelFuture,这里相当于启动服务器,异步绑定端口，sync()阻塞直到绑定完成
            ChannelFuture bind = serverBootstrap.bind(6668);
            ChannelFuture channelFuture = bind.sync();
            //给future注册监听，监听我们关心的时间,如果此监听在添加到channelFuture的时候已经完成，那么此方法会被直接调用
            //也消除了手动检查对应的操作完成的必要
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("监听端口6668成功");
                    } else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });
            //对关闭通道进行监听，只是监听，而不是立马关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
