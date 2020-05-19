package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
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

public class Betting {
    public static ExecutorService excutors  = Executors.newFixedThreadPool(300);
    public static boolean isStop = true;

    private String addBettingUrl;
    private String getBetRebate;

    private JSONObject rebateJson;//返点json配置文件

    private boolean isFilePoint;//是否使用返点json文件

    private EV ev;

    public Betting(String host, List<LotteryObj> lotteryObjs, boolean isFilePoint, int threadStepTimeMill) {

        this.addBettingUrl = host + "/v1/betting/addBetting";
        this.getBetRebate = host + "/v1/betting/getBetRebate";
        this.isFilePoint = isFilePoint;
        this.ev = EV.getEV(host);

        List<String> users = null;
        List<String> usersTenant = null;

        if (ev == null) {
            System.out.println("ev = null");
            return;
        }
        //
        boolean isContainsTenantLottery = false;
        for (LotteryObj lotteryObj : lotteryObjs) {
            LotteryConfig lotteryConfig = LotteryConfig.getLottery(lotteryObj.getLotteryCode());
            String betContentPath;
            List<String> userSub = new ArrayList<>();
            if (lotteryConfig == null) {
                System.out.println("lotteryCode = null");
                return;
            }
            if (!lotteryConfig.isTenant) {//平台彩种
                if (users == null) {
                    if (ev.isIP)
                        users = FileUtil.readFile(Betting.class.getResourceAsStream(ev.userIP));
                    else
                        users = FileUtil.readFile(Betting.class.getResourceAsStream(ev.users));
                }
                for (int i = 0; i < lotteryObj.getUserCount(); i++) {
                    userSub.add(users.remove(i));
                }
                betContentPath = String.format("/betContent/%s.txt", lotteryObj.getBettingContentFileName());
            } else {//站长彩种
                if (!isContainsTenantLottery) {
                    isContainsTenantLottery = true;
                }
                if (usersTenant == null) {
                    usersTenant = FileUtil.readFile(Betting.class.getResourceAsStream(ev.userTenantIP));
                }
                for (int i = 0; i < lotteryObj.getUserCount(); i++) {
                    userSub.add(usersTenant.remove(i));
                }
                betContentPath = String.format("/tenantBetContent/%s%s.txt", lotteryObj.getLotteryCode(), lotteryObj.getBettingContentFileName());

            }
            bettingLoop(userSub, betContentPath, lotteryObj.getBettingStepTime(), lotteryConfig, threadStepTimeMill);
        }
        //返点配置文件
        if (isFilePoint) {
            JSONObject jsonObjectPoint = JSONObject.parseObject(FileUtil.readFileRetrunString(BettingRunning.class.getResourceAsStream("/pointJson/point.json")));
            if (isContainsTenantLottery)
                rebateJson = jsonObjectPoint.getJSONObject(ev.evPointTenant);
            else
                rebateJson = jsonObjectPoint.getJSONObject(ev.evName);
        }
    }

    /**
     * 按每个彩种，用户数量执行
     */
    private void bettingLoop(List<String> users, String betContentPath, int bettingStepTime, LotteryConfig lotteryConfig, int threadStepTimeMill) {
        List<String> betContents = FileUtil.readFile(Betting.class.getResourceAsStream(betContentPath));
        for (String user : users) {
            System.out.println(user);
            excutors.execute(() -> bettingExecu(user, betContents, bettingStepTime, lotteryConfig));
            try {
                Thread.sleep(threadStepTimeMill);//每隔n秒启动一个线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void bettingExecu(String users, List<String> betContents, int bettingStepTime, LotteryConfig lotteryConfig) {
        String[] userArray = users.split(",");
        Header[] headers;
        HttpConfig httpConfig;
        if (ev.isIP) {
            headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", userArray.length == 3 ? userArray[2] : "dafa")
                    .other("x-user-name", userArray[0])
                    .other("x-source-Id", "1")
                    //.other("x-client-ip", RandomIP.getRandomIp())
                    .build();
            httpConfig = HttpConfig.custom()
                    .url(addBettingUrl)
                    .headers(headers);
        } else {
            httpConfig = Login.loginReturnHttpConfig(users);//登录
        }
        String rebate;
        if (isFilePoint) {
            rebate = rebateJson.getString(lotteryConfig.code);
        } else {
            rebate = JSONObject.parseObject(DafaRequest.get(httpConfig.url(getBetRebate + "?lotteryType=" + lotteryConfig.type)))
                    .getJSONObject("data")
                    .getString("rebate");
        }
        //int lotteryType = isYsw(lotteryCode);
        for (int i = 0; i < 100000000; i++) {
            if (isStop) {
                Thread.currentThread().interrupt();
                break;
            }
            String betContent = getBettingData(betContents, rebate, lotteryConfig.timeType);
            String result = DafaRequest.post(httpConfig.url(addBettingUrl).body(betContent));//下注请求
            try {
                if (JSONObject.parseObject(result).getInteger("code") != 1)
                    System.out.println(betContent);
            } catch (Exception e) {
                System.out.println("json error:" + result);
            }
            System.out.println(result);
            try {
                Thread.sleep(bettingStepTime); //投注间隔时间
            } catch (Exception e) {
                e.printStackTrace();
                //Thread ;
            }
            //while (!Thread.interrupted()) {
            //    try {
            //        Thread.sleep(1000);
            //    } catch (InterruptedException ex) {
            //        ex.getMessage();
            //    }
            //}
        }
    }

    /**
     * 随机获取投注记录
     */
    private static String getBettingData(List<String> betContents, String rebate, int timeType) {
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
        String betContent = betContents.get((int) (Math.random() * (betContents.size())));
        String[] betContentArray0 = betContent.split("@");
        for (int i = 0; i < betContentArray0.length; i++) {
            String[] betContentArray = betContentArray0[i].split("`");
            net.sf.json.JSONObject order1 = JsonObjectBuilder.custom()
                    .put("lotteryCode", betContentArray[0])
                    .put("playDetailCode", betContentArray[1])
                    .put("bettingNumber", betContentArray[2])
                    .put("bettingAmount", betContentArray[3])
                    .put("bettingCount", betContentArray[4])
                    //.put("bettingPoint", rebate.get(getLotteryType(betContentArray[0])))
                    .put("bettingPoint", rebate)
                    .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(timeType))
                    .put("graduationCount", betContentArray[5])
                    .put("bettingUnit", betContentArray[6])
                    .bulid();
            jsonArrayBuilder
                    .addObject(order1);
        }

        //for (int i = 0; i < 4; i++) {
        //    int betContentIndex = (int) (Math.random() * (betContents.size()));
        //    String betContent = betContents.get(betContentIndex);
        //    String[] betContentArray = betContent.split("`");
        //    net.sf.json.JSONObject order1 = JsonObjectBuilder.custom()
        //            .put("lotteryCode", betContentArray[0])
        //            .put("playDetailCode", betContentArray[1])
        //            .put("bettingNumber", betContentArray[2])
        //            .put("bettingAmount", betContentArray[3])
        //            .put("bettingCount", betContentArray[4])
        //            //.put("bettingPoint", rebate.get(getLotteryType(betContentArray[0])))
        //            .put("bettingPoint", rebate)
        //            .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(lotteryType))
        //            .put("graduationCount", betContentArray[5])
        //            .put("bettingUnit", betContentArray[6])
        //            .bulid();
        //    jsonArrayBuilder
        //            .addObject(order1);
        //}
        return UrlBuilder.custom().addBuilder("bettingData", jsonArrayBuilder.bulid().toString()).fullBody();
        //if ("1407".equals(betContentArray[0])) {
        //    return UrlBuilder.custom().addBuilder("bettingData", "[{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"18\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"" + LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])) + "\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"17\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"16\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"15\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"14\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"13\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"12\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"11\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"10\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"9\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"8\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"7\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"5\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"4\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"6.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1},{\"lotteryCode\":\"1407\",\"playDetailCode\":\"1407A10\",\"bettingNumber\":\"3\",\"bettingCount\":1,\"bettingAmount\":1,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"202005011324\",\"graduationCount\":1}]").fullBody();
        //} else if ("1008".equals(betContentArray[0])) {
        //    return UrlBuilder.custom().addBuilder("bettingData", "[{\"lotteryCode\":\"1008\",\"playDetailCode\":\"1008A11\",\"bettingNumber\":\"0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8,0 1 2 3 4 5 6 7 8\",\"bettingCount\":45,\"bettingAmount\":90,\"bettingPoint\":\"8.0\",\"bettingUnit\":1,\"bettingIssue\":\"" + LotteryIssuePrivate.getCurrentIssue(isYsw(betContentArray[0])) + "\",\"graduationCount\":1}]").fullBody();
        //} else {
        //    return null;
        //}
    }
}
