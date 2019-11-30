package pers.testShaLv;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.page.pageLogin.Login;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;
import java.net.URLEncoder;

public class CreateTenant {


    @Test(description = "MD5")
    public static void test02() {
        String password = DigestUtils.md5Hex("shalv002" + DigestUtils.md5Hex("123456"));
        System.out.println(password);
        System.out.println(String.format("shalv%03d",1));
    }

    @Test(description = "创建站点+登陆+生成邀请码")
    public static void test01() throws Exception {
        for (int i = 650; i < 652; i++) {
            //创建站点
            String url = "http://pt03.dafacloud-test.com/v1/management/tenant/addTenant";
            String name = String.format("shalv%03d",i);
            String nameTest = String.format("shalvtest%03d",i);
            String password = DigestUtils.md5Hex(name + DigestUtils.md5Hex("123456"));
            String body = UrlBuilder.custom()
                    .addBuilder("tenantCode", name)
                    .addBuilder("name", name)
                    .addBuilder("webTitle", name)
                    .addBuilder("admin", name)
                    .addBuilder("password", password)
                    .addBuilder("topAgent", name)
                    .addBuilder("testAgent", nameTest)
                    .addBuilder("styleId", "1")
                    .addBuilder("url", String.format("pt.%s.com", name))
                    .addBuilder("ip", "192.168.1.1")
                    .fullBody();
            Header[] headers0 = HttpHeader
                    .custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                    .other("x-tenant-code", name)
                    .other("x-user-name", name)
                    .other("x-source-Id", "1")
                    .other("x-user-id", "")
                    .other("x-client-ip", "")
                    .other("x-url", "")
                    .build();
            //System.out.println(body);
            String result = DafaRequest.post(url, body, "A1F4BA12C42BE0026DD51795617D445E");
            //System.out.println(name+","+result);
            JSONObject jsonResult = JSONObject.fromObject(result);
            AssertUtil.assertCode(jsonResult.getInt("code")==1,name+","+result);
            //登陆
            //String name = "shalv001";
            String url00 = "http://52.76.195.164:8010/v1/users/login";
            String body00 = Login.getLoginBody(name, "123456");
            Header[] headers = HttpHeader
                    .custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                    .other("x-tenant-code", name)
                    .other("x-user-name", name)
                    .other("x-source-Id", "1")
                    .other("x-user-id", "")
                    .other("x-client-ip", "")
                    .other("x-url", "")
                    .other("", "")
                    .build();
            String result00 = DafaRequest.post(url00, body00, headers);
//            System.out.println(result00);
            JSONObject jsonResult00 = JSONObject.fromObject(result00);
            AssertUtil.assertCode(jsonResult00.getInt("code")==1,name+","+result00);
            //创建邀请码
            String url0 = "http://52.76.195.164:8010/v1/users/createInviteCode";
            String rebate = "{\"SSC\":\"7\",\"K3\":\"7\",\"SYX5\":\"7\",\"FC3D\":\"7\",\"PL35\":\"7\",\"KL8\":\"7\",\"PK10\":\"7\",\"LHC\":\"7\",\"GAME\":\"7\"}";
            String body0 = String.format("rebate=%s&isAgent=1", URLEncoder.encode(rebate, "utf-8"));
            //System.out.println(body0);
            String result0 = DafaRequest.post(url0, body0);
            //System.out.println(result0);
            JSONObject jsonResult0 = JSONObject.fromObject(result0);
            AssertUtil.assertCode(jsonResult0.getInt("code")==1,name+","+result0);
        }
    }
}
