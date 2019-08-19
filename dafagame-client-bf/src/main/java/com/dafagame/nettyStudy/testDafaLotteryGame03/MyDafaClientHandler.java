package com.dafagame.nettyStudy.testDafaLotteryGame03;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;

public class MyDafaClientHandler extends SimpleChannelInboundHandler<String> {

    private WebSocketClientHandshaker handshaker;

    public MyDafaClientHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(this.handshaker.isHandshakeComplete());
        System.out.println(ctx.channel().remoteAddress()+"，"+msg);//服务端远程地址
        //ctx.channel().writeAndFlush("from clinet...");
        System.out.println("111");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
        //ctx.writeAndFlush("来源客户端的信息...");
        System.out.println(this.handshaker.isHandshakeComplete());
        System.out.println("2222");

    }


//    public void setHandshaker(WebSocketClientHandshaker handshaker) {
//        this.handshaker = handshaker;
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        //cause.printStackTrace();
        ctx.close();//出现异常时关闭链接
    }
}
