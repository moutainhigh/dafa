package pers.test.changlong;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.mapper.tenantOpenMessage.TenantOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Ssc {
    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("lottery");
    private static TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);
    private static BigDecimal ben = new BigDecimal("10000000");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal rebate = new BigDecimal("0.985");
    private static final int minFlowBet = 5;
    private static final int maxFlowBet = 14;

    private static BigDecimal getAmount(int count) {
        int[] amount = {5, 12, 27, 70, 150, 327, 700, 1500, 3200};
        if (count - minFlowBet >= amount.length) {
            System.out.println(1);
            return BigDecimal.ZERO;
        }
        if (count < minFlowBet || count > maxFlowBet) {
            //System.out.println(2);
            return BigDecimal.ZERO;
        }
        return new BigDecimal(amount[count - minFlowBet]);
    }

    @Test(description = "下注")
    public static void testda() {
        //List<Map> mapList = tenantOpenMessageMapper.getLotteryOpenNumber();
        List<String> mapList = FileUtil.readFile("/Users/duke/Documents/1008openNum.txt");
        System.out.println("总数据量：" + mapList.size());
        int daCountTemp = 0;
        int xiaoCountTemp = 0;

        String isBonus = "";
        //for (Map map : mapList) {
        for (String num : mapList) {
            //String[] openNumber = map.get("openNumber").toString().split(",");
            String[] openNumber = num.split(",");
            int total = 0;
            for (String s : openNumber) {
                total += Integer.parseInt(s);
            }
            BigDecimal bettingAmount = getAmount(daCountTemp > 0 ? daCountTemp : xiaoCountTemp);
            BigDecimal bonus = BigDecimal.ZERO;

            //if (total > 22 ) {
            if (Integer.parseInt(openNumber[4]) % 2 == 1) {
                if (xiaoCountTemp >= minFlowBet && xiaoCountTemp <= maxFlowBet) {
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                } else {
                    isBonus = "未下注";
                }
                //连开
                if (daCountTemp >= minFlowBet && daCountTemp <= maxFlowBet) {
                    isBonus = "未中奖";
                    ben = ben.subtract(bettingAmount);
                }
                xiaoCountTemp = 0;
                daCountTemp++;
            } else {
                if (daCountTemp >= minFlowBet && daCountTemp <= maxFlowBet) {
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                } else {
                    isBonus = "未下注";
                }
                if (xiaoCountTemp >= minFlowBet && xiaoCountTemp <= maxFlowBet) {
                    isBonus = "未中奖";
                    ben = ben.subtract(bettingAmount);
                }
                daCountTemp = 0;
                xiaoCountTemp++;
            }

            //if (total % 2 == 1) { //单
            //    if (danCountTemp >= minFlowBet && danCountTemp <= maxFlowBet) {
            //        ben = ben.subtract(bettingAmount);
            //        isBonus = "未中奖";
            //    } else {
            //        isBonus = "未下注";
            //    }
            //    //danCountTemp = 0;
            //    //shuangCountTemp++;
            //}
            //System.out.println(ben.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + isBonus + " - " + map.get("issue").toString() + " - " + openNumber + (openNumber.length() == 1 ? " " : "") + " - " + bcbmCode.name + " - " + da + " - " + xiao);
            System.out.println(ben.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP)
                    + " - " + (total > 9 ? total : total + " ")
                    + " - " + isBonus
                    //+ " - " + map.get("issue").toString()
                    + " - " + daCountTemp
                    + " - " + xiaoCountTemp);
        }
    }

    @Test(description = "大小单双 长龙统计")
    public static void testChanglong() {
        List<Map> mapList = tenantOpenMessageMapper.getLotteryOpenNumber();
        System.out.println("总数据量：" + mapList.size());
        int[] dalong = new int[50]; //小长龙
        int[] xiaolong = new int[50]; //大长龙
        int[] danlong = new int[50]; //单长龙
        int[] shuanglong = new int[50]; //双长龙

        int xiaoCountTemp = 0;
        int daCountTemp = 0;
        int danCountTemp = 0;
        int shuangCountTemp = 0;
        for (Map map : mapList) {
            String openNumbers = map.get("openNumber").toString();
            String[] openNumber = openNumbers.split(",");
            int total = 0;
            for (String s : openNumber) {
                total += Integer.parseInt(s);
            }
            if (total > 22) { //大
                xiaoCountTemp = 0;
                daCountTemp++;
                dalong[daCountTemp - 1]++;
            } else {
                daCountTemp = 0;
                xiaoCountTemp++;
                xiaolong[xiaoCountTemp - 1]++;
            }

            if (total % 2 == 1) { //单
                shuangCountTemp = 0;
                danCountTemp++;
                danlong[danCountTemp - 1]++;
            } else {
                danCountTemp = 0;
                shuangCountTemp++;
                shuanglong[shuangCountTemp - 1]++;
            }

            System.out.println(map.get("gmtCreated").toString() + " - " + map.get("issue").toString() + " - " + openNumbers + " - " + total + " - " + daCountTemp + " - " + xiaoCountTemp + " - " + daCountTemp + " - " + shuangCountTemp);
        }
        StringBuilder sbDalong = new StringBuilder("开大长龙长度：");
        for (int i = 0; i < dalong.length; i++) {
            if (dalong[i] == 0) {
                break;
            }
            sbDalong.append(i + 1).append(":").append(dalong[i]).append(", ");
        }
        System.out.println(sbDalong.substring(0, sbDalong.length() - 2));
        StringBuilder sbXiaolong = new StringBuilder("开小长龙长度：");
        for (int i = 0; i < xiaolong.length; i++) {
            if (xiaolong[i] == 0) {
                break;
            }
            sbXiaolong.append(i + 1).append(":").append(xiaolong[i]).append(", ");
        }
        System.out.println(sbXiaolong.substring(0, sbXiaolong.length() - 2));

        StringBuilder sbDanlong = new StringBuilder("开大长龙长度：");
        for (int i = 0; i < danlong.length; i++) {
            if (danlong[i] == 0) {
                break;
            }
            sbDanlong.append(i + 1).append(":").append(danlong[i]).append(", ");
        }
        System.out.println(sbDanlong.substring(0, sbDanlong.length() - 2));

        StringBuilder sbShuanglong = new StringBuilder("开大长龙长度：");
        for (int i = 0; i < shuanglong.length; i++) {
            if (shuanglong[i] == 0) {
                break;
            }
            sbShuanglong.append(i + 1).append(":").append(shuanglong[i]).append(", ");
        }
        System.out.println(sbShuanglong.substring(0, sbShuanglong.length() - 2));

    }


}
