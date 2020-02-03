package dafagame.testCase.cms.platform.addCongZhi.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.enums.Path;

/**
 * 跟新充值会员等级
 *
 * */
public class UpdateWechatPaymentListSort {

    private final static Logger Log = LoggerFactory.getLogger(dafagame.testCase.front.chongzhi.RechargeFrontPaymentRecord.class);
    static Path path = Path.updateWechatPaymentListSort;

    @Test(description = "新增微信第三方支付")
    public void test001() {
        String body = "data=[{\"payAlias\":\"杜克新增\",\"id\":133624,\"gradeList\":\"-1,1,2,3,4,5,6,7,8,9\",\"sourceList\":\"1,2\",\"sort\":1}]";

    }
}
