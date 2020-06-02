package pers.dafacloud.nettyWebsocketTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LoggingHandler;

import java.net.URI;
import java.util.Map;

public class Send  {

//    private  static EventLoopGroup group = new NioEventLoopGroup();
    private static EventLoopGroup group = new NioEventLoopGroup(16);
//    private AtomicInteger counter = new AtomicInteger(0);

    private String url;
    private String username;
    private int gameID;

    public Send(String url,String username,int gameID ) {
        this.url = url;
        this.username=username;
        this.gameID =gameID;
    }

    public Map<String, String> process() throws Exception {
        Bootstrap boot = new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 1000)
                .group(group)
                .handler(new LoggingHandler())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                new HttpObjectAggregator(1024 * 1024 * 1000)});
                        p.addLast("hookedHandler", new WebSocketClientHandler());
                    }
                });
        //URI websocketURI = new URI("ws://m.caishen02.com:80/gameServer/?TOKEN=9aeb42ce79624797ac0d1b67255c8501&gameId=2003");
        URI uri = URI.create(url);
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String) null, true, httpHeaders);
        System.out.println("connecting");
        final Channel channel = boot.connect(uri.getHost(), 80).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("hookedHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();
        /*if(counter.addAndGet(1)>=300) {
            group = new NioEventLoopGroup();
        }*/

//        Thread text = new Thread(new Runnable() {
//            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (channel.isOpen()) {
//                        System.out.println("text send");
                        TextWebSocketFrame frame = new TextWebSocketFrame("我是文本");
                        channel.writeAndFlush(frame);
                    }}
                    /*channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            *//*if(channelFuture.isSuccess()){
                                System.out.println("text send success");
                            }else{
                                System.out.println("text send failed  "+channelFuture.cause().getMessage());
                            }*//*
                            if(!channelFuture.isSuccess()){
                                System.out.println();
                            }
                        }
                    });*/
//                }
//

            }
//        });

//        Thread bina=new Thread(new Runnable() {
//            public void run() {
//                File file=new File("C:\\Users\\Administrator\\Desktop\\testCookie.wav");
//                FileInputStream fin= null;
//                try {
//                    fin = new FileInputStream(file);
//                    int len=0;
//                    byte[] data=new byte[1024];
//                    while ((len=fin.read(data))>0){
//                        ByteBuf bf= Unpooled.buffer().writeBytes(data);
//                        BinaryWebSocketFrame binaryWebSocketFrame=new BinaryWebSocketFrame(bf);
//                        channel.writeAndFlush(binaryWebSocketFrame).addListener(new ChannelFutureListener() {
//                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                                if(channelFuture.isSuccess()){
//                                    System.out.println("bina send success");
//                                }else{
//                                    System.out.println("bina send failed  "+channelFuture.cause().toString());
//                                }
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        text.start();
//        bina.start();

    }
