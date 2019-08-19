package dafagame.testCase.cms.platform.addCongZhi.four;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

public class SaveOrUpdateFourthParty {

    private final static Logger Log = LoggerFactory.getLogger(SaveOrUpdateFourthParty.class);
    static Path path = Path.saveOrUpdateFourthParty;

    @Test(description = "新增微信第四方支付渠道")
    public void test001() {
        String body = "payAlias=duke&merchantNumber=481080ef11b44d3bbb392b2df8263641" +
                "&secretKey=20190527143709416FCD5718654ED195" +
                "&clientIp=192.168.1.1&payUrl=http://baidu.com&iconUrl=&subtitle=杜克中小城镇";
        String result = Request.doPost(path.value, body);
        System.out.println(result);
        Log.info(result);
        //Reporter.log(s);
    }
}
