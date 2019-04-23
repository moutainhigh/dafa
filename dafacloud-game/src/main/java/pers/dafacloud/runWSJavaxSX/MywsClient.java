package pers.dafacloud.runWSJavaxSX;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;


public class MywsClient {

    //public static Session session = null;

    private String uri;

    public MywsClient(String uri){
        this.uri=uri;
    }

    public void creatConnect() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        //String uri = "ws://app.dfcdn5.com/gameServer/?TOKEN=581d18053ae84b6dbba07e57c2389fbb&gameId=2003";
        System.out.println("Connecting to 2" + uri);
        try {
            Session session  = container.connectToServer(ResponceMessage.class, URI.create(uri));
            session.setMaxTextMessageBufferSize(2048000);
            session.getBasicRemote().sendText("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
