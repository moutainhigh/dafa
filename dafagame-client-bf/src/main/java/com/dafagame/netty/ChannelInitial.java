package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class ChannelInitial  extends ChannelInitializer<SocketChannel> {

    private SimpleChannelInboundHandler channelHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        // pipeline.addLast(new LengthFieldPrepender(4));
        //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(65535));
        // 协议包解码时指定Protobuf字节数实例化为CommonProtocol类型
        pipeline.addLast(new ProtobufDecoder(Gate.ClientMsg.getDefaultInstance()));
        // websocket定义了传递数据的6中frame类型
        //pipeline.addLast(channelHandler);???
        pipeline.addLast("hookedHandler",channelHandler);

    }

    public <T extends GameHandler>void setChannelHandler(T t) {
        this.channelHandler = t;
    }

}
