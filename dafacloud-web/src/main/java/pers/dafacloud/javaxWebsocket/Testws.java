package pers.dafacloud.javaxWebsocket;

import org.apache.http.cookie.Cookie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.concurrent.CallableTaskFrameWork;
import pers.dafacloud.concurrent.ICallableTaskFrameWork;
import pers.dafacloud.concurrent.WebSocket;
import pers.dafacloud.constans.Environment;
import pers.dafacloud.entities.User;
import pers.dafacloud.loginPage.LoginPage;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Testws {
    private static Environment environment = Environment.DEFAULT;
    private static WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    public static void main(String[] args) throws Exception{

        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<SendMessage> tasks = new ArrayList<>();

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");
        User user = (User) ac.getBean("user");
        List<String> userList = user.getUserList();
        LoginPage loginPage = new LoginPage();

        WebSocket webSocket =null;

        for (int i = 0; i < 500; i++) {
            String userName = userList.get(i);
            Cookie cookie = loginPage.getDafaCooike(userList.get(i),"123456");
            String token = loginPage.getGameToken(cookie);
            //System.out.println(token);
            //tokenList.add("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2002");
            // webSocket =new WebSocket("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003",userName);
            //WebSocketConnectionManager manager = new WebSocketConnectionManager(client, new TestWebsocket.MyHandler(), "ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003");
//            MywsClient mywsClient =new MywsClient("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003");
//            mywsClient.creatConnect();
//            tasks.add(mywsClient);

            //SendMessage sendMessage =new SendMessage("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003");
            ResponceMessage responceMessage =new ResponceMessage();
            Session session  = container.connectToServer(responceMessage, URI.create("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003"));
            session.setMaxTextMessageBufferSize(2048000);
            session.setMaxBinaryMessageBufferSize(204800);
            //因为是休眠保持连接，所以不能直接
            SendMessage sendMessage =new SendMessage(session,responceMessage);
            //sendMessage.process();//这里调用会进入单线程
            tasks.add(sendMessage);
            //manager.start();
            System.out.println(i+userList.get(i));
        }
        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);//多线程执行


        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        List<Map<String, String>> results = callableTaskFrameWork
//                .submitsAll(tasks);

    }
}
