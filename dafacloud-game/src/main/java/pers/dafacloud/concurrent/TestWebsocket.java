package pers.dafacloud.concurrent;

import org.apache.http.cookie.Cookie;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.utils.common.FileUtils;
import pers.dafacloud.constans.Environment;
import pers.dafacloud.loginPage.LoginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestWebsocket {

    static Environment environment = Environment.DEFAULT;
    public static String betContentPathName;

    public static final int stepTime=1000;

    public static void main(String[] args) throws Exception {

        //betContentPathName= args[1];

        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();

        //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");

        //登陆获取user登陆获取token
        //User user = (User) ac.getBean("user");
        List<String> userList = FileUtils.readfile("D:/users.txt");

        LoginPage loginPage = new LoginPage();

        //List<String> tokenList = new ArrayList<>();
        /*for (String userName : userList){
            Cookie cookie = loginPage.getDafaCooike(userName,"123456");
            String token = loginPage.getGameToken(cookie);
            System.out.println(token);
            tokenList.add("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2001");
        }*/

        for (int i = 0; i < 50; i++) {
            String userName = userList.get(i);
            Cookie cookie = loginPage.getDafaCooike(userList.get(i),"123456");
            String token = loginPage.getGameToken(cookie);
            //System.out.println(token);
            //tokenList.add("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2002");
            WebSocketSendTask  webSocketSendTask =new WebSocketSendTask("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2003",userName);
            tasks.add(webSocketSendTask);
            System.out.println(i+userList.get(i));
        }

        /*for (int i = 0; i < tokenList.size(); i++) {
            webSocketSendTask =new WebSocketSendTask(tokenList.get(i));
            tasks.add(webSocketSendTask);
        }*/

        //多线程时，手动初始化token
        /*Token token = (Token) ac.getBean("token");
        List<String> tokenList = new ArrayList<>();
        for (String tokens : token.getTokenList()){
            tokenList.add("ws://m.pers.dafacloud-test.com/gameServer/?TOKEN="+tokens+"&gameId=2002");
        }*/

        //单线程执行
        /*WebSocketSendTask webSocketSendTask =new WebSocketSendTask("ws://m.pers.dafacloud-test.com/gameServer/?TOKEN=549e4410ce784c419fcca94f13b5f486&gameId=2002");
        tasks.add(webSocketSendTask);*/


        //通过多线程一次性发起，并拿到返回结果集
        List<Map<String, String>> results = callableTaskFrameWork
                .submitsAll(tasks);
        // 解析返回结果集
        for (Map<String, String> map : results) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
        System.out.println("结束");

    }
}
