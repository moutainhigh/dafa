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
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.propertiesUtils.PropertiesUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsProtoClient {
    private Logger logger = LoggerFactory.getLogger(WsProtoClient.class);
    private static String url = "ws://game-gate.dafagame-test.com/v1/game/gameGate";

    private static EventLoopGroup group = new NioEventLoopGroup(1);
    private ChannelInitial channelInitial = new ChannelInitial();
    //private DdzHandler ddzHandler = new DdzHandler();
    //private BrnnHandler brnnHandler = new BrnnHandler();
    //private static Map<Integer, GameHandler> gameHandler = new HashMap<>();
    private ClientHandshaker clientHandshaker;
    //private int gameCode;

    private Channel channel;
    private Thread thread;


    //构造器
    public WsProtoClient(String phone,int gameCode) {
        //this.host = host;
        //this.port = port;
        //System.out.println(group);
        URI uri = URI.create(url);
        this.clientHandshaker = new ClientHandshaker(uri); //握手
        GameHandler handler = GameHandlerFactory.newGameHandler(gameCode);
        handler.setHandshaker(clientHandshaker);
        //this.brnnHandler.setHandshaker(handShaker);//
        //this.ddzHandler.setHandshaker(handShaker);
        //SimpleChannelInboundHandler handler = gameHandler.get(gameCode);
        this.channelInitial.setChannelHandler(handler);
        //this.channelInitial.setChannelHandler(this.ddzHandler);

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
                        .url(PropertiesUtil.getProperty("hostCoCos") + "/v1/users/login")
                        .body(body);
                String result = DafaRequest.post(httpConfig);
                System.out.println(result);
                String sessionId = JSONObject.fromObject(result).getJSONObject("data").getString("sessionId");
                handler.setPhone(phone);
                //ws链接==================================================================
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class) //
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(channelInitial);

                ChannelFuture f = bootstrap
                        .connect(uri.getHost(), 80)  //链接
                        .sync();

                this.channel = f.channel(); //
                //logger.info(LogUtil.info(String.format("连接 ip:%s port:%s",host,port)));
                this.clientHandshaker.setChannel(this.channel);
                this.clientHandshaker.handlerShakerChannel(this.channel);
                int counter = 0;
                while (counter++ <= 0) {
                    if (this.clientHandshaker.isSuccess()) {
                        Gate.GateReq gateReq = Gate.GateReq.newBuilder()
                                .setLoginReq(
                                        Gate.LoginReq.newBuilder()
                                                .setSessionId(sessionId) // sessionId
                                                .setUrl("192.168.8.44") //自定义ip
                                                .setSourceId("2")
                                                .setTenantCode("duke")
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
        //String phone = "1311234";
        //for (int i = 500; i < 600; i++) {
        //    new WsProtoClient(String.format("%s%s", phone, String.format("%04d", i))).start();
        //    Thread.sleep(200);
        //}
        //new WsProtoClient("92582013").start();
        List<String> users = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafagame-client-bf/src/main/resources/dukeUser.txt");
        for (int i = 0; i < users.size(); i++) {
            new WsProtoClient(users.get(i),102).start();
            Thread.sleep(200);
        }
    }

    private static class GameHandlerFactory {
        public static GameHandler newGameHandler(int gameCode) {
            switch (gameCode){
                case 101:
                    return new DdzHandler();
                case 102:
                    return new BrnnHandler();
            }
            return null;
        }
    }
}
