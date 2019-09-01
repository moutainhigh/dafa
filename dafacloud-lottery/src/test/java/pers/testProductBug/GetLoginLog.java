package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.listUtils.ListRemoveRepeat;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

public class GetLoginLog {

    public static void main(String[] args) {

    }

    /**
     * 客户管理 》会员管理 》 登陆日志
     */
    @Test(description = "获取用户登陆记录")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url("http://2019.dafacloud-admin.com//v1/users/loginLog")
                .addBuilder("userName", "356513927")
                .addBuilder("startTime", "2019-05-23 00:00:00")
                .addBuilder("endTime", "2019-08-08 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "300")
                .fullUrl();

        String result = DafaRequest.get(url, "F3E38BCFF381DA2C0C54F5D80D156C17");
        JSONArray ja = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("result");
        System.out.println(ja.size());
        for (int i = 0; i < ja.size(); i++) {
            System.out.println(ja.getJSONObject(i));
        }
        System.out.println(result);
    }

    /**
     * 客户管理 》会员管理 》 登陆日志
     */
    @Test(description = "cms通过ip获取登陆日志")
    public static void test02() {
        //通过ip获取该ip下的登陆用户，有重复
        String url = UrlBuilder.custom()
                .url("http://2019.dafacloud-admin.com//v1/users/loginLog")
                .addBuilder("ip", "14.116.141.246")
                .addBuilder("startTime", "2019-05-23 00:00:00")
                .addBuilder("endTime", "2019-08-08 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "300")
                .fullUrl();
        String result = DafaRequest.get(url, "F3E38BCFF381DA2C0C54F5D80D156C17");
        JSONArray ja = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("result");
        System.out.println(ja.size());
        List<String> usernameList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject js0 = ja.getJSONObject(i);
            usernameList.add(js0.getString("userName"));
        }

        //通过用户（去重后）获取该用户登陆记录
        List<String> usernameListNew = ListRemoveRepeat.removeRepeat(usernameList);
        for (int i = 0; i < usernameListNew.size(); i++) {
            //System.out.println(result);
            String url0 = UrlBuilder.custom()
                    .url("http://2019.dafacloud-admin.com//v1/users/loginLog")
                    .addBuilder("userName", usernameListNew.get(i))
                    .addBuilder("startTime", "2019-05-23 00:00:00")
                    .addBuilder("endTime", "2019-08-08 00:00:00")
                    .addBuilder("pageNum", "1")
                    .addBuilder("pageSize", "300")
                    .fullUrl();
            String result0 = DafaRequest.get(url0, "F3E38BCFF381DA2C0C54F5D80D156C17");
            JSONArray ja0 = JSONObject.fromObject(result0).getJSONObject("data").getJSONArray("result");
            System.out.print(usernameListNew.get(i) + "===");
            List<String> loginLocation = new ArrayList<>();
            for (int j = 0; j < ja0.size(); j++) {
                JSONObject js1 = ja0.getJSONObject(j);
                loginLocation.add(js1.getString("loginLocation"));
                //System.out.print(js1.getString("loginLocation")+",");
            }
            System.out.println(ListRemoveRepeat.removeRepeatCount(loginLocation));
        }
    }
}
