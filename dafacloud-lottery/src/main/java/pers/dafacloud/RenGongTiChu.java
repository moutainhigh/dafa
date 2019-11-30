package pers.dafacloud;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RenGongTiChu {

    private static String host = "http://pt05.dafacloud-test.com";
    private static String saveBatchManualRecord = host + "/v1/transaction/saveBatchManualRecord";
    private static String switchTenant = host + "/v1/management/manager/switchTenant";
    private static String manualRecordList = host + "/v1/transaction/manualRecordList";


    private static String withdrawRecordList = host + "/v1/transaction/withdrawRecordList";


    private static Logger log = LoggerFactory.getLogger(RenGongTiChu.class);

    private static List<String> other = new ArrayList<>();

    private static String tempTenant = "";

    public static void main(String[] args) throws Exception {
        List<String> list = FileUtil.readFile(RenGongTiChu.class.getResourceAsStream("/manualRecrdList.txt"));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i);
            saveBatchManualRecord(list.get(i));
        }
        FileUtil.writeFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/result0.txt", other, true);
        for (int i = 0; i < other.size(); i++) {
            System.out.println(other.get(i));
        }
    }


    public static void saveBatchManualRecord(String code) throws Exception {
        String[] codes = code.split(",");
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "5B3423AB5DBFA606BF293965F5B46EAF");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        HttpConfig httpConfig = HttpConfig.custom().headers(headers).context(context);

        if (StringUtils.isEmpty(tempTenant) || !tempTenant.equals(codes[0])) {
            tempTenant = codes[0];
            String bodySwitchTenant = UrlBuilder.custom()
                    .addBuilder("tenantCode", codes[0])
                    .fullBody();
            httpConfig.body(bodySwitchTenant).url(switchTenant);

            String resultSwitchTenant = DafaRequest.post(httpConfig);
            //System.out.println(resultSwitchTenant);
            log.info(String.format("切换站点：%s--%s", resultSwitchTenant, bodySwitchTenant));
            int codeInt = JSONObject.fromObject(resultSwitchTenant).getInt("code");
            if (codeInt != 1) {
                log.info(String.format("切换站点失败：%s--%s", resultSwitchTenant, bodySwitchTenant));
                return;
            }
        }

        String bodyManualRecordList = UrlBuilder.custom().url(manualRecordList)
                .addBuilder("userName", codes[1])
                .addBuilder("recordCode", "")
                .addBuilder("dictionId", "")
                .addBuilder("startTime", URLEncoder.encode("2019-11-15 00:00:00", "utf-8"))
                .addBuilder("endTime", URLEncoder.encode("2019-11-16 00:00:00", "utf-8"))
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .addBuilder("inOutFlag", "0")
                .fullUrl();
        httpConfig.url(bodyManualRecordList);
        String resultManualRecordList = DafaRequest.get(httpConfig);
        log.info(resultManualRecordList);
        JSONArray ja = JSONObject.fromObject(resultManualRecordList).getJSONObject("data").getJSONArray("rows");
        if (ja.size() > 0) {
            log.info(String.format("今日人工提出数据量：%s", ja.size()));
            return;
        }

        int withdrawRecordListSize = withdrawRecordList(httpConfig, codes[1]);

        if (withdrawRecordListSize > 0) {
            log.info(String.format("提现申请中数据量：%s", withdrawRecordListSize));
            return;
        }

        String bodySaveBatchManualRecord = UrlBuilder.custom()
                .addBuilder("userName", codes[1])
                .addBuilder("amount", codes[2])
                .addBuilder("remark", "重复派彩由系统提出")
                .addBuilder("dictionId", "405")
                .addBuilder("timeStamp", "15738143445941663")
                .fullBody();

        httpConfig.body(bodySaveBatchManualRecord).url(saveBatchManualRecord);
        String resultSaveBatchManualRecord = DafaRequest.post(httpConfig);
        //System.out.println(resultSaveBatchManualRecord);
        log.info(String.format("提出数据：%s--%s", resultSaveBatchManualRecord, bodySaveBatchManualRecord));
        int code2 = JSONObject.fromObject(resultSaveBatchManualRecord).getInt("code");
        if (code2 == 1) {
            other.add(String.format("%s--%s", code, resultSaveBatchManualRecord));
        }
    }

    public static int withdrawRecordList(HttpConfig httpConfig, String userName) throws Exception {
        String bodyManualRecordList = UrlBuilder.custom().url(withdrawRecordList)
                .addBuilder("userName", userName)
                .addBuilder("Update", "60")
                .addBuilder("state", "0")
                .addBuilder("grades", "")
                .addBuilder("startTime", URLEncoder.encode("2019-11-15 00:00:00", "utf-8"))
                .addBuilder("endTime", URLEncoder.encode("2019-11-16 00:00:00", "utf-8"))
                .addBuilder("startAmount", "")
                .addBuilder("endAmount", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        httpConfig.url(bodyManualRecordList);
        String resultWithdrawRecordList = DafaRequest.get(httpConfig);
        log.info(resultWithdrawRecordList);
        JSONArray ja = JSONObject.fromObject(resultWithdrawRecordList).getJSONObject("data").getJSONArray("rows");
        return ja.size();
    }
}
