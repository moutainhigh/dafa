package pers.dafacloud.broadCast8;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class SendMessageSX {

   public static void process() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ResponceMessage  responceMessage = new ResponceMessage();//返回信息类
            Session  session = container.connectToServer(responceMessage, URI.create("ws://m.caishen02.com/v1/broadCast/chat?type=8"));
            session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
            session.setMaxBinaryMessageBufferSize(204800);
            for (; ; ) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText("{\"code\":9}");//发送消息
                    Thread.sleep(30000);
                } else {
                    break;
                }

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}

