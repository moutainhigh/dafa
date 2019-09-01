package dafagame.testCase.cms.tenant.tenantManage.baseManage.PictureManage;

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

public class UpdateLogo {

    private static String updateLogo = "/v1/management/content/updateLogo";

    @Test(description = "后台登录页Logo")
    public static void test01() {
        String localFileName = "/Users/duke/Documents/2.png";
        File file = new File(localFileName);
        System.out.println(file.exists());
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(new File(localFileName)))
                .addPart("id", new StringBody("15", ContentType.MULTIPART_FORM_DATA))
                .addPart("logoId", new StringBody("3", ContentType.MULTIPART_FORM_DATA))
                .build();
        Header[] headers = HttpHeader.custom()
                //.contentType("multipart/form-data; boundary=----WebKitFormBoundaryNLVLucn0qizdK3XV") //添加会提示找不到file
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .build();
        String result = DafaRequest.upload(1, updateLogo, httpEntity, headers);
        System.out.println(result);
    }






}