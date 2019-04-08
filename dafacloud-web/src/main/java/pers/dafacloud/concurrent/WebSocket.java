package pers.dafacloud.concurrent;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.entities.BetGameContent;
import pers.dafacloud.webSocketClient.MyWebSocketClient;

import java.util.*;

public class WebSocket extends CallableTemplate<Map<String, String>> {
    Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private MyWebSocketClient client;
    private int index;

    public WebSocket(String url,int index) throws Exception {
        this.client = new MyWebSocketClient(url);
        this.index = index;
    }

    @Override
    public Map<String, String> process() {

        //bet内容
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");
        BetGameContent betGameContent = (BetGameContent) ac.getBean("betGameContent");
        List<String> listBetContent = betGameContent.getContent();
        //提取结果
        Map<String, String> map = new HashMap<>();

        //连接ws
        client.connect();
        //连接
        int count = 0;
        while (!client.getReadyState().equals(org.java_websocket.WebSocket.READYSTATE.OPEN)) {
            try {
                Thread.sleep(10);
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

        /*String betContent = "";
        if(index==0){
            betContent = listBetContent.get(0);
        }else if(index==1){
            betContent = listBetContent.get(1);
        } else if(index==2){
            betContent = listBetContent.get(2);
        }*/

        //投注
        int count2 = 1;
        while (count2 < 3000) {
            count2++;
            try {
                Thread.sleep(300);//投注间隔1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //不在投注状态，且时间不等于0
            /*if (client.status != 1) {
                System.out.println("不在投注，状态休眠：" + client.stateTime + "s,status：" + client.status);
                continue;
            }*/
            //随机选择一注
            int index = (int) (Math.random() * listBetContent.size());
            String sendMessage = String.format(listBetContent.get(index), client.getIssue(), client.userRebate);
            //System.out.println(System.currentTimeMillis()+"，"+(count2-1)+","+client.stateTime+","+client.status+",发送=====：" + sendMessage);


            client.send(sendMessage);

            /*String sendMessage = String.format(betContent, client.getIssue(), client.userRebate);
            client.send(sendMessage);*/
            //logger.info(sendMessage);
        }

        map.put("resulit", "建立websocket连接");
        client.close();

        return map;
    }
}
