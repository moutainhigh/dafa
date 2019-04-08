package pers.dafacloud.concurrent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.dafacloud.entities.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDuo {
    public static void main(String[] args) throws Exception {
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:dafaBeans.xml");

        //登陆获取user登陆获取token
        /*
        User user = (User) ac.getBean("user");
        List<String> userList = user.getUserList();
        LoginPage loginPage = new LoginPage();
        List<String> tokenList = new ArrayList<>();
        for (String userName : userList){
            Cookie cookie = loginPage.getDafaCooike(userName,"123456");
            String token = loginPage.getGameToken(cookie);
            System.out.println(token);
            tokenList.add("ws://m.dafacloud-test.com/gameServer/?TOKEN="+token+"&gameId=2002");
        }*/

        //手动初始化token
        Token token = (Token) ac.getBean("token");
        List<String> tokenList = new ArrayList<>();
        for (String tokens : token.getTokenList()){
            tokenList.add("ws://m.dafacloud-test.com/gameServer/?TOKEN="+tokens+"&gameId=2002");
        }

        //放在集合中，线程数
        WebSocket webSocket =null;
        for (int i = 0; i < tokenList.size(); i++) {
            webSocket =new WebSocket(tokenList.get(i),i);
            tasks.add(webSocket);
        }




        //通过多线程一次性发起，并拿到返回结果集
        List<Map<String, String>> results = callableTaskFrameWork
                .submitsAll(tasks);
        // 解析返回结果集
        for (Map<String, String> map : results) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }

    }
}
