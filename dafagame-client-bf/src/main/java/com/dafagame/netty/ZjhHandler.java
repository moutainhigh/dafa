package com.dafagame.netty;

import com.dafagame.proto.BjlMsg;
import com.dafagame.protocol.brnn.Brnn;
import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.world.World;
import com.dafagame.protocol.zjh.Zjh;
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

public class ZjhHandler extends GameHandler {

    private int uid; //自己的user-id
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    private double baseChip; //底注

    private int ownSeatId; //自己的座位号


    private int state;

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
                System.out.println("proto：" + clientMsg.getProto());
                switch (clientMsg.getProto()) {
                    case Gate.ProtoType.GateResType_VALUE:  //登陆成功通知
                        //System.out.println("102登陆成功通知：" + Gate.GateRes.parseFrom(clientMsg.getData()).toString().
                        //        replaceAll("\n", "").replaceAll("\t", ""));
                        Gate.GateRes gateRes = Gate.GateRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Gate.ProtoType.GateResType_VALUE + "登陆响应")
                                        .add(phone + "登陆回应", gateRes.getLoginRes().toString().replaceAll("\n", ",").replaceAll("\t", ""))
                                        .add(phone + "code", gateRes.getErrorCode())
                                        .build()
                        );
                        //uid = gateRes.getLoginRes().getUid(); //设置用户id
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("201") //游戏
                                    .setRoundType("101") //倍数场 101四倍场，102十倍场
                                    .build();
                            sendBf(enterGameReq.toByteString(), World.ProtoType.EnterGameReqType_VALUE, channel);//发送消息
                            System.out.println(World.ProtoType.EnterGameReqType_VALUE + "进入游戏请求 send Success");
                            isEnterGame = true;
                        }
                        break;

                    case World.ProtoType.EnterGameResType_VALUE:
                        World.EnterGameRes enterGameRes = World.EnterGameRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(World.ProtoType.EnterGameResType_VALUE + "进入游戏")
                                        .add(phone + "游戏code", enterGameRes.getGameCode())
                                        .add(phone + "msg", enterGameRes.getMsg())
                                        .build()
                        );
                        //发送进入场景请求
                        if (!isScenesReq) {
                            Zjh.ScenesReq scenesReq = Zjh.ScenesReq.newBuilder().build();
                            sendBf(scenesReq.toByteString(), Zjh.ProtoType.ScenesReqType_VALUE, channel);
                            System.out.println(Zjh.ProtoType.ScenesReqType_VALUE + "进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;
                    //==================================================================================================
                    case Zjh.ProtoType.ScenesDataType_VALUE://场景通知
                        Zjh.ScenesData enterPlayerSceneNtf = Zjh.ScenesData.parseFrom(clientMsg.getData());
                        this.baseChip = enterPlayerSceneNtf.getBaseChip();//底注
                        this.ownSeatId = enterPlayerSceneNtf.getOwn().getSeatId();//自己的座位号
                        int currentOpt = enterPlayerSceneNtf.getOpt();//当前操作人
                        System.out.println(this.baseChip);
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("场景通知")
                                        .add("游戏状态", enterPlayerSceneNtf.getState())
                                        .add("倒计时", enterPlayerSceneNtf.getRemainingTime())
                                        .add("走势", enterPlayerSceneNtf.getTableRecordListList())
                                        .add("上庄需要的金额", enterPlayerSceneNtf.getBankerNeedMoney())
                                        .add("各区域的投注", enterPlayerSceneNtf.getPlayerAreaBetList())
                                        .add("房间四个盘口的下注金额", enterPlayerSceneNtf.getRoomAreaBetList())
                                        .add("进入玩家具体筹码的分布情况", enterPlayerSceneNtf.getbet)
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
                        this.state = enterPlayerSceneNtf.getOpt();
                        if (ownSeatId == currentOpt) {
                            //bet(channel);//下注请求： 看牌 跟注 跟到底 加注 比牌 梭哈
                        }
                        break;

                    case Zjh.ProtoType.StartGameNtfType_VALUE://游戏开始通知
                        Zjh.StartGameNtf startNtfType = Zjh.StartGameNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("游戏开始通知")
                                        .add("操作玩家，即庄家", startNtfType.getOpt())
                                        .add("参与游戏的玩家", startNtfType.getGamePlayersList())
                                        .add("操作时间", startNtfType.getTime())
                                        .add("局号", startNtfType.getInning())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.SeeCardResType_VALUE://看牌回应
                        Zjh.SeeCardRes seeCardRes = Zjh.SeeCardRes.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("看牌回应")
                                        .add("手牌", seeCardRes.getCards())
                                        .add("牌型", seeCardRes.getCardsType())
                                        .build()
                        );
                        break;
                    case Zjh.ProtoType.SeeCardNtfType_VALUE://看牌广播
                        Zjh.SeeCardNtf seeCardNtf = Zjh.SeeCardNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("看牌广播")
                                        .add("看牌玩家", seeCardNtf.getOpt())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.CompareResNtfType_VALUE://比牌广播
                        Zjh.CompareResNtf compareResNtf = Zjh.CompareResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("比牌广播")
                                        .add("操作玩家", compareResNtf.getOpt())
                                        .add("被比玩家", compareResNtf.getOther())
                                        .add("操作总下注筹码", compareResNtf.getBetChip())
                                        .add("赢家", compareResNtf.getWinner())
                                        .add("比牌时间", compareResNtf.getTime())
                                        .add("真人数量", compareResNtf.getRealCount())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.GiveUpResNtfType_VALUE://弃牌回应/广播
                        Zjh.GiveUpResNtf giveUpResNtf = Zjh.GiveUpResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("比牌广播")
                                        .add("错误码", giveUpResNtf.getCode())
                                        .add("操作玩家", giveUpResNtf.getOpt())
                                        .add("??", giveUpResNtf.getRing())
                                        .add("真人数量", giveUpResNtf.getRealCount())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.StudResNtfType_VALUE://梭哈回应/广播
                        Zjh.StudResNtf studResNtf = Zjh.StudResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.StudResNtfType_VALUE + "梭哈回应/广播")
                                        .add("下一个操作玩家", studResNtf.getNextOpt())
                                        .add("下注筹码", studResNtf.getBetChip())
                                        .add("是否暗牌梭哈", studResNtf.getIsDark())
                                        .add("时间", studResNtf.getTime())
                                        .add("ring", studResNtf.getRing())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.AllInResNtfType_VALUE://全下请求回应/广播
                        Zjh.AllInResNtf allInResNtf = Zjh.AllInResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.StudResNtfType_VALUE + "全下请求回应/广播")
                                        .add("操作玩家", allInResNtf.getOpt())
                                        .add("下注筹码", allInResNtf.getBetChip())
                                        .add("ring", allInResNtf.getRing())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.GameOverNtfType_VALUE: //等待开始5秒通知
                        Zjh.GameOverNtf gameOverNtf = Zjh.GameOverNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.WaittingStartNtfType_VALUE + "等待开始5秒通知")
                                        .add("赢家", gameOverNtf.getWinner())
                                        .add("派彩金额", gameOverNtf.getReturnAmount())
                                        .add("余额", gameOverNtf.getBalance())
                                        .add("牌list", gameOverNtf.getCardsList())
                                        .add("牌型list", gameOverNtf.getCardsTypeList())
                                        .add("退还玩家", gameOverNtf.getOverSeat())
                                        .add("退还金额", gameOverNtf.getOverAmount())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.WaittingStartNtfType_VALUE: //等待开始5秒通知
                        Zjh.WaittingStartNtf waittingStartNtf = Zjh.WaittingStartNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.WaittingStartNtfType_VALUE + "等待开始5秒通知")
                                        .add("等待时间", waittingStartNtf.getTime())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.BetResNtfType_VALUE://下注响应
                        Zjh.BetResNtf betRes = Zjh.BetResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.BetResNtfType_VALUE + "下注响应")
                                        .add("下注筹码", betRes.getBetChip().toString().replaceAll("\n", ","))
                                        .add("操作玩家", betRes.getOpt())
                                        .add("下注时知否暗牌", betRes.getIsDark())
                                        .build()
                        );
                        break;


                    case BjlMsg.ProtoType.LotteryNtfType_VALUE://结算状态通知
                        BjlMsg.LotteryNtf lotteryNtf = BjlMsg.LotteryNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("结算状态通知")
                                        .add("倒计时", lotteryNtf.getCountDown())
                                        .build()
                        );
                        this.state = BjlMsg.State.Stop.getNumber();
                        break;

                    case World.ProtoType.ErrorNtfType_VALUE://错误消息通知
                        World.ErrorNtf errorNtf = World.ErrorNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(World.ProtoType.ErrorNtfType_VALUE + "错误消息")
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
        //Collections.sort(this.chipList);
        int[] count = new int[]{1000, 1000, 50, 20, 1, 1}; //个数
        int totalCount = 0;
        for (int i : count) {
            totalCount += i;
        }
        int[] amout = new int[totalCount];
        int index = 0;
        for (int i = 0; i < count.length; i++) {//count长度
            for (int j = 0; j < count[i]; j++) { //count内的长度
                //amout[index++] = chipList.get(i);
            }
        }
        //初始化投注盘口
        //int[] PosCount = new int[]{31, 500, 100, 30, 1, 1};
        BjlMsg.Pos[] bjlPos = {BjlMsg.Pos.XIANTIANWANG, BjlMsg.Pos.ZHUANGTIANWANG,
                BjlMsg.Pos.XIANDUI, BjlMsg.Pos.ZHUANGDUI,
                BjlMsg.Pos.ZHUANG, BjlMsg.Pos.XIAN, BjlMsg.Pos.PING, BjlMsg.Pos.TONGDIANPING};
        int[] posCount = {30, 30, 7, 7, 50, 50, 10, 1};
        int posCountTotal = 0;
        for (int i : posCount) {
            posCountTotal += i;
        }
        BjlMsg.Pos[] posTotal = new BjlMsg.Pos[posCountTotal];
        int indexs = 0;
        for (int i = 0; i < posCount.length; i++) {
            for (int j = 0; j < posCount[i]; j++) {
                posTotal[indexs++] = bjlPos[i];
            }
        }
        //投注
        executorService.scheduleWithFixedDelay(() -> {
                    int indexAmount = (int) (Math.random() * amout.length);
                    int indexPos = (int) (Math.random() * posTotal.length);
                    if (canBetting()) {
                        BjlMsg.BetInfo betinfo = BjlMsg.BetInfo.newBuilder()
                                .setPos(posTotal[indexPos].getNumber())
                                .addAmount(amout[indexAmount])
                                .build();
                        BjlMsg.BetReq betReq = BjlMsg.BetReq.newBuilder()
                                .addBetInfo(betinfo)
                                .build();
                        sendBf(betReq.toByteString(), BjlMsg.ProtoType.BetReqType_VALUE, channel);//发送消息
                        System.out.println("投注send发送成功：" + amout[indexAmount] + "，" + posTotal[indexPos]);
                    }
                }
                , 0, 500, TimeUnit.MILLISECONDS);
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
        return this.state == BjlMsg.State.Betting.getNumber();
    }

}
