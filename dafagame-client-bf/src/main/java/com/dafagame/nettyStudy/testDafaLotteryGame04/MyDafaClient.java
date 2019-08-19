package com.dafagame.nettyStudy.testDafaLotteryGame04;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

/**
 *链接 彩票棋牌游戏 测试
 * */
public class MyDafaClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            URI uri = URI.create("ws://m.caishen02.com/gameServer/?TOKEN=3b2a03c8097d412287573ab4f4cda5fb&gameId=2003");


            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) //客户端Nio
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_BACKLOG,1024*1024*100)
                    .handler(new MyDafaClientInitializer());//childHandler,谁处理


           //final  Channel channel = bootstrap.connect(uri.getHost(),80).sync().channel();
            final Channel channel=bootstrap.connect(uri.getHost(),80).sync().channel();
//            WebSocketClientHandshaker handshaker =WebSocketClientHandshakerFactory.newHandshaker(
//                    uri, WebSocketVersion.V13, (String)null, true,
//                    new DefaultHttpHeaders(),65536*100);
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String)null, true,httpHeaders);
            System.out.println("connect");
            MyDafaClientHandler handler = (MyDafaClientHandler)channel.pipeline().get("hookedHandler");
            System.out.println("handler:"+handler);
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            handler.handshakeFuture().sync();

            while (true){
                Thread.sleep(1000);
            }

            /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                channel.writeAndFlush(br.readLine()+"\r\n");
            }*/

            //channelFuture.channel().closeFuture().sync();
        }finally {
            //eventLoopGroup.shutdownGracefully();//优雅关闭
        }

    }

}
