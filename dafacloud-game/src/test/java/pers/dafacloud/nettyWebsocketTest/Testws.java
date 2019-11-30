package pers.dafacloud.nettyWebsocketTest;

import org.apache.http.cookie.Cookie;
import pers.dafacloud.loginPage.LoginPage;
import pers.dafacloud.model.BetGameContent;
import pers.dafacloud.utils.concurrent.CallableTaskFrameWork;
import pers.dafacloud.utils.concurrent.ICallableTaskFrameWork;
import pers.dafacloud.utils.enums.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1.用户
 */

public class Testws {
    private static Environment environment = Environment.DEFAULT;
    static int betChip[] = null;

    //用户
    private static  int NN = 0;//牛牛
    private static  int HH = 0;//红黑
    private static  int LH = 0;//龙虎
    private static  int BJL = 0;//百家乐
    private static  int BCBM = 0;//奔驰宝马
    private static  int SBAO = 500;//骰宝

    //每个用户每次投注筹码数量，例：1,1,5,5,10
    public static int chipCount = 1;//筹码个数

    //每种筹码数量，分别对应：1,5,10,50,100,500,1000,5000,10000
    private static int[] chip = {700, 700, 70, 5, 3, 2, 1, 1, 1};

    //每个用户每一注间隔时间，
    static boolean ifRandom = true;//是否随机,true表示随机，false表示不随机
    static int minSleep = 500;//随机最小间隔，毫秒
    static int MaxSleep = 1000;//随机最大间隔，毫秒
    static int defaultSleep = 3000;//不随机时，间隔

    static List<BetGameContent> PosThree;
    static List<BetGameContent> PosSix;
    static List<BetGameContent> PosEight;
    static List<BetGameContent> PosFour;


    public static void main(String[] args) throws Exception {
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<Send> tasks = new ArrayList<>();
        //初始化投注筹码
        betChip = initializateBetChip();
        //初始化BetContent
        //1.3个盘口，和盘口占少数，例：龙虎，红黑
        PosThree = initializateBetContent(new int[]{20, 20, 2});
        //2.6个盘口，均分,例：牛牛
        PosSix = initializateBetContent(new int[]{1, 1, 1, 1, 1, 1});
        //3.8个盘口，例：奔驰宝马
        PosEight = initializateBetContent(new int[]{1, 1, 1, 1, 8, 8, 8, 8});
        //4.4个盘口，均分，例：骰宝
        PosFour = initializateBetContent(new int[]{1, 1, 1, 1});

        //初始化用户
        //List<String> userList = FileUtils.readFile(FileUtils.class.getClassLoader().getResource("users.txt").getPath());


        LoginPage loginPage = new LoginPage();

        //用户数量
        for (int i = 0; i < NN + HH + LH + BJL + BCBM + SBAO; i++) {
            //String userName = userList.get(i);
            String userName = String.format("dafai%04d",i+1);
            Cookie cookie = loginPage.getDafaCooike(userName, "123456");
            String token = loginPage.getGameToken(cookie);
            Send send = null;
            if (i < NN) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2001", userName, 2001);
            } else if (i < (NN + HH)) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2002", userName, 2002);
            } else if (i < (NN + HH + LH)) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2003", userName, 2003);
            } else if (i < (NN + HH + LH + BJL)) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2004", userName, 2004);
            } else if (i < (NN + HH + LH + BJL + BCBM)) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2005", userName, 2005);
            } else if (i < (NN + HH + LH + BJL + BCBM + SBAO)) {
                send = new Send("ws://" + environment.domain + "/gameServer/?TOKEN=" + token + "&gameId=2006", userName, 2006);
            }


            if (send != null)
                tasks.add(send);
            System.out.println(i + userName);
        }
        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);//多线程执行
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化投注筹码
     */
    public static int[] initializateBetChip() {
        int[] betChip = new int[Testws.chip[0] + Testws.chip[1] + Testws.chip[2] + Testws.chip[3] + Testws.chip[4] + Testws.chip[5] + Testws.chip[6] + Testws.chip[7] + Testws.chip[8]];
        int indexCount = 0;
        //System.out.println("length:"+amount.length);
        for (int i = 0; i < Testws.chip[0]; i++) {
            betChip[indexCount++] = 1;
        }
        //System.out.println(indexCount);
        for (int i = 0; i < Testws.chip[1]; i++) {
            betChip[indexCount++] = 5;
        }
        //System.out.println(indexCount);
        for (int i = 0; i < Testws.chip[2]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 10;
        }
        for (int i = 0; i < Testws.chip[3]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 50;
        }
        for (int i = 0; i < Testws.chip[4]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 100;
        }
        for (int i = 0; i < Testws.chip[5]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 500;
        }
        for (int i = 0; i < Testws.chip[6]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 1000;
        }
        for (int i = 0; i < Testws.chip[7]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 5000;
        }
        for (int i = 0; i < Testws.chip[8]; i++) {
            //System.out.println("a:"+((indexCount++)));
            betChip[indexCount++] = 10000;
        }

       /* System.out.println(indexCount);
        for (int i = 0; i < amount.length; i++) {
            System.out.println(amount[i]);
        }*/
        return betChip;
    }
    /**
     *初始化投注内容
     * */
    public static List<BetGameContent> initializateBetContent(int[] i) {
        List<BetGameContent> listBetGameContent = new ArrayList<>();
        //int[] i = {20,20,3};
        for (int j = 0; j < i.length; j++) {
            for (int k = 0; k < i[j]; k++) {
                BetGameContent betGameContent = new BetGameContent();
                betGameContent.setProto("700");
                betGameContent.setPos(String.valueOf(j + 1));
                listBetGameContent.add(betGameContent);
            }
        }
        return listBetGameContent;
        /*for (BetGameContent betGameContent:listBetGameContent)
            System.out.println(betGameContent);*/

    }
}
