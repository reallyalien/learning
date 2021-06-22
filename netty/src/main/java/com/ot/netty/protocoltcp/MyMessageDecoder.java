package com.ot.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 解码器
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println("MyMessageDecoder的decode被调用");
        //将得到的二进制字节码转成messageProtocol
        int length = in.readInt();
        byte[] content = new byte[length];
        //调用readInt方法之后，内部指针会先后移动，因此读取byte直接可以获取到message
        in.readBytes(content);
        //封装成messageProtocol
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);
        //让下一个handler处理
        out.add(messageProtocol);
    }
}
