package pers.dafacloud.concurrent;

import org.apache.http.cookie.Cookie;
import org.apache.log4j.Logger;
import pers.dafacloud.common.FileUtils;
import pers.dafacloud.loginPage.LoginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestUserModule {
    private static Logger logger = Logger.getLogger(TestUserModule.class);

    public static void main(String[] args) throws Exception {
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();
        //登陆获取user登陆获取token
        List<String> userList = FileUtils.readfile("D:/users.txt");
        LoginPage loginPage = new LoginPage();

        for (int i = 0; i < 5; i++) {
            String userName = userList.get(i);
            Cookie cookie = loginPage.getDafaCooike(userName,"123456");
            //String token = loginPage.getGameToken(cookie);
            TaskUserModuleRequest taskUserModuleRequest =new TaskUserModuleRequest(cookie);
            tasks.add(taskUserModuleRequest);
            logger.info("userName");
            //System.out.println(i+userList.get(i));
        }
        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);
        // 解析返回结果集
        for (Map<String, String> map : results) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
        System.out.println("结束");
    }
}
