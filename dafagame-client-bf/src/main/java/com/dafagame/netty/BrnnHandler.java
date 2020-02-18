package com.dafagame.netty;

import com.dafagame.protocol.brnn.Brnn;
import com.dafagame.protocol.gate.Gate;
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


//@Data
public class BrnnHandler extends GameHandler {
    private int uid; //自己的user-id
    @Getter
    @Setter
    private boolean isEnterGame = false;//是否进入游戏
    @Getter
    @Setter
    private boolean isScenesReq = false;//是否进入场景

    private Brnn.State state;
    private List<Integer> chipList;

    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    public void handlerAdded(ChannelHandlerContext chc) {
        this.handshaker.setHandshakeFuture(chc.newPromise());
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
                        Gate.GateRes gateRes = Gate.GateRes.parseFrom(clientMsg.getData());
                        //uid = gateRes.getLoginRes().getUid();
                        if (!isEnterGame) {
                            World.EnterGameReq enterGameReq = World.EnterGameReq
                                    .newBuilder()
                                    .setGameCode("101") //游戏
                                    .setRoundType("101") //倍数场 101四倍场，102十倍场
                                    .build();

                            sendBf(enterGameReq.toByteString(), World.ProtoType.EnterGameReqType_VALUE, channel);//发送消息
                            System.out.println("102登陆游戏请求 send Success");
                            isEnterGame = true;
                        }
                        break;
                    case World.ProtoType.ErrorNtfType_VALUE:// 1004 错误消息通知
                        World.ErrorNtf errorNtf = World.ErrorNtf.parseFrom(clientMsg.getData());
                        //System.out.println(
                        //        StringBuilders.custom()
                        //                .add("1004错误消息")
                        //                .add(phone + "错误消息", errorNtf.getErr())
                        //                .build()
                        //);
                        break;
                    case World.ProtoType.EnterGameResType_VALUE:   //1001 进入游戏通知
                        World.EnterGameRes enterGameRes = World.EnterGameRes.parseFrom(clientMsg.getData());//.getGameCode();
//                        System.out.println(
//                                StringBuilders.custom()
//                                        .add("1001进入游戏")
//                                        .add(phone + "游戏code", enterGameRes.getGameCode())
//                                        .add(phone + "msg", enterGameRes.getMsg())
//                                        .build()
//                        );
                        //发送进入场景请求
                        if (!isScenesReq) {
                            Brnn.ScenesReq scenesReq = Brnn.ScenesReq.newBuilder().build();
                            sendBf(scenesReq.toByteString(), Brnn.ProtoType.ScenesReqType_VALUE, channel);
                            //System.out.println("20310进入场景请求 send Success");
                            isScenesReq = true;
                        }
                        break;
                    case Brnn.ProtoType.BetResType_VALUE: //投注响应
                        Brnn.BetRes betRes = Brnn.BetRes.parseFrom(clientMsg.getData());
                        //7 - BankerNoMonery ，5 - StateError
                        if (Brnn.ErrorCode.OK.getNumber() != betRes.getErrorCode().getNumber()
                                && betRes.getErrorCode().getNumber() != 7 && betRes.getErrorCode().getNumber() != 5)
                            System.out.println(
                                    StringBuilders.custom()
                                            .add("10156投注响应")
                                            .add("投注list", betRes.getBetInfoList().toString().replaceAll("\n", ""))
                                            .add("total", betRes.getTotal())
                                            .add("错误码", betRes.getErrorCode().getNumber())
                                            .add("错误码", betRes.getErrorCode())
                                            .build()
                            );
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
                    case Brnn.ProtoType.StartNtfType_VALUE:
                        Brnn.StartNtf startNtfType = Brnn.StartNtf.parseFrom(clientMsg.getData());
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("10163游戏开始通知")
                                        .add("局号", startNtfType.getInning())
                                        .add("倒计时", startNtfType.getCountDown())
                                        //.add("庄家信息", startNtfType.getBankerInfo())
                                        .build()
                        );*/
                        this.state = Brnn.State.Beting;//投注状态
                        break;
                    case Brnn.ProtoType.LotteryNtfType_VALUE:
                        Brnn.LotteryNtf lotteryNtf = Brnn.LotteryNtf.parseFrom(clientMsg.getData());
                        /*System.out.println(
                                StringBuilders.custom()
                                        .add("10165结算状态通知")
                                        .add("倒计时", lotteryNtf.getCountDown())
                                        .build()
                        );*/
                        this.state = Brnn.State.Stop;
                        break;
                    case Brnn.ProtoType.EnterPlayerSceneNtfType_VALUE:
                        Brnn.EnterPlayerSceneNtf enterPlayerSceneNtf = Brnn.EnterPlayerSceneNtf.parseFrom(clientMsg.getData());
                        this.chipList = new ArrayList<>(enterPlayerSceneNtf.getChipEnumList());//获取筹码list
                        //System.out.println(this.chipList);
                        System.out.println(
                                StringBuilders.custom()
                                        .add("10166场景通知")
                                        .add("游戏状态", enterPlayerSceneNtf.getState())
                                        //.add("倒计时", enterPlayerSceneNtf.getRemainingTime())
                                        //.add("走势", enterPlayerSceneNtf.getTableRecordListList())
                                        //.add("上庄需要的金额", enterPlayerSceneNtf.getBankerNeedMoney())
                                        //.add("各区域的投注", enterPlayerSceneNtf.getPlayerAreaBetList())
                                        //.add("房间四个盘口的下注金额", enterPlayerSceneNtf.getRoomAreaBetList())
                                        //.add("进入玩家具体筹码的分布情况", enterPlayerSceneNtf.getbet)
                                        .add("场次", enterPlayerSceneNtf.getRoundType())
                                        .add("房间号", enterPlayerSceneNtf.getRoomId())
                                        //.add("期号", enterPlayerSceneNtf.getInning())
                                        //.add("余额", enterPlayerSceneNtf.getBalance())
                                        //.add("在线人数", enterPlayerSceneNtf.getOnlineNumber())
                                        //.add("庄家列表", enterPlayerSceneNtf.getBankerList())
                                        //.add("赔率配置", enterPlayerSceneNtf.getMultipleEnumList())
                                        //.add("赔率配置", enterPlayerSceneNtf.getChipEnumList())
                                        .build()
                        );
                        this.state = enterPlayerSceneNtf.getState();
                        bet(channel);
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
                }
            }
        }
    }

    public void bet(Channel channel) {
        Collections.sort(this.chipList);
        //int[] count = new int[]{770, 770, 50, 20, 1, 1}; //个数
        int[] count = new int[]{1000, 10, 1, 0, 0, 0}; //个数
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
        //投注
        executorService.scheduleWithFixedDelay(() -> {
            int indexAmount = (int) (Math.random() * amout.length);
            Brnn.Pos pos;
            switch ((int) (Math.random() * 4)) {
                case 0:
                    pos = Brnn.Pos.Tian;
                    break;
                case 1:
                    pos = Brnn.Pos.Di;
                    break;
                case 2:
                    pos = Brnn.Pos.Xuan;
                    break;
                case 3:
                    pos = Brnn.Pos.Huang;
                    break;
                default:
                    //System.out.println("获取投注pos错误");
                    pos = Brnn.Pos.Tian;
                    break;
            }
            if (canBetting()) {
                Brnn.BetInfo betinfo = Brnn.BetInfo.newBuilder()
                        //.setPos(pos)
                        .setPos(Brnn.Pos.Tian)
                        //.setAmount(0, 10)
                        //.addAmount(amout[indexAmount])
                        .addAmount(100)
                        .build();
                Brnn.BetReq betReq = Brnn.BetReq.newBuilder()
                        .addBetInfo(betinfo)
                        .build();
                sendBf(betReq.toByteString(), Brnn.ProtoType.BetReqType_VALUE, channel);//发送消息
                //System.out.println(this.phone + "投注send发送成功：" + amout[indexAmount] + "，" + pos);
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
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
        return this.state == Brnn.State.Beting;
    }
}
