package com.wangp.millon;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class MillonServer {


    public void run(int start,int end) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_REUSEADDR, true);//快速复用端口
          /*      .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerCounthandler());
                    }
                });*/
        bootstrap.childHandler(new ServerCounthandler());
        for (int i = start; i <end ; i++) {
            int port = i;
            bootstrap.bind(port).addListener((ChannelFutureListener)future ->{
                System.out.println("服务端启动成功，绑定的端口port为："+port);
            });
        }
    }
    public static void main(String[] args) {
        try {
            new MillonServer().run(Config.START_PORT,Config.END_PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
