package pers.dafacloud.runBcbm;

import pers.dafacloud.dafacloudUtils.Constants;
import pers.dafacloud.dafacloudUtils.Login;
import pers.utils.httpclientUtils.HttpConfig;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试线上bug
 */
public class Testws {

    private static String host = Constants.host;

    private static ExecutorService executors = Executors.newFixedThreadPool(50);

    public static void main(String[] args) throws Exception {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(2005, 2);//奔驰宝马
        //List<String> user = FileUtil.readFile(StartWs.class.getResourceAsStream("/users.txt"));
        //List<String> user = new ArrayList<>(Arrays.asList("dukeabc", "duke003"));
        //List<String> user = new ArrayList<>(Arrays.asList("autodf00001", "autodf00002"));
        List<String> user = new ArrayList<>(Arrays.asList("dafai0002", "dafai0003"));
        int index = 0;
        for (Integer key : map.keySet()) {
            if (map.get(key) > 0) {
                task(key, map.get(key), user.subList(index, index + map.get(key)));
                index += map.get(key);
            }
        }
    }

    public static void task(int gameCode, int count, List<String> list) throws Exception {
        for (int i = 0; i < count; i++) {
            String userName = list.get(i);
            System.out.println(userName);
            HttpConfig httpConfig = Login.loginReturnHttpConfig(userName);
            String wsUrl = String.format("ws://%s/gameServer/?TOKEN=tokenvalue&gameId=%s", new URL(host).getHost(), gameCode);
            SendMessageSX sendMessageSX = new SendMessageSX(wsUrl, userName, 2003, httpConfig);
            executors.execute(sendMessageSX::process);
            try {
                Thread.sleep(4 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
