package pers.dafacloud.runBcbm;

import pers.dafacloud.dafacloudUtils.Login;
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
                //非投注间隔
                if (!responceMessage.isCanBetting()) {
                    responceMessage.setCanBetting(false);
                    Thread.sleep(2000);
                    continue;
                }
                //if (responceMessage.xiaoCount > 17 || responceMessage.xiaoCount < 4) {
                //    responceMessage.setCanBetting(false);
                //    System.out.println("长龙2:" + responceMessage.xiaoCount);
                //    Thread.sleep(2000);
                //    continue;
                //}
                System.out.println("长龙1:" + responceMessage.xiaoCount);
                //{"proto":700,"gameCode":2005,"data":{"issue":"06110147","bettingPoint":8.5,"betReqInfo":[{"pos":5,"bettingAmount":[100,100]},{"pos":6,"bettingAmount":[100,100]},{"pos":7,"bettingAmount":[100,100]},{"pos":8,"bettingAmount":[100,100]}]}}
                String tempD = "{\"proto\":700,\"gameCode\":2005,\"data\":{\"issue\":\"%s\",\"bettingPoint\":%s,\"betReqInfo\":[{\"pos\":1,\"bettingAmount\":[%s]},{\"pos\":2,\"bettingAmount\":[%s]},{\"pos\":3,\"bettingAmount\":[%s]},{\"pos\":4,\"bettingAmount\":[%s]}]}}";
                //String tempX = "{\"proto\":700,\"gameCode\":2005,\"data\":{\"issue\":\"%s\",\"bettingPoint\":%s,\"betReqInfo\":[{\"pos\":5,\"bettingAmount\":[%s]},{\"pos\":6,\"bettingAmount\":[%s]},{\"pos\":7,\"bettingAmount\":[%s]},{\"pos\":8,\"bettingAmount\":[%s]}]}}";
                String tempX = "{\"proto\":700,\"gameCode\":2005,\"data\":{\"issue\":\"%s\",\"bettingPoint\":%s,\"betReqInfo\":[{\"pos\":5,\"bettingAmount\":[%s]},{\"pos\":6,\"bettingAmount\":[%s]},{\"pos\":7,\"bettingAmount\":[%s]},{\"pos\":8,\"bettingAmount\":[%s]}]}}";
                String tempDX = "{\"proto\":700,\"gameCode\":2005,\"data\":{\"issue\":\"%s\",\"bettingPoint\":%s,\"betReqInfo\":[{\"pos\":1,\"bettingAmount\":[%s]},{\"pos\":2,\"bettingAmount\":[%s]},{\"pos\":3,\"bettingAmount\":[%s]},{\"pos\":4,\"bettingAmount\":[%s]},{\"pos\":5,\"bettingAmount\":[%s]},{\"pos\":6,\"bettingAmount\":[%s]},{\"pos\":7,\"bettingAmount\":[%s]},{\"pos\":8,\"bettingAmount\":[%s]}]}}";

                //JSONArray jsonArray = new JSONArray();
                //JSONObject jsonObject = new JSONObject();
                //jsonObject.put("pos",1);
                //jsonObject.put("bettingAmount","");

                if (session.isOpen()) {
                    //int amount = 100 * (responceMessage.xiaoCount + 1);
                    String amount;
                    String dzAmount;
                    //if (responceMessage.xiaoCount == 4) {
                    //    amount = "500";//2500
                    //} else if (responceMessage.xiaoCount == 5) {
                    //    amount = "500";//2500
                    //} else if (responceMessage.xiaoCount == 6) {
                    //    amount = "1000";//5000
                    //} else if (responceMessage.xiaoCount == 7) {
                    //    amount = "1000";//5000
                    //} else if (responceMessage.xiaoCount == 8) {
                    //    amount = "1000,500";//7500
                    //} else if (responceMessage.xiaoCount == 9) {
                    //    amount = "1000,500";//7500
                    //} else if (responceMessage.xiaoCount == 10) {
                    //    amount = "1000,1000";//10000
                    //} else if (responceMessage.xiaoCount == 11) {
                    //    amount = "1000,1000";//10000
                    //} else if (responceMessage.xiaoCount == 12) {
                    //    amount = "1000,1000,500";//12500
                    //} else if (responceMessage.xiaoCount == 13) {
                    //    amount = "1000,1000,500";//12500
                    //} else if (responceMessage.xiaoCount == 14) {
                    //    amount = "1000,1000,1000";//15000
                    //} else if (responceMessage.xiaoCount == 15) {
                    //    amount = "1000,1000,1000";//15000
                    //} else if (responceMessage.xiaoCount == 16) {
                    //    amount = "1000,1000,1000,500";//17500
                    //} else if (responceMessage.xiaoCount == 17) {
                    //    amount = "1000,1000,1000,500";//175000
                    //} else {
                    //    continue;
                    //}
                    String betconent;
                    if ("dukepre002".equals(username)) {
                        amount = "1000,1000,500";
                        dzAmount = "1000,1000,1000,1000";
                        String xiao = "10000,10000,5000";
                        betconent = String.format(tempD, responceMessage.getIssue(), responceMessage.getUserRebate(), amount, amount, amount, amount);
                        //betconent = String.format(tempDX, responceMessage.getIssue(), responceMessage.getUserRebate(), amount, amount, amount, amount, xiao, xiao, xiao, xiao);
                    } else {
                        amount = "500";
                        betconent = String.format(tempX, responceMessage.getIssue(), responceMessage.getUserRebate(), amount, amount, amount, amount);
                    }
                    //dzAmount = (amount + "," + amount);
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

    public static void function01() {

    }
}

