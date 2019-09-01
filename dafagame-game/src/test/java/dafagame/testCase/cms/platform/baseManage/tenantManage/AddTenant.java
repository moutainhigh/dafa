package dafagame.testCase.cms.platform.baseManage.tenantManage;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;

import java.io.File;

public class AddTenant {

    private static String addTenant = "/v1/management/tenant/addTenant";

    @Test(description = "厅主开户")
    public static void test001() {
        String pwd = "/Users/duke/Documents/建站图片/";
        String icnoLogo = pwd + "512x512小.png"; //icno图标
        String MasterLogo = pwd + "200x50.png"; //大厅logo
        String WebLogo = pwd + "360x200.png"; //官网logo
        String CMSLogo = pwd + "220x48.png"; //后台logo
        String name = "duke";
        String password = DigestUtils.md5Hex(name + DigestUtils.md5Hex("123456"));
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(new File(icnoLogo)))
                .addPart("file", new FileBody(new File(MasterLogo)))
                .addPart("file", new FileBody(new File(WebLogo)))
                .addPart("file", new FileBody(new File(CMSLogo)))
                .addPart("tenantType", new StringBody("1", ContentType.MULTIPART_FORM_DATA))//1直营,2渠道
                .addPart("tenantCode", new StringBody(name, ContentType.MULTIPART_FORM_DATA))
                .addPart("admin", new StringBody(name, ContentType.MULTIPART_FORM_DATA))
                .addPart("password", new StringBody(password, ContentType.MULTIPART_FORM_DATA))
                .addPart("name", new StringBody("123456", ContentType.MULTIPART_FORM_DATA))
                .addPart("url", new StringBody("duke01.dafagame-testCookie.com", ContentType.MULTIPART_FORM_DATA))
                .addPart("ip", new StringBody("192.168.1.1", ContentType.MULTIPART_FORM_DATA))
                .addPart("clientName", new StringBody("duke", ContentType.MULTIPART_FORM_DATA))
                .addPart("hallMusic", new StringBody("001", ContentType.MULTIPART_FORM_DATA))
                .addPart("hallScene", new StringBody("001", ContentType.MULTIPART_FORM_DATA))
                .addPart("loginScene", new StringBody("002", ContentType.MULTIPART_FORM_DATA))
                .build();
        Header[] headers = HttpHeader.custom()
                //.contentType("multipart/form-data; boundary=----WebKitFormBoundarykaly44I1lv9OAkIe")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .build();
        String result = DafaRequest.upload(1, addTenant, httpEntity, headers);
        System.out.println(result);


    }

    @Test(description = "测试")
    public static void test01() {

    }
}
