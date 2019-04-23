package pers.dafacloud.javaxWebsocket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.concurrent.CallableTemplate;
import pers.dafacloud.entities.BetGameContent;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class SendMessage extends CallableTemplate<Map<String, String>> {


    private Session session;
    private ResponceMessage responceMessage;

    public SendMessage(Session session,ResponceMessage responceMessage ){
        this.session = session;
        this.responceMessage = responceMessage;
    }

    @Override
    public Map<String, String> process() {
        //bet内容
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");
        BetGameContent betGameContent = (BetGameContent) ac.getBean("betGameContent");
        List<String> listBetContent = betGameContent.getContent();
        try {


            int index = (int) (Math.random() * listBetContent.size());
            Thread.sleep(1000);
            System.out.println("getIssue："+responceMessage.getIssue());
            for (int i = 0; i < 500; i++) {
                String sendMessage = String.format(listBetContent.get(index), responceMessage.getIssue(), responceMessage.getUserRebate());
                session.getBasicRemote().sendText(sendMessage);
                Thread.sleep(1000);
                //System.out.println("sendMessage:"+sendMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
