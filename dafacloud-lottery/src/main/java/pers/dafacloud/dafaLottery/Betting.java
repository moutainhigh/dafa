package pers.dafacloud.dafaLottery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.dafacloud.UserGroup;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Betting {
    //private static String addBettingUrl = "http://52.76.195.164:8020/v1/betting/addBetting";
    private static String host = ConstantLottery.host;
    private static String addBettingUrl = host + "/v1/betting/addBetting";
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);


    /**
     * 获取彩种对应的返点
     *
     * @param lotteryCode
     * @return double 返点
     */
    public static double getPoint(String lotteryCode) {
        switch (Integer.parseInt(lotteryCode)) {
            //测试 大发站 dafai0002 系列用户
            //case 1407:
            //case 1413:
            //    return 8;
            //case 1412:
            //    return 8;
            //case 1008:
            //case 1010:
            //    return 8;
            //case 1009:
            //    return 8;
            //case 1304:
            //case 1314:
            //    return 8;
            //case 1306:
            //    return 8;
            //case 1300:
            //    return 10;
            //case 1305:
            //    return 10;
            //default:
            //    return 8;

            //测试 测试站 dafai0002 系列用户
            case 1407:
            case 1413:
                return 7;
            case 1412:
                return 7;
            case 1008:
            case 1010:
                return 7;
            case 1009:
                return 7;
            case 1304:
            case 1314:
                return 7;
            case 1306:
                return 7;
            case 1300:
                return 7;
            case 1305:
                return 7;
            default:
                return 7;

            //pre 大发站 autodf01287 系列用户
            //case 1407:
            //case 1413:
            //    return 6;
            //case 1412:
            //    return 6;
            //case 1008:
            //case 1010:
            //    return 6;
            //case 1009:
            //    return 6;
            //case 1304:
            //case 1314:
            //    return 6;
            //case 1306:
            //    return 6;
            //case 1300:
            //    return 6;
            //case 1305:
            //    return 6;
            //default:
            //    return 6;
        }
    }

    /**
     * 获取彩种对应的系列：1分，3分，5分
     *
     * @param lotteryCode
     * @return int 1,3,5
     */
    public static int isYsw(String lotteryCode) {
        switch (Integer.parseInt(lotteryCode)) {
            case 1407:
            case 1008:
            case 1300:
            case 1304:
                return 1;
            case 1412:
            case 1009:
            case 1306:
            case 1305:
                return 5;
            case 1413:
            case 1010:
            case 1314:
                return 3;
            default:
                return 1;
        }
    }

    /**
     * 随机获取投注记录
     */
    public static String getBettingData(List<String> betContents) {
        int betContentIndex = (int) (Math.random() * (betContents.size()));
        String betContent = betContents.get(betContentIndex);
        String[] betContentArray = betContent.split("`");
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", betContentArray[0])
                .put("playDetailCode", betContentArray[1])
                .put("bettingNumber", betContentArray[2])
                .put("bettingAmount", betContentArray[3])
                .put("bettingCount", betContentArray[4])
                .put("bettingPoint", getPoint(betContentArray[0]))
                .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])))
                .put("graduationCount", betContentArray[5])
                .put("bettingUnit", betContentArray[6])
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        return bettingData;
    }

    /**
     * 通过ip地址调用投注服务，不需要登录
     */
    public static void bettingByIp(String users, List<String> betContents) {
        String[] userArray = users.split(",");
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-user-id", userArray[0])
                .other("x-tenant-code", userArray[1])
                .other("x-user-name", userArray[2])
                .other("x-source-Id", "1")
                .build();

        HttpConfig httpConfig = HttpConfig.custom()
                .url(addBettingUrl)
                .body(getBettingData(betContents)).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "-" + userArray[2] + "-" + result);
    }

    /**
     * 通过url登录投注
     * <p>
     * 入参：带cookie的httpConfig
     */
    public static void bettingByUrl(HttpConfig httpConfig, List<String> betContents) {
        HttpConfig httpConfig0 = httpConfig.url(addBettingUrl).body(getBettingData(betContents));
        String result = DafaRequest.post(httpConfig0);
        System.out.println(result);
    }

    /**
     * 通过url登录投注，陆续登录，再循环调投注接口
     */
    public static void bettingByUrl(String user, List<String> betContents, int n) {
        HttpConfig httpConfig0 = Login.loginReturnHttpConfig(user);//登录
        for (int i = 0; i < 1000000000; i++) {
            String result = DafaRequest.post(httpConfig0.url(addBettingUrl).body(getBettingData(betContents)));//下注
            System.out.println(result);
            try {
                Thread.sleep(n * 1000); //投注间隔时间
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 按用户数量创建线程
     */
    public static void bettingByUrlLoop0(List<String> users, List<String> betContents, int n) {
        for (int i = 0; i < users.size(); i++) {
            String user = users.get(i);
            excutors.execute(() -> bettingByUrl(user, betContents, n));
            try {
                Thread.sleep(5 * 1000); //每隔4秒登录一个用户
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        //List<String> userlist = FileUtil.readFile(UserGroup.class.getResourceAsStream("/userGroup/userPreDafa.txt"));
        //注单内容
        List<String> betContents1008dfssc = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1008dfssc.txt"));
        List<String> betContents1009wfssc = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1009wfssc.txt"));
        List<String> betContents1300yflhc = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1300yflhc.txt"));
        List<String> betContents1304dfpk10 = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1304dfpk10.txt"));
        List<String> betContents1305wflhc = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1305wflhc.txt"));
        List<String> betContents1306wfpk10 = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1306wfpk10.txt"));
        List<String> betContents1407dfk3 = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1407dfk3.txt"));
        List<String> betContents1412wfk3 = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1412wfk3.txt"));
        //
        List<String> betContents1010sfssc = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1010sfssc.txt"));
        List<String> betContents1314sfpk10 = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1314sfpk10.txt"));
        List<String> betContents1413sfks = FileUtil.readFile(UserGroup.class.getResourceAsStream("/betContent/1413sfks.txt"));

        //pre dafa站
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01277")), betContents1008dfssc, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01287")), betContents1009wfssc, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01286")), betContents1300yflhc, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01285")), betContents1304dfpk10, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01284")), betContents1305wflhc, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01283")), betContents1306wfpk10, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01282")), betContents1407dfk3, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01281")), betContents1412wfk3, 30);
        //
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01280")), betContents1010sfssc, 18);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01279")), betContents1314sfpk10, 18);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("autodf01278")), betContents1413sfks, 18);

        //test dafa站
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0002")), betContents1008dfssc, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0003")), betContents1009wfssc, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0004")), betContents1300yflhc, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0005")), betContents1304dfpk10, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0006")), betContents1305wflhc, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0007")), betContents1306wfpk10, 30);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0008")), betContents1407dfk3, 6);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0009")), betContents1412wfk3, 30);
        //
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0010")), betContents1010sfssc, 18);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0011")), betContents1314sfpk10, 18);
        //bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dafai0012")), betContents1413sfks, 18);

        //test test站
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0997")), betContents1008dfssc, 6);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0996")), betContents1009wfssc, 30);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0995")), betContents1300yflhc, 6);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0994")), betContents1304dfpk10, 6);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0993")), betContents1305wflhc, 30);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0992")), betContents1306wfpk10, 30);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0991")), betContents1407dfk3, 6);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0990")), betContents1412wfk3, 30);

        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0989")), betContents1010sfssc, 18);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0988")), betContents1314sfpk10, 18);
        bettingByUrlLoop0(new ArrayList<>(Arrays.asList("dukea0987")), betContents1413sfks, 18);



    }
}
