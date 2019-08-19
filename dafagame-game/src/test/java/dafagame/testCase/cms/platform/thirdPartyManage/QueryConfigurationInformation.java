package dafagame.testCase.cms.platform.thirdPartyManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class QueryConfigurationInformation {
    private static String queryConfigurationInformation = "/v1/transaction/queryConfigurationInformation";

    @Test(description = "第三方管理明细")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(queryConfigurationInformation)
                .addBuilder("thirdPartyName", "星星第三方")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONObject data = jsonResult.getJSONObject("data");
        //third_party_settings 基本配置信息
        Log.info("基本配置信息");
        JSONObject thirdPartyPaySettings = data.getJSONObject("thirdPartyPaySettings");
        Log.info(thirdPartyPaySettings.toString());
        Log.info("视窗样式");
        //视窗样式
        JSONArray thirdPartyMobileTypeSettingsList = data.getJSONArray("thirdPartyMobileTypeSettingsList");
        if (thirdPartyMobileTypeSettingsList.size() == 0) {
            Log.info("未设置视窗样式");
        } else {
            for (int i = 0; i < thirdPartyMobileTypeSettingsList.size(); i++) {
                Log.info(thirdPartyMobileTypeSettingsList.getJSONObject(i).toString());
            }
        }
        Log.info("启用的支付信息");
        //启用的支付信息
        JSONArray thirdPartyDetailSettingsList =data.getJSONArray("thirdPartyDetailSettingsList");
        if (thirdPartyDetailSettingsList.size() == 0) {
            Log.info("未设置支付通道");
        } else {
            for (int i = 0; i < thirdPartyDetailSettingsList.size(); i++) {
                Log.info(thirdPartyDetailSettingsList.getJSONObject(i).toString());
            }
        }

    }
}
