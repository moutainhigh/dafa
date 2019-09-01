package dafagame.testCase.cms.tenant.transactionManage.congZhiManage;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 第三方充值回调接口
 */
public class UpdateThirdPartyPaymentExternalState {

    private static String updateThirdPartyPaymentExternalState = "/v1/transaction/updateThirdPartyPaymentExternalState";

    @Test(description = "第三方充值回调接口")
    public static void test01() {
        String host = "http://pt.dafagame-testCookie.com";//
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode", "cindy")
                .addBuilder("recordCode", "8010803100026699")
                .addBuilder("amount", "34")
                .fullBody();
        String result = DafaRequest.post(0,host + updateThirdPartyPaymentExternalState, body);
        System.out.println(result);
    }

}
