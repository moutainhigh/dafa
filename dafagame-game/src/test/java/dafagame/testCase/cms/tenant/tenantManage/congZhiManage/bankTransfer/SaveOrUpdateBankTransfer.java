package dafagame.testCase.cms.tenant.tenantManage.congZhiManage.bankTransfer;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 银行充值渠道
 * */
public class SaveOrUpdateBankTransfer {

    private static String saveOrUpdateBankTransfer = "/v1/transaction/saveOrUpdateBankTransfer";

    @Test(description = "保存和新增银行转账充值渠道")
    public static void test01() {
        String body  = UrlBuilder.custom()
                //.addBuilder("bankId","") //传id就是修改
                .addBuilder("name","胜利")
                .addBuilder("bankName","招商银行")
                .addBuilder("branchName","胜利的支行")
                .addBuilder("account","622212345676")
                //.addBuilder("bankId")
                .addBuilder("minAmount","1")
                .addBuilder("maxAmount","100000")
                .fullBody();
        String result = DafaRequest.post(1,saveOrUpdateBankTransfer,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }
}
