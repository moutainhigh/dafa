package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

public class UserManageList {

    //private static final String HOST = "http://game.dafagame-test.com";
    private static final String HOST = "http://game.dafagame-pre.com";
    private static final String userManageList = HOST + "/v1/users/userManageList?pageSize=10&pageNum=1";

    /**
     * 测试 代理 > 下级管理，接口有概率会返回"系统异常"
     */
    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Session-Id", "0d4fd72cfa3948afb201b332eb76420e")
                .build();

        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);

        for (int i = 0; i < 10000; i++) {
            String result = DafaRequest.get(httpConfig.url(userManageList));
            if (!result.contains("{\"code\":1"))
                System.out.println(result);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
