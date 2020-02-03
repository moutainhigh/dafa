package com.dafagame.netty;

import com.alibaba.fastjson.JSONArray;
import com.dafagame.config.ZjhConfig;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCard;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCards;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCardsType;
import com.dafagame.enums.Action;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

@Data
public class RobotLogic {

    public double totalBet;                         //房间总下注
    public double selfBet;                     //各玩家下注数目
    public int seat;                                //自己的座位
    public int[] action = new int[5];
    public ZjhCards handCardData;                    //用户数据

    private ZjhConfig zjhConfig;

    public RobotLogic() {
        Arrays.fill(action, 0);
    }

    //参数计算
    public BigDecimal getEv(double deltaPx, double p) {
        //EV = （p -PX )* ( pool - bet ) - (1 - p + PX ) * bet
        p = p * 0.01;
        double ev = (p - deltaPx) * (totalBet - selfBet) - (1 - p + deltaPx) * selfBet;
        return BigDecimal.valueOf(ev).setScale(4, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getEvo(double deltaPx, double p, int otherPlayerCount) {
        //EV` = (1-p+Delta（PX） ) * （ pool –  ( pool-bet) / N）- ( p - Delta（PX） ) * ( pool-bet)  / N ；
        p = p * 0.01;
        double evo = ((1 - p + deltaPx) * (totalBet - (totalBet - selfBet) / otherPlayerCount))
                -
                ((p - deltaPx) * ((totalBet - selfBet) / otherPlayerCount));
        return BigDecimal.valueOf(evo).setScale(4, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getPx(int otherPlayerCount) {
        //PX = ( X1 * A + X2 * B + X3 * C + X4 * D +X5 * E)/ N；
        BigDecimal px = BigDecimal.ZERO;
        for (int i = 0; i < action.length; ++i) {
            px = px.add(new BigDecimal(Action.getValues(i) * action[i]));
        }
        Arrays.fill(action, 0);
        return px.divide(BigDecimal.valueOf(otherPlayerCount - 1), 2, RoundingMode.HALF_DOWN);
    }


    public double getP(int playerCount) {
        double p = 0;
        int incr;
        int type = handCardData.type.power;
        if (type == ZjhCardsType.ER_SAN_WU.power) {
            type = ZjhCardsType.DAN_ZHAN.power;
        }

        String key = playerCount + "" + type;
        JSONArray pConfig = zjhConfig.p.getJSONArray(key);

        switch (handCardData.type) {
            case ER_SAN_WU:
            case DAN_ZHAN:
                incr = handCardData.maxCard.power - 5;
                p = pConfig.getDouble(0) + pConfig.getDouble(1) * incr;
                break;
            case DUI_ZI:
                incr = handCardData.midCard.power - 2; //
                p = pConfig.getDouble(0) + pConfig.getDouble(1) * incr;
                break;
            case SHUN_ZI:
                incr = handCardData.minCard.power - 2;
                p = pConfig.getDouble(0) + pConfig.getDouble(1) * incr;
                break;
            case JIN_HUA:
            case SHUN_JIN:
            case BAO_ZI:
                p = pConfig.getDouble(0);
                break;
        }
        return p;
    }

    private static RobotLogic robotLogic = new RobotLogic();
    private static BigDecimal deltaPx = BigDecimal.ZERO;
    private static BigDecimal deltaEvo = BigDecimal.ZERO;
    private static BigDecimal deltaEv = BigDecimal.ZERO;

    public static void main(String[] args) {

        robotLogic.setZjhConfig(new ZjhConfig());
        robotLogic.setHandCardData(new ZjhCards(Arrays.asList(ZjhCard.HONG_TAO_SI, ZjhCard.FANG_KUAI_SAN, ZjhCard.MEI_HUA_ER)));

        robotLogic.setTotalBet(0.2);//全部注数
        robotLogic.setSelfBet(0.1); //自身下注

        int currentPlayers = 2;

        // 比牌，暗牌加注，明牌跟注，名牌加注，暗牌跟注
        getPXSelf(currentPlayers, new int[]{0, 0, 0, 0, 0}); //deltaPx
        System.out.println("deltaPx:" + deltaPx);

        double p = robotLogic.getP(currentPlayers);//
        System.out.println("p:" + p);
        BigDecimal Evo = robotLogic.getEvo(deltaPx.doubleValue(), p, currentPlayers - 1);
        System.out.println("Evo:" + Evo);
        deltaEvo = deltaEvo.add(Evo);

        BigDecimal Ev = robotLogic.getEv(deltaPx.doubleValue(), p);
        deltaEv = deltaEv.add(Ev);
        System.out.println("Ev:" + Ev);

        //Y(X) = （（p -deltaPx）* ( pool – bet )） / （( 1- p+deltaPx) * bet）
        double yx = ((p * 0.01 - deltaPx.doubleValue()) * (robotLogic.getTotalBet() - robotLogic.getSelfBet())) /
                ((1 - p * 0.01 + deltaPx.doubleValue()) * robotLogic.getSelfBet());

        System.out.println(String.format("【deltaEvo】：%s,【deltaEv】：%s,【yx】：%s", deltaEvo, deltaEv, yx));
    }


    public static void getPXSelf(int playersCount, int[] actionRing) {
        // Compare(1, 0.1),
        // DarkCardAdd(1, 0.04),
        // ClearCardFollow(1, 0.04),
        // ClearCardAdd(1, 0.06),
        // DarkCardFollow(1, 0.02),
        // 比牌，暗牌加注，明牌跟注，名牌加注，暗牌跟注
        robotLogic.action[Action.Compare.ordinal()] += actionRing[0];
        robotLogic.action[Action.DarkCardAdd.ordinal()] += actionRing[1];
        robotLogic.action[Action.ClearCardFollow.ordinal()] += actionRing[2];
        robotLogic.action[Action.ClearCardAdd.ordinal()] += actionRing[3];
        robotLogic.action[Action.DarkCardFollow.ordinal()] += actionRing[4];
        BigDecimal px = robotLogic.getPx(playersCount);
        System.out.println("px:" + px);
        deltaPx = deltaPx.add(px);
    }

    public static void getEVoSelf() {
        //EV` = (1-p+Delta（PX） ) * （ pool –  ( pool-bet) / N）- ( p - Delta（PX） ) * ( pool-bet)  / N ；

    }


}
