package com.dafagame.nettyStudy.chat022;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel =ctx.channel();
        channelGroup.forEach(ch -> {
            if(channel!=ch){
                ch.writeAndFlush(channel.remoteAddress()+"发送的消息"+"\n");
            }else{
                ch.writeAndFlush("【自己】"+channel.remoteAddress()+"发送的消息:"+msg+"\n");
            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel =ctx.channel();
        channelGroup.writeAndFlush("【服务器】 - "+channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel =ctx.channel();
        channelGroup.writeAndFlush("【服务器】 - "+channel.remoteAddress()+"离开\n");
        System.out.println(channelGroup.size());//有多少链接
        //channelGroup自动移除channel
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel =ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel =ctx.channel();
        System.out.println(channel.remoteAddress()+" 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        //cause.printStackTrace();
        ctx.close();//出现异常时关闭链接
    }
}
