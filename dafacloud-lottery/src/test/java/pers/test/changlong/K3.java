package pers.test.changlong;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.enums.BcbmCodeEmu;
import pers.dafacloud.mapper.tenantOpenMessage.TenantOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class K3 {
    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("lottery");
    private static TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);
    private static BigDecimal ben = new BigDecimal("10000000");
    private final static BigDecimal FIVE = new BigDecimal("5");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal FOUR = new BigDecimal("4");
    private static BigDecimal betAmount = new BigDecimal("500");
    private static BigDecimal rebate = new BigDecimal("0.985");
    private static final int minFlowBet = 6;//最小跟注
    private static final int maxFlowBet = 14;

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

    //下注金额终端
    private static BigDecimal getAmount0(int count) {
        int[] amount = {5, 12, 27, 70, 150};
        if (count < 0 || count >= amount.length) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(amount[count]);
    }


    @Test(description = "下注终端")
    public static void testda1() {
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
            boolean isTrue = total % 2 == 1; //单双
            //boolean isTrue = total > 10; //大小
            if (isTrue) {
                if (preleng > 5) {
                    bettingAmount = getAmount0(xiaoCountTemp - 1);
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);//剩余本金 = 本金+中奖-下注
                    isBonus = "中奖  ";
                    preleng = 0;
                } else {
                    isBonus = "未下注";
                }
                xiaoCountTemp = 0;
                daCountTemp++;
            } else {
                if (daCountTemp > 5) {//开小，且大long大于5
                    preleng = daCountTemp;
                }
                daCountTemp = 0;
                isBonus = "未下注";
                if (preleng > 5 && xiaoCountTemp != 0) {
                    bettingAmount = getAmount0(xiaoCountTemp - 1);
                    ben = ben.subtract(bettingAmount);//剩余本金 = 本金+中奖-下注
                    isBonus = "未中奖";
                }
                xiaoCountTemp++;
                //if (preleng > 5) {
                //    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                //    ben = ben.add(bonus).subtract(bettingAmount);
                //    isBonus = "中奖  ";
                //} else {
                //    isBonus = "未下注";
                //}
                //if (xiaoCountTemp >= minFlowBet && xiaoCountTemp <= maxFlowBet) {
                //    isBonus = "未中奖";
                //    ben = ben.subtract(bettingAmount);
                //}
                //daCountTemp = 0;
                //preleng = xiaoCountTemp++;
            }
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

    @Test(description = "下注终端")
    public static void testda2() {
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
                if (xiaoCountTemp > 5) {//开小，且大long大于5
                    preleng = daCountTemp;
                }
                isBonus = "未下注";
                if (preleng > 5 && daCountTemp != 0) {
                    bettingAmount = getAmount0(daCountTemp - 1);
                    ben = ben.subtract(bettingAmount);//剩余本金 = 本金+中奖-下注
                    isBonus = "未中奖";
                }
                xiaoCountTemp = 0;
                daCountTemp++;
            } else {
                if (preleng > 5) {
                    bettingAmount = getAmount0(xiaoCountTemp - 1);
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);//剩余本金 = 本金+中奖-下注
                    isBonus = "中奖  ";
                    preleng = 0;
                } else {
                    isBonus = "未下注";
                }
                daCountTemp = 0;
                xiaoCountTemp++;
                //if (preleng > 5) {
                //    bonus = bettingAmount.multiply(TWO).multiply(rebate);
                //    ben = ben.add(bonus).subtract(bettingAmount);
                //    isBonus = "中奖  ";
                //} else {
                //    isBonus = "未下注";
                //}
                //if (xiaoCountTemp >= minFlowBet && xiaoCountTemp <= maxFlowBet) {
                //    isBonus = "未中奖";
                //    ben = ben.subtract(bettingAmount);
                //}
                //daCountTemp = 0;
                //preleng = xiaoCountTemp++;
            }
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



    @Test(description = "下注")
    public static void testda() {
        //List<Map> mapList = tenantOpenMessageMapper.getLotteryOpenNumber();
        List<String> mapList = FileUtil.readFile("/Users/duke/Documents/下注测试/1407.txt");
        System.out.println("总数据量：" + mapList.size());
        int daCountTemp = 0;
        int xiaoCountTemp = 0;

        String isBonus;
        //for (Map map : mapList) {
        for (String num : mapList) {
            //String[] openNumber = map.get("openNumber").toString().split(",");
            String[] openNumber = num.split(",");
            int total = 0;
            for (String s : openNumber) {
                total += Integer.parseInt(s);
            }
            //下注金额
            BigDecimal bettingAmount = getAmount(daCountTemp > 0 ? daCountTemp : xiaoCountTemp);
            BigDecimal bonus = BigDecimal.ZERO;
            //boolean isTrue = total % 2 == 1; //单双
            boolean isTrue = total > 10; //大小
            if (isTrue) {
                //例子：开大，且小的长龙在范围内，则中奖大
                if (xiaoCountTemp >= minFlowBet && xiaoCountTemp <= maxFlowBet) {
                    bonus = bettingAmount.multiply(TWO).multiply(rebate);//中奖金额：下注*2*返点
                    ben = ben.add(bonus).subtract(bettingAmount);//剩余本金 = 本金+中奖-下注
                    isBonus = "中奖  ";
                } else {
                    isBonus = "未下注";
                }
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

    @Test(description = "总和大小单双 长龙统计")
    public static void testChanglong() {
        List<Map> mapList = tenantOpenMessageMapper.getLotteryOpenNumber();
        //List<String> mapList = FileUtil.readFile("/Users/duke/Documents/下注测试/1407.txt");
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
        //for (String openNumbers : mapList) {
            String openNumbers = map.get("openNumber").toString();
            String[] openNumber = openNumbers.split(",");
            int total = 0;
            for (String s : openNumber) {
                total += Integer.parseInt(s);
            }
            if (total > 10) { //大
                daCountTemp++;//如果一直开大，daCountTemp就一直累计，
                if (xiaoCountTemp != 0) { //如果这期开大，且xiaoCountTemp != 0,上期则是开小，统计上期开小长龙xiaoCountTemp
                    xiaolong[xiaoCountTemp - 1]++;
                }
                xiaoCountTemp = 0;
            } else {
                xiaoCountTemp++;
                if (daCountTemp != 0) {
                    dalong[daCountTemp - 1]++;
                }
                daCountTemp = 0;
            }

            if (total % 2 == 1) { //单
                danCountTemp++;
                if (shuangCountTemp != 0) {
                    shuanglong[shuangCountTemp - 1]++;
                }
                shuangCountTemp = 0;
            } else {
                shuangCountTemp++;
                if (danCountTemp != 0) {
                    danlong[danCountTemp - 1]++;
                }
                danCountTemp = 0;

            }

            System.out.println(map.get("gmtCreated").toString() + " - " + map.get("issue").toString() + " - " + openNumbers + " - " + total + " - " + daCountTemp + " - " + xiaoCountTemp + " - " + daCountTemp + " - " + shuangCountTemp);
            //System.out.println(openNumbers + " - " + total + " - " + daCountTemp + " - " + xiaoCountTemp + " - " + daCountTemp + " - " + shuangCountTemp);
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

        StringBuilder sbDanlong = new StringBuilder("开单长龙长度：");
        for (int i = 0; i < danlong.length; i++) {
            if (danlong[i] == 0) {
                break;
            }
            sbDanlong.append(i + 1).append(":").append(danlong[i]).append(", ");
        }
        System.out.println(sbDanlong.substring(0, sbDanlong.length() - 2));

        StringBuilder sbShuanglong = new StringBuilder("开双长龙长度：");
        for (int i = 0; i < shuanglong.length; i++) {
            if (shuanglong[i] == 0) {
                break;
            }
            sbShuanglong.append(i + 1).append(":").append(shuanglong[i]).append(", ");
        }
        System.out.println(sbShuanglong.substring(0, sbShuanglong.length() - 2));

    }
//开大长龙长度：1:2654, 2:1352, 3:661, 4:327, 5:177, 6:101, 7:52, 8:22, 9:7, 10:6, 11:1, 12:1
//开小长龙长度：1:2729, 2:1307, 3:644, 4:291, 5:218, 6:87, 7:45, 8:22, 9:8, 10:7, 11:1, 12:1
//开单长龙长度：1:2723, 2:1267, 3:637, 4:339, 5:157, 6:90, 7:44, 8:22, 9:14, 10:9, 11:3
//开双长龙长度：1:2597, 2:1286, 3:699, 4:337, 5:192, 6:98, 7:36, 8:28, 9:15, 10:6, 11:3, 12:5, 13:3

//327-(661-327)+ 177- (327-177)+101-(177-101)+52-(101-52)+22-(52-22)+7-(22-7)+6-(7-6)+1-(6-1)+1-(1-1)+0-(1-0) =33
//3:661, 4:327, 5:177, 6:101, 7:52, 8:22, 9:7, 10:6, 11:1, 12:1
//-661+327+177+101+52+22+7+6+1+1 = 33
//1284


}
