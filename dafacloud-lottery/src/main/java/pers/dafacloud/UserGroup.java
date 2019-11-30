package pers.dafacloud;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.dafacloud.dafaLottery.LotteryIssuePrivate;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserGroup {
    private static String host = "http://52.76.195.164:8010";
    private static String insertUserGroup = host + "/v1/users/insertUserGroup";
    private static String addBettingUrl = "http://52.76.195.164:8020/v1/betting/addBetting";
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    public static void createUserGroup() {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-tenant-code", "dafa")
                .other("x-manager-id", "100035")
                .other("x-manager-name", "duke")
                .other("x-is-system", "1")
                .other("x-client-ip", "1.1.1.1")
                .build();

        String body = UrlBuilder.custom()
                .addBuilder("groups", "3")
                .addBuilder("groupName", "groupName3")
                .fullBody();

        HttpConfig httpConfig = HttpConfig.custom().url(insertUserGroup).body(body).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
    }

    public static void betting(String users, List<String> betContents) {
        String[] userArray = users.split(",");
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-user-id", userArray[0])
                .other("x-tenant-code", userArray[1])
                .other("x-user-name", userArray[2])
                .other("x-source-Id", "1")
                .other("Origin", "http://52.76.195.164")
                .build();

        int betContentIndex = (int) (Math.random() * (betContents.size()));
        String betContent = betContents.get(betContentIndex);
        String[] betContentArray = betContent.split("`");

        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", betContentArray[0])
                .put("playDetailCode", betContentArray[1])
                .put("bettingNumber", betContentArray[2])
                .put("bettingAmount", betContentArray[3])
                .put("bettingCount", betContentArray[4])
                .put("bettingPoint", "7")
                .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(1))
                .put("graduationCount", betContentArray[5])
                .put("bettingUnit", betContentArray[6])
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(bettingData).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "-" + userArray[2] + "-" + result);
    }

    public static void bettingLoop(List<String> users, List<String> betContents) {
        for (int i = 0; i < users.size(); i++) {
            betting(users.get(i), betContents);
        }
    }

    public static void main(String[] args) {


    }


}
