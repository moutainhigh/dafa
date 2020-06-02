package pers.dafacloud.runWSJavaxSX;

import pers.dafacloud.dafacloudUtils.Login;
import pers.dafacloud.entity.BetGameContent;
import pers.utils.httpclientUtils.HttpConfig;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class SendMessageSX {
    private String url;
    private String username;
    private int gameID;

    private HttpConfig httpConfig;
    private Session session;
    private ResponceMessage responceMessage;


    public SendMessageSX(String url, String username, int gameID, HttpConfig httpConfig) {
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

    //@Override
    public Map<String, String> process() {
        try {
            //2.
            //bet内容
            List<BetGameContent> PosThree = StartWs.PosThree;
            List<BetGameContent> PosSix = StartWs.PosSix;
            List<BetGameContent> PosEight = StartWs.PosEight;
            List<BetGameContent> PosFour = StartWs.PosFour;
            //提取结果
            Thread.sleep(1000);
            int[] betChip = StartWs.betChip;

            StringBuffer sb = new StringBuffer();
            //
            for (int i = 0; i < 70000000; i++) {
                if (!session.isOpen()) {
                    System.out.println("断线重连");
                    getToken();
                    Thread.sleep(5000);
                }
                //System.out.println("status:" + responceMessage.getStatus());
                if (responceMessage.getStatus() != 1) {
                    //System.out.println(responceMessage.getStatus()+" - 不在投注状态，等待时间：" + responceMessage.getStateTime());
                    Thread.sleep(responceMessage.getStateTime() * 1000 > 1000 ? responceMessage.getStateTime() * 1000 : 1000);
                    continue;
                }
                //每一个用户没一次投注间隔时间
                if (StartWs.ifRandom) {
                    int indexSleep = StartWs.minSleep + (int) (Math.random() * (StartWs.MaxSleep - StartWs.minSleep));
                    Thread.sleep(indexSleep);
                } else {
                    Thread.sleep(StartWs.defaultSleep);
                }
                //设置筹码个数
                for (int j = 0; j < StartWs.chipCount; j++) {
                    int index2 = (int) (Math.random() * betChip.length);
                    sb.append(betChip[index2] + ",");
                }
                BetGameContent betGameContent;
                if (gameID == 2001) { //牛牛
                    int index = (int) (Math.random() * PosSix.size());
                    betGameContent = PosSix.get(index);
                    betGameContent.setIssue(responceMessage.getIssue());
                    betGameContent.setBettingPoint(responceMessage.getUserRebate());
                    betGameContent.setBettingAmount(sb.substring(0, sb.length() - 1));
                } else if (gameID == 2005) { //奔驰
                    int index = (int) (Math.random() * PosEight.size());
                    betGameContent = PosEight.get(index);
                    betGameContent.setIssue(responceMessage.getIssue());
                    betGameContent.setBettingPoint(responceMessage.getUserRebate());
                    betGameContent.setBettingAmount(sb.substring(0, sb.length() - 1));
                } else if (gameID == 2006) { //骰宝
                    int index = (int) (Math.random() * PosFour.size());
                    betGameContent = PosFour.get(index);
                    betGameContent.setIssue(responceMessage.getIssue());
                    betGameContent.setBettingPoint(responceMessage.getUserRebate());
                    betGameContent.setBettingAmount(sb.substring(0, sb.length() - 1));
                } else {
                    int index = (int) (Math.random() * PosThree.size());
                    betGameContent = PosThree.get(index);
                    betGameContent.setIssue(responceMessage.getIssue());
                    betGameContent.setBettingPoint(responceMessage.getUserRebate());
                    betGameContent.setBettingAmount(sb.substring(0, sb.length() - 1));
                }

                if (session.isOpen()) {
                    session.getBasicRemote().sendText(betGameContent.toString());//发送消息
                }
                sb.setLength(0);//清空字符流
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

