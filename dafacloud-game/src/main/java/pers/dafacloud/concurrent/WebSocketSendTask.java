package pers.dafacloud.concurrent;

import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.common.FileUtils;
//import pers.dafacloud.entities.BetGameContent;

import java.util.*;

public class WebSocketSendTask extends CallableTemplate<Map<String, String>> {

    //Logger logger = LoggerFactory.getLogger(WebSocketSendTask.class);

    private static Logger logger = Logger.getLogger(WebSocketSendTask.class);

    private MyWebSocketClient client;
    private String userName;
    private int sendCount;

    public WebSocketSendTask(String url, String userName) throws Exception {
        this.client = new MyWebSocketClient(url,userName);
        this.userName = userName;
    }

    @Override
    public Map<String, String> process() {
        //System.out.println("zxczxcxz");
        //bet内容
        //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");
        //BetGameContent betGameContent = (BetGameContent) ac.getBean("betGameContent");
        //List<String> listBetContent = betGameContent.getContent();
        List<String> listBetContent = FileUtils.readfile("D:/betContent.txt");
        //System.out.println(listBetContent);
        //提取结果
        Map<String, String> map = new HashMap<>();

        //连接ws
        client.connect();
        /*try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //连接
        int count = 0;
        while (!client.getReadyState().equals(org.java_websocket.WebSocket.READYSTATE.OPEN)) {
            try {
                Thread.sleep(TestWebsocket.stepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        //倒计时，1秒减1
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                client.stateTime--;
            }
        }, 1000, 1000);


//        String betContent = "";
//        if(userName.equals("dafai0001")){
//            betContent = listBetContent.get(0);
//        }else if(userName.equals("dafai0002")){
//            betContent = listBetContent.get(1);
//        } else if(userName.equals("dafai0003")){
//            betContent = listBetContent.get(2);
//        }else if(userName.equals("dafai0004")){
//            betContent = listBetContent.get(3);
//        }else if(userName.equals("dafai0005")){
//            betContent = listBetContent.get(4);
//        }else if(userName.equals("dafai0006")){
//            betContent = listBetContent.get(5);
//        }

//        int[] pos1=new int[20];
//        int[] pos2=new int[20];
//        int[] pos3=new int[20];
//        int[] pos4=new int[20];
//        int[] pos5=new int[20];
//        int[] pos6=new int[20];
        int[] amount=new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10,10,10,10,10,10,10,10,100,100,100,100,100,500,1000};//,5,10,50,100,500,1000,5000

        StringBuffer sb =new StringBuffer();



        //投注
        int count2 = 1;
        while (count2 < 50000000) {
            count2++;
            try {
                Thread.sleep(TestWebsocket.stepTime);//投注间隔1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //不在投注状态，且时间不等于0
//            if (client.status != 1) {
//                //System.out.println("不在投注，状态休眠：" + client.stateTime + "s,status：" + client.status);
//                continue;
//            }
            //随机选择一注
            /*int index = (int) (Math.random() * listBetContent.size());
            String sendMessage = String.format(listBetContent.get(index), client.getIssue(), client.userRebate);
            //System.out.println(System.currentTimeMillis()+"，"+(count2-1)+","+client.stateTime+","+client.status+",发送=====：" + sendMessage);
            client.send(sendMessage);*/

            for (int i = 0; i < 5; i++) {
                int index =(int)(Math.random()*amount.length);
                sb.append(amount[index]+",");
            }

            //System.out.println();
            int index = (int) (Math.random() * listBetContent.size());
            //String sendMessage = String.format(betContent, client.getIssue(), client.userRebate,sb.substring(0,sb.length()-1));
            String sendMessage = String.format(listBetContent.get(index), client.getIssue(), client.userRebate,sb.substring(0,sb.length()-1));
            //System.out.println(sendMessage);


            try {
                client.send(sendMessage);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                throw new RuntimeException("send message");
            }

            sendCount++;
            sb.setLength(0);
            //logger.info("发送-"+sendCount+","+userName+","+sendMessage);
            logger.info(sendMessage);
        }

        map.put("resulit", "建立websocket连接");
        client.close();

        return map;
    }
}
