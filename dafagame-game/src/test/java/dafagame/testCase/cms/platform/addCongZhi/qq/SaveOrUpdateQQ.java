package dafagame.testCase.cms.platform.addCongZhi.qq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 * QQ钱包支付新增和更新接口(后台POST)
 */
public class SaveOrUpdateQQ {

    private final static Logger Log = LoggerFactory.getLogger(dafagame.testCase.front.chongzhi.RechargeFrontPaymentRecord.class);
    static Path path = Path.saveOrUpdateQQ;

    @Test(description = "QQ钱包支付新增")
    public void test001() {
        String body = "payAlias=杜克新增一般&payType=一般&paymentInfo=duke01&auditInfo=duke02&qrcode=" +
                "&mobileType=&merchantNumber=&terminalNumber=&secretKey=&receiveKey=&payUrl=&id=&minAmount=10&maxAmount=5000000" +
                "&isFixedAmount=&quickAmountList=&fixedAmountList=&isFixedAmountThird=";
        String result = Request.doPost(path.value, body);
        System.out.println(result);
        Log.info(result);
        //Reporter.log(s);
    }
}
