package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
import pers.dafacloud.constant.LotteryConstant;
import pers.utils.fileUtils.FileUtil;

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
        List<LotteryObj> lotteryObjList = new ArrayList<>();
        //lotteryObjList.add(new LotteryObj("1407", "yfk3", 1, 10));
        //lotteryObjList.add(new LotteryObj("1008", "yfssc", 1, 10));
        //lotteryObjList.add(new LotteryObj("1304", "yfpk10", 1, 10));
        lotteryObjList.add(new LotteryObj("1418", "yfk3", 1, 10));
        new Betting(host, lotteryObjList, false, 2);
        //new Betting(LotteryConstant.host, "1407,yfk3,10,10", false, true, true).run(2);
    }

}
