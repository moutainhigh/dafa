package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

/**
 * 生产环境，获取安全服务返回的链接地址，http,ws,hall(broadcast),downloadUrl,imgDownloadUrl
 */
public class GetGameDomain {

    static String getGameDomain = "http://klqyhumdtz.com/v1/security/getGameDomain";

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Source-Id", "2")
                .other("Tenant-Code", "demo")
                //.other("Session-Id", "867bd58d43c94d72a77d3194aee5931d")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);
        String gameDomain = DafaRequest.get(httpConfig.url(getGameDomain));
        System.out.println(gameDomain);
    }
}
