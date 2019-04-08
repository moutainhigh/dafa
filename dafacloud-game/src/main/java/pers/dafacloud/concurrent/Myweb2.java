package pers.dafacloud.concurrent;

import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Myweb2 extends WebSocketClient {
    private static Logger logger = Logger.getLogger(Myweb2.class);

    public Myweb2(String serverUri) throws URISyntaxException {
        super(new URI(serverUri));
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("握手...");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("接收："+message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("异常"+ex);
    }
}
