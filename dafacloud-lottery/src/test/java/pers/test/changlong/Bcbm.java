package pers.test.changlong;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.enums.BcbmCodeEmu;
import pers.dafacloud.mapper.tenantOpenMessage.TenantOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Bcbm {

    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("lotteryGame");
    private static TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);
    private static BigDecimal ben = new BigDecimal("10000000");
    private final static BigDecimal FIVE = new BigDecimal("5");
    private static BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal FOUR = new BigDecimal("4");
    private static BigDecimal betAmount = new BigDecimal("500");
    private static final int minFlowBet = 3;
    private static final int maxFlowBet = 5;

    public static BigDecimal getAmount(int xiaoCount) {
        int amount = 0;
        if (xiaoCount == 1) {
            amount = 400;
        } else if (xiaoCount == 2) {
            amount = 450;
        } else if (xiaoCount == 3) {
            amount = 450;
        } else if (xiaoCount == 4) {
            amount = 500;
        } else if (xiaoCount == 5) {
            amount = 500;
        } else if (xiaoCount == 6) {
            amount = 1000;
        } else if (xiaoCount == 7) {
            amount = 1000;
        } else if (xiaoCount == 8) {
            amount = 1500;//7500
        } else if (xiaoCount == 9) {
            amount = 1500;//7500
        } else if (xiaoCount == 10) {
            amount = 2000;//10000
        } else if (xiaoCount == 11) {
            amount = 2000;//10000
        } else if (xiaoCount == 12) {
            amount = 2500;//12500
        } else if (xiaoCount == 13) {
            amount = 2500;//12500
        } else if (xiaoCount == 14) {
            amount = 3000;//15000
        } else if (xiaoCount == 15) {
            amount = 3000;//15000
        } else if (xiaoCount == 16) {
            amount = 3500;//17500
        } else if (xiaoCount == 17) {
            amount = 3500;//175000
        } else if (xiaoCount == 18) {
            amount = 4000;//17500
        } else if (xiaoCount == 19) {
            amount = 4000;//175000
        }
        return new BigDecimal(amount);
    }


    @Test(description = "只投大小车标盈利统计")
    public static void test01b() {
        List<Map> mapList = tenantOpenMessageMapper.getGameOpenNumber();
        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getNameByNum(openNumber);
            //if (bcbmCode.isBig == 1) {
            //    ben = ben.add(betAmount.divide(FOUR).multiply(new BigDecimal(bcbmCode.bonus))).subtract(betAmount);
            //} else {
            //    ben = ben.subtract(betAmount);
            //}
            if (bcbmCode.isBig == 1) {
                ben = ben.subtract(betAmount);
            } else {
                ben = ben.add(betAmount.divide(FOUR).multiply(new BigDecimal(bcbmCode.bonus))).subtract(betAmount);
            }
            System.out.println(ben.setScale(0, BigDecimal.ROUND_HALF_UP));
        }
    }


    //获取投注金额，下注小车标
    private static BigDecimal getAmountDa(int da) {
        int[] amount = {10, 70, 400, 2200, 12000};
        //int[] amount = {400, 2200};
        if (da > amount.length) {
            return BigDecimal.ZERO;
        }
        if (da < minFlowBet || da > maxFlowBet) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(amount[da - minFlowBet]);
    }

    @Test(description = "大车标长龙，下注小车标")
    public static void testda() {
        List<Map> mapList = tenantOpenMessageMapper.getGameOpenNumber();
        int xiao = 0;
        int da = 0;
        String isBonus = "";
        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getNameByNum(openNumber);
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            BigDecimal baseBonus = new BigDecimal(bcbmCode.bonus).multiply(new BigDecimal(0.985));
            BigDecimal bettingAmount = getAmountDa(da).multiply(FOUR);
            BigDecimal bonus = BigDecimal.ZERO;
            if (bcbmCode.isBig == 1) {
                if (da >= minFlowBet && da <= maxFlowBet) {
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖";
                } else {
                    isBonus = "未下注";
                }
                xiao = 0;
                da++;
            }
            //0小（da=0），1大（da=1）
            //
            if (bcbmCode.isBig == 0) {
                if (da >= minFlowBet && da <= maxFlowBet) {
                    bonus = bettingAmount.divide(FOUR).multiply(baseBonus);
                    //金额/5*奖金
                    //中奖加钱 投注*赔率-投注
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                } else {
                    isBonus = "未下注";
                }

                da = 0;
                xiao++;
            }
            System.out.println(ben.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + isBonus + " - " + map.get("issue").toString() + " - " + openNumber + (openNumber.length() == 1 ? " " : "") + " - " + bcbmCode.name + " - " + da + " - " + xiao);
        }
    }

    //下注大车标
    public static BigDecimal getAmount0(int xiao) {
        int amount = 0;
        if (xiao >= 1 && xiao < 2) {
            amount = 400 + (xiao - 5) * 50;
        }
        return new BigDecimal(amount);
    }

    @Test(description = "小车标长龙，下注大车标")
    public static void testxiao() {
        List<Map> mapList = tenantOpenMessageMapper.getGameOpenNumber();
        int xiao = 0;
        int da = 0;
        String isBonus = ""; //中奖 未中奖 未下注
        //BigDecimal current = new BigDecimal(0);
        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getNameByNum(openNumber);
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            BigDecimal baseBonus = new BigDecimal(bcbmCode.bonus).multiply(new BigDecimal(0.985));
            BigDecimal bettingAmount = getAmount0(xiao).multiply(FIVE);
            BigDecimal bonus = BigDecimal.ZERO;
            if (bcbmCode.isBig == 1) {
                if (xiao >= minFlowBet && xiao < maxFlowBet) {
                    if (bcbmCode == BcbmCodeEmu.b_dz) {//中大众
                        //bettingAmount = bettingAmount.divide(new BigDecimal(5)) * 2;
                        bonus = bettingAmount.divide(FIVE).multiply(baseBonus).multiply(TWO);
                    } else {
                        bonus = bettingAmount.divide(FIVE).multiply(baseBonus);
                    }
                    //金额/5*奖金
                    //中奖加钱 投注*赔率-投注
                    ben = ben.add(bonus).subtract(bettingAmount);
                    isBonus = "中奖  ";
                } else {
                    isBonus = "未下注";
                }
                xiao = 0;
                da++;
            }
            if (bcbmCode.isBig == 0) {
                if (xiao >= 1) {
                    ben = ben.subtract(bettingAmount);
                    isBonus = "未中奖";
                } else {
                    isBonus = "未下注";
                }
                da = 0;
                xiao++;
            }
            System.out.println(ben.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bettingAmount.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + bonus.setScale(0, BigDecimal.ROUND_HALF_UP) + " - " + isBonus + " - " + map.get("issue").toString() + " - " + openNumber + (openNumber.length() == 1 ? " " : "") + " - " + bcbmCode.name + " - " + da + " - " + xiao);
        }
    }


    public static void main(String[] args) {
        List<Map> mapList = tenantOpenMessageMapper.getGameOpenNumber();
        //List<String> mapList = FileUtil.readFile("/Users/duke/Downloads/宝马.txt");
        System.out.println("总数据量：" + mapList.size());
        int[] dalong = new int[50]; //小车标长龙
        int[] xiaolong = new int[50]; //大车标长龙
        int[] count = new int[8];//8个车标出现次数统计
        int xiaoCountTemp = 0;
        int daCountTemp = 0;
        for (Map map : mapList) {
        //for (String map : mapList) {
            String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getNameByNum(openNumber);
            //String openNumber = map.split(",")[1];
            //BcbmCodeEmu bcbmCode = BcbmCodeEmu.getBcbmBynameN(openNumber);
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            count[bcbmCode.ordinal()]++;
            if (bcbmCode.isBig == 1) {
                daCountTemp++;
                //dalong[daCountTemp - 1]++;
                if (xiaoCountTemp != 0)
                    xiaolong[xiaoCountTemp - 1]++;
                xiaoCountTemp = 0;
            }
            if (bcbmCode.isBig == 0) {
                xiaoCountTemp++;
                //xiaolong[xiaoCountTemp - 1]++;
                if (daCountTemp != 0)
                    dalong[daCountTemp - 1]++;
                daCountTemp = 0;
            }
            System.out.println(map.get("gmtCreated").toString() + " - " + map.get("issue").toString() + " - " + openNumber + (openNumber.length() == 1 ? " " : "") + " - " + bcbmCode.name + " - " + daCountTemp + " - " + xiaoCountTemp);
        }
        System.out.println("开大长龙长度：" + print(dalong));
        System.out.println("开小长龙长度：" + print(xiaolong));

        BcbmCodeEmu[] bcbmCodeEmus = BcbmCodeEmu.values();
        for (int i = 0; i < bcbmCodeEmus.length; i++) {
            System.out.print(bcbmCodeEmus[i].name + ":" + count[i] + ", ");
        }
    }

    public static String print(int[] data) {
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < data.length; i++) {
            if (flag && data[data.length - i - 1] == 0) {
                continue;
            }
            if (flag)
                flag = false;
            sb.insert(0, (data.length - i) + ":" + data[data.length - i - 1] + ", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    @Test(description = "测试")
    public static void abcdefg() {
        System.out.println(print(new int[]{100, 20, 0, 0, 9, 8, 7, 0, 0, 1, 0, 0, 0}));


    }

    @Test(description = "本地开奖数据分析")
    public static void test01() {
        List<String> names = FileUtil.readFile("/Users/duke/Downloads/大众(大).txt");
        System.out.println("总数据：" + names.size());
        int[] dalong = new int[150];
        int[] xiaolong = new int[150];
        int xiao = 0;
        int da = 0;
        int[] count = new int[8];//每个号的情况
        for (String openName : names) {
            //String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getBcbmBynameN(openName);
            count[bcbmCode.ordinal()]++;
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            if (bcbmCode.isBig == 1) {
                xiao = 0;
                da++;
                dalong[da - 1]++;
                //if (da > 3) {
                //    dalong[da - 3]++;
                //}
            }
            if (bcbmCode.isBig == 0) {
                da = 0;
                xiao++;
                xiaolong[xiao - 1]++;
                //if (xiao > 3) {
                //    xiaolong[xiao - 3]++;
                //}
            }
        }
        System.out.println("开车标大长龙长度：" + Arrays.toString(dalong));
        System.out.println("开小车标长龙长度：" + Arrays.toString(xiaolong));
        //System.out.println(Arrays.toString(count));
        BcbmCodeEmu[] bcbmCodeEmus = BcbmCodeEmu.values();
        for (int i = 0; i < bcbmCodeEmus.length; i++) {
            System.out.print(bcbmCodeEmus[i].name + ":" + count[i] + ", ");
        }


    }

}
