package pers.dafacloud.testAPi;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo1 {
    public static final String ENTITY_STRING="$ENTITY_STRING$";
    public static final String ENTITY_JSON="$ENTITY_JSON$";
    public static final String ENTITY_FILE="$ENTITY_FILEE$";
    public static final String ENTITY_BYTES="$ENTITY_BYTES$";
    public static final String ENTITY_INPUTSTREAM="$ENTITY_INPUTSTREAM$";
    public static final String ENTITY_SERIALIZABLE="$ENTITY_SERIALIZABLE$";
    public static final String ENTITY_MULTIPART="$ENTITY_MULTIPART$";
    private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_JSON, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE, ENTITY_MULTIPART);

    //请求接口
    @Test
    public static void test01() throws Exception{
        String url = "http://localhost:8080/request";
        //String data = "{\"url\":\"http://caishen01.com/v1/management/content/getHotLottery?\"," + "\"body\":\"\",\"cookie\":\"7547A015DC1F7AE2CB345D3AE0F77332\"}";
        String body = URLEncoder.encode("aa=11&cc=22", "UTF-8");
        //URLDecoder.decode( URL, "UTF-8" )//解码
        String data = "method=get&url=http://caishen01.com/v1/management/content/getHotLottery?&body=" + body +
                "&cookie=7547A015DC1F7AE2CB345D3AE0F77332";
        String result = DafaRequest.post(
                url,
                data,
                HttpHeader.custom().contentType("application/x-www-form-urlencoded;charset=UTF-8").build());
        System.out.println(result);
    }

    //add新增接口
    @Test
    public static void test02() throws Exception{
        String url = "http://localhost:8080/add";
        //body编码
        String body = URLEncoder.encode("inviteCode=&accountNumber=qwerty&password=b4e82b683394b50b679dc2b51a79d987&userType=1&random=12", "UTF-8");
        String data = "name=login&path=/v1/userCenter/login&mothod=get&body="+body+
                "header=&dependApiName=&module=用户&page=登陆&project=彩票&description=登陆&owner=duke";
        System.out.println(DafaRequest.post(url,data));
    }
    //query 查询接口
    @Test
    public static void test03() throws Exception{
        String url = "http://localhost:8080/query";
        String data = "name=duke";
        System.out.println(DafaRequest.get(String.format("%s?%s",url,data)));
    }


    @Test
    public static void testMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("userName", "duke0112");
        map.put("password", "2a189a4aef0198291d8c2eb32b7c6502");
        map.put("random", "ZGFmYWNsb3VkXzAuNzQ2MDg3NzExNDUwNjQ5Mw==");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(SPECIAL_ENTITIY.contains(entry.getKey()));
        }
        String s = DafaRequest.post(HttpConfig.custom().map(map).url("http://caishen01.com/v1/users/login"));

        System.out.println(s);
    }




}
