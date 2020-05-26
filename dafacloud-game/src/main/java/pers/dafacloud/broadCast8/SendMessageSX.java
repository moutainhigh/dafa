package pers.dafacloud.broadCast8;

import org.openjdk.jol.info.ClassLayout;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.UUID;

public class SendMessageSX {
    //static WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    public static void process() {
        try {
            //System.out.println(ClassLayout.parseInstance(responceMessage).toPrintable());
            //Session session = container.connectToServer(new ResponceMessage(), URI.create("ws://dafacloud-test.com/v1/broadCast/security?type=8&uuid=" + UUID.randomUUID() + "_" + System.currentTimeMillis()));

            //session.setMaxIdleTimeout(5000);
            //session.setMaxTextMessageBufferSize(128);//设置缓冲文本大小
            //session.setMaxBinaryMessageBufferSize(204800);
            //session.setMaxBinaryMessageBufferSize(128);
            //System.out.println(ClassLayout.parseInstance(session).toPrintable());
            for (; ; ) {
                //if (session.isOpen()) {
                //    session.getBasicRemote().sendText("{\"code\":9}");//发送消息
                    Thread.sleep(3000);
                //} else {
                //    break;
                //}

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

