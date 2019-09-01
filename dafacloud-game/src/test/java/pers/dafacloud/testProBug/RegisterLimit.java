package pers.dafacloud.testProBug;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.loginPage.Register;
import pers.dafacloud.utils.httpclientUtil.common.HttpHeader;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class RegisterLimit {

    @Test(description = "测试账号50个，正式账号25个")
    public static void test01() {
        // String url = "http://192.168.32.82:8010";
        String url = "http://caishen02.com";
        //String username = "abca"; 测试账号
        String username = "abcb"; //正式账号
        String inviteCode = "72562999";//测试环境大发站，正式账号邀请码
//        String inviteCode = "15940420";//测试环境大发站，正式账号邀请码
        for (int i = 0; i < 50; i++) {
            //System.out.println(String.format("%s%04d",username,i));
            Register.registerMeth(url, String.format("%s%04d", username, i), inviteCode);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Test(description = "测试")
    public static void test02() {
        String host = "http://pt03.dafacloud-testCookie.com/v1/users/manageUser";
        String url = UrlBuilder.custom()
                .url(host)
                .addBuilder("startTime", "2019-07-27 00:00:00")
                .addBuilder("endTime", "2019-07-28 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(url,"0EDB500676FE12B207180C8429CD8F78");
        JSONArray ja = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("result");
        for (int i = 0; i < ja.size(); i++) {
            System.out.println(ja.getString(i));
        }
        //System.out.println(result);
    }


}
