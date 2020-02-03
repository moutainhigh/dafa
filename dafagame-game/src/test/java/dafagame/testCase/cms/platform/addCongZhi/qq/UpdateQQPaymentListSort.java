package dafagame.testCase.cms.platform.addCongZhi.qq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.enums.Path;
import pers.utils.httpUtils.Request;

/**
 * 银行转账--排序接口:等级限制（后台POST）
 * */
public class UpdateQQPaymentListSort {

    private final static Logger Log = LoggerFactory.getLogger(UpdateQQPaymentListSort.class);
    static Path path = Path.updateQQPaymentListSort;

    @Test(description = "更新等级限制")
    public void test001() {
        String body = "data=[{\"payAlias\":\"杜克新增一般\",\"id\":100156,\"gradeList\":\"-1,1,2,3,4,5,6,7,8,9\",\"sourceList\":\"1,2\",\"sort\":1}]";
        //String body = "id=100156&gradeList=1,2,3,4,5,6,7,8,9&sort=1";

    }
}
