package com.ot.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler->add");
    }

    /**
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //确认evt事件类型
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            switch (event.state()) {
                case ALL_IDLE:
                    type = "读写空闲";
                    break;
                case READER_IDLE:
                    type = "读空闲";
                    break;
                case WRITER_IDLE:
                    type = "写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件发生，" + "type:" + type);
            //作相应的处理
        }
    }
}
