package pers.dafacloud.dafaLottery;

import pers.dafacloud.constant.LotteryConstant;

import java.util.ArrayList;
import java.util.List;

public class BettingRunning {
    //lotteryCode，文件名，用户数量，下单间隔

    public static String host = "http://52.76.195.164:8020";
    //public static String host = "http://caishen02.com";//dev
    //public static String host = "http://52.76.195.164:8020";//dev第一套
    //public static String host = "http://52.77.207.64:8020";//dev第二套
    //public static String host = "http://dafacloud-pre.com";//pre

    public static void main(String[] args) {
        Betting.isStop=false;
        List<LotteryObj> lotteryObjList = new ArrayList<>();
        //lotteryObjList.add(new LotteryObj("1407", "yfk3", 600, 3));
        //lotteryObjList.add(new LotteryObj("1008", "yfssc", 1, 5));
        //lotteryObjList.add(new LotteryObj("1304", "yfpk10", 1, 5));
        //lotteryObjList.add(new LotteryObj("1305", "yflhc", 1, 5));
        //
        //lotteryObjList.add(new LotteryObj("1413", "sfks", 1, 5));
        //lotteryObjList.add(new LotteryObj("1010", "sfssc", 1, 5));
        //lotteryObjList.add(new LotteryObj("1314", "sfpk10", 1, 5));
        //
        //lotteryObjList.add(new LotteryObj("1412", "wfk3", 1, 5));
        //lotteryObjList.add(new LotteryObj("1009", "wfssc", 1, 5));
        //lotteryObjList.add(new LotteryObj("1306", "wfpk10", 1, 5));
        lotteryObjList.add(new LotteryObj("1300", 1, 5));
        new Betting(host, "dafa", 1, lotteryObjList, false, 100);


    }

}
