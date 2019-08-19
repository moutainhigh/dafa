package dafagame.testCase.cms.platform.thirdPartyManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 平台管理 > 基础管理 > 第三方管理
 */
public class SaveOrUpdateThirdPartySettings {

    //static Path path = Path.saveOrUpdateThirdPartySettings;
    private static String saveOrUpdateThirdPartySettings = "/v1/transaction/saveOrUpdateThirdPartySettings";
    //101	101	网银支付
    //102	102	银行转账
    //103	103	支付宝
    //104	104	微信支付
    //105	105	QQ钱包
    //106	106	第四方支付
    //107	107	银联支付
    //108	108	云闪付
    //109	109	京东钱包
    @Test(description = "新增第三方")
    public void test001() {
        //开启的支付通道，需要则添加  -------------------------------------------------------------------------------------------------------------
        //支付宝
        JSONObject zhifubao = JsonObjectBuilder
                .custom()
                .put("dictionId", 103)
                .put("mobileTypeList", "扫码")
                .put("fixedAmountList","10,100,1000,8000")
                .put("isDecimal","") //是否啟用小數 , 0 默認(可輸可不輸) 1	一定要 2	一定不要
                .put("isFixedAmount",1) //是否固定金额
                .put("minRandomDecimal",0.01) //第三方充值隨機小數下限	要的才紀錄
                .bulid();
        //微信
        JSONObject weixin = JsonObjectBuilder
                .custom()
                .put("dictionId", 104)
                .put("mobileTypeList", "")
                .put("fixedAmountList","")
                .put("isDecimal","")
                .put("isFixedAmount",0)
                .put("minRandomDecimal",0.01)
                .bulid();
        JSONArray thirdPartyDetailSettingsVoList = JsonArrayBuilder
                .custom()
                .addObject(zhifubao)
                .addObject(weixin)
                .bulid();
        //视窗类型  -------------------------------------------------------------------------------------------------------------
        //扫码
        JSONObject zhifubaoSaoma = JsonObjectBuilder
                .custom()
                .put("openType", 2) // 1	内页打开	(一般  默认0) , 2	新窗口打开
                .put("style", "扫码")
                .put("dictionId",103)
                .put("mobileType","扫码")
                .bulid();
        JSONObject weixinSaoma = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",104)
                .put("mobileType","扫码")
                .bulid();
        JSONObject qqQianbaoSaoma = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",105)
                .put("mobileType","扫码")
                .bulid();

        JSONObject yinlianZhifuSaoma = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",107)
                .put("mobileType","扫码")
                .bulid();
        //H5
        JSONObject zhifubaoH5 = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",103)
                .put("mobileType","H5")
                .bulid();
        JSONObject weixinH5 = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",104)
                .put("mobileType","H5")
                .bulid();
        JSONObject qqQianbaoH5 = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",105)
                .put("mobileType","H5")
                .bulid();

        JSONObject yinlianZhifuH5 = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",107)
                .put("mobileType","H5")
                .bulid();
        //WAP
        JSONObject zhifubaoWAP = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",103)
                .put("mobileType","WAP")
                .bulid();
        JSONObject weixinWAP = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",104)
                .put("mobileType","WAP")
                .bulid();
        JSONObject qqQianbaoWAP = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",105)
                .put("mobileType","WAP")
                .bulid();

        JSONObject yinlianZhifuWAP = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",107)
                .put("mobileType","WAP")
                .bulid();

        //条形码
        JSONObject zhifubaoTXM = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",103)
                .put("mobileType","条形码")
                .bulid();
        JSONObject weixinTXM = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",104)
                .put("mobileType","条形码")
                .bulid();
        JSONObject qqQianbaoTXM = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",105)
                .put("mobileType","条形码")
                .bulid();

        JSONObject yinlianZhifuTXM = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",107)
                .put("mobileType","条形码")
                .bulid();

        //银联快捷
        JSONObject zhifubaoYLKJ = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",103)
                .put("mobileType","银联快捷")
                .bulid();
        JSONObject weixinYLKJ = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",104)
                .put("mobileType","银联快捷")
                .bulid();
        JSONObject qqQianbaoYLKJ = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "扫码")
                .put("dictionId",105)
                .put("mobileType","银联快捷")
                .bulid();

        JSONObject yinlianZhifuYLKJ = JsonObjectBuilder
                .custom()
                .put("openType", 2)
                .put("style", "")
                .put("dictionId",107)
                .put("mobileType","银联快捷")
                .bulid();
        //添加到json数组
        JSONArray thirdPartyMobileTypeSettingsVoList = JsonArrayBuilder
                .custom()
                .addObject(zhifubaoSaoma)
                .addObject(weixinSaoma)
                .addObject(qqQianbaoSaoma)
                .addObject(yinlianZhifuSaoma)

                .addObject(zhifubaoH5)
                .addObject(weixinH5)
                .addObject(qqQianbaoH5)
                .addObject(yinlianZhifuH5)

                .addObject(zhifubaoWAP)
                .addObject(weixinWAP)
                .addObject(qqQianbaoWAP)
                .addObject(yinlianZhifuWAP)

                .addObject(zhifubaoYLKJ)
                .addObject(weixinYLKJ)
                .addObject(qqQianbaoYLKJ)
                .addObject(yinlianZhifuYLKJ)

                .bulid();
        //  -------------------------------------------------------------------------------------------------------------
        //thirdPartySettingsVo
        JSONObject thirdPartySettingsVo = JsonObjectBuilder
                .custom()
                .put("id", "") //修改需要传入id
                .put("amountRange", 0.1) //金额误差范围
                .put("isEnabled",true) //是否启用
                .put("payLink","baidu.com") //第三方名称接口地址
                .put("thirdPartyName","星星第三方")
                .bulid();
        //整合以上数据 -------------------------------------------------------------------------------------------------------------
        JSONObject data = JsonObjectBuilder.custom()
                .put("thirdPartyDetailSettingsVoList",thirdPartyDetailSettingsVoList)
                .put("thirdPartyMobileTypeSettingsVoList",thirdPartyMobileTypeSettingsVoList)
                .put("thirdPartySettingsVo",thirdPartySettingsVo)
                .bulid();

        String body = UrlBuilder.custom()
                .addBuilder("data",data.toString())
                .fullBody();
        String result = DafaRequest.post(1,saveOrUpdateThirdPartySettings,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);

    }

}
