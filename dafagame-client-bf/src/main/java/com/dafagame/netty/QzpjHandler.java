package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.qzpj.Qzpj;
import com.dafagame.protocol.world.World;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.Getter;
import lombok.Setter;
import pers.utils.StringUtils.StringBuilders;


public class QzpjHandler extends GameHandler {
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        if (!handshaker.isSuccess()) {
            handshaker.isSuccess(ctx.channel(), msg);
        } else if (msg instanceof WebSocketFrame) {
            if (msg instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame frame = ((BinaryWebSocketFrame) msg);
                ByteBuf buf = frame.content();
                byte[] bytes = new byte[buf.readableBytes()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = buf.getByte(i);
                }
                Gate.ClientMsg clientMsg = Gate.ClientMsg.parseFrom(bytes);
                //handlerManager.handler(ctx.channel(),clientMsg);
                System.out.println("proto：" + clientMsg.getProto() + "-----------------------------------------------------------------------------");
                switch (clientMsg.getProto()) {
                    case World.ProtoType.ErrorNtfType_VALUE:// 1004 错误消息通知
                        System.out.println(World.ErrorNtf.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n", "").replaceAll("\t", ""));
                        break;
                    case Gate.ProtoType.GateResType_VALUE:  //登陆成功通知
                        Gate.GateRes gateRes = Gate.GateRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Gate.ProtoType.GateResType_VALUE + "登陆响应")
                                        .add("昵称", gateRes.getLoginRes().getPlayer().getNickName())
                                        .add(phone + "登陆回应", gateRes.getLoginRes().toString().replaceAll("\n", ",").replaceAll("\t", ""))
                                        .add(phone + "code", gateRes.getErrorCode())
                                        .build()
                        );
                        //uid = gateRes.getLoginRes().getUid(); //设置用户id
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("206") //游戏
                                    .setRoundType("101") //
                                    .build();
                            sendBf(enterGameReq.toByteString(), World.ProtoType.EnterGameReqType_VALUE, channel);//发送消息
                            System.out.println(World.ProtoType.EnterGameReqType_VALUE + "进入游戏请求 send Success");
                            isEnterGame = true;
                        }
                        break;
                    case World.ProtoType.EnterGameResType_VALUE: //进入游戏响应
                        World.EnterGameRes enterGameRes = World.EnterGameRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(World.ProtoType.EnterGameResType_VALUE + "进入游戏响应")
                                        .add(phone + "-游戏code", enterGameRes.getGameCode())
                                        .add("msg", enterGameRes.getMsg())
                                        .add("errorCode", enterGameRes.getErrorCode())
                                        .build()
                        );
                        //发送进入场景请求
                        if (!isScenesReq) {
                            Qzpj.ScenesReq scenesReq = Qzpj.ScenesReq.newBuilder().build();
                            sendBf(scenesReq.toByteString(), Qzpj.ProtoType.ScenesReqType_VALUE, channel);
                            System.out.println("进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;

                    case Qzpj.ProtoType.ScenesResType_VALUE: //场景回应
                        Qzpj.ScenesRes SceneRes = Qzpj.ScenesRes.parseFrom(clientMsg.getData());
                        System.out.println(SceneRes.parseFrom(clientMsg.getData()).toString());
                        break;

                }
            }
        }
    }

    /**
     * send方法
     */
    public void sendBf(ByteString byteString, int protoType, Channel channel) {
        Gate.ClientMsg sendMsg = Gate.ClientMsg.newBuilder()
                .setProto(protoType)
                .setData(byteString)
                .build();
        ByteBuf bf = Unpooled.buffer().writeBytes(sendMsg.toByteArray());
        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(bf);
        channel.writeAndFlush(binaryWebSocketFrame);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshaker.setHandshakeFuture(ctx.newPromise());
    }

}