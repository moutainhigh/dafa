package pers.dafacloud.runWSJavaxSX;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.utils.common.FileUtils;
import pers.dafacloud.concurrent.CallableTemplate;

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

    public SendMessageSX(String url, String username,int gameID) {
        this.url = url;
        this.username = username;
        this.gameID = gameID;
    }

    @Override
    public Map<String, String> process() {
        try {
            //1。创建链接
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ResponceMessage responceMessage = new ResponceMessage(username);
            Session session = container.connectToServer(responceMessage, URI.create(url));
            session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
            session.setMaxBinaryMessageBufferSize(204800);

            //2.
            //bet内容
            List<String> listBetContent = FileUtils.readfile("D:/betContent.txt");
            List<String> listBetContentNiuNiu = FileUtils.readfile("D:/betContentNiuNiu.txt");
            //提取结果
            Map<String, String> map = new HashMap<>();
            Thread.sleep(1000);
            //System.out.println("getIssue：" + responceMessage.getIssue());
            int[] amount=new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,5,10,50,100,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,5,10,50,100,100,500,1000,5000,10000};//,5,10,50,100,500,1000,5000
            StringBuffer sb =new StringBuffer();
            //
            for (int i = 0; i < 700000; i++) {
                //每一个用户没一次投注间隔时间
                int indexSleep = 3+(int) (Math.random() * 6);
                Thread.sleep(100*indexSleep);
                //筹码个数
                for (int j = 0; j < 6; j++) {
                    int index2 =(int)(Math.random()*amount.length);
                    sb.append(amount[index2]+",");
                }
                int index = (int) (Math.random() * listBetContent.size());
                int indexNiuNiu = (int) (Math.random() * listBetContentNiuNiu.size());
                String sendMessage;
                if (gameID==2001){
                     sendMessage = String.format(listBetContentNiuNiu.get(indexNiuNiu), responceMessage.getIssue(), responceMessage.getUserRebate(),sb.substring(0,sb.length()-1));
                }else {
                    sendMessage = String.format(listBetContent.get(index), responceMessage.getIssue(), responceMessage.getUserRebate(),sb.substring(0,sb.length()-1));
                }
                if(session.isOpen())
                    session.getBasicRemote().sendText(sendMessage);
                sb.setLength(0);//清空字符流
                //System.out.println("sendMessage:"+sendMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

