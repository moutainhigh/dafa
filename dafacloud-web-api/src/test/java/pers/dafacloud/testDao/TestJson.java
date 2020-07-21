package pers.dafacloud.testDao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJson {

    public static void main(String[] args) {
        String s = "{\"code\":1,\"msg\":\"获取成功\",\"data\":[{\"id\":\"1403\",\"tenantCode\":null,\"date\":null,\"betAmount\":1.0,\"returnAmount\":1.94,\"profit\":0.94,\"profitability\":0.94}]}";
        //String s = "[{\"id\":\"1403\",\"tenantCode\":null,\"date\":null,\"betAmount\":1.0,\"returnAmount\":1.94,\"profit\":0.94,\"profitability\":0.94}]";
        //JSON ja= JSON.parseObject(s);
        //JSONArray ja = JSONArray.parseArray(s);
        //Object object = JSON.parse(s);
        //if (object instanceof JSONObject) {
        //    System.out.println("1");
        //} else {
        //    System.out.println("2");
        //}

        Object object = JSONObject.parseObject(s).get("data");
        if (object instanceof JSONObject) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }


    }
}
