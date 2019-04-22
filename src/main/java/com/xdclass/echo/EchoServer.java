package com.xdclass.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }
    public void run() throws InterruptedException {



            //线程组

            EventLoopGroup bossGrop = new NioEventLoopGroup();
            EventLoopGroup workGrop = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGrop, workGrop)
                    .channel(NioSctpServerChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            System.out.println("echo server 启动");
            //绑定端口，同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
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
