package dafagame.test;

import com.alibaba.fastjson.JSONObject;
import pers.dafagame.enums.AwardType;
import pers.dafagame.enums.PrizeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcadeKillRate {

    //private static BigDecimal POINT15 = new BigDecimal(0.15);
    //private static BigDecimal POINT2 = new BigDecimal(0.2);
    //private static BigDecimal POINT1POINT15 = new BigDecimal(1.15);
    //private static BigDecimal TWO = new BigDecimal(2);
    //private static BigDecimal MULTI = new BigDecimal(100000);
    //private static BigDecimal BASE = new BigDecimal(0.01);
    //private static BigDecimal POINT5 = new BigDecimal(0.5);

    public static BigDecimal POINT5 = new BigDecimal("0.5");
    public static BigDecimal TWO = new BigDecimal("2");
    public static BigDecimal BASE = new BigDecimal("0.01");
    public static BigDecimal MULTI = new BigDecimal("100000");
    public static BigDecimal HUNDRED = new BigDecimal("100");
    public static BigDecimal NEGATIVE1 = new BigDecimal("-1");
    public static BigDecimal POINT1 = new BigDecimal("0.1");
    public static BigDecimal POINT2 = new BigDecimal("0.2");
    public static BigDecimal POINT1POINT15 = new BigDecimal("1.15");
    public static BigDecimal POINT15 = new BigDecimal("0.15");

    public static void main(String[] args) {
        //sgj();
        //shz();
        //getProbability(new BigDecimal("-0.2"), new BigDecimal("2.5"));
        function01();
    }

    public static int function01() {
        try {
            //ArcadeConfig config = KillTaskServiceImpl.arcadeConfigMap.get(tenantCode.txt);
            //if(config == null){
            //    logger.info(LogUtil.info("站点配置为Null默认空奖"));
            //    return AwardType.thank.ordinal();
            //}

            //测试数据
//        String s = "{\"system\":{\"result\":[2,1.8,1.5,1.2,1,0.8,0.6,0.4,0.2,0],\"target\":[-0.2,-0.15,-0.1,-0.05,0,0.05,0.2,0.5,0.8,1]},\"player\":{\"result\":[2,1,0.5,0.2,0.1,-0.1,-0.2,-0.5,-0.8,-1],\"target\":[0.5,0.3,0.1,0.05,0,0,-0.05,-0.1,-0.2,-0.3]}}";
//        ArcadeConfig config = new ArcadeConfig();
//        config.setProfit(new BigDecimal("2.5"));
//        config.setStandardStock(100000);
//        config.setCurrentStock(BigDecimal.valueOf(100004.9));
//        config.setSetting(s);
//        config.setStockType(1);
//
//        JSONObject jsonObject = JSON.parseObject(config.getSetting());
//
//        JSONObject system = jsonObject.getJSONObject("system");
//        JSONObject player = jsonObject.getJSONObject("player");
//
//        config.setSystemResult(JSONObject.parseArray(system.getString("result"), BigDecimal.class));
//        config.setSystemTarget(JSONObject.parseArray(system.getString("target"), BigDecimal.class));
//
//        config.setPlayerResult(JSONObject.parseArray(player.getString("result"), BigDecimal.class));
//        config.setPlayerTarget(JSONObject.parseArray(player.getString("target"), BigDecimal.class));

///////////////////////////////////////////

            //BigDecimal systemRatio = ArcadeKillUtils.getSystemRatio(playerRatio, config);
            BigDecimal systemRatio = new BigDecimal("-0.2");

            Map<Integer, BigDecimal> proMap = getProbability(systemRatio, new BigDecimal("2.5"));
            int thank = proMap.get(AwardType.thank.ordinal()).intValue();
            int ordinary = proMap.get(AwardType.ordinary.ordinal()).intValue();
            int special = proMap.get(AwardType.special.ordinal()).intValue();

            System.out.println("------------------------------------------------------------------");
            System.out.println("thankRate = " + thank);
            System.out.println("普通奖概率公式=（1-A/B）*C = " + ordinary);
            System.out.println("特殊奖的概率：=A/B*C = " + special);

            int total = thank + ordinary + special;
            System.out.println(total);

            int rand = 2;

            List<Map.Entry<Integer, BigDecimal>> list = sored(proMap);
            System.out.println(JSONObject.toJSONString(proMap));
            BigDecimal front = BigDecimal.ZERO;
            for (int i = 0; i < list.size(); ++i) {
                Map.Entry<Integer, BigDecimal> entry = list.get(i);
                front = front.add(entry.getValue());
                entry.setValue(front);
            }
            int lotteryType = 0;

            for (Map.Entry<Integer, BigDecimal> entry : list) {
                if (rand < entry.getValue().intValue()) {
                    lotteryType = entry.getKey();
                    break;
                }
            }
            System.out.println(JSONObject.toJSONString(proMap) + "开奖" + lotteryType + "玩家盈率" + "1" + "系统盈率结果" + systemRatio.toString());
            return lotteryType;
        } catch (Exception e) {
            return AwardType.thank.ordinal();
        }
    }

    public static Map<Integer, BigDecimal> getProbability(BigDecimal systemRatio, BigDecimal hopeRatio) {
        Map<Integer, BigDecimal> resultPro = new HashMap<>();
        hopeRatio = hopeRatio.divide(HUNDRED, 4, RoundingMode.DOWN);

        //空奖
        BigDecimal thankPro = hopeRatio.add(systemRatio).subtract(POINT5).multiply(TWO).add(BASE);
        if (thankPro.compareTo(BigDecimal.ONE) > 0) {
            thankPro = BigDecimal.ONE;
        } else if (thankPro.compareTo(BASE) < 0) {
            thankPro = BASE;
        }
        resultPro.put(AwardType.thank.ordinal(), thankPro.multiply(MULTI));


        //普通奖
        BigDecimal c = BigDecimal.ONE.subtract(thankPro);
        BigDecimal b = AwardType.special.odds.subtract(AwardType.ordinary.odds);

        BigDecimal a1 = new BigDecimal("8")
                .multiply(BigDecimal.ONE.subtract(hopeRatio).subtract(systemRatio));
        BigDecimal a;
        if (c.compareTo(BigDecimal.ZERO) == 0) {
            a = BigDecimal.ZERO.subtract(AwardType.ordinary.odds);
        } else {
            a = a1.divide(c, 6, RoundingMode.DOWN).subtract(AwardType.ordinary.odds);
        }
        //b 一定不是0
        BigDecimal ordinaryPro = BigDecimal.ONE.subtract(a.divide(b, 6, RoundingMode.DOWN)).multiply(c);

        if (ordinaryPro.compareTo(BigDecimal.ONE) > 0) {
            ordinaryPro = BigDecimal.ONE;
        } else if (ordinaryPro.compareTo(BigDecimal.ZERO) < 0) {
            ordinaryPro = BigDecimal.ZERO;
        }

        resultPro.put(AwardType.ordinary.ordinal(), ordinaryPro.multiply(MULTI));

        BigDecimal specialPro = a.divide(b, 6, RoundingMode.DOWN).multiply(c);

        if (specialPro.compareTo(BigDecimal.ONE) > 0) {
            specialPro = BigDecimal.ONE;
        } else if (specialPro.compareTo(BigDecimal.ZERO) < 0) {
            specialPro = BigDecimal.ZERO;
        }

        resultPro.put(AwardType.special.ordinal(), specialPro.multiply(MULTI));

        System.out.println("------------------------------------------------------------------");
        System.out.println("thankRate = " + thankPro);
        System.out.println("普通奖概率公式=（1-A/B）*C = " + ordinaryPro);
        System.out.println("特殊奖的概率：=A/B*C = " + specialPro);

        return resultPro;
    }

    /**
     * 水果机
     */
    public static void sgj() {
        BigDecimal hopeRatio = new BigDecimal("0.025");
        hopeRatio = hopeRatio.divide(HUNDRED, 4, RoundingMode.DOWN);
        System.out.println(hopeRatio);
        BigDecimal systemRatio = new BigDecimal("-0.2");

        //空奖（谢谢）的概率：=（预期盈率+系统盈率-0.5）*2+保底值0.01
        //最终结果，下限为0.01，上限为1
        BigDecimal thankPro = hopeRatio.add(systemRatio).subtract(POINT5).multiply(TWO).add(BASE);
        if (thankPro.compareTo(BigDecimal.ONE) > 0) {
            thankPro = BigDecimal.ONE;
        } else if (thankPro.compareTo(BASE) < 0) {
            thankPro = BASE;
        }

        //C=1-空奖概率
        BigDecimal c = BigDecimal.ONE.subtract(thankPro);
        //B=特殊奖赔率-普通奖赔率
        BigDecimal b = AwardType.special.odds.subtract(AwardType.ordinary.odds);
        //A=8*（1-预期盈率-系统盈率）/C - 普通奖赔率
        BigDecimal a1 = new BigDecimal("8")
                .multiply(BigDecimal.ONE.subtract(hopeRatio).subtract(systemRatio));
        BigDecimal a;
        if (c.compareTo(BigDecimal.ZERO) == 0) {
            a = BigDecimal.ZERO.subtract(AwardType.ordinary.odds);
        } else {
            a = a1.divide(c, 6, RoundingMode.DOWN).subtract(AwardType.ordinary.odds);
        }

        //普通奖概率公式=（1-A/B）*C
        //BigDecimal ordinaryPro = BigDecimal.ONE.subtract(a.divide(b, 6, RoundingMode.DOWN)).multiply(c);
        BigDecimal ordinaryPro = BigDecimal.ONE.subtract(a.divide(b, 6, RoundingMode.DOWN)).multiply(c);
        if (ordinaryPro.compareTo(BigDecimal.ONE) > 0) {
            ordinaryPro = BigDecimal.ONE;
        } else if (ordinaryPro.compareTo(BigDecimal.ZERO) < 0) {
            ordinaryPro = BigDecimal.ZERO;
        }

        //特殊奖的概率：=A/B*C
        BigDecimal specialPro = a.divide(b, 6, RoundingMode.DOWN).multiply(c);

        if (specialPro.compareTo(BigDecimal.ONE) > 0) {
            specialPro = BigDecimal.ONE;
        } else if (specialPro.compareTo(BigDecimal.ZERO) < 0) {
            specialPro = BigDecimal.ZERO;
        }

        System.out.println("thankRate = " + thankPro);
        System.out.println("普通奖概率公式=（1-A/B）*C = " + ordinaryPro);
        System.out.println("特殊奖的概率：=A/B*C = " + specialPro);
        System.out.println("------------------------------------------------------------------");
        System.out.println("thankRate = " + thankPro.multiply(MULTI).intValue());
        System.out.println("普通奖概率公式=（1-A/B）*C = " + ordinaryPro.multiply(MULTI).intValue());
        System.out.println("特殊奖的概率：=A/B*C = " + specialPro.multiply(MULTI).intValue());
    }

    /**
     * 水浒传
     */
    public static void shz() {
        BigDecimal hopeRatio = new BigDecimal(0.025);
        BigDecimal systemRatio = new BigDecimal(-0.2);

        BigDecimal thankPro = POINT15.add(hopeRatio.add(systemRatio).subtract(POINT2).multiply(POINT1POINT15));
        System.out.println("0-thankPro:" + thankPro);
        if (thankPro.compareTo(BigDecimal.ONE) > 0) {
            thankPro = BigDecimal.ONE;
        } else if (thankPro.compareTo(BigDecimal.ZERO) < 0) {
            thankPro = BigDecimal.ZERO;
        }


        BigDecimal c = BigDecimal.ONE.subtract(thankPro);
        BigDecimal b = PrizeType.OVERALL.odds.add(PrizeType.MARY.odds)
                .divide(TWO, 6, RoundingMode.DOWN)
                .subtract(PrizeType.ORDINARY.odds);

        BigDecimal a;
        BigDecimal a1 = new BigDecimal("9").multiply(BigDecimal.ONE.subtract(hopeRatio).subtract(systemRatio));
        if (c.compareTo(BigDecimal.ZERO) == 0) {
            a = BigDecimal.ZERO.subtract(PrizeType.ORDINARY.odds);
        } else {
            a = a1.divide(c, 6, RoundingMode.DOWN).subtract(PrizeType.ORDINARY.odds);
        }

        System.out.println("A:" + a);
        //普通奖概率公式=（1-A/B）*C
        //全盘奖概率=A/B*C/2
        //小玛丽奖概率=A/B*C/2
        //普通奖概率公式=（1-A/B）*C
        BigDecimal ordinaryPro = BigDecimal.ONE.subtract(a.divide(b, 6, RoundingMode.DOWN)).multiply(c);
        BigDecimal allAndMaryPro = a.divide(b, 6, RoundingMode.DOWN).multiply(c).divide(TWO, 6, RoundingMode.DOWN);
        if (allAndMaryPro.compareTo(BigDecimal.ONE) > 0) {
            allAndMaryPro = BigDecimal.ONE;
        } else if (allAndMaryPro.compareTo(BigDecimal.ZERO) < 0) {
            allAndMaryPro = BigDecimal.ZERO;
        }

        System.out.println("空奖概率thankPro:" + thankPro);
        System.out.println("普通奖概率公式=（1-A/B）*C = " + ordinaryPro.multiply(MULTI).intValue());
        System.out.println("全盘奖概率A/B*C/2 = " + allAndMaryPro.multiply(MULTI).intValue());
        System.out.println("小玛丽奖概率=A/B*C/2 = " + allAndMaryPro.multiply(MULTI).intValue());
    }

    public static List<Map.Entry<Integer,BigDecimal>> sored(Map<Integer, BigDecimal> map){
        //升序
        List<Map.Entry<Integer,BigDecimal>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }
}
