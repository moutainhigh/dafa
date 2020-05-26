package pers.dafacloud.broadCast8;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.UUID;

public class SendMessageSX {

    public static void process() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ResponceMessage responceMessage = new ResponceMessage();//返回信息类
            Session session = container.connectToServer(responceMessage, URI.create("ws://dafacloud-test.com/v1/broadCast/security?type=8&uuid="+UUID.randomUUID()+"_"+System.currentTimeMillis()));
            session.setMaxIdleTimeout(5000);
            //session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
            session.setMaxTextMessageBufferSize(128);//设置缓冲文本大小
            //session.setMaxBinaryMessageBufferSize(204800);
            session.setMaxBinaryMessageBufferSize(128);
            for (; ; ) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText("{\"code\":9}");//发送消息
                    Thread.sleep(3000);
                } else {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

