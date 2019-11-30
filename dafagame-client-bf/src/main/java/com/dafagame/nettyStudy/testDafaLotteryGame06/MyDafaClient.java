package com.dafagame.nettyStudy.testDafaLotteryGame06;

import com.dafagame.protocol.gate.Gate;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

/**
 * 彩票棋牌游戏 测试
 * */
public class MyDafaClient {

    public void test() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            URI uri = URI.create("ws://192.168.8.193:7100");


            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) //客户端Nio
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_BACKLOG,1024*1024*100)
                    .handler(new MyDafaClientInitializer());//childHandler,谁处理

            ChannelFuture f = bootstrap.connect(uri.getHost(),7110).sync();
            Channel channel=f.channel();
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String)null, true,httpHeaders);

            MyDafaClientHandler handler = (MyDafaClientHandler)channel.pipeline().get("hookedHandler");
            System.out.println("handler:"+handler);
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);

            while(!handshaker.isHandshakeComplete()) {
                Thread.sleep(100);
            }

            Gate.GateReq gateReq = Gate.GateReq.newBuilder()
                    .setLoginReq(
                            Gate.LoginReq.newBuilder()
                                    .setSessionId("82ceffdc92fc46619cc77cb94c942587")
                                    .setUrl("192.168.8.44:7000")
                                    .setSourceId("2")
                                    .setTenantCode("testCookie")
                                    .build()
                    ).build();

            Gate.ClientMsg clientMsg = Gate.ClientMsg.newBuilder()
                    .setProto(Gate.ProtoType.GateReqType_VALUE)
                    .setData(gateReq.toByteString())
                    .build();

            ByteBuf bf= Unpooled.buffer().writeBytes(clientMsg.toByteArray());
            BinaryWebSocketFrame binaryWebSocketFrame=new BinaryWebSocketFrame(bf);
            channel.writeAndFlush(binaryWebSocketFrame);
            System.out.println("send message Success");



            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int counter = 0;
                        while(counter++ <= 10){
                            Thread.sleep(1000);
                            ByteBuf bf= Unpooled.buffer().writeBytes(clientMsg.toByteArray());
                            BinaryWebSocketFrame binaryWebSocketFrame=new BinaryWebSocketFrame(bf);
                            channel.writeAndFlush(binaryWebSocketFrame);
                            System.out.println("send message Success");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
            handler.handshakeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();//优雅关闭
        }
    }

}
