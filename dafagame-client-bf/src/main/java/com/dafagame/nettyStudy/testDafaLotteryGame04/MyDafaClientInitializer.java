package com.dafagame.nettyStudy.testDafaLotteryGame04;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class MyDafaClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
       // pipeline.addLast(new LengthFieldPrepender(4));
        //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ChannelHandler[]{new HttpClientCodec(),
                new HttpObjectAggregator(1024*1024*100)});

        //pipeline.addLast(new MyDafaClientHandler());
        pipeline.addLast("hookedHandler",new MyDafaClientHandler());

    }
}
