package com.dafagame.nettyStudy.chat022;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"，"+msg);//服务端远程地址
        //ctx.channel().writeAndFlush("from clinet...");
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //super.channelActive(ctx);
//        //触发channelRead0被调用
//        ctx.writeAndFlush("来源客户端的信息...");
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        //cause.printStackTrace();
        ctx.close();//出现异常时关闭链接
    }
}
