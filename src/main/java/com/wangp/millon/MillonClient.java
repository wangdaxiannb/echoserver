package com.wangp.millon;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MillonClient {

    private static final  String ip_Address  ="192.168.100.128";
    public static void main(String[] args) {

        new MillonClient().run(Config.START_PORT,Config.END_PORT);
    }

    public void run(int start,int end){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });

        int index = 0;
        int firstPort;
        while (true){
            firstPort = start+index;
            try {
                bootstrap.connect(ip_Address,firstPort).addListener((ChannelFutureListener)future ->{
                    if(!future.isSuccess()){
                        System.out.println("客户端连接失败...............");
                    }
                }).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            index++;
            if(index == (end-start)){
                index = 0;
            }

        }

    }
}
