package com.dafagame.netty;

import com.dafagame.protocol.gate.Gate;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.propertiesUtils.PropertiesUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URI;

public class WsProtoClient {

    private static EventLoopGroup group = new NioEventLoopGroup(1);
    private ChannelInitial initial = new ChannelInitial();
    //private DafaHandler dafaHandler = new DafaHandler();
    private BrnnHandler dafaHandler = new BrnnHandler();
    private ClientHandshaker handShaker;

    private Logger logger = LoggerFactory.getLogger(WsProtoClient.class);
    private Channel channel;
    private Thread thread;

    private static String url = "ws://game-gate.dafagame-test.com/v1/game/gameGate";

    //构造器
    public WsProtoClient( String phone) {
//        this.host = host;
//        this.port = port;
        System.out.println(group);
        URI uri = URI.create(url);
        this.handShaker = new ClientHandshaker(uri); //握手
        this.dafaHandler.setHandshaker(handShaker);//
        this.initial.setChannelHandler(this.dafaHandler);

        thread = new Thread(() -> {
            try {
                //登陆==================================================================
                String random = "9722";
                //Encoder encoder = Base64.getEncoder();
                //String encode = encoder.encodeToString(random.getBytes());
                String encodeRandom = "OTcyMg==";
                String body = UrlBuilder.custom()
                        .addBuilder("inviteCode", "")
                        .addBuilder("accountNumber", phone)
                        .addBuilder("password", DigestUtils.md5Hex("duke123") + encodeRandom) //"b4e82b683394b50b679dc2b51a79d987"
                        .addBuilder("userType", "0") //正式0/测试1/遊客2
                        .addBuilder("random", random)
                        .fullBody();
                HttpConfig httpConfig = HttpConfig
                        .custom()
                        .url(PropertiesUtil.getProperty("hostCoCos")+"/v1/users/login")
                        .body(body);
                String result = DafaRequest.post(httpConfig);
                System.out.println(result);
                String sessionId = JSONObject.fromObject(result).getJSONObject("data").getString("sessionId");
                dafaHandler.setPhone(phone);
                //ws链接==================================================================
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class) //
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(initial);

                ChannelFuture f = bootstrap
                        .connect(uri.getHost(), 80)  //链接
                        .sync();
                this.channel = f.channel(); //
                //logger.info(LogUtil.info(String.format("连接 ip:%s port:%s",host,port)));
                this.handShaker.setChannel(this.channel);
                this.handShaker.handlerShakerChannel(this.channel);
                int counter = 0;
                while (counter++ <= 0) {
                    if (this.handShaker.isSuccess()) {
                        Gate.GateReq gateReq = Gate.GateReq.newBuilder()
                                .setLoginReq(
                                        Gate.LoginReq.newBuilder()
                                                .setSessionId(sessionId) // sessionId
                                                .setUrl("192.168.8.44:7000")
                                                .setSourceId("2")
                                                .setTenantCode("cindy")
                                                .build()
                                ).build();
                        Gate.ClientMsg clientMsg = Gate.ClientMsg.newBuilder()
                                .setProto(Gate.ProtoType.GateReqType_VALUE)
                                .setData(gateReq.toByteString())
                                .build();
                        ByteBuf bf = Unpooled.buffer().writeBytes(clientMsg.toByteArray());
                        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(bf);
                        channel.writeAndFlush(binaryWebSocketFrame);
                        System.out.println("ws链接成功");
                        Thread.sleep(2000);
                    }
                }
                this.channel.closeFuture().sync();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("服务启动错误");
                //logger.error(LogUtil.error("服务启动错误",e));
            } finally {
//                group.shutdownGracefully();
            }
        });

    }

    public void start() {
        if (!this.thread.isAlive()) {
            this.thread.start();
        }
    }

    public static void main(String[] args) throws Exception {
//        Login loginPage = new Login();
//        loginPage.loginDafaGame("95231885");
//        loginPage.loginDafaGame("94790768");
//        loginPage.loginDafaGame("60398442");

//        String url = "ws://game-gate.dafagame-test.com/v1/game/gameGate";
//        WsProtoClient ws1 = new WsProtoClient(url, "44408638159645ab96f1c56e41786c66");
//        WsProtoClient ws2 = new WsProtoClient(url, "5b238fc95e7a45bd9f1952e996d86cd2");
//        WsProtoClient ws3 = new WsProtoClient(url, "70aa6bbc142b454db3f19e6ddc77aa57");
//        ws1.start();
//        ws2.start();
//        ws3.start();

//        String phone = "1311234";
//        for (int i = 500; i < 600; i++) {
//            new WsProtoClient(String.format("%s%s", phone, String.format("%04d", i))).start();
//            Thread.sleep(200);
//        }
        new WsProtoClient("21539097").start();
    }
}
