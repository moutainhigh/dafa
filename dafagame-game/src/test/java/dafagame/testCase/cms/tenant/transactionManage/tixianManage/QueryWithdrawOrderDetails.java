package dafagame.testCase.cms.tenant.transactionManage.tixianManage;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 提现管理 > 提现订单管理  点击提现金额  显示的信息（后台GET）
 * 主要获取用户的报表，余额等信息
 * //1.report :
 * 2.交易QueryWithdrawOrderDetails //表报（UserProfitCMS），用户 ，余额
 * 3。
 */
public class QueryWithdrawOrderDetails {

    private static String queryWithdrawOrderDetails = "/v1/transaction/queryWithdrawOrderDetails";

    /**
     * agentName 上级代理
     * balance 用户现金
     * safeBalance 保险箱余额
     * profit 总盈利
     * profitAmount 游戏盈利
     * serviceAmount 游戏服务金额
     * outFeeAmount 提现手续费
     * inAmount 充值金额
     */
    @Test(priority = 1, description = "")
    public void test001() {

        String url  =UrlBuilder.custom()
                .url(queryWithdrawOrderDetails)
                .addBuilder("userId","1316")
                .addBuilder("userName","78938089")
                //.addBuilder("date","2019-05-29")
                .fullUrl();
        String result = DafaRequest.get(1,url);
        System.out.println(result);

    }
}
