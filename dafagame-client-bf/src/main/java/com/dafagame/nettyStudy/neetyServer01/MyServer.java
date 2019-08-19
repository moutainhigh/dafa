package com.dafagame.nettyStudy.neetyServer01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) //服务Nio
                    .option(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new MyServerInitializer());//childHandler是给workerGroup作用

            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boosGroup.shutdownGracefully();//优雅关闭
            workerGroup.shutdownGracefully();
        }








    }


}
