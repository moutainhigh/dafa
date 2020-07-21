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
public class P01BankTransfer {

    private static String bankTransferList() {
        String path = "/v1/transaction/bankTransferList";
        String url = UrlBuilder
                .custom()
                .url(path)
                .addBuilder("startTime", LocalDate.now().toString())
                .addBuilder("endTime", LocalDate.now().toString())
                .fullUrl();
        return LotteryRequest.getCms(url);
    }

    @MyTestCase
    public static void saveOrUpdateBankTransfer() {
        if (bankTransferList().contains("银行(勿动)")) {
            System.out.println("已存在支付方式");
            return;
        }
        String path = "/v1/transaction/saveOrUpdateBankTransfer";
        String body = UrlBuilder.custom()
                .addBuilder("name", "银行(勿动)")
                .addBuilder("branchName", "百度支行")
                .addBuilder("account", "622212345678")
                .addBuilder("bankId", "")
                .addBuilder("bankName", "招商银行")
                .addBuilder("minAmount", "10")
                .addBuilder("maxAmount", "10000")
                .fullBody();
        String result0 = LotteryRequest.postCms(path, body);
        System.out.println(result0);
    }

    /**
     * 更新支付顺序，开放终端，开放等级/组别
     */
    @MyTestCase
    public static void updateBankTransferSort() {
        JSONObject jsonObject = JSONObject.parseObject(bankTransferList());
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
                if ("银行(勿动)".equals(get.getString("payAlias"))) {
                    set.put("gradeList", "-1,1,2,3,4,5,6,7,8,9,10");
                }
                setData.add(set);
            } else if ("银行(勿动)".equals(get.getString("name"))) {
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
        String path = "/v1/transaction/updateBankTransferSort";
        String result = LotteryRequest.postCms(path, body);
        System.out.println(result);


    }

    public static void main(String[] args) {
        //saveOrUpdateBankTransfer();
        //updateBankTransferSort();
    }


}
