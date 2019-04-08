package pers.dafacloud.webSocketClient;

import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.entities.BetGameContent;

import java.util.List;

public class TestSocket {

    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(TestSocket.class);
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:dafaBeans.xml");
        BetGameContent betGameContent = (BetGameContent) ac.getBean("betGameContent");
        List<String> listBetContent = betGameContent.getContent();

        MyWebSocketClient client =
                new MyWebSocketClient(
                        "ws://m.dafacloud-test.com/gameServer/?TOKEN=a435234d86a846509d9fd8ad2d15e3a4&gameId=2002");
        client.connect();
        int count = 0;
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("正在连接..." + (count++));
        }
        System.out.println("建立websocket连接");


        //进入游戏时 等待713出现
        int  countTime=0;
        while (countTime<10){
            System.out.println("等待713出现次数："+countTime);
            countTime++;
            Thread.sleep(1000);
            if(client.stateTime!=0){//获取到值结束循环
                break;
            }
        }
        //状态和倒计时
        /*Timer timer = new Timer();
        if (!client.status.equals("1")) { //不在投注状态
            timer.schedule(new TimerTask() {
                int time = Integer.parseInt(client.stateTime);
                @Override
                public void run() {
                    time--;
                    if (time == 0) {

                    }
                }
            }, 0, 1000);
        }*/

        //循环取list的数据投注
        /*for (int i = 0; i < listBetContent.size(); i++) {
            while(client.status!=1&&client.stateTime!=0){
                System.out.println("不在投注，状态休眠："+client.stateTime+"s,status："+client.status);
                Thread.sleep(client.stateTime*1000);
            }
            Thread.sleep(2000);//投注间隔2秒
            String sendMessage = String.format(listBetContent.get(i),client.getIssue(),client.userRebate);
            System.out.println("发送信息："+sendMessage);
            client.send(sendMessage);
        }*/

        //随机从list取一条数据投注
        while (true){
            while(client.status!=1&&client.stateTime!=0){//不在投注状态，且时间不等于0
                System.out.println("不在投注，状态休眠："+client.stateTime+"s,status："+client.status);
                Thread.sleep(client.stateTime*1000);
            }
            Thread.sleep(2000);//投注间隔2秒
            int index = (int) (Math.random() * listBetContent.size());
            String sendMessage = String.format(listBetContent.get(index),client.getIssue(),client.userRebate);
            System.out.println("发送=====："+sendMessage);
            client.send(sendMessage);
        }
    }

}


