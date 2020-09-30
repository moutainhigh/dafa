package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.dafaGame.DafaGameLogin;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URI;
import java.util.List;

public class StartWsProtoClient {
    private Logger logger = LoggerFactory.getLogger(StartWsProtoClient.class);

    //web端请求地址
    //private static String url = "ws://game-gate.dafagame-test.com/v1/game/gameGate";
    //private static String url = "ws://game-gate.dafagame-pre.com/v1/game/gameGate";
    //private static String url = "ws://game-gate.dafagame-pro.com/v1/game/gameGate";

    private static final String tenantCode = "alysia";

    //app请求地址
    //private static String host = "23.101.14.122";//第一套
    private static String host = "137.116.175.108";//第二套
    private static String url = String.format("ws://%s:1082/v1/game/gameGate", host);

    private static EventLoopGroup group = new NioEventLoopGroup(1);
    private ChannelInitial channelInitial = new ChannelInitial();

    private ClientHandshaker clientHandshaker;
    //private int gameCode;

    private Channel channel;
    private Thread thread;

    private static int count = 0;

    private String phone;

    //构造器
    private StartWsProtoClient(String phone, int userType, int gameCode, String roundType) {
        this.phone = phone;
        GameHandler gameHandler = GameHandlerFactory.newGameHandler(gameCode);
        gameHandler.setPhone(phone);
        gameHandler.setUserType(userType);
        gameHandler.setRoundType(roundType);
        URI uri = URI.create(url);
        this.clientHandshaker = new ClientHandshaker(uri);//握手
        gameHandler.setHandshaker(this.clientHandshaker);//游戏设置握手
        this.channelInitial.setChannelHandler(gameHandler);
        thread = new Thread(() -> {
            try {
                //-------------------------------ws链接-------------------------------
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(channelInitial);

                ChannelFuture f = bootstrap
                        .connect(uri.getHost(), uri.getPort())  //链接 1082
                        .sync();

                this.channel = f.channel();
                this.clientHandshaker.setChannel(this.channel);
                this.clientHandshaker.handlerShakerChannel(this.channel);
                int counter = 0;
                while (counter++ <= 0) {
                    if (this.clientHandshaker.isSuccess()) {
                        Gate.GateReq gateReq = Gate.GateReq.newBuilder().setLoginReq(
                                Gate.LoginReq.newBuilder()
                                        .setSessionId("fb80a5b5e19940cdbf33d5fc1eed071a") // getLoginSession()
                                        .setUrl("120.25.177.43") //自定义ip
                                        .setSourceId("2")
                                        .setTenantCode(tenantCode)
                                        .build()
                        ).build();

                        Gate.ClientMsg clientMsg = Gate.ClientMsg.newBuilder()
                                .setProto(Gate.ProtoType.GateReqType_VALUE)
                                .setData(gateReq.toByteString())
                                .build();

                        ByteBuf bf = Unpooled.buffer().writeBytes(clientMsg.toByteArray());
                        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(bf);
                        channel.writeAndFlush(binaryWebSocketFrame);
                        System.out.println("ws链接成功" + (count++));
                        Thread.sleep(2000);
                    }
                }
                this.channel.closeFuture().sync();
            } catch (Exception e) {
                System.out.println("服务启动错误:" + e);
                //logger.error(LogUtil.error("服务启动错误",e));
            }
            //finally {
            //    group.shutdownGracefully();
            //}
        });

    }

    public void start() {
        if (!this.thread.isAlive()) {
            this.thread.start();
        }
    }

    public static void main(String[] args) throws Exception {
        //String phone = "1311234";
        //for (int i = 500; i < 600; i++) {
        //    new StartWsProtoClient(String.format("%s%s", phone, String.format("%04d", i))).start();
        //    Thread.sleep(200);
        //}
        //new StartWsProtoClient("92582013").start();
        //List<String> users = FileUtil.readFile(StartWsProtoClient.class.getResourceAsStream("/dukePhone.txt"));
        //System.out.println(users.size());
        //for (int i = 0; i < 3; i++) {
        //    new StartWsProtoClient(users.get(i), 201).start();
        //    Thread.sleep(200);
        //}

        //new StartWsProtoClient("63025749", 102).start();
        //List<String> users = FileUtil.readFile(StartWsProtoClient.class.getResourceAsStream("/txt/dg-pro-duke.txt"));
        //for (int i = 0; i < 10; i++) {
        //    new StartWsProtoClient(users.get(i), 102).start();
        //    try {
        //        Thread.sleep(600);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
        //
        //for (int i = 10; i < 20; i++) {
        //    new StartWsProtoClient(users.get(i), 104).start();
        //    try {
        //        Thread.sleep(600);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
        //
        //for (int i = 20; i < 30; i++) {
        //    new StartWsProtoClient(users.get(i), 105).start();
        //    try {
        //        Thread.sleep(600);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}

        //new StartWsProtoClient("22491998", 205).start();
        //Thread.sleep(2000);
        //new StartWsProtoClient("31237108", 205).start();
        //Thread.sleep(2000);
        //new StartWsProtoClient("47118496", 205).start();
        //Thread.sleep(2000);
        //new StartWsProtoClient("47876132", 205).start();

        //new StartWsProtoClient("88448162", 201).start();
        //List<String> devJessieUsers = FileUtil.readFile(StartWsProtoClient.class.getResourceAsStream("/usersTxt/devJessieUsers.txt"));
        //for (int i = 0; i < 40; i++) {
        //    if (i < 10) {
        //        new StartWsProtoClient(devJessieUsers.get(i), 201, "101").start();
        //    } else if (i < 20) {
        //        new StartWsProtoClient(devJessieUsers.get(i), 201, "102").start();
        //    } else if (i < 30) {
        //        new StartWsProtoClient(devJessieUsers.get(i), 201, "103").start();
        //    } else {
        //        new StartWsProtoClient(devJessieUsers.get(i), 201, "104").start();
        //    }
        //    try {
        //        Thread.sleep(1000);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}

        //抢庄牌九
        //new StartWsProtoClient("47876132", 206).start();

        //抢庄三公
        //new StartWsProtoClient("47876132", 207).start();

        //红包扫雷
        //new StartWsProtoClient("47876132", 107, "101").start();
        List<String> dev2alysiaT = FileUtil.readFile(StartWsProtoClient.class.getResourceAsStream("/usersTxt/dev2alysiaT.txt")).subList(0, 25);
        //for (int i = 0; i < dev2alysiaT.size(); i++) {
        //    if (i < 10) {
        //        new StartWsProtoClient(dev2alysiaT.get(i), 1, 107, "101").start();
        //    } else {
        //        new StartWsProtoClient(dev2alysiaT.get(i), 2, 107, "101").start();
        //    }
        //}
        //new StartWsProtoClient(dev2alysiaT.get(0), 2, 107, "101").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(1), 2, 107, "101").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(2), 2, 107, "101").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(3), 2, 107, "101").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(4), 2, 107, "101").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(5), 2, 107, "101").start();
        //Thread.sleep(200);
        //
        //new StartWsProtoClient(dev2alysiaT.get(6), 2, 107, "102").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(7), 2, 107, "102").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(8), 2, 107, "102").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(9), 2, 107, "102").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(10), 2, 107, "102").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(11), 2, 107, "102").start();
        //Thread.sleep(200);
        //
        //new StartWsProtoClient(dev2alysiaT.get(12), 2, 107, "103").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(13), 2, 107, "103").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(14), 2, 107, "103").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(15), 2, 107, "103").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(16), 2, 107, "103").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(17), 2, 107, "103").start();
        //Thread.sleep(200);
        //
        //new StartWsProtoClient(dev2alysiaT.get(18), 2, 107, "104").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(19), 2, 107, "104").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(20), 2, 107, "104").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(21), 2, 107, "104").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(22), 2, 107, "104").start();
        //Thread.sleep(200);
        //new StartWsProtoClient(dev2alysiaT.get(23), 2, 107, "104").start();
        //Thread.sleep(200);

        //new StartWsProtoClient("31237108", 107).start();

        new StartWsProtoClient("68531032", 2, 209, "101").start();

    }

    private static class GameHandlerFactory {
        static GameHandler newGameHandler(int gameCode) {
            switch (gameCode) {
                case 102:
                    return new BrnnHandler();
                case 104:
                    return new BjlHandler();
                case 105:
                    return new TbHandler();
                case 107:
                    return new HbslHandler();
                case 201:
                    return new ZjhHandler();
                case 203:
                    return new DdzHandler();
                case 205:
                    return new EbgHandler();
                case 206:
                    return new QzpjHandler();
                case 209:
                    return new SshlHandler();
            }
            return null;
        }
    }

    private String getLoginSession() {
        //-------------------------------登陆-------------------------------
        String random = "9722";
        //Encoder encoder = Base64.getEncoder();
        //String encode = encoder.encodeToString(random.getBytes());
        String encodeRandom = "OTcyMg==";
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", "")
                .addBuilder("accountNumber", this.phone)
                .addBuilder("password", DafaGameLogin.getPasswordEncode(random, "123qwe")) //"b4e82b683394b50b679dc2b51a79d987"
                .addBuilder("userType", "0") //正式0/测试1/遊客2
                .addBuilder("random", encodeRandom)
                //.addBuilder("tenantCode", "demo")
                //.addBuilder("sourceId", "2")
                .fullBody();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("Tenant-Code", tenantCode)
                .other("Source-Id", "1")
                .build();

        System.out.println(body);
        HttpConfig httpConfig = HttpConfig
                .custom().headers(headers)
                //.url("http://" + host + "/v1/users/login")
                .url("http://a838a400eea44468f8505b0c55518267-667d845bfac931ec.elb.ap-east-1.amazonaws.com/v1/users/login")
                .body(body);
        //this.gameHandler.setHttpConfig(httpConfig);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        String sessionId = JSONObject.fromObject(result).getJSONObject("data").getString("sessionId");
        if (StringUtils.isEmpty(sessionId))
            throw new RuntimeException("sessionId返回异常:" + sessionId);
        return sessionId;
    }


}
