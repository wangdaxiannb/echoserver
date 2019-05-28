package com.wangp.chapter7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    private  int sum;

/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf  buf =  (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String body = new String(bytes,"UTF-8").substring(0,bytes.length-System.getProperty("line.separator").length());
            System.out.println("服务端收到的消息为："+body+"收到的消息次数"+ ++sum);
    }*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String mes = (String) msg;
        System.out.println("服务端收到的消息为："+mes+"收到的消息次数"+ ++sum);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
