package com.dafagame.netty;

import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;

@Data
public abstract class GameHandler extends SimpleChannelInboundHandler {
    protected ClientHandshaker handshaker;
    protected String phone;
}
