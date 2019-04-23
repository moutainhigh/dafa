package pers.dafacloud.concurrent;

import org.apache.http.cookie.Cookie;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.constans.Environment;
import pers.dafacloud.entities.User;
import pers.dafacloud.loginPage.LoginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDuo {

    static Environment environment = Environment.DEFAULT;

    public static void main(String[] args) throws Exception {
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:beans.xml");


        //登陆获取user登陆获取token
        User user = (User) ac.getBean("user");
        List<String> userList = user.getUserList();
        LoginPage loginPage = new LoginPage();
        List<String> tokenList = new ArrayList<>();
        //for (String userName : userList){
        for (int i = 0; i < 30; i++) {
            Cookie cookie = loginPage.getDafaCooike(userList.get(i),"123456");
            String token = loginPage.getGameToken(cookie);
            System.out.println(token);
            tokenList.add("ws://"+environment.domain+"/gameServer/?TOKEN="+token+"&gameId=2002");
        }

        WebSocket webSocket =null;
        for (int i = 0; i < tokenList.size(); i++) {
            webSocket =new WebSocket(tokenList.get(i),i);
            webSocket.process();
            //tasks.add(webSocket);
        }

        //多线程时，手动初始化token
        /*Token token = (Token) ac.getBean("token");
        List<String> tokenList = new ArrayList<>();
        for (String tokens : token.getTokenList()){
            tokenList.add("ws://m.pers.dafacloud-test.com/gameServer/?TOKEN="+tokens+"&gameId=2002");
        }*/

        //单线程执行
        /*WebSocket webSocket =new WebSocket("ws://m.pers.dafacloud-test.com/gameServer/?TOKEN=549e4410ce784c419fcca94f13b5f486&gameId=2002");
        tasks.add(webSocket);*/


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
