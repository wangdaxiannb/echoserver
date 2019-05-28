package com.wangp.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private String host;
    private int port;
    public EchoClient(String h,int p){
        this.host = h;
        this.port = p;
    }

    public  void start(){

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .option(ChannelOption.TCP_NODELAY,true)  //tcp连接有数据直接发送 不需要等待大数据一起发送
                    .handler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
       /*     Channel channel = future.channel();
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("请输入：");
                channel.write(scanner.next());
            }*/

           future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient("127.0.0.1",8080);
        client.start();
    }

}
