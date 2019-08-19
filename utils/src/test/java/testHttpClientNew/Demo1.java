package testHttpClientNew;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.Request;

public class Demo1 {

    public static void main(String[] args) throws Exception {
        //设置cookie
        HttpClientContext context = new HttpClientContext();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "EAD588C1439978E4A169E7C72CC302CA");
        cookie.setVersion(0);
        cookie.setDomain("pt.dafagame-test.com");//设置范围
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        context.setCookieStore(cookieStore);
        //请求
        String s = Request.get(HttpConfig.custom().context(context).url("http://pt.dafagame-test.com:83/v1/transactionManage/withdrawRecordList?userName=&grades=&state=-1&pageNum=1&pageSize=20&startTime=2019-07-02&endTime=2019-07-03&startAmount=&endAmount="));
        System.out.println(s);
    }

    @Test
    public static void test01() {

        System.out.println(DafaRequest.get("http://caishen01.com/v1/balance/queryBalanceFront?",
                "7547A015DC1F7AE2CB345D3AE0F77332"));
    }


}
