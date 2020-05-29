package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.dafacloud.server.BetContentServer;
import pers.dafacloud.server.BetUsersServer;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Betting {
    public static ExecutorService excutors = Executors.newFixedThreadPool(300);
    public static boolean isStop = true;

    private String addBettingUrl;
    private String getBetRebate;

    private JSONObject rebateJson;//返点json配置文件

    private boolean isFilePoint;//是否使用返点json文件

    private EV ev;
    static String host0;

    private static Betting betting;

    private Map<String, List<Map>> betContentMap = new HashMap<>();


    @PostConstruct
    public void init() {
        betting = this;
    }

    @Autowired
    BetUsersServer betUsersServer;

    @Autowired
    BetContentServer betContentServer;


    public Betting() {
    }

    public Betting(String host, String urlTenantCode, int betContentType, List<LotteryObj> lotteryObjs, boolean isFilePoint, int threadStepTimeMill) {
        host0 = host;
        this.addBettingUrl = host + "/v1/betting/addBetting";
        this.getBetRebate = host + "/v1/betting/getBetRebate";
        this.isFilePoint = isFilePoint;
        this.ev = EV.getEV(host);

        List<Map> users = null;
        List<Map> usersTenant = null;

        if (ev == null) {
            System.out.println("ev = null");
            return;
        }

        boolean isContainsTenantLottery = false;
        for (LotteryObj lotteryObj : lotteryObjs) {
            if (isStop) {
                betContentMap.clear();
                break;
            }
            LotteryConfig lotteryConfig = LotteryConfig.getLottery(lotteryObj.getLotteryCode());
            List<Map> userSub = new ArrayList<>();
            if (lotteryConfig == null) {
                System.out.println("lotteryCode = null");
                return;
            }
            if (!lotteryConfig.isTenant) {//平台彩种
                if (users == null) {
                    if (ev.isIP) // type :1 url 1个字段，2 ip 3个字段 ； userType 1平台 ，2站长彩（要单独区分），
                        users = betting.betUsersServer.getBetUsersList(2, 1, ev.evCode, urlTenantCode);
                    else
                        users = betting.betUsersServer.getBetUsersList(1, 1, ev.evCode, urlTenantCode);
                }
                for (int i = 0; i < lotteryObj.getUserCount(); i++) {
                    try {
                        userSub.add(users.remove(0));
                    } catch (Exception e) {
                        System.out.println("users不足，" + lotteryObj.getLotteryCode() + "，" + lotteryObj.getLotteryCode() + "，usersTenant.size：" + usersTenant.size() + "，当前" + i);
                        break;
                    }
                }
            } else {//站长彩种
                if (!isContainsTenantLottery) {
                    isContainsTenantLottery = true;
                }
                if (usersTenant == null) {//== null保证只初始化一次
                    usersTenant = betting.betUsersServer.getBetUsersList(2, 2, ev.evCode, urlTenantCode);
                }
                for (int i = 0; i < lotteryObj.getUserCount(); i++) {
                    try {
                        userSub.add(usersTenant.remove(i));
                    } catch (Exception e) {
                        System.out.println("usersTenant不足，" + lotteryObj.getLotteryCode() + "，" + lotteryObj.getLotteryCode() + "，usersTenant.size：" + usersTenant.size() + "，当前" + i);
                        break;
                    }

                }
            }
            betContentMap.put(lotteryConfig.code, betting.betContentServer.getBetContentList(betContentType, lotteryConfig.code));
            bettingLoop(userSub, betContentType, lotteryObj.getBettingStepTime(), lotteryConfig, threadStepTimeMill);
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
    private void bettingLoop(List<Map> users, int betContentType, int bettingStepTime, LotteryConfig lotteryConfig, int threadStepTimeMill) {
        for (Map usersMap : users) {
            if (isStop) {
                //Thread.currentThread().interrupt();
                break;
            }
            excutors.execute(() -> bettingExecu(usersMap, betContentType, bettingStepTime, lotteryConfig));
            try {
                if (ev.isIP)
                    Thread.sleep(threadStepTimeMill);//每隔n秒启动一个线程
                else
                    Thread.sleep(5000);//每隔5秒启动一个线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行下注
     */
    private void bettingExecu(Map usersMap, int betContentType, int bettingStepTime, LotteryConfig lotteryConfig) {
        //List<Map> betContents = betting.betContentServer.getBetContentList(betContentType, lotteryConfig.code);
        Header[] headers;
        HttpConfig httpConfig;
        if (ev.isIP) {
            headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-id", usersMap.get("user_id").toString())
                    .other("x-tenant-code", usersMap.get("tenant_code").toString())
                    .other("x-user-name", usersMap.get("user_name").toString())
                    .other("x-source-Id", "1")
                    //.other("x-client-ip", RandomIP.getRandomIp())
                    .build();
            httpConfig = HttpConfig.custom()
                    .url(addBettingUrl)
                    .headers(headers);
        } else {
            httpConfig = Login.loginReturnHttpConfig(usersMap.get("user_name").toString());//登录
        }
        String rebate;
        if (isFilePoint) {
            rebate = rebateJson.getString(lotteryConfig.code);
        } else {
            String getBetRebateResult = "";
            try {
                getBetRebateResult = DafaRequest.get(httpConfig.url(getBetRebate + "?lotteryType=" + lotteryConfig.type));
                rebate = JSONObject.parseObject(getBetRebateResult)
                        .getJSONObject("data")
                        .getString("rebate");
            } catch (Exception e) {
                System.out.println("返点获取失败：" + getBetRebateResult);
                return;
            }
        }
        for (int i = 0; i < 100000000; i++) {
            if (isStop) {
                Thread.currentThread().interrupt();
                break;
            }
            String betContent = getBettingData(lotteryConfig.code, rebate, lotteryConfig.timeType);
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
                betContentMap.clear();
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        betContentMap.clear();
    }

    /**
     * 随机获取投注记录
     */
    private String getBettingData(String lotteryCode, String rebate, int timeType) {
        //String betContent = betContents.get((int) (Math.random() * (betContents.size()))).get("content").toString();
        String betContent =
                betContentMap.get(lotteryCode)
                        .get((int) (Math.random() * betContentMap.get(lotteryCode).size())).get("content").toString();

        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
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
