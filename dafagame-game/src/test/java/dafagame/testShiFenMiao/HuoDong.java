package dafagame.testShiFenMiao;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaHandle;
import pers.utils.dafaRequest.DafaRequest;

public class HuoDong {


    //提现订单管理
    @Test
    public void test000() throws Exception {
        String url = "/v1/transfer/queryPersonaRedEnvelopeCircumstance?" +
                "recordId=HB370168185103059963";
        String result = DafaRequest.get(url);
        DafaHandle.dafaHandler(result);
    }

    //提现订单管理
    @Test
    public void test00() throws Exception {
        String url = "/v1/transactionManage/withdrawRecordList?userName=&Update=15" +
                "&state=-1&grades=&startTime=2019-07-10&endTime=2019-07-11&startAmount=&endAmount=&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(url);
        DafaHandle.dafaHandler(result);
    }
    //一般充值
    @Test
    public void test001() throws Exception {
        String url = "/v1/transactionManage/paymentRecordList?Update=15&dictionId=" +
                "&state=-1&grades=&userName=&startTime=2019-07-11&endTime=2019-07-11" +
                "&rechargeType=1&accountName=&startAmount=&endAmount=&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(url);
        DafaHandle.dafaHandler(result);
    }
    //
    @Test
    public void test002() throws Exception {
        String url = "/v1/transactionManage/paymentRecordList?Update=15&dictionId=" +
                "&state=-1&grades=&userName=&startTime=2019-06-25&endTime=2019-06-26" +
                "&rechargeType=1&accountName=&startAmount=&endAmount=&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(url);
        DafaHandle.dafaHandler(result);
    }

    //活动明细
    @Test
    public void test01() throws Exception {
        String url = "/v1/activity/getActivityLogList?sortBy=&name=&userName=&startTime=" +
                "2019-06-25 00:00:00&endTime=" +
                "2019-07-05 00:00:00&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(url);
        DafaHandle.dafaHandler(result);
    }

    //投注记录
    @Test
    public void test02() throws Exception {
        String path = "/v1/betting/getBetDataList?userName=&lotteryCode=&openState=&startTime=" +
                "2019-06-24 14:58:32&endTime=" +
                "2019-06-24 15:58:32&issue=&pageNum=1&pageSize=300&";
        String result = DafaRequest.get(path);
        DafaHandle.dafaHandler(result);
    }
    //追号记录
    @Test
    public void test03() throws Exception {
        String path = "/v1/betting/getChaseBetDataList?userName=&lotteryCode=&openState=&startTime=" +
                "2019-06-03 00:00:00&endTime=" +
                "2019-07-03 00:00:00&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(path);
        DafaHandle.dafaHandler(result);
    }

    //转账记录
    @Test
    public void test04() throws Exception {
        String path = "/v1/transfer/queryTransferList?userName=&receiveUserName=&status=-1&recordCode=&startTime=" +
                "2019-06-21 18:33:08&endTime=" +
                "2019-06-21 18:33:08&pageNum=1&pageSize=20&";
        String result = DafaRequest.get(path);
        DafaHandle.dafaHandler(result);
    }

    //红包领取记录
    @Test
    public void test05() throws Exception {
        String path = "/v1/transfer/queryReceiveRedEnvelopeList?userName=&type=-1&status=-1&recordCode=&startTime=" +
                "2019-06-04 14:27:19&endTime=" +
                "2019-07-05 00:00:00&pageNum=1&pageSize=2500&";
        String result = DafaRequest.get(path);
        DafaHandle.dafaHandler(result);
    }

    public static void main(String[] args) throws Exception {

    }
}
