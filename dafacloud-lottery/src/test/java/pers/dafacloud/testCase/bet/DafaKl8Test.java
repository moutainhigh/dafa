package pers.dafacloud.testCase.bet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import pers.dafacloud.utils.randomHaoMa.CalculateAC;

import java.util.ArrayList;
import java.util.List;

public class DafaKl8Test {


    String url = Path.betting.value;
    @Test(priority = 2,description = "一千注以上")
    public  void testDafaKl8(){
        String[] s =new String[40];
        for (int i = 0; i < 40; i++) {
            s[i] = String.format("%02d",i+1);
            System.out.println(s[i]);
        }
        ArrayList<List<String>> list =  CalculateAC.combinationSelect(s,4);
        String[] s2 =new String[40];
        for (int i = 0; i < 40; i++) {
            s2[i] = String.format("%02d",i+1+40);
            System.out.println(s2[i]);
        }
        System.out.println(list.size());
        ArrayList<List<String>> list2 =  CalculateAC.combinationSelect(s2,4);

        StringBuffer sb = new StringBuffer();
        /*sb.append("bettingData=[{\"lotteryCode\":\"1308\",\"playDetailCode\":\"1308A16\",\"bettingNumber\":\"09 24 28 36 37 63 74\",\"bettingCount\":1,\"bettingAmount\":2,\"bettingPoint\":\"7\",\"bettingUnit\":1,\"bettingIssue\":\"201905141009\",\"graduationCount\":1}]");*/
        sb.append("bettingData=[");

        for (int i = 0; i < 3; i++) {
            sb.append("{\"lotteryCode\":\"1308\",\"playDetailCode\":\"1308A16\",\"bettingNumber\":\"");
            List<String> list1  = list.remove(i);
            for (int j = 0; j < list1.size(); j++) {
                sb.append(list1.get(j)+" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\",\"bettingCount\":1,\"bettingAmount\":2,\"bettingPoint\":\"7\",\"bettingUnit\":1,\"bettingIssue\":\"201905141009\",\"graduationCount\":1},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]").toString();

        System.out.println(sb.toString());
        /*String ss = Request.doPost(url,sb.toString());
        Reporter.log(ss);
        Reporter.log(sb.toString());
        Assert.assertEquals(true,ss.contains("投注成功"),"投注失败");
        System.out.println("投注成功");*/
    }
}
