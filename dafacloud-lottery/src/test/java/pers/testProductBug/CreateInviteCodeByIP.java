package pers.testProductBug;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.page.pageLogin.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;

import java.net.URLEncoder;

public class CreateInviteCodeByIP {

    @Test(description = "测试")
    public static void test01() throws Exception {
        String url = "http://52.76.195.164:8010/v1/users/createInviteCode";
        String body = "rebate={\"SSC\":\"2\",\"K3\":\"2\",\"SYX5\":\"2\",\"FC3D\":\"2\",\"PL35\":\"2\",\"KL8\":\"2\",\"PK10\":\"5\",\"LHC\":\"6\",\"GAME\":\"2\"}&isAgent=1";
        String username = "shalv001";
//        Header[] headers = HttpHeader
//                .custom()
//                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
//                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
//                .other("x-tenant-code",username)
//                .other("x-user-name",username)
//                .other("x-source-Id","1")
//                .other("","")
//                .build();
        String result = DafaRequest.post(url, URLEncoder.encode(body, "utf-8"), "");
        System.out.println(result);
    }

    @Test(description = "彩票登陆+生成邀请码")
    public static void test02() throws Exception {
        String username = "shalv001";
        String url = "http://52.76.195.164:8010/v1/users/login";
        String body = Login.getLoginBody(username, "123456");
        Header[] headers = HttpHeader
                .custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .other("x-tenant-code", username)
                .other("x-user-name", username)
                .other("x-source-Id", "1")
                .other("x-user-id", "")
                .other("x-client-ip", "")
                .other("x-url", "")
                .other("", "")
                .build();
        String result = DafaRequest.post(url, body, headers);
        System.out.println(result);
        String url0 = "http://52.76.195.164:8010/v1/users/createInviteCode";
        String rebate = "{\"SSC\":\"8\",\"K3\":\"2\",\"SYX5\":\"2\",\"FC3D\":\"2\",\"PL35\":\"2\",\"KL8\":\"2\",\"PK10\":\"5\",\"LHC\":\"6\",\"GAME\":\"2\"}";
        String body0 = String.format("rebate=%s&isAgent=1", URLEncoder.encode(rebate, "utf-8"));//"rebate={\"SSC\":\"2\",\"K3\":\"2\",\"SYX5\":\"2\",\"FC3D\":\"2\",\"PL35\":\"2\",\"KL8\":\"2\",\"PK10\":\"5\",\"LHC\":\"6\",\"GAME\":\"2\"}&isAgent=1";
        System.out.println(body0);
        String result0 = DafaRequest.post(url0, body0);
        System.out.println(result0);
    }

}
