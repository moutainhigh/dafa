package pers.test.yueBao;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.constant.LotteryConstant;
import pers.dafacloud.mapper.activityLogXX.ActivityLogXXMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YueBaoFront {
    private static ExecutorService executors = Executors.newFixedThreadPool(300);
    private static String host = LotteryConstant.host;
    //private static String host = "http://52.76.195.164:8010";
    //private static String host = "http://caishen03.com";


    static SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("dev");
    static ActivityLogXXMapper activityLogXXMapper = sqlSessionTransaction.getMapper(ActivityLogXXMapper.class);

    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .build();

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(host, "JSESSIONID", "E072C82D8763431F704E1E3672ABE91C")
                    .getContext());

    private static String rechargeFrontPaymentRecord = host + "/v1/transaction/rechargeFrontPaymentRecord";

    public static void verifySafetyPassword() {
        String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";
        String result = DafaRequest.post(httpConfig.url(verifySafetyPassword)
                .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
        System.out.println(result);

    }

    @Test(description = "测试")
    public static void test01ab() {
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev2DafaIP2.txt"));
        //System.out.println(StringUtils.join(list, ","));
        StringBuilder sb = new StringBuilder();
        list.forEach(p -> {
            String[] s = p.split(",");
            sb.append(s[0]).append(",");
        });
        System.out.println(sb.substring(0, sb.length() - 1));

    }

    @Test(description = "余额批量人工存入")
    public static void test01a() {
        String saveBatchManualRecord = host + "/v1/transaction/saveBatchManualRecord";
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev1TestIp.txt"));
        int size = list.size();
        List<String> list0 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list0.add(list.remove(0).split(",")[0]);
            if (list0.size() > 99 || (list0.size() > 0 && list.size() <= 0)) {
                String s0 = StringUtils.join(list0.toArray(), ",");
                System.out.println(s0);
                list0.clear();
                //String[] s = list.get(i).split(",");
                String body = UrlBuilder.custom()
                        .addBuilder("userName", s0)
                        .addBuilder("amount", "98000")
                        .addBuilder("remark", "1")
                        .addBuilder("dictionId", "402")
                        .fullBody();
                System.out.println(DafaRequest.post(httpConfig.url(saveBatchManualRecord).body(body)));
            }
        }


    }

    @Test(description = "测试")
    public static void test01() {
        String findYuebaoStatusFront = host + "/v1/balance/findYuebaoStatusFront?"; //余额宝状态
        System.out.println(DafaRequest.get(httpConfig.url(findYuebaoStatusFront)));
    }

    @Test(description = "测试")
    public static void test02() {
        String findYuebaoStatusFront = host + "/v1/balance/findUserYuebaoInfo"; //余额宝状态
        String result = DafaRequest.get(httpConfig.url(findYuebaoStatusFront));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "测试")
    public static void test03() {
        String getYuebaoOperationInfo = host + "/v1/balance/getYuebaoOperationInfo"; //
        String result = DafaRequest.get(httpConfig.url(getYuebaoOperationInfo));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转入")
    public static void test04() {
        String transferMoney = host + "/v1/balance/transferMoney";
        String body = "money=100&direction=BR";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转出")
    public static void test05() {
        String transferMoney = host + "/v1/balance/transferMoney"; //
        String body = "money=10&direction=BC";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }


    @Test(description = "余额宝批量存入")
    public static void test06() {
        //String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";


    }

    /**
     * 余额宝批量存入
     */
    public static void main(String[] args) {
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev1TestIp.txt"));
        System.out.println(list.size());
        //List<String> list = new ArrayList<>(Arrays.asList("dev2td0008,50415228".split(";")));
        schedule(list);
        //yueBaoBr(list);
    }


    static void schedule(List<String> list) {
        List<List<String>> list0 = ListSplit.split(list, 1000);
        System.out.println(list0.size());
        for (int i = 0; i < list0.size(); i++) {
            List<String> sub = list0.get(i);
            executors.execute(() -> yueBaoBr(sub));
        }
    }

    public static void yueBaoBr(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String[] userArray = list.get(i).split(",");
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-name", userArray[0])
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", "test")
                    .other("x-token", "111")
                    .other("x-source-Id", "1")
                    .build();

            HttpConfig httpConfig = HttpConfig.custom()
                    .headers(headers)
                    //.context(HttpCookies
                    //        .custom()
                    //        //.setBasicClientCookie(host, "JSESSIONID", "428F2C3D896CF79C6D081733079C8829")
                    //        .getContext()
                    ;

            //String resultV = DafaRequest.post(httpConfig.url(verifySafetyPassword)
            //        .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
            //System.out.println(resultV);
            int amount = RandomUtils.nextInt(2, 10) * 100;
            //int amount = 200;
            String body = "money=" + amount + "&direction=BR";
            //verifySafetyPassword();
            for (int j = 0; j < 10; j++) {
                String result = DafaRequest.post(httpConfig.url("http://52.76.195.164:8090/v1/balance/transferMoney").body(body));//52.77.207.64
                System.out.println(result);
                if (JSONObject.parseObject(result).getInteger("code") != 1) {
                    System.out.println(i + " - j:" + j + " - " + userArray[0] + " - " + amount + " - " + result);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }

        }


    }
}