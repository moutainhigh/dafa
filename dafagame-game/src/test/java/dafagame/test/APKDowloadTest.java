package dafagame.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.io.File;
import java.io.FileOutputStream;

public class APKDowloadTest {
    private static String HOST1 = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com";

    /**
     * 官网域名
     */
    //private final static String HOST2 = "http://caishenzhengba.net:3087";
    private final static String HOST2 = "https://cszbasdfgh.com";
    /**
     * 官网获取下载地址
     */
    private final static String getMainWebInfo = HOST2 + "/v1/management/tenant/getMainWebInfo";


    private final static String HOST = "http://cms.csz8.net";

    //cms 获取下载设置信息
    private final static String getWebsiteInfo = HOST + "/v1/management/setting/getWebsiteInfo";

    private final static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0")
            //.other("JSESSIONID", "25B16A437AD48E26B4F8157A1B936B1D")
            //.other("Source-Id", "2")
            //.other("Tenant-Code", "lqqp")
            .build();

    private final static HttpConfig httpConfig = HttpConfig
            .custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(HOST, "JSESSIONID", "5A92101A0434C5E7E66649DF2DDA2751")
                    .getContext());


    //public static void main(String[] args) throws Exception {
    //
    //    getWebsiteInfo();
    //    //setWebsiteInfo();
    //
    //    //applicationGetPreheatDomain();
    //
    //    //downloadPlist();
    //}

    /**
     * 官网获取apk下载地址
     * <p>
     * name	名称
     * supportUrl	客服连接
     * logo	官网logo
     * banner	官网海报
     * mainWeb	主域名
     * iosClientUrl	ios下载地址
     * isIos	ios下载状态
     * <p>
     * andoridDomain	安卓下载地址，如果是空字符串，则使用当前域名进行拼接
     * clientUrl	安卓下载路径
     * tenantCode	站长编码
     */
    private static void getWebsiteInfo() {
        String getMainWebInfo = "https://cszbasdfgh.com/v1/management/tenant/getMainWebInfo?domain=https%3A%2F%2Fcszbasdfgh.com";
        //String getMainWebInfo = "https://app2jskahs.com/v1/management/tenant/getMainWebInfo?domain=https%3A%2F%2Fapp2jskahs.com";
        //String getMainWebInfo = "https://caishenzhengba.net/v1/management/tenant/getMainWebInfo?domain=https%3A%2F%2Fcaishenzhengba.net";
        String result = DafaRequest.get(httpConfig.url(getMainWebInfo));
        System.out.println(result);
        String resultJsonObject = JSON.toJSONString(JSONObject.parseObject(result), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(resultJsonObject);
    }

    //设置站点信息
    private static void setWebsiteInfo() {
        String setWebsiteInfo = "http://cms.csz8.net/v1/management/setting/setWebsiteInfo";
        String body = UrlBuilder.custom()
                .addBuilder("apkUrl", "http://test.toneasy.cn")
                .addBuilder("sharedPlist", "0")
                .addBuilder("sharedApk", "1")
                .addBuilder("plistUrl", "https://download.images-appple.com")
                .fullBody();
        String result = DafaRequest.post(httpConfig.url(setWebsiteInfo).body(body));
        System.out.println(result);
    }

    @Test
    public void applicationGetPreheatDomain() {
        //应用服务获取预热地址，内部调用接口
        String applicationGetPreheatDomain = HOST1 + "/v1/management/setting/applicationGetPreheatDomain";
        String result = DafaRequest.get(httpConfig.url(applicationGetPreheatDomain));
        System.out.println(result);
    }

    public static void downloadPlist() throws Exception {
        String downloadPList = "http://caishenzhengba.net/v1/management/tenant/downloadPList?domain=caishenzhengba.net&tenantCode=jessie";
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                //.other("Source-Id", "2")
                //.other("Tenant-Code", "lqqp")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers)
                .url(downloadPList)
                .out(new FileOutputStream(new File("/Users/duke/Documents/javaDownload/aa.plist")));
        //文件存在会覆盖
        DafaRequest.down(httpConfig);
    }


}
