package com.dafagame.netty;

import com.dafagame.enums.Card;
import com.dafagame.proto.LandLoadMsg;
import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.world.World;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import lombok.Data;
import pers.utils.StringUtils.StringBuilders;
//import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class DafaHandler extends SimpleChannelInboundHandler {
    private ClientHandshaker handshaker;
    //    private ClientHandlerManager handlerManager;
    private ProtobufDecoder protobufDecoder = new ProtobufDecoder(Gate.ClientMsg.getDefaultInstance());

    private int uid; //自己的user-id
    private int order;//自己的座位号
    private List<Integer> cards; //自己的牌
    private boolean isEnterGame = false;//是否进入游戏
    private boolean isSceneInfoReq = false;//是否进入场景


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //super.handlerAdded(ctx);
        this.handshaker.setHandshakeFuture(ctx.newPromise());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Thread.sleep(1000);
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
                //handlerManager.handler(ctx.channel(),clientMsg); //消息分发处理
                //System.out.println("proto：" + clientMsg.getProto());
                switch (clientMsg.getProto()) {
                    case Gate.ProtoType.GateResType_VALUE:  //102登陆成功通知
                        System.out.println("102登陆成功通知：" + Gate.GateRes.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n", "").replaceAll("\t", ""));
                        Gate.GateRes gateRes = Gate.GateRes.parseFrom(clientMsg.getData());
                        uid = gateRes.getLoginRes().getUid();
                        //发送进入游戏请求
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("203") //游戏
                                    .setRoundType("104") //倍数场
                                    .build();
                            sendBf(enterGameReq.toByteString(), World.ProtoType.EnterGameReqType_VALUE, channel);//发送消息
                            System.out.println("102登陆游戏请求 send Success");
                            isEnterGame = true;
                        }
                        break;
                    case World.ProtoType.ErrorNtfType_VALUE:// 1004 错误消息通知
                        System.out.println(World.ErrorNtf.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n", "").replaceAll("\t", ""));
                        break;
                    case World.ProtoType.EnterGameResType_VALUE:   //1001 进入游戏通知
                        World.EnterGameRes.parseFrom(clientMsg.getData()).getGameCode();
                        System.out.println("1001进入游戏通知：" + World.EnterGameRes.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n", "").replaceAll("\t", ""));
                        //发送进入场景请求
                        if (!isSceneInfoReq) {
                            LandLoadMsg.SceneInfoReq sceneInfoReq = LandLoadMsg.SceneInfoReq.newBuilder().build();
                            sendBf(sceneInfoReq.toByteString(), LandLoadMsg.ProtoType.SceneInfoReqType_VALUE, channel);
                            System.out.println("20310进入场景请求 send Success");
                            isSceneInfoReq = true;
                        }
                        break;
                    case LandLoadMsg.ProtoType.SceneInfoResType_VALUE:   //20311 场景通知
                        System.out.println(LandLoadMsg.SceneInfoRes.parseFrom(clientMsg.getData()).toString().
                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.SceneInfoRes sceneInfoRes = LandLoadMsg.SceneInfoRes.parseFrom(clientMsg.getData());
                        //遍历玩家
                        for (LandLoadMsg.PlayerInfo playerInfo : sceneInfoRes.getPlayersList()) {
                            if (playerInfo.getUid() == uid) {
                                order = playerInfo.getOrder();//自己的座位号
                                System.out.println("自己的座位号：" + order);
                            }
                        }
                        break;

                    case LandLoadMsg.ProtoType.PlayerEnterTableResType_VALUE://20326 用户进出桌子通知
//                        System.out.println(LandLoadMsg.PlayerEnterTableRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.PlayerEnterTableRes playerEnterTableRes = LandLoadMsg.PlayerEnterTableRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("20326用户进出桌子通知")
                                        .add("玩家", playerEnterTableRes.getPlayer())
                                        .build()
                        );

                        break;
                    case LandLoadMsg.ProtoType.SendCardResType_VALUE://20312 发牌通知
//                        System.out.println(LandLoadMsg.SendCardRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.SendCardRes sendCardRes = LandLoadMsg.SendCardRes.parseFrom(clientMsg.getData());
                        cards = sendCardRes.getCardList();//自己的牌//排序

                        //System.out.println(cards);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("20312发牌通知")
                                        .add("牌", cards.toString())
                                        .add("叫牌位置", sendCardRes.getCallPosition())
                                        .add("底牌", sendCardRes.getCauseNoSuitableCard())
                                        .build()
                        );
                        int callPosition = sendCardRes.getCallPosition(); //第一个叫牌的座位号
                        if (callPosition == order) { //第一个是自己叫牌
                            System.out.println("等待4s");
                            Thread.sleep(4000);
                            //叫牌请求
                            LandLoadMsg.CallCardReq callCardReq = LandLoadMsg.CallCardReq
                                    .newBuilder()
                                    .setCallType(3) //3分
                                    .build();
                            sendBf(callCardReq.toByteString(), LandLoadMsg.ProtoType.CallCardReqType_VALUE, channel);//发送消息
                            System.out.println("20314叫牌请求发送成功");
                        }
                        break;

                    case LandLoadMsg.ProtoType.CallCardResType_VALUE:   // 20315 叫牌响应
                        //World.Msg msg1 = World.Msg.parseFrom(clientMsg.getData());
                        //System.out.println(LandLoadMsg.CallCardRes.parseFrom(msg1.getSendMsg().getData()));
//                        System.out.println(LandLoadMsg.CallCardRes.parseFrom(clientMsg.getData()).toString().
//                                replace("\n", "").replace("\t", ""));
                        LandLoadMsg.CallCardRes callCardRes = LandLoadMsg.CallCardRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(callCardRes.getOrder() + "，20315叫牌响应")
                                        .add("叫牌分数", callCardRes.getCallType())
                                        .add("order", callCardRes.getOrder())
                                        .add("landLoadUid", callCardRes.getLandLoadUid())
                                        .add("landLoadOrder", callCardRes.getLandLoadOrder())
                                        .add("hiddenCards", callCardRes.getHiddenCardsList())
                                        .add("timeout", callCardRes.getTimeout())
                                        .add("code", callCardRes.getCode())//1表示正常，0表示异常
                                        .build()
                        );
                        break;
                    case LandLoadMsg.ProtoType.CallDoubleResType_VALUE: //20317 加倍响应
//                        System.out.println(LandLoadMsg.CallDoubleRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.CallDoubleRes callDoubleRes = LandLoadMsg.CallDoubleRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(callDoubleRes.getOrder() + "，20317加倍响应")
                                        .add("isDouble", callDoubleRes.getIsDouble())
                                        .add("order", callDoubleRes.getOrder())
                                        .add("nextCallerOrder", callDoubleRes.getNextCallerOrder())
                                        .add("timeout", callDoubleRes.getTimeout())
                                        .add("code", callDoubleRes.getCode())
                                        .build()
                        );
                        //int[] c = new int[]{1,2,3};
                        //出牌请求
                        LandLoadMsg.PlayCardsReq playCardsReq = LandLoadMsg.PlayCardsReq
                                .newBuilder()
                                //.addAllCards()
                                .build();
                        sendBf(playCardsReq.toByteString(), LandLoadMsg.ProtoType.CallCardReqType_VALUE, channel);//发送消息
                        break;
                    case LandLoadMsg.ProtoType.PlayCardsResType_VALUE: //20319 出牌响应
//                        System.out.println(LandLoadMsg.PlayCardsRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.PlayCardsRes playCardsRes = LandLoadMsg.PlayCardsRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(playCardsRes.getOrder()+"，20319出牌响应")
                                        .add("uid", playCardsRes.getUid())
                                        .add("order", playCardsRes.getOrder())
                                        .add("nextPlayOrder", playCardsRes.getNextPlayOrder())
                                        .add("cards", playCardsRes.getCardsList())
                                        .add("cardsType", playCardsRes.getCardsType())
                                        .add("nextPlayCanHold", playCardsRes.getNextPlayCanHold())
                                        .add("timeout", playCardsRes.getTimeout())
                                        .add("code", playCardsRes.getCode())
                                        .build()
                        );
                        break;
                    case LandLoadMsg.ProtoType.AbandonResType_VALUE: //20321不要响应
//                        System.out.println(LandLoadMsg.PlayCardsRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.AbandonRes abandonRes = LandLoadMsg.AbandonRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(abandonRes.getOrder()+"，20321不要响应")
                                        .add("order", abandonRes.getOrder())
                                        .add("nextPlayOrder", abandonRes.getNextPlayOrder())
                                        .add("isNewTurn", abandonRes.getIsNewTurn())
                                        .add("nextPlayCanHold", abandonRes.getNextPlayCanHold())
                                        .add("nextPlayCanHold", abandonRes.getNextPlayCanHold())
                                        .add("timeout", abandonRes.getTimeout())
                                        .add("msg", abandonRes.getMsg())
                                        .add("code", abandonRes.getCode())
                                        .build()
                        );
                        break;
                    case LandLoadMsg.ProtoType.SysHostResType_VALUE: //20323托管响应
//                        System.out.println(LandLoadMsg.PlayCardsRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.SysHostRes sysHostRes = LandLoadMsg.SysHostRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(sysHostRes.getOrder()+"，20323托管响应")
                                        .add("uid", sysHostRes.getUid())
                                        .add("order", sysHostRes.getOrder())
                                        .add("code", sysHostRes.getCode())
                                        .add("msg", sysHostRes.getMsg())
                                        .build()
                        );
                        break;
                    case LandLoadMsg.ProtoType.CompetitionResultResType_VALUE: //20328结算响应
//                        System.out.println(LandLoadMsg.PlayCardsRes.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        LandLoadMsg.CompetitionResultRes competitionResultRes = LandLoadMsg.CompetitionResultRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("20328结算响应")
                                        .add("uid", competitionResultRes.getResultList())
                                        .build()
                        );
                        break;
                    default:
                        System.out.println("未解析协议proto：" + clientMsg.getProto());
                }
            }
        }
    }


    /**
     * 封装send方法
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //super.channelInactive(ctx);
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

    public static void main(String[] args) {
        int[] s = new int[]{28, 40, 44, 52, 5, 53, 42, 46, 54, 7, 11, 15, 27, 39, 43, 47, 57};
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            cards.add(Card.getCard(s[i]));
        }

        List<Character> chars = cards.stream()
                .sorted(Comparator.comparingInt(Card::getNum))
                .map((c) -> c.shown)
//                .forEach(System.out::print)
                .collect(Collectors.toList());
        System.out.println(chars);

        System.out.println("before: " + cards);

        char[] out = {'K', 'K'};
        outCard(cards, out);

        System.out.println("after: " + cards);
    }

    public static void outCard(List<Card> fromCard, char[] out) {
        List<Card> bb = new ArrayList<>();
        for (char c : out) {
            Card temp = null;
            for (Card cc : fromCard) {
                if (cc.shown == c) {
                    temp = cc;
                }

            }
            if (temp == null) {
                System.out.println("card dont exsit error");
                return;
            }

            bb.add(temp);
        }
        System.out.println(bb);
        fromCard.removeAll(bb);
    }
}
