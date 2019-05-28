package com.wangp.chapter7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    //使用换行符来解决tcp半包读写
/*    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = null;
        byte[] req = ("小弟"+System.getProperty("line.separator")).getBytes();
        for (int i = 0; i < 100; i++) {
            byteBuf = Unpooled.buffer(req.length);
            byteBuf.writeBytes(req);
            ctx.writeAndFlush(byteBuf);
        }

    }*/

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String message = "AAAAAAAAAAAAAA AAAAAAAAA AAAAAAAAAAAA&&" +
                "BBBBBBBBBB BBBB BBB B&&" +
                "C C C C CCCCCCC C&&" +
                "D D DD  DDD  DDDDD&&";  //结尾没有分隔符，最后一行丢失
        ByteBuf byteBuf = null;
        byteBuf = Unpooled.buffer(message.getBytes().length);
        byteBuf.writeBytes(message.getBytes());
        ctx.writeAndFlush(byteBuf);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
