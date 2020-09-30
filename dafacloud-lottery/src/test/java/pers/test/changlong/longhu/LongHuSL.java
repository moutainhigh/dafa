package pers.test.changlong.longhu;

import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LongHuSL {
    private static BigDecimal ben = new BigDecimal("10000000");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal rebate = new BigDecimal("0.985");
    private static List<String> resultList = new ArrayList<>();
    private static BigDecimal totalBettingAmount = BigDecimal.ZERO;

    @Test(description = "龙虎顺龙")
    public static void test01() {
        List<String> mapList = FileUtil.readFile("/Users/duke/Documents/下注测试/longhu.txt");
        System.out.println("总数据量：" + mapList.size());
        int daCountTemp = 0;
        int xiaoCountTemp = 0;
        String isBonus;
        for (String num : mapList) {
            String[] openNumber = num.split(",");
            BigDecimal bettingAmount = new BigDecimal("100");
            BigDecimal bonus = BigDecimal.ZERO;
            int flow = 3;
            int longNum = Integer.parseInt(openNumber[0]) / 4;
            int huNum = Integer.parseInt(openNumber[1]) / 4;
            if (longNum > huNum) {
                isBonus = "未下注";
                if (daCountTemp > flow) {//这一期大，且上一期也是大，
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                } else if (xiaoCountTemp > flow) {//这一期大，且上一期小，大于5期，
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                } else {
                    bettingAmount = BigDecimal.ZERO;
                }
                xiaoCountTemp = 0;
                daCountTemp++;
            } else if (longNum < huNum) {

                if (xiaoCountTemp > flow) {//这一期大，且上一期也是大，
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                } else if (daCountTemp > flow) {//这一期大，且上一期小，大于5期，
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                } else {
                    isBonus = "未下注";
                    bettingAmount = BigDecimal.ZERO;
                }
                daCountTemp = 0;
                xiaoCountTemp++;
            } else {
                isBonus = "和值";
                bettingAmount = BigDecimal.ZERO;
            }
            String result = ben.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + isBonus
                    //+ " - " + map.get("issue").toString()
                    + " - " + daCountTemp
                    + " - " + xiaoCountTemp;
            resultList.add(result);
            System.out.println(result);
        }
        FileUtil.writeFile("/Users/duke/Documents/下注测试/longhuResult.txt", resultList, true);
        System.out.println(totalBettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP));
    }
}
