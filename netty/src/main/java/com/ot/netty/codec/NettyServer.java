package com.ot.netty.codec;

import com.google.protobuf.MessageLite;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * proto 文件生成命令
 * a.-I :proto文件所在路径
 * b.--java_out:生成的java文件的路径
 * c.要生成的java文件的proto文件
 * protoc -I=./  --java_out=./ ./Student.proto
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建2个group，线程组
        //boosGroup只处理连接请求
        //workGroup真正与客户端进行交互io请求
        //2个都是无限循环
        //bossGroup和workGroup含有的子线程默认是cpu的核数*2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //创建服务器端启动器,
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置启动参数
            serverBootstrap
                    .group(bossGroup, workGroup)//设置2个线程组
                    .channel(NioServerSocketChannel.class) //设置NioServerSocketChannel作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置整个连接保持长连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道对象（匿名对象）
                        @Override
                        //向pipeline设置处理器
                        protected void initChannel(SocketChannel ch) throws Exception {
//
                            ChannelPipeline pipeline = ch.pipeline();//返回channel关联的pipeline
                            pipeline.addLast(new ProtobufDecoder(StudentPojo.Student.getDefaultInstance()));//指定对哪种对象解码
                            pipeline.addLast(new NettyServerHandler());//加入管道
                        }
                    });//给workGroup的某一个eventLoop的对应的管道设置处理器
            System.out.println("服务器准备好....");
            //绑定一个端口，并且同步处理，生成一个channelFuture,这里相当于启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //给future注册监听，监听我们关心的时间
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else {
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
        }
    }
}
