package TestListUtils;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.util.List;

public class Test01 {
    private static String host = "http://2019.dafacloud-admin.com";
    //private static String host = "http://pt05.dafacloud-test.com";
    private static String clearing = host + "/v1/report/clearing";


    public static void main(String[] args) throws Exception {
        List<String> list = FileUtil.readFile(Test01.class.getResourceAsStream("/test/f.txt"));
        System.out.println(list.size());
        //System.out.println(ListRemoveRepeat.removeRepeat(a).size());

        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                //.other("Origin", "http://2019.dafacloud-admin.com")
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "546B5C8ECB2F8992069FB676210EBB3E");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        HttpConfig httpConfig = HttpConfig.custom().headers(headers).context(context);

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] sa = s.split(",");
            //System.out.println(sa[0] + "," + sa[1]);
            task(httpConfig, i, sa[0], sa[1]);
            //task(httpConfig, i, "duke02", "RG451022658180534175");
        }
    }

    /**
     * userNames: dafai0001
     */
    public static void task(HttpConfig httpConfig, int i, String userNames, String record) throws Exception {
        String body = UrlBuilder.custom()
                .addBuilder("userNames", userNames)
                .addBuilder("startTime")
                .addBuilder("endTime")
                .addBuilder("type", "0")
                .addBuilder("recordCode", record)
                .addBuilder("remark", "站长红包发送错误," + userNames + "," + record)
                .fullBody();
        String result = DafaRequest.post(httpConfig.url(clearing).body(body));
        System.out.println(i + " - " + userNames + " - " + record + " - " + result);
        int code = JSONObject.fromObject(result).getInt("code");
        Thread.sleep(200);
        if (code != 1) {
            Thread.sleep(1000 * 1000);
        }

    }
}
