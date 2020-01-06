package pers.dafacloud.runWSOnline;

import pers.dafacloud.dafacloudUtils.Login;
import pers.dafacloud.model.BetGameContent;
import pers.utils.httpclientUtils.HttpConfig;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class SendMessageSX {
    private String url;
    private String username;
    private int gameID;
    private HttpConfig httpConfig;
    private Session session;
    private ResponceMessage responceMessage;


    SendMessageSX(String url, String username, int gameID, HttpConfig httpConfig) {
        this.url = url;
        this.username = username;
        this.gameID = gameID;
        this.httpConfig = httpConfig;
        getToken();
    }

    private void getToken() {
        String token = Login.getGameToken(httpConfig);//获取token
        createConnection(url.replaceAll("(TOKEN=).*?(&gameId)", String.format("$1%s$2", token)));
    }

    /**
     * 1.创建链接
     */
    private void createConnection(String url) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            responceMessage = new ResponceMessage(username);//返回信息类
            session = container.connectToServer(responceMessage, URI.create(url));
            session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
            session.setMaxBinaryMessageBufferSize(204800);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void process() {
        try {
            for (int i = 0; i < 70000000; i++) {
                if (!session.isOpen()) {
                    System.out.println("重连");
                    getToken();
                    Thread.sleep(5000);
                }
                if (responceMessage.getStatus() != 1) {
                    Thread.sleep(responceMessage.getStateTime() * 1000 > 1000 ? responceMessage.getStateTime() * 1000 : 1000);
                    continue;
                }
                BetGameContent betGameContent = new BetGameContent();
                betGameContent.setProto("700");
                betGameContent.setIssue(responceMessage.getIssue());
                betGameContent.setBettingPoint(responceMessage.getUserRebate());
                betGameContent.setBettingAmount("100");
                if (session.isOpen()) {
                    if (ResponceMessage.count == 13 ) {
                        if (ResponceMessage.chipTotalPos1 > ResponceMessage.chipTotalPos2) { //下注小的一边
                            if ("duke002".equals(username)) {
                                betGameContent.setPos("1");
                                session.getAsyncRemote().sendText(betGameContent.toString());
                            } else if ("duke003".equals(username)) {
                                betGameContent.setPos("2");
                                session.getAsyncRemote().sendText(betGameContent.toString());
                            } else {
                                System.out.println(username + "下注用户名对不上");
                            }
                        } else if (ResponceMessage.chipTotalPos1 < ResponceMessage.chipTotalPos2) { //下注大的一边
                            if ("duke002".equals(username)) {
                                betGameContent.setPos("2");
                                session.getAsyncRemote().sendText(betGameContent.toString());
                            } else if ("duke003".equals(username)) {
                                betGameContent.setPos("1");
                                session.getAsyncRemote().sendText(betGameContent.toString());
                            } else {
                                System.out.println(username + "下注用户名对不上");
                            }
                        } else {
                            System.out.println("第13秒，此时龙虎金额相等，此局不下注");
                        }
                    }
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

