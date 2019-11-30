package pers;

import pers.utils.dafaRequest.DafaRequest;

public class TestChangLong {

    public static void main(String[] args) throws Exception {
        String result = DafaRequest.post("http://2019.dafacloud-admin.com",
                "lotteryCode=1304&issue=201911271258&remark=系统故障",
                "A1057CAF506997E5986ED4010FA016D3");
        System.out.println(result);
    }



    public static void function01() throws Exception{
        /**
         * 长龙 停机
         * */
        for (int i = 0; i < 100000; i++) {
            String result = DafaRequest.get("http://caishen01.com/v1/lottery/longDragon?type=1&",
                    "A4A2C6CC1CF1CEA27BEBB779A5B627FD");
            System.out.println(result);
            Thread.sleep(5000);
        }
    }

}
