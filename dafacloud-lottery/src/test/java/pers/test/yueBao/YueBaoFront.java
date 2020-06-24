package pers.test.yueBao;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.constant.LotteryConstant;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class YueBaoFront {
    private static ExecutorService executors = Executors.newFixedThreadPool(300);
    private static String host = LotteryConstant.host;
    private static AtomicInteger count = new AtomicInteger(100000);

    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("X-Token", "YBU39umqNCFEeEpicVnVhD28PbfujiLM0JwYWnKQVpkVIqDmvBClz/hmR27Sw14I")
            .build();

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(host, "JSESSIONID", "C5907DBE3E7848A6E1503E7826186D15")
                    .getContext());

    public static void verifySafetyPassword() {
        String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";
        String result = DafaRequest.post(httpConfig.url(verifySafetyPassword)
                //.body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
                .body("verifyType=yueBaoSafetyPassword&safetyPassword=c1fe5c98fd1e72f7a8783f0e7a2ae0b5"));//100200
        System.out.println("verifySafetyPassword：" + result);
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
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev2DafaIP2.txt"));
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

    @Test(description = "转入余额宝")
    public static void test04() {
        String transferMoney = host + "/v1/balance/transferMoney";
        String body = "money=100&direction=BR";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转出余额宝")
    public static void test05() {
        String transferMoney = host + "/v1/balance/transferMoney";
        String body = "money=10&direction=BC";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }

    /**
     * 余额宝批量存入
     */
    public static void main(String[] args) {
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev2DafaIP2.txt"));//.subList(0, 100000);
        System.out.println(list.size());
        schedule(list);
        //List<String> list = new ArrayList<>(Arrays.asList("dev2td1985,50417171".split(";")));
        //yueBaoBr(list);
    }


    static void schedule(List<String> list) {
        List<List<String>> list0 = ListSplit.split(list, 600);
        System.out.println(list0.size());
        CountDownLatch cdl = new CountDownLatch(list0.size());
        for (int i = 0; i < list0.size(); i++) {
            List<String> sub = list0.get(i);
            executors.execute(() -> yueBaoBr(sub, cdl));
        }
        try {
            cdl.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void yueBaoBr(List<String> list, CountDownLatch cdl) {
        for (int i = 0; i < list.size(); i++) {
            String[] userArray = list.get(i).split(",");
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-name", userArray[0])
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", "dafa")
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

            //String resultV = DafaRequest.post(httpConfig.url("http://52.77.207.64:8010/v1/users/verifySafetyPassword")
            //        .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
            //System.out.println(resultV);
            int amount = RandomUtils.nextInt(2, 10) * 100;
            //int amount = 200;
            String body = "money=" + amount + "&direction=BR";
            //verifySafetyPassword();
            String result = "";
            for (int j = 0; j < 10; j++) {
                //52.76.195.164 52.77.207.64
                try {
                    result = DafaRequest.post(httpConfig.url("http://52.77.207.64:8090/v1/balance/transferMoney").body(body));//52.77.207.64
                    //System.out.println(result);
                    if (JSONObject.parseObject(result).getInteger("code") != 1) {
                        System.out.println("重试：i - " + i + " - j:" + j + " - " + userArray[0] + " - " + amount + " - " + result);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            System.out.println(count.getAndDecrement() + " - " + result);
        }
        cdl.countDown();


    }
}