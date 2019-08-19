package com.dafagame.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import lombok.Data;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

//握手
@Data
public class ClientHandshaker {

    private HttpHeaders headers;
    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    private Channel channel;
    private AtomicBoolean success = new AtomicBoolean(false); // AtomicBoolean 高效并发处理 “只初始化一次” 的功能要求

    //构造器
    public ClientHandshaker(URI uri){
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        try {
            //URI websocketURI = new URI(String.format("ws://%s:%s/ws",host,port));
            //进行握手
            this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String) null, true, httpHeaders);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handlerShakerChannel(Channel channel) {
        try {
            this.channel = channel;
            this.handshaker.handshake(channel);
            this.handshakeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean isSuccess(Channel channel, Object msg) {
        boolean isSuccess = this.handshaker.isHandshakeComplete();
        if (!isSuccess) {
            if (!success.get()) {
                this.handshaker.finishHandshake(channel,(FullHttpResponse)msg);
                this.handshakeFuture.setSuccess();
                this.success.set(true);
            }
        }
        return this.success.get();
    }

    public boolean isSuccess() {
        return success.get();
    }

}
