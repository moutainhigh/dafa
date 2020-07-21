package pers.dafacloud.TestCase.payment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import pers.dafacloud.utils.LotteryRequest;
import pers.dafacloud.utils.persAnnontation.MyTestCase;
import pers.dafacloud.utils.persAnnontation.MyTestClass;
import pers.utils.urlUtils.UrlBuilder;

import java.time.LocalDate;

@MyTestClass
public class P05QQPay {
    private static final String payName = "QQ钱包(勿动)";

    private static String qqList() {
        String path = "/v1/transaction/qqList";
        String url = UrlBuilder
                .custom()
                .url(path)
                .addBuilder("startTime", LocalDate.now().toString())
                .addBuilder("endTime", LocalDate.now().toString())
                .fullUrl();
        return LotteryRequest.getCms(url);
    }

    @MyTestCase
    public static void saveOrUpdate() {
        if (qqList().contains(payName)) {
            System.out.println("已存在支付方式");
            return;
        }
        String path = "/v1/transaction/saveOrUpdateQQ";
        String body = UrlBuilder.custom()
        .addBuilder("payAlias",payName)
        .addBuilder("payType","一般")
        .addBuilder("name","腾讯QQ")
        .addBuilder("account","tencent")
        .addBuilder("qrcode")
        .addBuilder("mobileType")
        .addBuilder("terminalNumber")
        .addBuilder("merchantNumber")
        .addBuilder("payUrl")
        .addBuilder("minAmount","10")
        .addBuilder("maxAmount","1000")
        .addBuilder("quickAmountList")
        .addBuilder("remark",payName)
        .addBuilder("accountName")
        .addBuilder("bankCardNumber")
        .addBuilder("bankName")
        .addBuilder("otherName")
        .addBuilder("id")
        .addBuilder("secretKey")
        .addBuilder("receiveKey")
        .addBuilder("auditInfo","QQ昵称#falg#请填写您的QQ昵称")
        .addBuilder("isFixedAmountThird","0")
        .addBuilder("isFixedAmount","0")
                .fullBody();
        String result0 = LotteryRequest.postCms(path, body);
        System.out.println(result0);
    }

    /**
     * 更新支付顺序，开放终端，开放等级/组别
     */
    @MyTestCase
    public static void updateSort() {
        JSONObject jsonObject = JSONObject.parseObject(qqList());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONArray setData = new JSONArray();
        int sort = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject get = jsonArray.getJSONObject(i);
            if (StringUtils.isNotEmpty(get.getString("sourceList"))
                    && (StringUtils.isNotEmpty(get.getString("gradeList"))) || StringUtils.isNotEmpty(get.getString("groupList"))) {
                JSONObject set = new JSONObject(true);
                set.put("id", get.getInteger("id"));
                set.put("gradeList", get.getString("gradeList"));
                set.put("sourceList", get.getString("sourceList"));
                set.put("groupList", get.getString("groupList"));
                set.put("sort", get.getInteger("sort"));
                sort = get.getInteger("sort");
                if (payName.equals(get.getString("payAlias"))) {
                    set.put("gradeList", "-1,1,2,3,4,5,6,7,8,9,10");
                }
                setData.add(set);
            } else if (payName.equals(get.getString("payAlias"))) {
                JSONObject set = new JSONObject();
                set.put("payAlias", get.getString("name"));
                set.put("id", get.getInteger("id"));
                set.put("gradeList", "-1,1,2,3,4,5,6,7,8,9,10");
                set.put("sourceList", "1,2");
                set.put("groupList", "");
                set.put("sort", ++sort);
                setData.add(set);
            }

        }
        System.out.println(setData);
        String body = UrlBuilder.custom()
                .addBuilder("data", setData.toString())
                .fullBody();
        String path = "/v1/transaction/updateQQPaymentListSort";
        String result = LotteryRequest.postCms(path, body);
        System.out.println(result);


    }

    public static void main(String[] args) {
        saveOrUpdate();
        updateSort();
    }


}
