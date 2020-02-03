package pers.dafacloud.runWSJavaxSX;

import pers.dafacloud.dafacloudUtils.Constants;
import pers.dafacloud.dafacloudUtils.Login;
import pers.dafacloud.model.BetGameContent;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class StartWs {

    private static String host = Constants.host;

    public static Map<String, Integer> map = new ConcurrentHashMap<>();
    public static volatile String currentIssue = "";

    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    //每个用户每次投注筹码数量，例：1,1,5,5,10
    static int chipCount = 3;//筹码个数

    //每种筹码数量，分别对应：1,5,10,50,100,500,1000,5000,10000
    private static int[] chip = {700, 700, 70, 12, 12, 2, 2, 1, 0};

    //每个用户每一注间隔时间，
    static boolean ifRandom = false;//true随机，false不随机
    static int minSleep = 200;//随机最小间隔，毫秒
    static int MaxSleep = 1000;//随机最大间隔，毫秒
    static int defaultSleep = 5000;//不随机时，间隔

    static List<BetGameContent> PosThree;
    static List<BetGameContent> PosSix;
    static List<BetGameContent> PosEight;
    static List<BetGameContent> PosFour;

    static int[] betChip = initializateBetChip();

    public static void main(String[] args) throws Exception {

        //初始化BetContent
        //1.3个盘口，和盘口占少数，例：龙虎，红黑
        PosThree = initializateBetContent(new int[]{20, 20, 2});
        //2.6个盘口，均分,例：牛牛
        PosSix = initializateBetContent(new int[]{1, 1, 1, 1, 1, 1});
        //3.8个盘口，例：奔驰宝马
        PosEight = initializateBetContent(new int[]{1, 1, 1, 1, 8, 8, 8, 8});
        //4.4个盘口，均分，例：骰宝
        PosFour = initializateBetContent(new int[]{1, 1, 1, 1});

        Map<Integer, Integer> map = new HashMap<>();
        map.put(2001, 0);//牛牛
        map.put(2002, 0);//红黑
        map.put(2003, 50);//龙虎
        map.put(2004, 0);//百家乐
        map.put(2005, 0);//奔驰宝马
        map.put(2006, 0);//骰宝
        //List<String> user = new ArrayList<>(Arrays.asList("servant", "servant5", "servant10", "pp111", "servant101", "servant102"));
        List<String> user = FileUtil.readFile(StartWs.class.getResourceAsStream("/users.txt"));
        int index = 0;
        for (Integer key : map.keySet()) {
            //System.out.println("key=" + key);
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
            SendMessageSX sendMessageSX = new SendMessageSX(wsUrl, userName, gameCode, httpConfig);
            excutors.execute(sendMessageSX::process);//等价于excutors.execute(() -> sendMessageSX.process());
            try {
                Thread.sleep(4 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 初始化投注筹码
     */
    private static int[] initializateBetChip() {
        int[] betChip = new int[StartWs.chip[0] + StartWs.chip[1] + StartWs.chip[2] + StartWs.chip[3] + StartWs.chip[4] + StartWs.chip[5] + StartWs.chip[6] + StartWs.chip[7] + StartWs.chip[8]];
        int indexCount = 0;
        for (int i = 0; i < StartWs.chip[0]; i++) {
            betChip[indexCount++] = 1;
        }
        for (int i = 0; i < StartWs.chip[1]; i++) {
            betChip[indexCount++] = 5;
        }
        for (int i = 0; i < StartWs.chip[2]; i++) {
            betChip[indexCount++] = 10;
        }
        for (int i = 0; i < StartWs.chip[3]; i++) {
            betChip[indexCount++] = 50;
        }
        for (int i = 0; i < StartWs.chip[4]; i++) {
            betChip[indexCount++] = 100;
        }
        for (int i = 0; i < StartWs.chip[5]; i++) {
            betChip[indexCount++] = 500;
        }
        for (int i = 0; i < StartWs.chip[6]; i++) {
            betChip[indexCount++] = 1000;
        }
        for (int i = 0; i < StartWs.chip[7]; i++) {
            betChip[indexCount++] = 5000;
        }
        for (int i = 0; i < StartWs.chip[8]; i++) {
            betChip[indexCount++] = 10000;
        }
        return betChip;
    }

    /**
     * 初始化投注内容
     */
    private static List<BetGameContent> initializateBetContent(int[] i) {
        List<BetGameContent> listBetGameContent = new ArrayList<>();
        for (int j = 0; j < i.length; j++) {
            for (int k = 0; k < i[j]; k++) {
                BetGameContent betGameContent = new BetGameContent();
                betGameContent.setProto("700");
                betGameContent.setPos(String.valueOf(j + 1));
                listBetGameContent.add(betGameContent);
            }
        }
        System.out.println(listBetGameContent);
        return listBetGameContent;
    }
}
