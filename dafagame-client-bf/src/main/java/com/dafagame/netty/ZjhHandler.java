package com.dafagame.netty;

import com.dafagame.dafaGameUtils.cardLogic.ZjhCard;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCards;
import com.dafagame.enums.Action;
import com.dafagame.proto.BjlMsg;
import com.dafagame.protocol.gate.Gate;
import com.dafagame.protocol.world.World;
import com.dafagame.protocol.zjh.Zjh;
import com.dafagame.utils.RandomUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.ProtocolStringList;
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
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ZjhHandler extends GameHandler {
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景
    @Setter
    @Getter
    private HttpConfig httpConfig;

    private int currentPlayersNum = 0; //当前玩家数量
    private int startPlayersNum = 0; //游戏开始时的玩家数量

    private String baseChip; //底注，基础筹码

    private List<Double> addChipEnumList; //加注筹码数组

    private String beforBaseChip;

    private int ownSeatId; //自己的座位号

    private int ring;//轮数

    private int state;

    private boolean isSeeCard = false;

    private int cardType = 0;

    //private RobotLogic robotLogic;

    private ZjhCards zjhCards;

    private List<Integer> players;

    private String inning = "";

    private boolean isInGame = false;

    private int compareRing = 0; //比牌的轮次

    private double winAmount = 0;
    private double bettingAmount = 0;

    private boolean iskill;

    //private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private double ownBalance;

    //public ZjhHandler() {
    //    robotLogic = new RobotLogic();
    //    robotLogic.action[0] = 1;
    //}

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
                                    .setRoundType("104") //
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
                        this.ownBalance = Double.parseDouble(scenesData.getOwn().getBalance());
                        System.out.println("【我的座位号】" + this.ownSeatId);
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
                        if (ownSeatId == currentOpt && scenesData.getOthersList().size() > 0) {
                            bet(channel);//下注请求：看牌 跟注 跟到底 加注 比牌 梭哈
                        }
                        break;

                    case Zjh.ProtoType.WaittingStartNtfType_VALUE: //等待开局5秒通知
                        Zjh.WaittingStartNtf waittingStartNtf = Zjh.WaittingStartNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【等待开始5秒通知】")
                                        .add("等待时间", waittingStartNtf.getTime())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.StartGameNtfType_VALUE://游戏开始通知
                        Zjh.StartGameNtf startNtfType = Zjh.StartGameNtf.parseFrom(clientMsg.getData());
                        this.isSeeCard = false;
                        this.ring = 0;
                        this.players = new ArrayList<>(startNtfType.getGamePlayersList());
                        this.inning = startNtfType.getInning();
                        this.isInGame = true;
                        this.currentPlayersNum = startNtfType.getGamePlayersList().size(); //当前参与游戏的玩家数
                        this.startPlayersNum = this.currentPlayersNum;
                        this.compareRing = 0;
                        this.bettingAmount = 0;
                        this.iskill = startNtfType.getKill();
                        System.out.println("游戏开始通知,玩家数:" + this.currentPlayersNum);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【游戏开始通知】")
                                        .add("操作玩家，即庄家", startNtfType.getOpt())
                                        .add("参与游戏的玩家", startNtfType.getGamePlayersList())
                                        .add("操作时间", startNtfType.getTime())
                                        .add("局号", startNtfType.getInning())
                                        .add("iskill", startNtfType.getKill())
                                        .build()
                        );
                        if (ownSeatId == startNtfType.getOpt()) {
                            bet(channel);//下注请求：看牌 跟注 跟到底 加注 比牌 梭哈
                        }
                        break;

                    case Zjh.ProtoType.EnterRoomNtfType_VALUE://玩家进入房间通知
                        Zjh.EnterRoomNtf enterRoomNtf = Zjh.EnterRoomNtf.parseFrom(clientMsg.getData());
                        //System.out.println(
                        //        StringBuilders.custom()
                        //                .add("【玩家进入房间通知】")
                        //                .add("昵称", enterRoomNtf.getPlayer().getNickName())
                        //                .add("进入房间的玩家", enterRoomNtf.getPlayer())
                        //                .build()
                        //);
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
                        if (nextActionNtf.getOpt() == this.ownSeatId) {
                            //double p = robotLogic.getP(currentPlayersNum);
                            //System.out.println("p:" + p);
                            //BigDecimal px = robotLogic.getPx(currentPlayersNum);
                            //robotLogic.getEv(px.doubleValue(), p);
                            //robotLogic.getEvo()
                            bet(channel); //下注请求
                        }
                        break;

                    case Zjh.ProtoType.SeeCardResType_VALUE://看牌回应
                        Zjh.SeeCardRes seeCardRes = Zjh.SeeCardRes.parseFrom(clientMsg.getData());
                        this.isSeeCard = true;
                        this.cardType = seeCardRes.getCardsType(); //牌型
                        String cards = seeCardRes.getCards();
                        List<ZjhCard> zjhCardList = new ArrayList<>();
                        for (String s : cards.split(",")) {
                            zjhCardList.add(ZjhCard.getZjhCard(Integer.parseInt(s)));
                        }
                        this.zjhCards = new ZjhCards(zjhCardList);

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
                        //if (compareResNtf.getOpt() == this.ownSeatId) {
                        //    //robotLogic.action[Action.Compare.ordinal()] += 1;
                        //
                        //}
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
                        if (compareResNtf.getOpt() == this.ownSeatId || compareResNtf.getOther() == this.ownSeatId) {
                            //if (compareResNtf.getWinner() != this.ownSeatId) {
                            //
                            //}
                            this.compareRing = this.ring;
                        }

                        if (compareResNtf.getOpt() == this.ownSeatId) {
                            bettingAmount += Double.parseDouble(compareResNtf.getBetChip());
                        }
                        //移除比牌输的玩家
                        if (players != null) {
                            if (compareResNtf.getWinner() == compareResNtf.getOpt())
                                players.remove((Integer) compareResNtf.getOther());
                            else
                                players.remove((Integer) compareResNtf.getOpt());
                        }

                        /**
                         * 1.被比牌玩家不扣钱
                         *
                         * 2.弃牌要扣钱
                         *
                         * 3.梭哈要扣钱 --
                         *
                         * 5.结算赢家是自己要加钱
                         *
                         * 6.下注响应，下注扣钱 --
                         *
                         * 7.发起比牌要扣钱 --
                         *
                         * */
                        if (compareResNtf.getOther() == this.ownSeatId) {
                            if (compareResNtf.getWinner() == this.ownSeatId)
                                this.ownBalance -= Double.parseDouble(compareResNtf.getBetChip());
                        }
                        break;

                    case Zjh.ProtoType.GiveUpResNtfType_VALUE://弃牌回应/广播
                        Zjh.GiveUpResNtf giveUpResNtf = Zjh.GiveUpResNtf.parseFrom(clientMsg.getData());
                        currentPlayersNum--;
                        System.out.println("弃牌回应/广播，当前游戏玩家数" + currentPlayersNum);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【弃牌回应/广播】")
                                        //.add("错误码", giveUpResNtf.getCode())
                                        .add("操作玩家", giveUpResNtf.getOpt())
                                        .add("轮数", giveUpResNtf.getRing())
                                        .add("真人数量", giveUpResNtf.getRealCount())
                                        .build()
                        );
                        //移除弃牌的玩家
                        if (players != null) {
                            players.remove((Integer) giveUpResNtf.getOpt());
                        }
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
                        if (gameOverNtf.getWinner() == this.ownSeatId)
                            this.ownBalance += Double.parseDouble(gameOverNtf.getReturnAmount());
                        System.out.println("结算时余额：" + this.ownBalance);
                        //牌值和牌型
                        ProtocolStringList cardsList = gameOverNtf.getCardsList();
                        StringBuilder sb = new StringBuilder();
                        if (!this.isInGame) {
                            break;
                        }

                        String isWin;
                        if (gameOverNtf.getWinner() == this.ownSeatId) {
                            isWin = "win";
                            winAmount = Double.parseDouble(gameOverNtf.getReturnAmount());
                        } else {
                            isWin = "lost";
                            winAmount = -(10 + bettingAmount);
                        }
                        //sb.append(this.inning + " - " + this.ring + " - " + this.compareRing + " - slef【" + this.zjhCards + " , " + this.zjhCards.type + "】");
                        sb.append(this.inning + ";" + this.iskill + ";" + this.startPlayersNum + ";" + this.ring + ";" + this.compareRing + ";" + this.zjhCards + ";" + isWin + ";" + winAmount);
                        //for (int i = 0; i < cardsList.size(); i++) {
                        //    List<ZjhCard> zjhCardList0 = new ArrayList<>();
                        //    for (String s : cardsList.get(i).split(",")) {
                        //        zjhCardList0.add(ZjhCard.getZjhCard(Integer.parseInt(s)));
                        //    }
                        //    ZjhCards zjhCards0 = new ZjhCards(zjhCardList0);
                        //    sb.append(";" + zjhCards0 + "," + zjhCards0.type + "");
                        //}
                        System.out.println(sb.append("\n").toString());
                        this.isInGame = false;
                        //FileUtil.writeFile("/usr/duke/a.txt", sb.toString(), true);
                        //FileUtil.writeFile("/Users/duke/Documents/github/dafa/dafagame-client-bf/src/main/resources/a.txt", sb.toString(), true);
                        break;

                    case Zjh.ProtoType.BetResNtfType_VALUE://下注响应/广播
                        Zjh.BetResNtf betRes = Zjh.BetResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【下注响应/广播】")
                                        .add("下注筹码", betRes.getBetChip())
                                        .add("操作玩家", betRes.getOpt())
                                        .add("知否暗牌", betRes.getIsDark())
                                        .build()
                        );
                        baseChip = betRes.getIsDark() ? betRes.getBetChip() : String.valueOf(Double.parseDouble(betRes.getBetChip()) / 2);
                        beforBaseChip = baseChip;

                        if (ownSeatId == betRes.getOpt()) { //自己下注
                            ownBalance = ownBalance - Double.parseDouble(betRes.getBetChip());
                            bettingAmount += Double.parseDouble(betRes.getBetChip());
                        }
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
                        if (this.ownSeatId == studResNtf.getOpt()) {
                            this.ownBalance -= Double.parseDouble(studResNtf.getBetChip());
                        }
                        break;

                    case Zjh.ProtoType.FollowStudResNtfType_VALUE://跟梭哈回应/广播
                        Zjh.FollowStudResNtf followStudResNtf = Zjh.FollowStudResNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【跟梭哈回应/广播】")
                                        .add("梭哈筹码", followStudResNtf.getBetChip())
                                        .add("操作玩家", followStudResNtf.getOpt())
                                        .add("知否暗牌", followStudResNtf.getRing())
                                        .build()
                        );
                        break;

                    case Zjh.ProtoType.SystemCompareNtfType_VALUE://系统比牌广播
                        Zjh.SystemCompareNtf systemCompareNtf = Zjh.SystemCompareNtf.parseFrom(clientMsg.getData());
                        System.out.println(
                                StringBuilders.custom()
                                        .add("【系统比牌广播】")
                                        .add("赢家", systemCompareNtf.getWinner())
                                        .add("各自比牌结果", systemCompareNtf.getInfoList())
                                        .add("游戏是否结束", systemCompareNtf.getIsOver())
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
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!this.isSeeCard) {
                Zjh.SeeCardReq seeCardReq = Zjh.SeeCardReq.newBuilder()
                        .build();
                sendBf(seeCardReq.toByteString(), Zjh.ProtoType.SeeCardReqType_VALUE, channel);//看牌请求
                System.out.println("****看牌请求******");
            }

            try {
                Thread.sleep(3 * 1000);//等看牌回应
            } catch (Exception e) {
                e.printStackTrace();
            }

            ////第一轮下注
            //if (this.ring == 0) { //第一轮只能下注，不能比牌
            //    flowBet(channel);
            //    System.out.println("****** 第一轮下注成功 ******");
            //    return;
            //}
            //梭哈
            //if (this.currentPlayersNum == 2 && this.cardType > 3) {
            //    Zjh.StudReq studReq = Zjh.StudReq.newBuilder().build();
            //    sendBf(studReq.toByteString(), Zjh.ProtoType.StudReqType_VALUE, channel);
            //    System.out.println("******梭哈请求******:");
            //}
            //flowBet(channel);
            //comparePoker(channel);
            playPokers(channel);
        }).start();
    }

    /**
     * cardType : 235(0)，单张(1)，对子(2)，顺子(3)，金花(4)
     * 金花及以上，或者顺子大于9，跟到底
     * 顺子> 9跟8-14轮 比牌
     * 顺子<= 9跟5-10轮 比牌
     * 对子> 9跟5-10轮 比牌
     * 对子<=9跟3-6轮 比牌
     * 单张<=12弃牌
     * 单张> 12跟1-2轮弃牌
     */
    public void playPokers(Channel channel) {
        if (this.cardType > 3) {
            flowBet(channel);
        } else if (this.cardType == 3) { //顺子
            if (this.zjhCards.maxCard.power > 9) {
                if (this.startPlayersNum > 3) { //开局人数大于3
                    if (this.ring < 5) {
                        flowBet(channel);
                    } else if (this.ring < 10) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                } else {
                    if (this.ring < 8) {
                        flowBet(channel);
                    } else if (this.ring < 14) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                }
            } else { //顺子小于9
                if (this.startPlayersNum > 3) { //开局人数大于3
                    if (this.ring < 3) {
                        flowBet(channel);
                    } else if (this.ring < 6) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                } else {
                    if (this.ring < 5) {
                        flowBet(channel);
                    } else if (this.ring < 8) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                }
            }
        } else if (this.cardType == 2) { //对子
            if (this.zjhCards.midCard.power > 9) {
                if (this.startPlayersNum > 3) { //开局人数大于3
                    if (this.ring < 3) {
                        flowBet(channel);
                    } else if (this.ring < 6) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                } else {//开局人数 <= 3
                    if (this.ring < 5) {
                        flowBet(channel);
                    } else if (this.ring < 10) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                }
            } else { //对子小于9
                if (this.startPlayersNum > 3) {
                    if (this.ring < 1) {
                        flowBet(channel);
                    } else if (this.ring < 3) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                } else {
                    if (this.ring < 3) {
                        flowBet(channel);
                    } else if (this.ring < 4) {
                        if (RandomUtil.random(2) == 1) {
                            flowBet(channel);
                        } else {
                            comparePoker(channel);
                        }
                    } else {
                        comparePoker(channel);
                    }
                }
            }
        } else if (this.cardType == 1 && this.zjhCards.maxCard.power > 12) {
            if (this.startPlayersNum > 3) { //开局人数大于3
                giveUpPoker(channel);
            }
            if (this.ring < 1) {
                flowBet(channel);
            }
            //else if (this.ring < 2) {
            //    if (RandomUtil.random(2) == 1) {
            //        flowBet(channel);
            //    } else {
            //        comparePoker(channel);
            //    }
            //}
            else {
                comparePoker(channel);
            }
        } else {
            giveUpPoker(channel);
        }
    }

    ////跟到底请求
    //Zjh.FollowEndReq followEndReq = Zjh.FollowEndReq.newBuilder().build();
    //sendBf(followEndReq.toByteString(), Zjh.ProtoType.FollowEndReqType_VALUE, channel);
    //
    //梭哈
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
    private void flowBet(Channel channel) {
        String currentBetChip = isSeeCard ? String.valueOf(Double.parseDouble(baseChip) * 2) : baseChip;
        System.out.println("currentBetChip:" + currentBetChip);
        Zjh.BetReq betReq = Zjh.BetReq.newBuilder()
                .setChip(currentBetChip)
                .build();
        sendBf(betReq.toByteString(), Zjh.ProtoType.BetReqType_VALUE, channel); //20102
        System.out.println("******跟注请求******:" + baseChip);
    }

    private void comparePoker(Channel channel) {
        if (players.size() != 0) {
            int zeroIndex = players.get(0);
            if (zeroIndex == this.ownSeatId) {
                zeroIndex = players.get(1);
            }
            System.out.println("zeroIndex:" + zeroIndex);
            Zjh.CompareReq compareReq = Zjh.CompareReq.newBuilder().setOther(zeroIndex).build();
            sendBf(compareReq.toByteString(), Zjh.ProtoType.CompareReqType_VALUE, channel);
            System.out.println("******比牌请求****** 比牌座位 : " + zeroIndex);
        }
    }

    private void giveUpPoker(Channel channel) {
        Zjh.GiveUpReq giveUpReq = Zjh.GiveUpReq.newBuilder().build();
        sendBf(giveUpReq.toByteString(), Zjh.ProtoType.GiveUpReqType_VALUE, channel);
        System.out.println("******弃牌请求******");
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
        //ctx.channel()
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
