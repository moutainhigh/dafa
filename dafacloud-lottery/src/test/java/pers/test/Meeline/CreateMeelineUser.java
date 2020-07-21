package pers.test.Meeline;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.List;

public class CreateMeelineUser {
    private static String host = "http://caishen02.com";

    public static void main(String[] args) {
        //login("50000511,dafai0001");
        //loginList();
        meeLineLoginList();
        //addFriendList();
        //applyToGroupList();
        //groupNotiGet();
    }

    public static void applyToGroupList() {
        List<String> tokens = FileUtil.readFile("/Users/duke/Documents/dafaUsers/token.txt");
        //for (int i = 0; i < tokens.size(); i++) {
        for (int i = 10; i < tokens.size(); i++) {
            applyToGroup(tokens.get(i));
        }
    }

    public static void addFriendList() {
        List<String> tokens = FileUtil.readFile("/Users/duke/Documents/dafaUsers/token.txt");
        for (int i = 0; i < tokens.size(); i++) {
            //for (int i = 1; i < 2; i++) {
            addFriend(tokens.get(i));
        }
    }

    public static void meeLineLoginList() {
        List<String> tokens = FileUtil.readFile("/Users/duke/Documents/dafaUsers/token.txt");
        for (int i = 0; i < tokens.size(); i++) {
            //for (int i = 0; i < 2; i++) {
            meelineLogin(tokens.get(i));
        }
    }

    public static void loginList() {
        List<String> users = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev1Dafa.txt");
        for (int i = 0; i < users.size(); i++) {
            //for (int i = 0; i < 2; i++) {
            login(users.get(i));
        }
    }

    public static void login(String users) {
        String[] userArray = users.split(",");
        String login = "http://52.76.195.164:8010/v1/users/login";
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.other("x-forwarded-for", ip)
                //.other("x-remote-IP", ip)
                //.other("X-Real-IP", ip);
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-id", userArray[0])
                .other("x-user-name", userArray[1])
                .other("x-client-ip", RandomIP.getRandomIp())
                .other("x-source-Id", "1")
                .other("x-url", "caishen02.com");
        String body = Login.getLoginBody(userArray[1], "123qwe");
        String result = DafaRequest.post(HttpConfig.custom().url(login).body(body).headers(httpHeader.build()));
        try {
            System.out.println(JSONObject.parseObject(result).getJSONObject("data").getString("token"));
        } catch (Exception e) {
            System.out.println(result);
        }
    }

    //登录密聊
    public static String meelineLogin(String token) {
        String login = host + "/v1/users/meeLineLogin";
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("X-Token", token);
        String result = DafaRequest.get(HttpConfig.custom().url(login).headers(httpHeader.build()));
        try {
            String meeToken = JSONObject.parseObject(result).getJSONObject("data").getJSONObject("d").getString("token");
            System.out.println(meeToken);
            return meeToken;
        } catch (Exception e) {
            System.out.println(result);
        }
        return "";
    }

    //添加好友
    public static void addFriend(String token) {
        String pAuthorization = meelineLogin(token);
        if (StringUtils.isEmpty(pAuthorization)) {
            System.out.println("pAuthorization is null :" + pAuthorization);
            return;
        }
        String friendInvite = host + "/api/srvc/v1/friendInvite";
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/json")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", pAuthorization)
                .other("random", "9311h87181ba");
        HttpCookies h = HttpCookies.custom().setBasicClientCookie(host, "SERVERID", "a14d932cc98b71dd1350272eb2f84da6|1593520360|1593518735");
        String body = "{\"p\":\"friendInvite\",\"d\":{\"user_id\":\"0002e800\",\"ml_id\":\"M000119311\",\"msgs\":[\"\\\"dafai0000\\\"\"],\"source_type\":\"0\"},\"r\":\"9311h34021ef\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593517633402\"}}";
        String result = DafaRequest.post(HttpConfig.custom().url(friendInvite).body(body).context(h.getContext()).headers(httpHeader.build()));
        System.out.println(result);
    }

    //申请加群
    public static void applyToGroup(String token) {
        String pAuthorization = meelineLogin(token);
        if (StringUtils.isEmpty(pAuthorization)) {
            System.out.println("pAuthorization is null :" + pAuthorization);
            return;
        }
        String applyToGroup = host + "/api/srvc/v1/applyToGroup";
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/json")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", pAuthorization)
                .other("random", "9311h87181ba");
        HttpCookies h = HttpCookies.custom().setBasicClientCookie(host, "SERVERID", "a14d932cc98b71dd1350272eb2f84da6|1593520360|1593518735");
        String body = "{\"p\":\"applyToGroup\",\"d\":{\"group_id\":\"3913df2f71\"},\"r\":\"9547h5719f76\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593524755719\"}}";
        String result = DafaRequest.post(HttpConfig.custom().url(applyToGroup).body(body).context(h.getContext()).headers(httpHeader.build()));
        System.out.println(result);
    }

    //审批用户加群
    public static void groupNotiGet() {
        String groupNotiGet = host + "/api/srvc/v1/groupNotiGet"; //获取待审批用户
        Header[] httpHeader = HttpHeader.custom()
                .contentType("application/json")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", "a8c22c656c7fc1e4548c8dafaf416753fff21b170887839dd2b0e3867c229bc3")
                .other("random", "9311h87181ba").build();
        String body = "{\"p\":\"groupNotiGet\",\"d\":{\"time\":\"1593526778159\"},\"r\":\"0892h880068a\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593526838800\"}}";
        String result = DafaRequest.post(HttpConfig.custom().url(groupNotiGet).body(body).headers(httpHeader));
        System.out.println(result);
        String body2 = "{\"p\":\"applyingStateUpdate\",\"d\":{\"group_id\":\"3913df2f71\",\"user_id\":\"%s\",\"is_accept\":\"1\"},\"r\":\"0892h3581de4\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593528223581\"}}";
        JSONArray ja = JSONObject.parseObject(result).getJSONArray("d");
        String applyingStateUpdate = host + "/api/srvc/v1/applyingStateUpdate";
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = ja.getJSONObject(i).getJSONObject("from");
            String bodyNew = String.format(body2, jo.getString("user_id"));
            String result2 = DafaRequest.post(HttpConfig.custom().url(applyingStateUpdate).body(bodyNew).headers(httpHeader));
            System.out.println(result2);
        }

    }
}
