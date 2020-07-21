package pers.dafacloud.TestCase.payment;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.dafacloud.utils.LotteryRequest;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

public class P0ThirdSettings {

    public static void saveOrUpdateThirdPartySettings() {
        int[] dictionIds = {101, 103, 104, 105, 107, 108, 109};
        String[] viewNames = {"扫码", "WAP", "H5", "条形码", "银联快捷"};
        //thirdPartyDetailSettingsVoList,开启的支付通道，需要则添加  -------------------------------------------------------------------------------------------------------------
        //网银支付
        JSONObject internetBank = JsonObjectBuilder
                .custom()
                .put("dictionId", 101)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();
        //102银行转账
        //支付宝
        JSONObject zhifubao = JsonObjectBuilder
                .custom()
                .put("dictionId", 103)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "") //是否啟用小數 , 0 默認(可輸可不輸) 1	一定要 2	一定不要
                .put("isFixedAmount", 0) //是否固定金额
                .put("minRandomDecimal", 0.01) //第三方充值隨機小數下限	要的才紀錄
                .bulid();
        //微信
        JSONObject weixin = JsonObjectBuilder
                .custom()
                .put("dictionId", 104)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();
        //QQ钱包
        JSONObject QQPay = JsonObjectBuilder
                .custom()
                .put("dictionId", 105)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();
        //106第四方
        //银联支付
        JSONObject unionPay = JsonObjectBuilder
                .custom()
                .put("dictionId", 107)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();
        //云闪付
        JSONObject quickpass = JsonObjectBuilder
                .custom()
                .put("dictionId", 108)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();

        //京东钱包
        JSONObject jdPay = JsonObjectBuilder
                .custom()
                .put("dictionId", 109)
                .put("mobileTypeList", "银联快捷,条形码,WAP,H5,扫码")
                .put("fixedAmountList", "")
                .put("isDecimal", "")
                .put("isFixedAmount", 0)
                .put("minRandomDecimal", 0.01)
                .bulid();

        JSONArray thirdPartyDetailSettingsVoList = JsonArrayBuilder
                .custom()
                .addObject(internetBank)
                .addObject(zhifubao)
                .addObject(weixin)
                .addObject(QQPay)
                .addObject(unionPay)
                .addObject(quickpass)
                .addObject(jdPay)
                .bulid();
        //视窗类型  -------------------------------------------------------------------------------------------------------------
        JSONArray thirdPartyMobileTypeSettingsVoList = new JSONArray();
        for (String viewName : viewNames) {
            for (int dictionId : dictionIds) {
                JSONObject view = JsonObjectBuilder
                        .custom()
                        .put("openType", 2)
                        .put("style", "")
                        .put("dictionId", dictionId)
                        .put("mobileType", viewName)
                        .bulid();
                thirdPartyMobileTypeSettingsVoList.add(view);
            }
        }
        //  -------------------------------------------------------------------------------------------------------------
        //thirdPartySettingsVo
        JSONObject thirdPartySettingsVo = JsonObjectBuilder
                .custom()
                .put("id", "12187") //修改需要传入id
                .put("amountRange", 0.1) //金额误差范围
                .put("isEnabled", true) //是否启用
                .put("payLink", "baidu.com") //第三方名称接口地址
                .put("thirdPartyName", "自动化测试")
                .bulid();
        //整合以上数据 -------------------------------------------------------------------------------------------------------------
        JSONObject data = JsonObjectBuilder.custom()
                .put("thirdPartyDetailSettingsVoList", thirdPartyDetailSettingsVoList)
                .put("thirdPartyMobileTypeSettingsVoList", thirdPartyMobileTypeSettingsVoList)
                .put("thirdPartySettingsVo", thirdPartySettingsVo)
                .bulid();

        String body = UrlBuilder.custom()
                .addBuilder("data", data.toString())
                .fullBody();
        String result = LotteryRequest.postCms("/v1/transaction/saveOrUpdateThirdPartySettings", body);
        System.out.println(result);
    }

    public static void main(String[] args) {
        saveOrUpdateThirdPartySettings();
    }
}
