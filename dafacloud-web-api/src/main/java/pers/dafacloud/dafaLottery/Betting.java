package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.dafacloud.enums.EV;
import pers.dafacloud.enums.LotteryConfig;
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
    private static ExecutorService executors = Executors.newFixedThreadPool(660);
    public static boolean isStop = true;

    private String addBettingUrl;
    private String getBetRebate;

    private JSONObject rebateJson;//返点json配置文件

    private boolean isFilePoint;//是否使用返点json文件

    private EV ev;

    private static Betting betting;
    public static String host0;

    private Map<String, List<String>> betContentMap = new HashMap<>();


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
        this.addBettingUrl = host + "/v1/betting/addBetting";
        this.getBetRebate = host + "/v1/betting/getBetRebate";
        this.isFilePoint = isFilePoint;
        this.ev = EV.getEV(host);
        host0 = host;
        List<Map> users = null;
        List<Map> usersTenant = null;

        betContentMap.clear();

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
            if(!betContentMap.containsKey(lotteryConfig.type))
                betContentMap.put(lotteryConfig.type, betting.betContentServer.getBetContentList(betContentType, lotteryConfig.type));
            //betContentMap.put(lotteryConfig.code, FileUtil.readFile("/aa"));
            bettingLoop(userSub, lotteryObj.getBettingStepTime(), lotteryConfig, threadStepTimeMill);
        }
        //返点配置文件
        if (isFilePoint) {
            JSONObject jsonObjectPoint = JSONObject.parseObject(FileUtil.readFileRetrunString(BettingRunning.class.getResourceAsStream("/pointJson/point.json")));
            if (isContainsTenantLottery)
                rebateJson = jsonObjectPoint.getJSONObject(ev.evPointTenant);//站长彩
            else
                rebateJson = jsonObjectPoint.getJSONObject(ev.evName);
        }
    }

    /**
     * 按每个彩种，用户数量执行
     */
    private void bettingLoop(List<Map> users, int bettingStepTime, LotteryConfig lotteryConfig, int threadStepTimeMill) {
        for (Map usersMap : users) {
            if (isStop) {
                //Thread.currentThread().interrupt();
                break;
            }
            executors.execute(() -> bettingExecu(usersMap, bettingStepTime, lotteryConfig));
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
    private void bettingExecu(Map usersMap, int bettingStepTime, LotteryConfig lotteryConfig) {
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
            String betContent = getBettingData(lotteryConfig, rebate, lotteryConfig.timeType);
            String result = DafaRequest.post(httpConfig.url(addBettingUrl).body(betContent));//下注请求
            try {
                if (JSONObject.parseObject(result).getInteger("code") != 1)
                    System.out.println(betContent);
            } catch (Exception e) {
                System.out.println("json error:" + result);
            }
            System.out.println(result);
            try {
                Thread.sleep(bettingStepTime);//投注间隔时间
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
    private String getBettingData(LotteryConfig lotteryConfig, String rebate, int timeType) {
        //String betContent = betContents.get((int) (Math.random() * (betContents.size()))).get("content").toString();
        String betContent =
                betContentMap.get(lotteryConfig.type).get((int) (Math.random() * betContentMap.get(lotteryConfig.type).size()));
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
        String[] betContentArray0 = betContent.split("@");
        for (String betContent0 : betContentArray0) {
            String[] betContentArray = betContent0.split("`");
            net.sf.json.JSONObject order1 = JsonObjectBuilder.custom()
                    .put("lotteryCode", lotteryConfig.code)
                    .put("playDetailCode", lotteryConfig.code + betContentArray[0])
                    .put("bettingNumber", betContentArray[1])
                    .put("bettingAmount", betContentArray[2])
                    .put("bettingCount", betContentArray[3])
                    //.put("bettingPoint", rebate.get(getLotteryType(betContentArray[0])))
                    .put("bettingPoint", rebate)
                    .put("bettingIssue", lotteryConfig.isPrivate ? LotteryIssuePrivate.getCurrentIssue(timeType) : LotteryIssuePublic.getPublicIssue(lotteryConfig.code))
                    .put("graduationCount", betContentArray[4])
                    .put("bettingUnit", betContentArray[5])
                    .bulid();
            jsonArrayBuilder
                    .addObject(order1);
        }
        return UrlBuilder.custom().addBuilder("bettingData", jsonArrayBuilder.bulid().toString()).fullBody();
    }

    public static void main(String[] args) {

    }
}
