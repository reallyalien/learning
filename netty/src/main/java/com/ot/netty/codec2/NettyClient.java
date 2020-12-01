package com.ot.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //只需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建一个启动器
            Bootstrap bootstrap = new Bootstrap();
            //绑定参数
            bootstrap
                    .group(group)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类(反射来处理)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("endoder",new ProtobufEncoder());//protobuf的编码器
                            pipeline.addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端准备好了....");
            //启动客户端连接服务端
            //关于channelFuture，netty异步模型，sync让方法不会阻塞,非阻塞，bind是异步操作
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }
}
