package com.dafagame.netty;

import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import pers.utils.httpclientUtils.HttpConfig;

@Data
public abstract class GameHandler extends SimpleChannelInboundHandler {
    protected ClientHandshaker handshaker;
    protected String phone;
    protected HttpConfig httpConfig;
}
