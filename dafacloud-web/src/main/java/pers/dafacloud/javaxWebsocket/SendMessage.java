package pers.dafacloud.javaxWebsocket;

import pers.dafacloud.concurrent.CallableTemplate;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Map;

public class SendMessage extends CallableTemplate<Map<String, String>> {

    private static WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    private String uri;

    public SendMessage(String uri){
        this.uri=uri;
    }

    @Override
    public Map<String, String> process() {
        try {
            Session session  = container.connectToServer(ResponceMessage.class, URI.create(uri));
            session.setMaxTextMessageBufferSize(20480000);
            session.setMaxBinaryMessageBufferSize(2048000);
            session.getBasicRemote().sendText("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
