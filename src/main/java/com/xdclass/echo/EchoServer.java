package com.xdclass.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }
    public void run() throws InterruptedException {


            /*      单线程模型
                    EventLoopGroup workGrop = new NioEventLoopGroup(1);   //
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workGrop).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
             */
        //线程组
            EventLoopGroup bossGrop = new NioEventLoopGroup();   //默认线程数量未cpu核数*2
            EventLoopGroup workGrop = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGrop, workGrop)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024) //io连接的等待队列长度
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            System.out.println("echo server 启动");
            //绑定端口，同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            System.out.println("ChannelFuture....===="+channelFuture.get());
            channelFuture.channel().closeFuture().sync();
        } catch (ExecutionException e) {
        } finally {
                bossGrop.shutdownGracefully();
                workGrop.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port =8080;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).run();
    }
}
