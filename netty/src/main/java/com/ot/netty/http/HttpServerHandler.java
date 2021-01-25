package com.ot.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 当保持长连接时，如何判断一次请求已经完成？
 * Content-Length
 * Content-Length表示实体内容的长度。浏览器通过这个字段来判断当前请求的数据是否已经全部接收。
 * 所以，当浏览器请求的是一个静态资源时，即服务器能明确知道返回内容的长度时，可以设置Content-Length来控制请求的结束。
 * 但当服务器并不知道请求结果的长度时，如一个动态的页面或者数据，Content-Length就无法解决上面的问题，这个时候就需要
 * 用到Transfer-Encoding字段。
 *
 * Transfer-Encoding
 * Transfer-Encoding是指传输编码，在上面的问题中，当服务端无法知道实体内容的长度时，就可以通过指定Transfer-Encoding:
 * chunked来告知浏览器当前的编码是将数据分成一块一块传递的。当然, 还可以指定Transfer-Encoding: gzip, chunked表明实体
 * 内容不仅是gzip压缩的，还是分块传递的。最后，当浏览器接收到一个长度为0的chunked时， 知道当前请求内容已全部接收。
 */

/**
 * 1.客户端与服务器端交互的数据被封装成HttpObject
 * 2.SimpleChannelInboundHandler继承ChannelInboundHandlerAdapter
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据 HttpRequest ->HttpMessage->HttpObject
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是http请求
        if (msg instanceof HttpRequest){

            System.out.println("pipeline 的hashcode："+ctx.pipeline().hashCode()+"\t"+"HttpServerHandler的hashcode："+this.hashCode());
            //获取到msg的请求路径
            HttpRequest httpRequest = (HttpRequest) msg;
            String uri = httpRequest.uri();
//            if (uri.contains("favicon.ico")){
//                return;
//            }
            System.out.println("msg类型："+msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());
            //回复信息给浏览器，得满足http协议
            ByteBuf content= Unpooled.copiedBuffer("hello,我是服务器",CharsetUtil.UTF_8);

            //构造httpResponse
            /**
             * http1.0协议每一次http请求都会打开tcp连接
             */
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            //设置其他信息
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好的对象返回
            ctx.writeAndFlush(httpResponse);
        }
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("http连接失效");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("http连接未注册");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("http连接中断");
    }
}
