package pers.testProductBug;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.testng.annotations.Test;
import pers.dafacloud.page.pageLogin.Login;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
public class TestProxyIPLogin {


    @Test(description = "修改header的ip登陆")
    public static void test01() {
        Header[] headers = HttpHeader
                .custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .other("X-Forwarded-For","127.0.0.1")
                .other("Proxy-Client-IP","127.0.0.1")
                .other("WL-Proxy-Client-IP","127.0.0.1")
                .other("HTTP_CLIENT_IP","127.0.0.1")
                .other("X-Real-IP","127.0.0.1")
                .build();
        /*
        *   X-Forwarded-For：Squid 服务代理
            Proxy-Client-IP：apache 服务代理
            WL-Proxy-Client-IP：weblogic 服务代理
            HTTP_CLIENT_IP：有些代理服务器
            X-Real-IP：nginx服务代理
        * */
        String url00 = "http://caishen02.com/v1/users/login";
        String name = "dafai0000";
        String body00 = Login.getLoginBody(name, "123456");
        String result00 = DafaRequest.post(url00, body00, headers);
        JSONObject jsonResult00 = JSONObject.fromObject(result00);
        AssertUtil.assertCode(jsonResult00.getInt("code")==1,name+","+result00);

//        HttpClient httpClient = new HttpClient();
        HttpHost proxy = new HttpHost("168.169.1.1", 8080, "HTTP");
    }

}
