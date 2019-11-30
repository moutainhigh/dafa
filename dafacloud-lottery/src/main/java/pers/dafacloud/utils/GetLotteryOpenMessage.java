package pers.dafacloud.utils;

import pers.utils.dafaRequest.DafaRequest;

public class GetLotteryOpenMessage {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            String result = DafaRequest.get("http://caishen02.com/v1/lottery/openResult?lotteryCode=1314&dataNum=10&",
                    "8AF6180E062DF384D969F844782E20A5");
            System.out.println(result);
            Thread.sleep(5000);
        }


    }
}
