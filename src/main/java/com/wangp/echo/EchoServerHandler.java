package com.wangp.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private Integer      integer  = 0;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered ...... ");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered ...... ");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
              integer++;

        System.out.println("channelActive ...... "+integer);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       integer--;
        System.out.println("channelInactive ...... "+integer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf =(ByteBuf) msg;
        System.out.println("服务端接受数据："+byteBuf.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush("你好啊");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete ...... ");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
      ctx.close();
    }


}
