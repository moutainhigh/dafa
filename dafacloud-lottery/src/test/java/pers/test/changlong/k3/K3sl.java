package pers.test.changlong.k3;

import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class K3sl {
    private static BigDecimal ben = new BigDecimal("10000000");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal rebate = new BigDecimal("0.985");
    private static List<String> resultList = new ArrayList<>();
    private static BigDecimal totalBettingAmount = BigDecimal.ZERO;


    @Test(description = "下注顺龙")
    public static void testshunLong() {
        List<String> mapList = FileUtil.readFile("/Users/duke/Documents/下注测试/1407.txt");
        System.out.println("总数据量：" + mapList.size());
        int daCountTemp = 0;
        int xiaoCountTemp = 0;
        int preleng = 0;
        String isBonus = "";
        for (String num : mapList) {
            String[] openNumber = num.split(",");
            int total = 0;
            for (String s : openNumber) {
                total += Integer.parseInt(s);
            }
            //下注金额
            BigDecimal bettingAmount = BigDecimal.ZERO;
            BigDecimal bonus = BigDecimal.ZERO;
            //boolean isTrue = total % 2 == 1; //单双
            boolean isTrue = total > 10; //大小
            if (isTrue) {
                isBonus = "未下注";
                daCountTemp++;
                if (daCountTemp > 2) {//这一期大，且上一期也是大，
                    bettingAmount = new BigDecimal("100");
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                if(xiaoCountTemp > 2){//这一期大，且上一期小，大于5期，
                    bettingAmount = new BigDecimal("100");
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                xiaoCountTemp = 0;

            } else {
                xiaoCountTemp++;
                isBonus = "未下注";
                if (xiaoCountTemp > 2) {//这一期大，且上一期也是大，
                    bettingAmount = new BigDecimal("100");
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                if(daCountTemp > 2){//这一期大，且上一期小，大于5期，
                    bettingAmount = new BigDecimal("100");
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                daCountTemp = 0;

            }
            String result = ben.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + (total > 9 ? total : total + " ")
                    + " - " + isBonus
                    //+ " - " + map.get("issue").toString()
                    + " - " + daCountTemp
                    + " - " + xiaoCountTemp;
            resultList.add(result);
            System.out.println(result);
        }
        FileUtil.writeFile("/Users/duke/Documents/下注测试/1407Result0.txt", resultList, true);
        System.out.println(totalBettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP));
    }
}
