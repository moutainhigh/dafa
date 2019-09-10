package pers.dafacloud;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;

public class TheadChatPlan01 {
    private static String updatePlayStatus = "http://52.76.195.164:8100/v1/chat/updatePlayStatus";

    public static void main(String[] args) {
        List<String> data = FileUtil.readFile(ThreadTenantLottery2.class.getResourceAsStream("/chatPlan/shengyuUser01.txt"));
        List<List<String>> lists = ListSplit.split(data, 29);//每个list n 个
        System.out.println(lists);
        for (int i = 0; i < lists.size(); i++) {
            List<String> listNew = lists.get(i);
            new Thread(() -> {
                for (int j = 0; j < listNew.size(); j++) {
                    String[] datas = listNew.get(j).split(",");
                    Header[] headers = HttpHeader.custom()
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                            //.other("x-user-id", userArray[0])
                            .other("x-tenant-code", datas[0])
                            .other("x-manager-name", "duke")
                            .other("x-client-ip", "127.0.0.1")
                            .other("x-is-system", "0")
                            //.other("x-source-Id", "1")
                            //.other("Origin", "http://52.76.195.164")
                            .build();
                    String body = UrlBuilder.custom()
                            .addBuilder("id", datas[1])
                            .addBuilder("status", "1")
                            .fullBody();
                    HttpConfig httpConfig = HttpConfig.custom().url(updatePlayStatus).body(body).headers(headers);
                    try {
                        String result = DafaRequest.post(httpConfig);
                        JSONObject jsonResult = JSONObject.fromObject(result);
                        //System.out.println(body + "===" + listNew.get(j));
                        //System.out.println(result);
                        //AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
                        if (jsonResult.getInt("code") != 1) {
                            System.out.println(body + "===" + listNew.get(j) + "===" + result);
                        } else {
                            System.out.println(result);
                        }
                    } catch (Exception e) {
                        System.out.println(body + "===" + listNew.get(j));
                        //e.printStackTrace();
                    }
                }
            }).start();

        }


    }

}
