package pers.dafacloud.runBcbm;

import pers.dafacloud.dafacloudUtils.Login;
import pers.dafacloud.entity.BetGameContent;
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
                if (!responceMessage.isCanBetting() || responceMessage.daCount > 7) {
                    Thread.sleep(2000);
                    continue;
                }
                //{"proto":700,"gameCode":2005,"data":{"issue":"06110147","bettingPoint":8.5,"betReqInfo":[{"pos":5,"bettingAmount":[100,100]},{"pos":6,"bettingAmount":[100,100]},{"pos":7,"bettingAmount":[100,100]},{"pos":8,"bettingAmount":[100,100]}]}}
                String temp = "{\"proto\":700,\"gameCode\":2005,\"data\":{\"issue\":\"%s\",\"bettingPoint\":%s,\"betReqInfo\":[{\"pos\":8,\"bettingAmount\":[%s]},{\"pos\":7,\"bettingAmount\":[%s]},{\"pos\":6,\"bettingAmount\":[%s]},{\"pos\":5,\"bettingAmount\":[%s]}]}}";
                if (session.isOpen()) {
                    //int amount = 100 * (responceMessage.daCount + 1);
                    String amount;//400
                    if (responceMessage.daCount == 0)
                        amount = "100";
                    else if (responceMessage.daCount == 1)
                        amount = "100,50";//700
                    else if (responceMessage.daCount == 2)
                        amount = "100,100,50";//1000
                    else if (responceMessage.daCount == 3)
                        amount = "100,100,100,50";//350*4=1400
                    else if (responceMessage.daCount == 4)
                        amount = "500";//2000
                    else if (responceMessage.daCount == 5)
                        amount = "500,100,100";//2800
                    else if (responceMessage.daCount == 6)
                        amount = "1000";//4000
                    else if (responceMessage.daCount == 7)
                        amount = "1000,1000";//8000
                    else
                        continue;
                    String betconent = String.format(temp, responceMessage.getIssue(), responceMessage.getUserRebate(), amount, amount, amount, amount);
                    System.out.println(betconent);
                    session.getAsyncRemote().sendText(betconent);
                    responceMessage.setCanBetting(false);
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

