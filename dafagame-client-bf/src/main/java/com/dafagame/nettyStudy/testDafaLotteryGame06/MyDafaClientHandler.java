package com.dafagame.nettyStudy.testDafaLotteryGame06;

import com.dafagame.protocol.gate.Gate;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

public class MyDafaClientHandler extends SimpleChannelInboundHandler<Object> {

    WebSocketClientHandshaker handshaker;

    ChannelPromise handshakeFuture;


    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (!handshaker.isSuccess()) {
//            handshaker.isSuccess(ctx.channel(),msg);
//        } else if (msg instanceof WebSocketFrame) {
        System.out.println("返回数据");
        FullHttpResponse response;
        if (msg instanceof FullHttpResponse){
            handshaker.finishHandshake(ctx.channel(),(FullHttpResponse)msg);
            System.out.println("isHandshakeComplete : "+handshaker.isHandshakeComplete());
            response = (FullHttpResponse)msg;
            System.out.println(response.status());
            System.out.println(response.content().toString(CharsetUtil.UTF_8));
            //System.out.println(((FullHttpResponse)msg).content());
        } else if (msg instanceof WebSocketFrame) {
            if (msg instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame frame = ((BinaryWebSocketFrame)msg);
                ByteBuf buf = frame.content();
                byte[] bytes = new byte[buf.readableBytes()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = buf.getByte(i);
                }
                Gate.ClientMsg clientMsg = Gate.ClientMsg.parseFrom(bytes);
                //handlerManager.handler(ctx.channel(),clientMsg);
                //System.out.println(clientMsg);
                switch (clientMsg.getProto()){
                    case Gate.ProtoType.GateResType_VALUE:
                        System.out.println(Gate.GateRes.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n","").replaceAll("\t",""));
                        break;
                }
            }
        } else {
            System.out.println(msg.getClass());
        }


    }




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("close:" + ctx.channel().id());
//        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}
