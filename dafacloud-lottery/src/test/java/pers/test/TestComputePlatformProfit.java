package pers.test;

import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;

public class TestComputePlatformProfit {
    private static String  testComputePlatformProfit = "http://52.76.195.164:8050/v1/lottery/test/testComputePlatformProfit";

    //刷新lottery服务赢率更新，杀率使用
    public static void main(String[] args) {
        System.out.println(DafaRequest.get( HttpConfig.custom().url(testComputePlatformProfit)));
    }
}
