package com.dafagame.netty;

import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import pers.utils.httpclientUtils.HttpConfig;

@Data
public abstract class GameHandler extends SimpleChannelInboundHandler {
    protected ClientHandshaker handshaker;
    protected String phone;
    protected String roundType;
    protected  int userType;
    //protected HttpConfig httpConfig;
}
