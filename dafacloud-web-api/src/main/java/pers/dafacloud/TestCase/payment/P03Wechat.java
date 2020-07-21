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
public class P03Wechat {

    private static String wechatList() {
        String path = "/v1/transaction/wechatList";
        String url = UrlBuilder
                .custom()
                .url(path)
                .addBuilder("startTime", LocalDate.now().toString())
                .addBuilder("endTime", LocalDate.now().toString())
                .fullUrl();
        return LotteryRequest.getCms(url);
    }
    @MyTestCase
    public static void addWechat() {
        if (wechatList().contains("微信(勿动)")) {
            System.out.println("已存在支付方式");
            return;
        }
        String path = "/v1/transaction/saveOrUpdateWechat";
        String body = UrlBuilder.custom()
                .addBuilder("payAlias", "微信(勿动)")
                .addBuilder("payType", "一般")
                .addBuilder("account", "12345678")
                .addBuilder("name", "微信支付")
                .addBuilder("bankName", "招商银行")
                .addBuilder("qrcode", "")
                .addBuilder("mobileType", "扫码")
                .addBuilder("terminalNumber", "")
                .addBuilder("merchantNumber", "")
                .addBuilder("secretKey", "")
                .addBuilder("receiveKey", "")
                .addBuilder("payUrl", "")
                .addBuilder("minAmount", "10")
                .addBuilder("maxAmount", "1000")
                .addBuilder("quickAmountList", "")
                .addBuilder("remark", "微信(勿动)")
                .addBuilder("auditInfo", "微信昵称#falg#请填写您的微信昵称")
                .addBuilder("isFixedAmountThird", "0")
                .addBuilder("isFixedAmount", "0")
                .fullBody();
        String result0 = LotteryRequest.postCms(path, body);
        System.out.println(result0);
    }

    /**
     * 更新支付顺序，开放终端，开放等级/组别
     */
    @MyTestCase
    public static void updateWechatPaymentListSort() {
        JSONObject jsonObject = JSONObject.parseObject(wechatList());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONArray setData = new JSONArray();
        int sort = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject get = jsonArray.getJSONObject(i);
            if (StringUtils.isNotEmpty(get.getString("sourceList"))
                    && (StringUtils.isNotEmpty(get.getString("gradeList"))) || StringUtils.isNotEmpty(get.getString("groupList"))) {
                JSONObject set = new JSONObject(true);
                set.put("payAlias", get.getString("name"));
                set.put("id", get.getInteger("id"));
                set.put("gradeList", get.getString("gradeList"));
                set.put("sourceList", get.getString("sourceList"));
                set.put("groupList", get.getString("groupList"));
                set.put("sort", get.getInteger("sort"));
                sort = get.getInteger("sort");
                if ("微信(勿动)".equals(get.getString("payAlias"))) {
                    set.put("gradeList", "-1,1,2,3,4,5,6,7,8,9,10");
                }
                setData.add(set);
            } else if ("微信(勿动)".equals(get.getString("payAlias"))) {
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
        String path = "/v1/transaction/updateWechatPaymentListSort";
        String result = LotteryRequest.postCms(path, body);
        System.out.println(result);


    }

    public static void main(String[] args) {
        addWechat();
        updateWechatPaymentListSort();
    }


}
