package com.wangp.millon;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class ServerCounthandler extends ChannelInboundHandlerAdapter {


    private AtomicInteger count = new AtomicInteger();

    public ServerCounthandler(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            System.out.println("目前所获得的Socker连接数count为"+count.get());
        },0,3,TimeUnit.SECONDS);

    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        count.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      count.decrementAndGet();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
