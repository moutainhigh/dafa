package dafagame.testCase.cms.platform.addCongZhi.four;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

public class UpdateFourthPartySettingsListSort {

    private final static Logger Log = LoggerFactory.getLogger(UpdateFourthPartySettingsListSort.class);
    static Path path = Path.updateFourthPartySettingsListSort;

    @Test(description = "更新等级限制")
    public void test001() {
        String body = "data=[{\"payAlias\":\"duke\",\"id\":100042,\"gradeList\":\"-1,1,2,3,4,5,6,7,8,9\",\"sourceList\":\"1,2\",\"sort\":1}]";
        System.out.println(body);
        String result = Request.doPost(path.value, body);
        System.out.println(result);
        Log.info(result);
        //Reporter.log(s);
    }
}
