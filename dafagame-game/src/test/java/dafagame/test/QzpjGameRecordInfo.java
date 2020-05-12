package dafagame.test;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import pers.utils.dafaGame.DafaGameLogin;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 抢庄牌九游戏记录和游戏记录详情
 */
public class QzpjGameRecordInfo {

    static String host = "23.101.14.122";

    static String sessionId = "1a37e7fc72ee499fbdd079d79691fe88";

    static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("Tenant-Code", "youxi")
            .other("Source-Id", "1")
            .other("Session-Id", sessionId)
            .build();


    public static void main(String[] args) {

        String fullUrl = UrlBuilder
                .custom().url("http://" + host + "/v1/game/frontBattleGameRecord")
                .addBuilder("pageSize", "10")
                .addBuilder("pageNum", "1")
                .fullUrl();
        String fullUrl0 = UrlBuilder
                .custom().url("http://" + host + "/v1/game/frontBattleGameRecordInfo")
                .addBuilder("orderId", "5392")
                .addBuilder("userId", "2564829")
                .fullUrl();

        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers)
                .url(fullUrl0);


        System.out.println(DafaRequest.get(httpConfig));

    }

    private String getLoginSession() {
        //-------------------------------登陆-------------------------------
        String random = "9722";
        //Encoder encoder = Base64.getEncoder();
        //String encode = encoder.encodeToString(random.getBytes());
        String encodeRandom = "OTcyMg==";
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", "")
                .addBuilder("accountNumber", "")
                .addBuilder("password", DafaGameLogin.getPasswordEncode(random, "duke123")) //"b4e82b683394b50b679dc2b51a79d987"
                .addBuilder("userType", "0") //正式0/测试1/遊客2
                .addBuilder("random", encodeRandom)
                //.addBuilder("tenantCode", "demo")
                //.addBuilder("sourceId", "2")
                .fullBody();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("Tenant-Code", "")
                .other("Source-Id", "1")
                .build();

        System.out.println(body);
        HttpConfig httpConfig = HttpConfig
                .custom().headers(headers)
                .url("http://" + host + "/v1/users/login")
                .body(body);
        //this.gameHandler.setHttpConfig(httpConfig);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        String sessionId = JSONObject.fromObject(result).getJSONObject("data").getString("sessionId");
        if (StringUtils.isEmpty(sessionId))
            throw new RuntimeException("sessionId返回异常:" + sessionId);
        return sessionId;
    }

}
