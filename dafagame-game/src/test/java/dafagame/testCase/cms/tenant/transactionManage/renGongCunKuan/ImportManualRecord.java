package dafagame.testCase.cms.tenant.transactionManage.renGongCunKuan;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 人工存取款导入接口（后台POST）
 *
 * 401	401	人工存款
 * 402	402	人工存入
 * 403	403	其他优惠
 * 404	404	误存提出
 * 405	405	行政提出
 * 406	406	提现拒绝
 * 407	407	奖金提出
 */
public class ImportManualRecord {

    private static String importManualRecord = "/v1/transaction/importManualRecord";

    @Test(description = "人工存款批量导入")
    public static void test02() {
        String localFileName = "/Users/duke/Downloads/quantity.xlsx";
        File file = new File(localFileName);
        System.out.println(file.exists());
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(new File(localFileName)))
                .addPart("dictionId", new StringBody("402", ContentType.MULTIPART_FORM_DATA))
                .addPart("timeStamp", new StringBody("15653365483963528", ContentType.MULTIPART_FORM_DATA))
                .build();
        Header[] headers = HttpHeader.custom()
                //.contentType("multipart/form-data; boundary=----WebKitFormBoundarykaly44I1lv9OAkIe")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .build();
        String result = DafaRequest.upload(1, importManualRecord, httpEntity, headers);
        System.out.println(result);
    }



    /*@Test(description = "1")
    public void test002() {
        System.out.println(path.value);
        String body = "dictionId=401&timeStamp=0.123456789";
        Map<String, String> headers = new HashMap<>();
        headers.put("x-tenant-type", "1");//1直营,2渠道
        headers.put("x-tenant-code", "test");//站名test
        headers.put("x-manager-name", "55153320");//用户名
        headers.put("x-manager-id", "55153320");//用户id
        headers.put("x-is-system", "1");//平台账号1，其他是0
        headers.put("x-client-ip", "134.159.225.3");//ip
        String s = Request.uploadFIle(path.value, body, headers);
        System.out.println(s);
        Reporter.log(s);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }*/


}
