package pers.dafacloud.runWSOnline;

import org.testng.annotations.Test;
import pers.dafacloud.dafacloudUtils.Constants;
import pers.dafacloud.dafacloudUtils.Login;
import pers.dafacloud.model.BetGameContent;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Testws {

    private static String host = Constants.host;

    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    public static void main(String[] args) throws Exception {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(2001, 0);//牛牛
        map.put(2002, 0);//红黑
        map.put(2003, 2);//龙虎
        map.put(2004, 0);//百家乐
        map.put(2005, 0);//奔驰宝马
        map.put(2006, 0);//骰宝
        //List<String> user = FileUtil.readFile(Testws.class.getResourceAsStream("/users.txt"));
        List<String> user = new ArrayList<>(Arrays.asList("duke002", "duke003"));
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
            HttpConfig httpConfig = Login.loginReturnHttpConfig(userName);//登录
            //System.out.println(token);
            String wsUrl = String.format("ws://%s:4147/gameServer/?TOKEN=tokenvalue&gameId=%s", new URL(host).getHost(), gameCode);
            //SendMessageSX sendMessageSX =
            // new SendMessageSX("ws://" + new URL(host).getHost() + "/gameServer/?TOKEN=" + token + "&gameId=" + gameCode, userName, 2003);
            //System.out.println(wsUrl);
            SendMessageSX sendMessageSX = new SendMessageSX(wsUrl, userName, 2003, httpConfig);
            excutors.execute(sendMessageSX::process);//等价于excutors.execute(() -> sendMessageSX.process());
            try {
                Thread.sleep(4 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test(description = "测试")
    public static void test01() {
        String s = "ws://m.caishen01.com:4147/gameServer/?TOKEN=47f270489e784ff3b0ab4b000c58d325&gameId=2003";
        System.out.println(s.replaceAll("[TOKEN=][.*]{0,9}[&gameId]", "aaaaaa"));
        System.out.println(s.replaceAll("(TOKEN=).*?(&gameId)", "$1aaaaaa$2"));
        //System.out.println("".replace());

        String rex2 = "TOKEN=(.*?)&gameId";
        Pattern pattern2 = Pattern.compile(rex2);
        //Matcher matcher2 = pattern2.matcher(s);
        //if(matcher2.find()) {
        //    System.out.println(matcher2.group());
        //}


    }
}
