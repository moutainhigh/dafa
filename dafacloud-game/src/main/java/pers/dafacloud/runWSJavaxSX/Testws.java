package pers.dafacloud.runWSJavaxSX;

import org.apache.http.cookie.Cookie;
import pers.dafacloud.common.FileUtils;
import pers.dafacloud.concurrent.CallableTaskFrameWork;
import pers.dafacloud.concurrent.ICallableTaskFrameWork;
import pers.dafacloud.constans.Environment;
import pers.dafacloud.loginPage.LoginPage;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Testws {
    private static Environment environment = Environment.DEFAULT;
    //private static WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    public static void main(String[] args) throws Exception{

        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<SendMessageSX> tasks = new ArrayList<>();
        //用户数据
        List<String> userList = FileUtils.readfile("D:/users.txt");

        LoginPage loginPage = new LoginPage();

        //用户数量
        for (int i = 0; i < 150; i++) {
            String userName = userList.get(i);
            Cookie cookie = loginPage.getDafaCooike(userList.get(i),"123456");
            String token = loginPage.getGameToken(cookie);
            SendMessageSX sendMessageSX= null;
            if (i<50){
                 sendMessageSX= new SendMessageSX("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2001",userName,2001);}
            else if (i<100){
                sendMessageSX= new SendMessageSX("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2002",userName,2002);}
            else if (i<150){
                sendMessageSX= new SendMessageSX("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003",userName,2003);}
            //sendMessage.process();//这里调用会进入单线程
            tasks.add(sendMessageSX);
            System.out.println(i+userList.get(i));
        }
        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);//多线程执行
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
