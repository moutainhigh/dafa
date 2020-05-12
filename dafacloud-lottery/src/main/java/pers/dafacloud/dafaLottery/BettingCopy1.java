package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BettingCopy1 {
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    //private static String host = LotteryConstant.host;
    //private static String host = "http://caishen02.com";//dev
    //private static String host = "http://52.76.195.164:8020";//dev第一套
    //private static String host = "http://52.77.207.64:8020";//dev第二套
    //private static String host = "http://dafacloud-pre.com";//pre

    private String lotteryConfig;

    private List<String> users = null;
    private List<String> usersTenant = null;

    private String addBettingUrl;
    //private String rebateUrl;
    private String getBetRebate;

    private JSONObject rebateJson;//返点json配置文件

    private boolean isFilePoint;//是否使用返点json文件

    private boolean isIP = false;//是否通过ip下注

    public BettingCopy1(String host, String lotteryConfig, boolean isFilePoint, boolean isTenant, boolean isPlatform) {
        //this.host = host;
        this.lotteryConfig = lotteryConfig;
        this.addBettingUrl = host + "/v1/betting/addBetting";
        this.getBetRebate = host + "/v1/betting/getBetRebate";
        this.isFilePoint = isFilePoint;

        JSONObject jsonObjectPoint = null;
        if (isFilePoint)
            jsonObjectPoint = JSONObject.parseObject(FileUtil.readFileRetrunString(BettingRunning.class.getResourceAsStream("/pointJson/point.json")));
        if (host.contains("52.76.195.164")) {
            this.isIP = true;
            if (isFilePoint)
                rebateJson = jsonObjectPoint.getJSONObject("dev1Dafa");
            if (isPlatform)
                users = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev1DafaIP.txt"));
            if (isTenant)
                usersTenant = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev1DafaIPTenant.txt"));
        } else if (host.contains("52.77.207.64")) {
            this.isIP = true;
            if (isFilePoint)
                rebateJson = jsonObjectPoint.getJSONObject("dev2Dafa");
            if (isPlatform)
                users = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev2DafaIP.txt"));
            if (isTenant)
                usersTenant = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev2DafaIPTenant.txt"));
        } else if (host.contains("caishen02")) {
            users = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev1Dafa.txt"));
            if (isFilePoint)
                rebateJson = jsonObjectPoint.getJSONObject("dev1Dafa");
        } else if (host.contains("caishen03")) {
            users = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/dev2Dafa.txt"));
            if (isFilePoint)
                rebateJson = jsonObjectPoint.getJSONObject("dev2Dafa");
        } else if (host.contains("dafacloud-pre")) {
            users = FileUtil.readFile(BettingCopy1.class.getResourceAsStream("/users/preDafa.txt"));
            if (isFilePoint)
                rebateJson = jsonObjectPoint.getJSONObject("preDafa");
        }
    }

    /**
     * 获取彩种对应的系列：1分彩，3分彩，5分彩
     *
     * @param lotteryCode 彩种编码
     * @return int 1,3,5
     */
    private static int isYsw(String lotteryCode) {
        switch (Integer.parseInt(lotteryCode)) {
            case 1407:
            case 1008:
            case 1300:
            case 1304:
            case 1418:
            case 1018:
            case 1312:
            case 1310:
            case 101:
                return 1;
            case 1412:
            case 1009:
            case 1306:
            case 1305:
            case 1419:
            case 1019:
            case 1313:
            case 1311:
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
     * 获取彩种类型
     */
    private static String getLotteryType(String lotteryCode) {
        switch (Integer.parseInt(lotteryCode)) {
            case 1407:
            case 1413:
            case 1412:
            case 1418:
            case 1419:
            case 101:
                return "K3";
            case 1008:
            case 1010:
            case 1009:
            case 1018:
            case 1019:
                return "SSC";
            case 1304:
            case 1314:
            case 1306:
            case 1312:
            case 1313:
                return "PK10";
            case 1305:
            case 1300:
                return "LHC";
            default:
                return "";
        }
    }


    /**
     * 随机获取投注记录
     */
    private static String getBettingData(List<String> betContents, String rebate) {
        int betContentIndex = (int) (Math.random() * (betContents.size()));
        String betContent = betContents.get(betContentIndex);
        String[] betContentArray = betContent.split("`");
        net.sf.json.JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", betContentArray[0])
                .put("playDetailCode", betContentArray[1])
                .put("bettingNumber", betContentArray[2])
                .put("bettingAmount", betContentArray[3])
                .put("bettingCount", betContentArray[4])
                //.put("bettingPoint", rebate.get(getLotteryType(betContentArray[0])))
                .put("bettingPoint", rebate)
                .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])))
                .put("graduationCount", betContentArray[5])
                .put("bettingUnit", betContentArray[6])
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        return UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        //if ("1407".equals(betContentArray[0])) {
        //    return UrlBuilder.custom().addBuilder("bettingData", "[{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"18\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"" + LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])) + "\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"17\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"16\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"15\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"14\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"13\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"12\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"11\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"10\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"9\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"8\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"7\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"5\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"4\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"3\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1}]").fullBody();
        //} else if ("1008".equals(betContentArray[0])) {
        //    return UrlBuilder.custom().addBuilder("bettingData", "[{\"lotteryCode\":\"1008\",\"playDetailCode\":\"1008A11\",\"bettingNumber\":\"0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8\",\"bettingCount\":45,\"bettingAmount\":90,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"" + LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])) + "\",\"graduationCount\":1}]").fullBody();
        //} else {
        //    return null;
        //}
    }

    /**
     *
     */
    private void bettingExecu(String users, List<String> betContents, int n, String lotteryCode) {
        String[] userArray = users.split(",");
        Header[] headers;
        HttpConfig httpConfig;
        if (isIP) {
            headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", userArray.length == 3 ? userArray[2] : "dafa")
                    .other("x-user-name", userArray[0])
                    .other("x-source-Id", "1")
                    .build();
            httpConfig = HttpConfig.custom()
                    .url(addBettingUrl)
                    .headers(headers);
        } else {
            httpConfig = Login.loginReturnHttpConfig(users);//登录
        }
        String rebate;
        if (isFilePoint) {
            rebate = rebateJson.getString(lotteryCode);
        } else {
            rebate = JSONObject.parseObject(DafaRequest.get(httpConfig.url(getBetRebate + "?lotteryType=" + getLotteryType(lotteryCode))))
                    .getJSONObject("data")
                    .getString("rebate");
        }
        for (int i = 0; i < 100000000; i++) {
            String betContent = getBettingData(betContents, rebate);
            String result = DafaRequest.post(httpConfig.url(addBettingUrl).body(betContent));//下注请求
            //try {
            //    if (JSONObject.parseObject(result).getInteger("code") != 1)
            //        System.out.println(betContent);
            //} catch (Exception e) {
            //    System.out.println("json error:" + result);
            //}
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
    private void bettingLoop(List<String> users, String betContentPath, int n, String lotteryCode, int threadTime) {
        List<String> betContents = FileUtil.readFile(BettingCopy1.class.getResourceAsStream(betContentPath));
        for (String user : users) {
            System.out.println(user);
            excutors.execute(() -> bettingExecu(user, betContents, n, lotteryCode));
            try {
                Thread.sleep(threadTime * 1000);//每隔4秒登录一个用户
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void run(int threadTime) {
        for (String lotteryOne : lotteryConfig.split(";")) {
            String[] lottery = lotteryOne.split(",");//lotteryCode，文件名，用户数量，下单间隔
            //执行
            String betContentPath;
            List<String> user0 = new ArrayList<>();
            if ("1018".equals(lottery[0]) || "1019".equals(lottery[0])
                    || "1312".equals(lottery[0]) || "1313".equals(lottery[0]) || "1418".equals(lottery[0]) || "1419".equals(lottery[0])) {
                if (usersTenant == null) {
                    System.out.println("usersTenanat = null ");
                    return;
                }
                for (int i = 0; i < Integer.parseInt(lottery[2]); i++) {
                    user0.add(usersTenant.remove(i));
                }
                betContentPath = String.format("/tenantBetContent/%s%s.txt", lottery[0], lottery[1]);
            } else {
                if (users == null) {
                    System.out.println("users = null ");
                    return;
                }
                for (int i = 0; i < Integer.parseInt(lottery[2]); i++) {
                    user0.add(users.remove(i));
                }
                betContentPath = String.format("/betContent/%s.txt", lottery[1]);
            }
            bettingLoop(user0, betContentPath, Integer.parseInt(lottery[3]), lottery[0], threadTime);
        }
    }
}
