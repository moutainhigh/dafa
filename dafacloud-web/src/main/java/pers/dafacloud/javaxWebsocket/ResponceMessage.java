package pers.dafacloud.javaxWebsocket;


import javax.websocket.*;

@ClientEndpoint
public class ResponceMessage {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        //System.out.println(message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        System.out.println("关闭websocket："+session.getMaxBinaryMessageBufferSize()+","+session+","+reason+session.getMaxTextMessageBufferSize());

    }
//    @OnClose
//    public void OnClose(String t) {
//        System.out.println(t);
//    }
}
