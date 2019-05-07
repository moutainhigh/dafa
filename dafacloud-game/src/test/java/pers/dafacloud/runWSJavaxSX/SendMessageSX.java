package pers.dafacloud.runWSJavaxSX;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.dafacloud.pojo.BetGameContent;
import pers.dafacloud.utils.common.FileUtils;
import pers.dafacloud.utils.concurrent.CallableTemplate;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMessageSX extends CallableTemplate<Map<String, String>> {


    private String url;
    private String username;
    private int gameID;

    public SendMessageSX(String url, String username, int gameID) {
        this.url = url;
        this.username = username;
        this.gameID = gameID;
    }

    @Override
    public Map<String, String> process() {
        try {
            //1。创建链接
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ResponceMessage responceMessage = new ResponceMessage(username);//返回信息
            Session session = container.connectToServer(responceMessage, URI.create(url));
            session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
            session.setMaxBinaryMessageBufferSize(204800);

            //2.
            //bet内容
            //List<String> listBetContent = FileUtils.readFile(FileUtils.class.getClassLoader().getResource("betContent.txt").getPath());
            //List<String> listBetContentNiuNiu = FileUtils.readFile(FileUtils.class.getClassLoader().getResource("betContentNiuNiu.txt").getPath());
            List<BetGameContent> PosThree = Testws.PosThree;
            List<BetGameContent> PosSix = Testws.PosSix;
            List<BetGameContent> PosEight = Testws.PosEight;
            List<BetGameContent> PosFour = Testws.PosFour;

            //提取结果
            //Map<String, String> map = new HashMap<>();
            Thread.sleep(1000);

            //int[] amount=new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,5,10,50,100,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,5,10,50,100,100,500,1000,5000,10000};//,5,10,50,100,500,1000,5000
            int[] betChip = Testws.betChip;

            StringBuffer sb = new StringBuffer();
            //
            for (int i = 0; i < 700000; i++) {
                //每一个用户没一次投注间隔时间
                if (Testws.ifRandom) {
                    int indexSleep = Testws.minSleep + (int) (Math.random() * (Testws.MaxSleep - Testws.minSleep));
                    Thread.sleep(indexSleep);
                } else {
                    Thread.sleep(Testws.defaultSleep);
                }
                //设置筹码个数
                for (int j = 0; j < Testws.chipCount; j++) {
                    int index2 = (int) (Math.random() * betChip.length);
                    sb.append(betChip[index2] + ",");
                }
                //设置投注内容
                /*int index = (int) (Math.random() * listBetContent.size());
                int indexNiuNiu = (int) (Math.random() * listBetContentNiuNiu.size());
                String sendMessage;
                if (gameID == 2001) {
                    sendMessage = String.format(listBetContentNiuNiu.get(indexNiuNiu), responceMessage.getIssue(), responceMessage.getUserRebate(), sb.substring(0, sb.length() - 1));
                } else {
                    sendMessage = String.format(listBetContent.get(index), responceMessage.getIssue(), responceMessage.getUserRebate(), sb.substring(0, sb.length() - 1));
                }*/

                //int indexNiuNiu = (int) (Math.random() * listBetContentNiuNiu.size());
                BetGameContent betGameContent = null;
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
                //System.out.println(betGameContent);
                //发送消息
                if (session.isOpen())
                    session.getBasicRemote().sendText(betGameContent.toString());
                sb.setLength(0);//清空字符流
                //System.out.println("sendMessage:"+sendMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

