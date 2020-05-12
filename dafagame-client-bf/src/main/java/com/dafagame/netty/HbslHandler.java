package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.hbsl.Hbsl;
import com.dafagame.protocol.qzsg.Qzsg;
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


public class HbslHandler extends GameHandler {
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    static int count = 0;

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
                                    .setGameCode("107") //游戏
                                    .setRoundType("101")
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
                        //if (!isScenesReq) {
                        //    Hbsl.RoomInformationNtf sceneNtf = Hbsl.RoomInformationNtf.newBuilder().build();
                        //    sendBf(sceneNtf.toByteString(), Qzsg.ProtoType.ScenesReqType_VALUE, channel);
                        //    System.out.println("进入场景请求 send Success");
                        //    isScenesReq = true;
                        //}
                        break;

                    case Qzsg.ProtoType.PlayerEnterNtfType_VALUE: //玩家进入游戏广播
                        Qzsg.PlayerEnterNtf playerEnterNtf = Qzsg.PlayerEnterNtf.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Qzsg.ProtoType.PlayerEnterNtfType_VALUE + "玩家进入游戏广播")
                                .add("玩家", playerEnterNtf.getPlayer())
                                .build());
                        break;

                    case Hbsl.ProtoType.RoomInformationNtfType_VALUE: //场景通知
                        Hbsl.RoomInformationNtf roomInformationNtf = Hbsl.RoomInformationNtf.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Hbsl.ProtoType.RoomInformationNtfType_VALUE + "场景通知")
                                .add("SceneNtf", roomInformationNtf.getSceneNtf())
                                .add("rankNtf", roomInformationNtf.getRankNtf())
                                .add("Config", roomInformationNtf.getConfig())
                                .build());
                        break;
                    case Hbsl.ProtoType.SceneNtfType_VALUE: //房间信息
                        Hbsl.SceneNtf sceneNtf = Hbsl.SceneNtf.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Hbsl.ProtoType.SceneNtfType_VALUE + "房间信息")
                                .add("当前的红包", sceneNtf.getRedEnvelope())
                                .add("红包总数", sceneNtf.getTotal())
                                .add("当前红包领取排名", sceneNtf.getRanksList())
                                .build());
                        String id = sceneNtf.getRedEnvelope().getId();
                        System.out.println("id:" + id);//1100417
                        if (sceneNtf.getRedEnvelope() != null && !this.phone.equals("47876132")) {
                            Hbsl.CrabRedEnvelopeReq crabRedEnvelopeReq = Hbsl.CrabRedEnvelopeReq
                                    .newBuilder()
                                    .setId(id)
                                    .build();
                            sendBf(crabRedEnvelopeReq.toByteString(), Hbsl.ProtoType.CrabRedEnvelopeReqType_VALUE, channel);
                            System.out.println(this.phone + "获取红包");
                        }

                        if (count == 0 && this.phone.equals("47876132")) {
                            count++;
                            Hbsl.OutRedEnvelopeReq outRedEnvelopeReq = Hbsl.OutRedEnvelopeReq
                                    .newBuilder()
                                    .setMoney(10)
                                    .setTempIdx(1)
                                    .setBombNum(1)
                                    .setRepeated(1)
                                    .build();
                            sendBf(outRedEnvelopeReq.toByteString(), Hbsl.ProtoType.OutRedEnvelopeReqType_VALUE, channel);
                            System.out.println("红包发送");
                        }
                        break;

                    case Hbsl.ProtoType.CrabRedEnvelopeResType_VALUE: //获取红包回应
                        Hbsl.CrabRedEnvelopeRes crabRedEnvelopeRes = Hbsl.CrabRedEnvelopeRes.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Hbsl.ProtoType.CrabRedEnvelopeResType_VALUE + "获取红包回应")
                                .add("成功码", crabRedEnvelopeRes.getCode())
                                .add("获取的金钱", crabRedEnvelopeRes.getAward())
                                .add("玩家最新金额", crabRedEnvelopeRes.getIsBomb())
                                .add("是否踩雷", crabRedEnvelopeRes.getBalance())
                                .add("赔付金额", crabRedEnvelopeRes.getPayMoney())
                                .build());
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