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
import org.apache.commons.lang.StringUtils;
import pers.utils.StringUtils.StringBuilders;

import java.util.List;
import java.util.stream.Collectors;


public class HbslHandler extends GameHandler {
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    private static int redEnvelopeCount = 0;
    private int redEnvelopeLength;

    private int uid;

    private String currentRedEnvelopeId;


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
                                        .add(phone + "/登陆回应", gateRes.getLoginRes().toString().replaceAll("\n", ",").replaceAll("\t", ""))
                                        .add(phone + "code", gateRes.getErrorCode())
                                        .build()
                        );
                        uid = gateRes.getLoginRes().getUid(); //用户id
                        System.out.println(uid);
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("107") //游戏
                                    .setRoundType(roundType)
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
                            Hbsl.SceneReq sceneNtf = Hbsl.SceneReq.newBuilder().build();
                            sendBf(sceneNtf.toByteString(), Hbsl.ProtoType.SceneReqType_VALUE, channel);
                            System.out.println("进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;
                    case Hbsl.ProtoType.SceneResType_VALUE: //场景通知
                        Hbsl.SceneRes sceneRes = Hbsl.SceneRes.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Hbsl.ProtoType.SceneResType_VALUE + "场景通知")
                                .add("房间场景信息", sceneRes.getSceneNtf())
                                //.add("", roomInformationNtf.getRankNtf())
                                .add("红包的配置", sceneRes.getConfig())
                                .add("当前红包的信息", sceneRes.getRedNtf())
                                .build());
                        if (userType == 2)
                            break;
                        OutRedEnvelopeReq(channel);//发送红包
                        break;
                    case Hbsl.ProtoType.RedEnvelopeNtfType_VALUE: //红包信息 （当前红包信息，当前红包的） 1s 钟一次
                        Hbsl.RedEnvelopeNtf redEnvelopeNtf = Hbsl.RedEnvelopeNtf.parseFrom(clientMsg.getData());
                        //System.out.println(StringBuilders.custom()
                        //        .add(Hbsl.ProtoType.RedEnvelopeNtfType_VALUE + "红包信息】")
                        //        .add("红包id", redEnvelopeNtf.getRedEnvelope().getId())
                        //        .add("红包生成时间点", redEnvelopeNtf.getRedEnvelope().getCreatedTime())
                        //        .add("剩余金钱", redEnvelopeNtf.getRedEnvelope().getTotalMoney())
                        //        .add("剩余包数", redEnvelopeNtf.getRedEnvelope().getMultiple())
                        //        .add("总包数", redEnvelopeNtf.getRedEnvelope().getTotalMultiple())
                        //        .add("雷号", redEnvelopeNtf.getRedEnvelope().getBombNum())
                        //        .add("发包人昵称", redEnvelopeNtf.getRedEnvelope().getOwnerName())
                        //        .build());
                        //
                        if (userType == 1)
                            break;
                        if (StringUtils.isEmpty(currentRedEnvelopeId) || !redEnvelopeNtf.getRedEnvelope().getId().equals(currentRedEnvelopeId)) {
                            currentRedEnvelopeId = redEnvelopeNtf.getRedEnvelope().getId();
                            sendRedEnvelopeReq(redEnvelopeNtf.getRedEnvelope().getTotalMultiple(), channel);
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
                    case Hbsl.ProtoType.OutRedEnvelopeResType_VALUE: //埋雷红包回应
                        Hbsl.OutRedEnvelopeRes outRedEnvelopeRes = Hbsl.OutRedEnvelopeRes.parseFrom(clientMsg.getData());
                        System.out.println(StringBuilders.custom()
                                .add(Hbsl.ProtoType.OutRedEnvelopeResType_VALUE + "埋雷红包回应】")
                                .add("成功码", outRedEnvelopeRes.getCode())
                                .add("获取的金钱", outRedEnvelopeRes.getBalance())
                                .build());
                        break;

                    case Hbsl.ProtoType.SceneNtfType_VALUE: //房间信息
                        Hbsl.SceneNtf sceneNtf = Hbsl.SceneNtf.parseFrom(clientMsg.getData());
                        //System.out.println(StringBuilders.custom()
                        //        .add(Hbsl.ProtoType.SceneNtfType_VALUE + "房间信息】")
                        //        //.add("当前红包uid", sceneNtf.getRedUids(0))
                        //        .build());
                        redEnvelopeLength = sceneNtf.getRedUidsList().size();
                        redEnvelopeCount = sceneNtf.getRedUidsList().stream().filter((Integer i) -> i == uid).collect(Collectors.toList()).size();
                        break;
                }
            }
        }
    }

    public void sendRedEnvelopeReq(int total, Channel channel) {
        new Thread(() -> {
            try {
                Thread.sleep((5 + (int) (Math.random() * 5)) * 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (total != 0) {
                Hbsl.CrabRedEnvelopeReq crabRedEnvelopeReq =
                        Hbsl.CrabRedEnvelopeReq.newBuilder()
                                .setId(currentRedEnvelopeId)
                                .build();
                sendBf(crabRedEnvelopeReq.toByteString(), Hbsl.ProtoType.CrabRedEnvelopeReqType_VALUE, channel);
                System.out.println("打开红包");
            }
        }).start();
    }


    public void OutRedEnvelopeReq(Channel channel) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            for (; ; ) {

                if (redEnvelopeCount < 6 && redEnvelopeLength < 100) {
                    Hbsl.OutRedEnvelopeReq outRedEnvelopeReq = Hbsl.OutRedEnvelopeReq
                            .newBuilder()
                            .setMoney(10)
                            .setTempIdx(0)
                            .setRepeated(1)
                            .setBombNum(6)
                            .build();
                    sendBf(outRedEnvelopeReq.toByteString(), Hbsl.ProtoType.OutRedEnvelopeReqType_VALUE, channel);
                    System.out.println("埋雷红包");
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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