package com.dafagame.nettyStudy.testDafaLotteryGame03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class MyDafaClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            URI uri = URI.create("ws://m.caishen02.com/gameServer/?TOKEN=356d8fe4fded41a3bea1f42b994107ac&gameId=2003");
            MyDafaClientHandler myDafaClientHandler = new MyDafaClientHandler(WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13, (String)null, true,
                    new DefaultHttpHeaders(),65536*100));

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) //客户端Nio
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new MyDafaClientInitializer(myDafaClientHandler));//childHandler,谁处理


            Channel channel = bootstrap.connect(uri.getHost(),80).channel();


            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                channel.writeAndFlush(br.readLine()+"\r\n");
            }

            //channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();//优雅关闭
        }








    }


}
