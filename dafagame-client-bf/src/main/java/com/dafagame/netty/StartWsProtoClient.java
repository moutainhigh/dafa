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
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.propertiesUtils.PropertiesUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URI;

public class StartWsProtoClient {
    private Logger logger = LoggerFactory.getLogger(StartWsProtoClient.class);

    //web端请求地址
    //private static String url = "ws://game-gate.dafagame-test.com/v1/game/gameGate";
    //private static String url = "ws://game-gate.dafagame-pre.com/v1/game/gameGate";
    //private static String url = "ws://game-gate.dafagame-pro.com/v1/game/gameGate";

    private static String tenantCode = "duke";

    //app请求地址
    private static String host = "52.139.157.97";
    private static String url = String.format("ws://%s:7176/v1/game/gameGate", host);

    private static EventLoopGroup group = new NioEventLoopGroup(1);
    private ChannelInitial channelInitial = new ChannelInitial();

    private ClientHandshaker clientHandshaker;
    //private int gameCode;

    private Channel channel;
    private Thread thread;

    private static int count = 0;

    private String phone;

    //构造器
    private StartWsProtoClient(String phone, int gameCode) {
        this.phone = phone;

        GameHandler gameHandler = GameHandlerFactory.newGameHandler(gameCode);
        gameHandler.setPhone(phone);
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
                                        .setSessionId(getLoginSession()) // sessionId
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

    public static void main(String[] args) {
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

        //new StartWsProtoClient("18012340001", 201).start();
        new StartWsProtoClient("13012345678", 102).start();


    }

    private static class GameHandlerFactory {
        static GameHandler newGameHandler(int gameCode) {
            switch (gameCode) {
                case 101:
                    return new DdzHandler();
                case 102:
                    return new BrnnHandler();
                case 105:
                    return new TbHandler();
                case 104:
                    return new BjlHandler();
                case 201:
                    return new ZjhHandler();
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
                .addBuilder("password", DafaGameLogin.getPasswordEncode(random, "duke123")) //"b4e82b683394b50b679dc2b51a79d987"
                .addBuilder("userType", "0") //正式0/测试1/遊客2
                .addBuilder("random", encodeRandom)
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
                .url("http://" + host + "/v1/users/login")
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
