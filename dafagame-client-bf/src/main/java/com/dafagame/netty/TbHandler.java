package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.tb.Tb;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TbHandler extends GameHandler {

    private int uid; //自己的user-id
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    private List<Integer> chipList;

    private Tb.State state;

    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshaker.setHandshakeFuture(ctx.newPromise());
    }

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
                //handlerManager.handler(ctx.channel(),clientMsg); //消息分发处理
                //System.out.println("proto：" + clientMsg.getProto());
                switch (clientMsg.getProto()) {
                    case Gate.ProtoType.GateResType_VALUE:  //102登陆成功通知
                        //System.out.println("102登陆成功通知：" + Gate.GateRes.parseFrom(clientMsg.getData()).toString().
                        //        replaceAll("\n", "").replaceAll("\t", ""));
                        Gate.GateRes gateRes = Gate.GateRes.parseFrom(clientMsg.getData());
                        //System.out.println(
                        //        StringBuilders.custom()
                        //                .add("102登陆请求")
                        //                .add(phone + "登陆回应", gateRes.getLoginRes().toString().replaceAll("\n", "").replaceAll("\t", ""))
                        //                .add(phone + "code", gateRes.getErrorCode())
                        //                .build()
                        //);
                        //uid = gateRes.getLoginRes().getUid(); //设置用户id
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("105") //游戏
                                    .setRoundType("101") //倍数场 101四倍场，102十倍场
                                    .build();
                            sendBf(enterGameReq.toByteString(), World.ProtoType.EnterGameReqType_VALUE, channel);//发送消息
                            //System.out.println("102进入游戏请求 send Success");
                            isEnterGame = true;
                        }
                        break;

                    case World.ProtoType.EnterGameResType_VALUE:   //1001 进入游戏通知
                        World.EnterGameRes enterGameRes = World.EnterGameRes.parseFrom(clientMsg.getData());//.getGameCode();
                        //System.out.println(
                        //        StringBuilders.custom()
                        //                .add("1001进入游戏")
                        //                .add(phone + "游戏code", enterGameRes.getGameCode())
                        //                .add(phone + "msg", enterGameRes.getMsg())
                        //                .build()
                        //);
                        //发送进入场景请求
                        if (!isScenesReq) {
                            //Tb.ScenesReq scenesReq = Brnn.ScenesReq.newBuilder().build();
                            sendBf(World.Msg.getDefaultInstance().toByteString(), Tb.ProtoType.ScenesReqType_VALUE, channel);
                            //System.out.println("20310进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;
                    case Tb.ProtoType.BetResType_VALUE:
                        Tb.BetRes betRes = Tb.BetRes.parseFrom(clientMsg.getData());
                        //System.out.println(
                        //        StringBuilders.custom()
                        //                .add("10156投注响应")
                        //                .add("投注list", betRes.getBetInfoList().toString().replaceAll("\n", ","))
                        //                .add("total", betRes.getTotal())
                        //                .add("错误码", betRes.getErrorCode())
                        //                .build()
                        //);
                        break;
                    /*case Brnn.ProtoType.OnlineNumberNtfType_VALUE:
                        Brnn.OnlineNumberNtf onlineNumberNtf = Brnn.OnlineNumberNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("10172在线人数")
                                        .add("现在人数", onlineNumberNtf.getOnlineNumber())
                                        .build()
                        );
                        break;*/
                    /*case Brnn.ProtoType.PokerInfoNtfType_VALUE:
                        Brnn.PokerInfoNtf pokerInfoNtf = Brnn.PokerInfoNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("10164扑克信息通知")
                                        .add("输赢", pokerInfoNtf.getPokerState()) //正 赢 负 输
                                        .add("牌型", pokerInfoNtf.getPokerType())
                                        .add("牌信息", pokerInfoNtf.getPoker())
                                        .build()
                        );
                        break;*/
                    case Tb.ProtoType.StartNtfType_VALUE:
                        //Brnn.StartNtf startNtfType = Brnn.StartNtf.parseFrom(clientMsg.getData());
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("10163游戏开始通知")
                                        .add("局号", startNtfType.getInning())
                                        .add("倒计时", startNtfType.getCountDown())
                                        //.add("庄家信息", startNtfType.getBankerInfo())
                                        .build()
                        );*/
                        this.state = Tb.State.Beting;//投注状态
                        break;
                    case Tb.ProtoType.LotteryNtfType_VALUE:
                        Tb.LotteryNtf lotteryNtf = Tb.LotteryNtf.parseFrom(clientMsg.getData());
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("10165结算状态通知")
                                        .add("倒计时", lotteryNtf.getCountDown())
                                        .build()Tb
                        );*/
                        this.state = Tb.State.Stop;
                        break;
                    case Tb.ProtoType.EnterPlayerSceneNtfType_VALUE:
                        Tb.EnterPlayerSceneNtf enterPlayerSceneNtf = Tb.EnterPlayerSceneNtf.parseFrom(clientMsg.getData());
                        this.chipList = new ArrayList<>(enterPlayerSceneNtf.getChipEnumList());//获取筹码list
                        System.out.println(this.chipList);
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("10166场景通知")
                                        .add("游戏状态", enterPlayerSceneNtf.getState())
                                        .add("倒计时", enterPlayerSceneNtf.getRemainingTime())
                                        .add("走势", enterPlayerSceneNtf.getTableRecordListList())
                                        .add("上庄需要的金额", enterPlayerSceneNtf.getBankerNeedMoney())
                                        .add("各区域的投注", enterPlayerSceneNtf.getPlayerAreaBetList())
                                        .add("房间四个盘口的下注金额", enterPlayerSceneNtf.getRoomAreaBetList())
//                                        .add("进入玩家具体筹码的分布情况", enterPlayerSceneNtf.getbet)
                                        .add("场次", enterPlayerSceneNtf.getRoundType())
                                        .add("房间号", enterPlayerSceneNtf.getRoomId())
                                        .add("期号", enterPlayerSceneNtf.getInning())
                                        .add("余额", enterPlayerSceneNtf.getBalance())
                                        .add("在线人数", enterPlayerSceneNtf.getOnlineNumber())
                                        .add("庄家列表", enterPlayerSceneNtf.getBankerList())
                                        .add("赔率配置", enterPlayerSceneNtf.getMultipleEnumList())
                                        .add("筹码配置", enterPlayerSceneNtf.getChipEnumList())
                                        .build()
                        );*/
                        this.state = enterPlayerSceneNtf.getState();
                        bet(channel);//下注请求
                        break;
                    /*case Brnn.ProtoType.BetNtfType_VALUE:
                        Brnn.BetNtf betNtf = Brnn.BetNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("10170广播")
                                        .add("各区域投注统计", betNtf.getAreaAmountList())
                                        .add("投注详细天", betNtf.getTianInfoList())
                                        .add("投注详细地", betNtf.getDiInfoList())
                                        .add("投注详细玄", betNtf.getXuanInfoList())
                                        .add("投注详细黄", betNtf.getHuangInfoList())
                                        .add("在线人数", betNtf.getOnlineNumber())
                                        .build()
                        );
                        break;*/
                    /*case Brnn.ProtoType.SpecialBetNtfType_VALUE:
                        Brnn.SpecialBetNtf specialBetNtf = Brnn.SpecialBetNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("10171特殊玩家投注广播")
                                        .add("uid", specialBetNtf.getSpecialUid())
                                        .add("投注内容", specialBetNtf.getBetInfoList().toString().replaceAll("\n", ""))
                                        .build()
                        );
                        break;
                    default:
                        System.out.println("未解析协议：" + clientMsg.getProto());
                        break;*/
                    case World.ProtoType.ErrorNtfType_VALUE:// 1004 错误消息通知
//                        System.out.println(World.ErrorNtf.parseFrom(clientMsg.getData()).toString().
//                                replaceAll("\n", "").replaceAll("\t", ""));
                        World.ErrorNtf errorNtf = World.ErrorNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("1004错误消息")
                                        .add(phone + "错误消息", errorNtf.getErr())
                                        .build()
                        );
                        break;
                }
            }
        }
    }

    public void bet(Channel channel) {
        //初始化投注金额
        //int[] chip = new int[]{1, 10, 50, 100, 500, 1000};//筹码
        Collections.sort(this.chipList);
        int[] count = new int[]{1000, 1000, 50, 20, 1, 1}; //个数
        int totalCount = 0;
        for (int i : count) {
            totalCount += i;
        }
        int[] amout = new int[totalCount];
        int index = 0;
        for (int i = 0; i < count.length; i++) {//count长度
            for (int j = 0; j < count[i]; j++) { //count内的长度
                amout[index++] = chipList.get(i);
            }
        }
        //初始化投注盘口
        //int[] PosCount = new int[]{31, 500, 100, 30, 1, 1};
        Tb.Pos[] tbPos = {Tb.Pos.Big, Tb.Pos.Small, Tb.Pos.Odd, Tb.Pos.Even,
                Tb.Pos.Four, Tb.Pos.Five, Tb.Pos.Six, Tb.Pos.Seven, Tb.Pos.Eight, Tb.Pos.Nine, Tb.Pos.Ten, Tb.Pos.Eleven, Tb.Pos.Twelve, Tb.Pos.Thirteen, Tb.Pos.Fourteen, Tb.Pos.Fifteen, Tb.Pos.Sixteen, Tb.Pos.Seventeen,
                Tb.Pos.OneThreeSame, Tb.Pos.TwoThreeSame, Tb.Pos.ThreeThreeSame, Tb.Pos.FourThreeSame, Tb.Pos.FiveThreeSame, Tb.Pos.SixThreeSame, Tb.Pos.AnyThreeSam,
                Tb.Pos.OneOneTwoThree, Tb.Pos.TwoOneTwoThree, Tb.Pos.ThreeOneTwoThree, Tb.Pos.FourOneTwoThree, Tb.Pos.FiveOneTwoThree, Tb.Pos.SixOneTwoThree};
        int[] posCount = {50, 50, 50, 50, 5, 6, 7, 8, 9, 10, 10, 10, 10, 9, 8, 7, 6, 5, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3, 3};
        int posCountTotal = 0;
        for (int i : posCount) {
            posCountTotal += i;
        }
        Tb.Pos[] posTotal = new Tb.Pos[posCountTotal];
        int indexs = 0;
        for (int i = 0; i < posCount.length; i++) {
            for (int j = 0; j < posCount[i]; j++) {
                posTotal[indexs++] = tbPos[i];
            }
        }
        //投注
        executorService.scheduleWithFixedDelay(() -> {
                    int indexAmount = (int) (Math.random() * amout.length);
                    int indexPos = (int) (Math.random() * posTotal.length);
                    if (canBetting()) {
                        Tb.BetInfo betinfo = Tb.BetInfo.newBuilder()
                                //.setPos(posTotal[indexPos])
                                .setPos(Tb.Pos.Big)
                                //.addAmount(amout[indexAmount])
                                .addAmount(100)
                                .build();
                        Tb.BetReq betReq = Tb.BetReq.newBuilder()
                                .addBetInfo(betinfo)
                                .build();
                        sendBf(betReq.toByteString(), Tb.ProtoType.BetReqType_VALUE, channel);//发送消息
                        //System.out.println("投注send发送成功：" + amout[indexAmount] + "，" + posTotal[indexPos]);
                    }
                }
                , 0, 3000, TimeUnit.MILLISECONDS);
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //super.channelInactive(ctx);
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }


    private boolean canBetting() {
        return this.state == Tb.State.Beting;
    }

}
