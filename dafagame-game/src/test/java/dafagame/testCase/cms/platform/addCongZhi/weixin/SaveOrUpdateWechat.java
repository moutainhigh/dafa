package dafagame.testCase.cms.platform.addCongZhi.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 * 新增微信支付方式
 *
 * payAlias             别名
 * payType              类型
 * paymentInfo          转账信息
 * auditInfo            查账信息
 * qrcode               二维码
 * mobileType           接口模式
 * merchantNumber       商户号
 * terminalNumber       终端号
 * secretKey            密钥
 * receiveKey           接受密钥
 * payUrl               购物网址
 * id                   主键
 * minAmount            下限
 * maxAmount            上限
 * isFixedAmount        固定值 (0,1) 站长
 * quickAmountList      快捷金额
 * fixedAmountList      固定金额
 * isFixedAmountThird   第三方原有的固定金额判断
 * */                   
public class SaveOrUpdateWechat {

    private final static Logger Log = LoggerFactory.getLogger(dafagame.testCase.front.chongzhi.RechargeFrontPaymentRecord.class);
    static Path path = Path.saveOrUpdateWechat;

    @Test(description = "新增微信第三方支付")
    public void test001() {
        String body = "payAlias=杜克新增&payType=微信第三方duke&paymentInfo=&auditInfo=&qrcode=" +
                "&mobileType=扫码&merchantNumber=duke01&terminalNumber=duke01&secretKey=duke01&receiveKey=duke01&payUrl=http://duke01.com" +
                "&id=&minAmount=10&maxAmount=5000000" +
                "&isFixedAmount=&quickAmountList=&fixedAmountList=&isFixedAmountThird=";
        String result = Request.doPost(path.value, body);
        System.out.println(result);
        Log.info(result);
        //Reporter.log(s);
    }

}
