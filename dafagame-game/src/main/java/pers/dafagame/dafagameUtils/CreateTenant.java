package pers.dafagame.dafagameUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 新建站点
 */
public class CreateTenant {

    private static String host = "http://pt.dafagame-pro.com";
    private static String uploadFilePlatform = host + "/v1/files/new/uploadFilePlatform";
    private static String addTenant = host + "/v1/management/tenant/addTenant";

    private static String re = "";
    private static String tenantCode = "alysia1";
    private static String url = "asd.asdz.com";

    public static void main(String[] args) throws Exception {

        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .build();

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "3A0E3EA2FF375A1CC57FCD719303588E");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        HttpConfig httpConfig = HttpConfig.custom().headers(headers).context(context);
        addTenant(httpConfig, uploadFilePlatform(httpConfig));
        //addTenant(httpConfig, re);
    }

    public static void addTenant(HttpConfig httpConfig, String re) {
        JSONArray jsonArray = JSONObject.fromObject(re).getJSONArray("data");
        String body = UrlBuilder.custom()
                .addBuilder("tenantType", "1")
                .addBuilder("tenantCode", tenantCode)
                .addBuilder("admin", tenantCode)
                .addBuilder("url", url)
                .addBuilder("password", DigestUtils.md5Hex(tenantCode + DigestUtils.md5Hex("123456")))
                .addBuilder("name", tenantCode)
                .addBuilder("ip", "1.1.1.1")
                .addBuilder("clientName", tenantCode)
                .addBuilder("hallMusic", "008")
                .addBuilder("hallScene", "008")
                .addBuilder("loginScene", "008")
                .addBuilder("iconFileUrl", jsonArray.getString(0))
                .addBuilder("hallFileUrl", jsonArray.getString(1))
                .addBuilder("webFileUrl", jsonArray.getString(2))
                .addBuilder("cmsFileUrl", jsonArray.getString(3))
                .fullBody();

        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .build();

        String result = DafaRequest.post(httpConfig.url(addTenant).body(body).headers(headers));
        System.out.println(result);
    }


    public static String uploadFilePlatform(HttpConfig httpConfig) throws Exception {
        String pwd = "/Users/duke/Documents/大发图片/新建站点new/";
        String gameicon = pwd + "gameicon.png"; //icno图标
        String MasterLogo = pwd + "MasterLogo.png"; //大厅logo
        String WebLogo_Mb = pwd + "WebLogo_Mb.png"; //官网logo
        String CMSLogo = pwd + "CMSLogo.png"; //后台logo

        //String tenantCode = "frankc";
        //String password = DigestUtils.md5Hex(tenantCode + DigestUtils.md5Hex("123456"));
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .setCharset(Charset.forName("utf-8"))
                .addPart("files", new FileBody(new File(gameicon), ContentType.create("image/png", Consts.UTF_8)))
                .addPart("files", new FileBody(new File(MasterLogo), ContentType.create("image/png", Consts.UTF_8)))
                .addPart("files", new FileBody(new File(WebLogo_Mb), ContentType.create("image/png", Consts.UTF_8)))
                .addPart("files", new FileBody(new File(CMSLogo), ContentType.create("image/png", Consts.UTF_8)))

                .addPart("serviceFrom", new StringBody("management", ContentType.MULTIPART_FORM_DATA))
                .addPart("tenantCode", new StringBody(tenantCode, ContentType.MULTIPART_FORM_DATA))
                .addPart("checkTypes", new StringBody("8,8,8,8", ContentType.MULTIPART_FORM_DATA))

                .build();

        HttpEntity httpEntity1 = MultipartEntityBuilder.create()
                .setCharset(Charset.forName("utf-8"))
                .addBinaryBody("file", new File(gameicon))
                .addBinaryBody("file", new File(MasterLogo))
                .addBinaryBody("file", new File(MasterLogo))
                .addBinaryBody("file", new File(CMSLogo))

                .addTextBody("serviceFrom", "management")
                .addTextBody("tenantCode", tenantCode)
                .addTextBody("checkTypes", "8,8,8,8")

                .build();

        Header[] headers = HttpHeader.custom()
                //.contentType("multipart/form-data; boundary=----WebKitFormBoundaryxCtB7V1l3Bg4cdhT")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .build();

        //HttpConfig httpConfig = HttpConfig.custom().url(uploadFilePlatform).headers(headers).context(context).httpEntity(httpEntity);
        String result = DafaRequest.post(httpConfig.url(uploadFilePlatform).headers(headers).httpEntity(httpEntity));
        System.out.println(result);
        httpConfig.httpEntity(null);//清空httpEntity
        return result;

    }

}
