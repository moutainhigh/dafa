package pers.test;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//cms批量冻结会员
public class FreezeUser {
    private static ExecutorService executors = Executors.newFixedThreadPool(300);
    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("x-tenant-code", "dafa")
            .other("x-manager-name", "duke")
            .other("x-manager-id", "100035")
            .other("x-is-system", "1")
            .other("x-client-ip", "118.143.214.129")
            .build();
    private static String path = "http://52.77.207.64:8010/v1/users/freezeUserBatch";

    private static HttpConfig httpConfig = HttpConfig.custom()
            .url(path)
            .headers(headers);

    public static void freezeUser(String userIds) {
        String body = UrlBuilder.custom()
                .addBuilder("userIds", userIds)
                .addBuilder("isUse", "0")
                .fullBody();
        httpConfig.body(body);
        long start = System.currentTimeMillis();
        String result = DafaRequest.post(httpConfig);
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println(result);
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> userIds = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev2Dafa.txt");
        List<List<String>> userIdss = ListSplit.split(userIds, 300);
        for (List<String> userIdsSub : userIdss) {
            String userIdsSubSting = StringUtils.join(userIdsSub, ",");
            executors.execute(() -> freezeUser(userIdsSubSting));
            Thread.sleep(5000);
        }

    }
}
