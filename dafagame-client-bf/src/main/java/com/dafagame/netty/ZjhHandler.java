package com.dafagame.netty;

import com.dafagame.enums.Action;
import com.dafagame.proto.BjlMsg;
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
import org.apache.commons.lang.StringUtils;
import pers.utils.StringUtils.StringBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ZjhHandler extends GameHandler {

    //private int uid; //自己的user-id
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    private int currentPlayersNum = 0; //当前 玩家数量

    private String baseChip; //底注，基础筹码
    private List<Double> addChipEnumList; //加注筹码数组

    private String beforBaseChip;

    private int ownSeatId; //自己的座位号

    private int ring;//轮数

    private int state;

    private boolean isSeeCard = false;

    private RobotLogic robotLogic;

    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    BigDecimal px = BigDecimal.ZERO;

    public ZjhHandler() {
        robotLogic = new RobotLogic();
        robotLogic.action[0] = 1;
    }

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
                //handlerManager.handler(ctx.channel(),clientMsg);
                System.out.println("proto：" + clientMsg.getProto() + "-----------------------------------------------------------------------------");
                switch (clientMsg.getProto()) {
                    case Gate.ProtoType.GateResType_VALUE:  //登陆成功通知
                        //System.out.println("102登陆成功通知：" + Gate.GateRes.parseFrom(clientMsg.getData()).toString().
                        //        replaceAll("\n", "").replaceAll("\t", ""));
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
                                    .setGameCode("201") //游戏
                                    .setRoundType("102") //倍数场 101四倍场，102十倍场
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
                                        .build()
                        );
                        //发送进入场景请求
                        if (!isScenesReq) {
                            Zjh.ScenesReq scenesReq = Zjh.ScenesReq.newBuilder().setRoundType("102").build();
                            sendBf(scenesReq.toByteString(), Zjh.ProtoType.ScenesReqType_VALUE, channel);
                            System.out.println(Zjh.ProtoType.ScenesReqType_VALUE + "进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;
                    //-------------------------------------游戏消息------------------------------------------------------
                    case Zjh.ProtoType.ScenesDataType_VALUE://场景通知
                        Zjh.ScenesData scenesData = Zjh.ScenesData.parseFrom(clientMsg.getData());
                        this.baseChip = String.valueOf(scenesData.getBaseChip());//底注
                        this.ownSeatId = scenesData.getOwn().getSeatId();//自己的座位号
                        this.ring = scenesData.getRing();
                        int currentOpt = scenesData.getOpt();//当前操作人,庄家
                        addChipEnumList = scenesData.getAddChipEnumList();
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【场景通知】")
                                        .add("房间加注类型", scenesData.getAddChipEnumList())
                                        .add("当前游戏圈数", scenesData.getRing())
                                        .add("当前操作玩家", scenesData.getOpt())
                                        .add("操作时间", scenesData.getOptTime())
                                        .add("房号", scenesData.getRoomNumber())
                                        .add("其他玩家", scenesData.getOthersList())
                                        .build()
                        );
                        System.out.println("我的座位号" + this.ownSeatId);
                        System.out.println("baseChip：" + this.baseChip);
                        this.state = scenesData.getOpt();
                        if (ownSeatId == currentOpt) {
                            bet(channel);//下注请求：看牌 跟注 跟到底 加注 比牌 梭哈
                        }
                        break;

                    case Zjh.ProtoType.StartGameNtfType_VALUE://游戏开始通知
                        Zjh.StartGameNtf startNtfType = Zjh.StartGameNtf.parseFrom(clientMsg.getData());
                        this.isSeeCard = false;
                        this.ring = 0;
                        currentPlayersNum = startNtfType.getGamePlayersList().size(); //当前参与游戏的玩家数
                        System.out.println("游戏开始通知,玩家数:" + currentPlayersNum);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【游戏开始通知】")
                                        .add("操作玩家，即庄家", startNtfType.getOpt())
                                        .add("参与游戏的玩家", startNtfType.getGamePlayersList())
                                        .add("操作时间", startNtfType.getTime())
                                        .add("局号", startNtfType.getInning())
                                        .build()
                        );
                        if (ownSeatId == startNtfType.getOpt()) {
                            bet(channel);//下注请求：看牌 跟注 跟到底 加注 比牌 梭哈
                        }
                        break;

                    case Zjh.ProtoType.EnterRoomNtfType_VALUE://玩家进入房间通知
                        Zjh.EnterRoomNtf enterRoomNtf = Zjh.EnterRoomNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【玩家进入房间通知】")
                                        .add("昵称", enterRoomNtf.getPlayer().getNickName())
                                        .add("进入房间的玩家", enterRoomNtf.getPlayer())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.ExitRoomNtfType_VALUE://玩家退出房间通知
                        Zjh.ExitRoomNtf exitRoomNtf = Zjh.ExitRoomNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【玩家退出房间通知】")
                                        .add("进入房间的玩家", exitRoomNtf.getSeatId())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.NextActionNtfType_VALUE://下一个操作玩家
                        Zjh.NextActionNtf nextActionNtf = Zjh.NextActionNtf.parseFrom(clientMsg.getData());
                        this.ring = nextActionNtf.getRing();

                        System.out.println(
                                StringBuilders.custom()
                                        .add("【下一个操作玩家通知】")
                                        .add("座位号", nextActionNtf.getOpt())
                                        .add("时间", nextActionNtf.getTime())
                                        .add("当前轮数", nextActionNtf.getRing())
                                        .build()
                        );
                        //只计算机器人
                        if (nextActionNtf.getOpt() != this.ownSeatId) {
                            double p = robotLogic.getP(currentPlayersNum);
                            System.out.println("p:" + p);
                            BigDecimal px = robotLogic.getPx(currentPlayersNum);
                            robotLogic.getEv(px.doubleValue(), p);

                            //robotLogic.getEvo()

                            bet(channel); //下注请求
                        }
                        break;

                    case Zjh.ProtoType.SeeCardResType_VALUE://看牌回应
                        Zjh.SeeCardRes seeCardRes = Zjh.SeeCardRes.parseFrom(clientMsg.getData());
                        this.isSeeCard = true;
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【看牌回应】")
                                        .add("手牌", seeCardRes.getCards())
                                        .add("牌型", seeCardRes.getCardsType())
                                        .build()
                        );
                        break;
                    case Zjh.ProtoType.SeeCardNtfType_VALUE://看牌广播
                        Zjh.SeeCardNtf seeCardNtf = Zjh.SeeCardNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【看牌广播】")
                                        .add("看牌玩家", seeCardNtf.getOpt())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.CompareResNtfType_VALUE://比牌回应/广播
                        Zjh.CompareResNtf compareResNtf = Zjh.CompareResNtf.parseFrom(clientMsg.getData());
                        if (compareResNtf.getOpt() != this.ownSeatId) {
                            robotLogic.action[Action.Compare.ordinal()] += 1;
                        }
                        currentPlayersNum--;
                        System.out.println("比牌，当前游戏玩家数" + currentPlayersNum);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【比牌广播】")
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
                        currentPlayersNum--;
                        System.out.println("弃牌，当前游戏玩家数" + currentPlayersNum);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【弃牌回应/广播】")
                                        //.add("错误码", giveUpResNtf.getCode())
                                        .add("操作玩家", giveUpResNtf.getOpt())
                                        .add("轮数", giveUpResNtf.getRing())
                                        .add("真人数量", giveUpResNtf.getRealCount())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.StudResNtfType_VALUE://梭哈回应/广播
                        Zjh.StudResNtf studResNtf = Zjh.StudResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【梭哈回应/广播】")
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
                                        .add("【全下请求回应/广播】")
                                        .add("操作玩家", allInResNtf.getOpt())
                                        .add("下注筹码", allInResNtf.getBetChip())
                                        .add("ring", allInResNtf.getRing())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.GameOverNtfType_VALUE: //结算广播
                        Zjh.GameOverNtf gameOverNtf = Zjh.GameOverNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(Zjh.ProtoType.GameOverNtfType_VALUE + "【结算广播】")
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
                                        .add("【等待开始5秒通知】")
                                        .add("等待时间", waittingStartNtf.getTime())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.BetResNtfType_VALUE://下注响应
                        Zjh.BetResNtf betRes = Zjh.BetResNtf.parseFrom(clientMsg.getData());
                        baseChip = betRes.getIsDark() ? betRes.getBetChip() : String.valueOf(Double.parseDouble(betRes.getBetChip()) / 2);

                        if (StringUtils.isNotEmpty(beforBaseChip)) {
                            if (beforBaseChip.equals(baseChip)) { //跟注
                                if (betRes.getOpt() != this.ownSeatId) {
                                    if (betRes.getIsDark()) {
                                        robotLogic.action[Action.DarkCardAdd.ordinal()] += 1;
                                    } else {
                                        robotLogic.action[Action.ClearCardAdd.ordinal()] += 1;
                                    }
                                }
                            } else {//加注
                                if (betRes.getOpt() != this.ownSeatId) {
                                    if (betRes.getIsDark()) {
                                        robotLogic.action[Action.DarkCardFollow.ordinal()] += 1;
                                    } else {
                                        robotLogic.action[Action.ClearCardFollow.ordinal()] += 1;
                                    }
                                }
                            }
                        }
                        beforBaseChip = baseChip;

                        System.out.println(
                                StringBuilders.custom()
                                        .add("【下注响应】")
                                        .add("下注筹码", betRes.getBetChip())
                                        .add("操作玩家", betRes.getOpt())
                                        .add("知否暗牌", betRes.getIsDark())
                                        .build()
                        );
                        break;

                    case World.ProtoType.ErrorNtfType_VALUE://错误消息通知
                        World.ErrorNtf errorNtf = World.ErrorNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add(World.ProtoType.ErrorNtfType_VALUE + "【错误消息】")
                                        .add(phone + "错误消息", errorNtf.getErr())
                                        .build()
                        );
                        break;
                }
            }
        }
    }

    public void bet(Channel channel) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //下注
        if (this.ring == 0) { //第一轮只能下注，不能比牌
            Zjh.BetReq betReqFist = Zjh.BetReq.newBuilder()
                    .setChip(isSeeCard ? String.valueOf(Double.parseDouble(baseChip) * 2) : baseChip)
                    .build();
            sendBf(betReqFist.toByteString(), Zjh.ProtoType.BetReqType_VALUE, channel);
            System.out.println("首次下注成功");
            return;
        }
        Zjh.BetReq betReq = Zjh.BetReq.newBuilder()
                .setChip(baseChip)
                .build();
        sendBf(betReq.toByteString(), Zjh.ProtoType.BetReqType_VALUE, channel); //20102
        System.out.println("******下注成功******:" + baseChip);

        ////跟到底请求
        //Zjh.FollowEndReq followEndReq = Zjh.FollowEndReq.newBuilder().build();
        //sendBf(followEndReq.toByteString(), Zjh.ProtoType.FollowEndReqType_VALUE, channel);
        //
        ////看牌
        //Zjh.SeeCardReq seeCardReq = Zjh.SeeCardReq.newBuilder().build();
        //sendBf(seeCardReq.toByteString(), Zjh.ProtoType.SeeCardReqType_VALUE, channel);
        //
        ////选择比牌请求
        //Zjh.CompareReq compareReq = Zjh.CompareReq.newBuilder().build();
        //sendBf(compareReq.toByteString(), Zjh.ProtoType.CompareReqType_VALUE, channel);
        //
        ////弃牌
        //Zjh.GiveUpReq giveUpReq = Zjh.GiveUpReq.newBuilder().build();
        //sendBf(giveUpReq.toByteString(), Zjh.ProtoType.GiveUpReqType_VALUE, channel);
        //
        ////梭哈
        //Zjh.StudReq studReq = Zjh.StudReq.newBuilder().build();
        //sendBf(studReq.toByteString(), Zjh.ProtoType.StudReqType_VALUE, channel);
        //
        ////全下请求
        //Zjh.AllInReq allInReq = Zjh.AllInReq.newBuilder().build();
        //sendBf(allInReq.toByteString(), Zjh.ProtoType.AllInReqType_VALUE, channel);
        //
        ////跟梭哈
        //Zjh.FollowStudReq followStudReq = Zjh.FollowStudReq.newBuilder().build();
        //sendBf(followStudReq.toByteString(), Zjh.ProtoType.FollowStudReqType_VALUE, channel);

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
