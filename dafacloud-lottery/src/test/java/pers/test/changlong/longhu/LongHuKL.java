package pers.test.changlong.longhu;

import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LongHuKL {
    private static BigDecimal ben = new BigDecimal("10000000");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal rebate = new BigDecimal("0.985");
    private static List<String> resultList = new ArrayList<>();
    private static BigDecimal totalBettingAmount = BigDecimal.ZERO;

    private static final int minFlowBet = 3;//最小跟注
    private static final int maxFlowBet = 12;

    //下注金额
    private static BigDecimal getAmount(int count) {
        int[] amount = {5, 12, 27, 70, 150, 327, 700, 1500, 3200};
        if (count - minFlowBet >= amount.length) {
            return BigDecimal.ZERO;
        }
        if (count < minFlowBet || count > maxFlowBet) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(amount[count - minFlowBet]);
    }

    @Test(description = "龙虎砍龙")
    public static void test01() {
        List<String> mapList = FileUtil.readFile("/Users/duke/Documents/下注测试/longhu.txt");
        System.out.println("总数据量：" + mapList.size());
        int daCountTemp = 0;
        int xiaoCountTemp = 0;
        String isBonus;
        for (String num : mapList) {
            isBonus = "未下注";
            String[] openNumber = num.split(",");
            //下注金额
            //BigDecimal bettingAmount = BigDecimal.ZERO;
            BigDecimal bettingAmount = getAmount(daCountTemp > 0 ? daCountTemp : xiaoCountTemp);
            BigDecimal bonus = BigDecimal.ZERO;
            int longNum = Integer.parseInt(openNumber[0]) / 4;
            int huNum = Integer.parseInt(openNumber[1]) / 4;
            if (longNum > huNum) {
                if (xiaoCountTemp > minFlowBet && xiaoCountTemp < maxFlowBet) {//这一期大，上一期是小，且是小龙
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                if (daCountTemp > minFlowBet && daCountTemp < maxFlowBet) {//连续开大
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                xiaoCountTemp = 0;
                daCountTemp++;
            } else if (longNum < huNum) {
                if (daCountTemp > minFlowBet && daCountTemp < maxFlowBet) {//这一期小，上一期是大，且是大龙
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                if (xiaoCountTemp > minFlowBet && xiaoCountTemp < maxFlowBet) {//连续开小
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖  ";
                    totalBettingAmount = totalBettingAmount.add(bettingAmount);
                }
                daCountTemp = 0;
                xiaoCountTemp++;
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
