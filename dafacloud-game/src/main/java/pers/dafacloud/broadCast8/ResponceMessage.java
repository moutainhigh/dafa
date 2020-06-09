package pers.dafacloud.broadCast8;


import pers.dafacloud.broadCast.Config;

import javax.websocket.*;

@ClientEndpoint(configurator = Config.class)
public class ResponceMessage {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: "  + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("关闭websocket：" + session.getMaxBinaryMessageBufferSize() + "," + reason + session.getMaxTextMessageBufferSize());
    }
}
