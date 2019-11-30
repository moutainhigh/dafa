package pers;

import pers.utils.dafaRequest.DafaRequest;

public class SaveFrontWithdrawRecord {

    private static String url = "http://caishen01.com/v1/transaction/saveFrontWithdrawRecord";


    private static String body = "bankCardID=12560541&amount=313100&safetyPassword=89171121c16feb7b4791581d41d9f4ff";

    public static void main(String[] args) {

        String result = DafaRequest.post(url, body, "8F908B567A934DFF8368FAE6D5B79E3A");

        System.out.println(result);
    }

}
