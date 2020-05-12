package dafagame.test;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 上传文件测试，jpg文件上传后文件变大，500k -> 2m
 *
 */
public class UploadFile {

    //private static String host = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com";
    private static String host = "http://pt.dafagame-test.com";
    private static String uploadFile = host + "/v1/files/new/uploadFile";

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                //.other("Source-Id", "2")
                //.other("Tenant-Code", "test1")
                .build();

        HttpConfig httpConfig = HttpConfig.custom()
                .context(HttpCookies.custom()
                        .setBasicClientCookie(host, "JSESSIONID", "4297BDCE82AF2CC4D39724F2D2720484")
                        .getContext());

        String pwd = "/Users/duke/Documents/大发图片/海报和游戏-new/webPoster/";
        String file = pwd + "WebPoster1.jpg";

        //.addPart("file", new FileBody(new File(localFileName)))
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .setCharset(Charset.forName("utf-8"))
                .addPart("files", new FileBody(new File(file)))
                .addPart("serviceFrom", new StringBody("management", ContentType.MULTIPART_FORM_DATA))
                //.addPart("tenantCode.txt", new StringBody("test1", ContentType.MULTIPART_FORM_DATA))
                .addPart("checkTypes", new StringBody("5", ContentType.MULTIPART_FORM_DATA))
                .build();
        String result = DafaRequest.post(httpConfig.url(uploadFile).headers(headers).httpEntity(httpEntity));
        System.out.println(result);
        httpConfig.httpEntity(null);//清空httpEntity
    }
}
