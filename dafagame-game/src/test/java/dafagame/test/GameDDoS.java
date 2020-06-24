package dafagame.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.Md5HA1.AesEncodeUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.jsonUtils.JsonUtils;

/**
 * 游戏盾
 */
public class GameDDoS {
    //private static String HOST = "http://a12780fabe0e711e98f02061e82846b5-92b7ba572b52a600.elb.ap-east-1.amazonaws.com";//第一套内置域名
    private static String HOST = "http://137.116.175.108";//第二套alysia站 内置域名
    //private static String HOST = "http://pre.uvjgrnzdwj.com";//pre

    //测试环境内置域名
    //private static String HOST = "http://pre.uvjgrnzdwj.com";
    //private static String HOST = "https://lqqp.mkjpauuus.com";

    //private static String HOST = "http://120.76.134.176";

    //private static String HOST = "https://hlqp.mkjpauuus.com";
    //private static String HOST = "http://120.78.240.236";
    //private static String HOST = "http://39.108.237.12";//lqqp

    private final static String GET_GAME_DOMAIN = HOST + "/v1/security/getGameDomain";

    //private static String frontInitData = HOST + "/v1/management/tenant/frontInitData?clientVersion=1.3.1.28.0.1";
    private static String frontInitData = HOST + "/v1/management/tenant/frontInitData?clientVersion=1.3.1.41.0.1";


    static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0")
            .other("Source-Id", "1")
            .other("Tenant-Code", "alysia")
            .build();
    static HttpConfig httpConfig = HttpConfig
            .custom()
            .headers(headers);

    @Test(description = "GET_GAME_DOMAIN")
    public static void test01() {
        String result = DafaRequest.get(httpConfig.url(GET_GAME_DOMAIN));
        System.out.println(result);
        System.out.println(JsonFormat.formatPrint(result));
        System.out.println("dataEncrypt:-------------------------------------------");
        String content = JSONObject.parseObject(result).getJSONObject("data").getString("dataEncrypt");
        //String content = "";
        String decryptResult = AesEncodeUtil.decrypt(content);
        System.out.println(JsonFormat.formatPrint(decryptResult));
    }

    @Test(description = "frontInitData")
    public static void test02() {
        String result = DafaRequest.get(httpConfig.url(frontInitData));
        System.out.println(result);
        System.out.println(JsonFormat.formatPrint(result));
    }

    public static void main(String[] args) {
        String gameDomain = "";
        for (int i = 0; i < 1; i++) {
            gameDomain = DafaRequest.get(httpConfig.url(frontInitData));
            System.out.println("gameDomain: " + gameDomain);
        }
        //System.out.println(gameDomain);
        //String content = JSONObject.parseObject(gameDomain).getJSONObject("data").getString("dataEncrypt");
        String content = "VICtyLn6XYCGxIwfluW7Cw8SocE/puaPW+O1ThARYMbDK6LHu4Le8eZ0KhPpgeER5zdhpCZZ0HMSX7vwpiXetbCZdDV+41g9XYM3lG8Qg2CedNCGYM+qg2iEmth9SogSihGctxoDujZyhkOSHOed6xk25d3d+SfPAf/8m8KcpIhpOkdIm473OuRtKV1Xr3WKu1p6l/scyCKX5eV4IP8JCcoS5N3CRmpvOZG/N5QPwfe8TuP0rW/HorvolbrWfbavoyPmdB5a2WfHOKhoPFsE281TO/xV2/mE42zax8Bn9sn9SbYFlkxVbD4Mj549P+m8jiUfbb6GkSY2+gB8SH1oM0wsQFeqxDdMNThod+Lvge2qQlWHz3SUk3sywd91FSQhLvyTjH44UiA8iLhFUYSFol9nc7HZqVoTyvaAQV2hR5rOMgPVd60t4WFNib7s6Ja1mb4fJFaV3LLL5lMvdPCRMa2sCp4dXpu/hI9yZ/Gz/0VaGm1w+4hcYSp0UbNLlG9z1U9BoCqdxPuSabAZHX1KKMNEISioiTYDRy9172tmapmTbIl3Lbf3wqpkt/FXcA6BxXSCF7J/pQqIVUvhDuWOBqUrsOdG/jHbKjeYUVRaRJltoqw1BLX/b71VOzbRdT5MNxwNLPOeidRYejduXjry5MKcKxEamlTIJ2G35PY79FV48ly/OdLwAvP90Oh2cE7eZIzuXhCXSg3KzgeusfCp8fzBNgg2mOoJYxvYhNXDI9D58xoPHJuJx8ILzIKIELSlDdiPCPksmoVNfsGY1tuMiCvZacYgo2+faUfGlJYsEWDj4rp39ECZ2HYeQzpMyZcoDsHCFUlKkEwRClJWM2B0Xh+qYjF+3RQiER4HVFVMiihgWM3mBBQzY62pFRCTcX2u18tYu0cAmVJeQFN0X653VlRG+L6yTK8hlRDO95JN+DJq1nvCox3pySEGRaSnbGg/3fR6Mrv5kjlVEUrRa/BN4HCs91gLcD26o3tluDM/5mLfjPFphjwBL0DNS7KoAEF1u3T2GiQAtqD30nOg8bgmGm56ppWlSvqLe4wWIU/fcAXhyNvHqkj3ZzXUCg4NtL/5b5c2kUyoIWzvYMx6W+DPp6Ph5TPLSMDu3DQ6/pbojQA=";
        String decryptResult = AesEncodeUtil.decrypt(content);
        //JsonFormat.formatPrint(decryptResult);
    }
}
