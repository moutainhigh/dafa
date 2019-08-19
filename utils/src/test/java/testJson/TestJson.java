package testJson;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.jsonUtils.Response2;

public class TestJson {

    @Test(description = "测试")
    public static void test01() {
        function01("{\"code\":1,\"msg\":\"我们已向您的，请一分钟后再重试\",\"data\":\"1694\"}");
    }

    public static void function01(String s) {
        Response2 response = (Response2)JSONObject.toBean(JSONObject.fromObject(s),Response2.class);
        System.out.println(response);
        JSONObject jsonObject = JSONObject.fromObject(s);
        int code = jsonObject.getInt("code"); //获取int
        String msg = jsonObject.getString("msg");
        String data = jsonObject.getString("msg");
    }








}
