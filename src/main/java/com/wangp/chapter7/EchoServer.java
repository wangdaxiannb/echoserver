package com.wangp.chapter7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


public class EchoServer {

    private int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void  start() throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap  bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024) //io已经获得连接，需要等待的队列。
                    .option(ChannelOption.TCP_NODELAY,true) //要求实时性设为true,关闭Nagle算法;默认为false，开启Nagle算法
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
                            //ch.pipeline().addLast(new LineBasedFrameDecoder(7));//限制字符最大长度为7，以换行符进行获取
                            ByteBuf copiedBuffer = Unpooled.copiedBuffer("&&".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,true,copiedBuffer)); //true表示接受数据不带分隔符
                            ch.pipeline().addLast(new StringDecoder()); //将对象解码成字符串
                            ch.pipeline().addLast(new ServerHandler());//
                        }
                    });
            //绑定端口同步等待成功
            ChannelFuture sync = bootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) {

        try {
            new EchoServer(8080).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
