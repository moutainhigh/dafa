package com.dafagame.netty;

import com.dafagame.config.ZjhConfig;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCard;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCards;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCardsType;
import com.dafagame.enums.Action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

public class RobotLogic {

    public double totalBet;                         //房间总下注
    public double[] playerBets;                        //各玩家下注数目
    public int seat;                                //自己的座位
    public int[] action = new int[5];
    public ZjhCards handCardData = new ZjhCards(Arrays.asList(ZjhCard.HEI_TAO_A, ZjhCard.FANG_KUAI_ER, ZjhCard.FANG_KUAI_A));                    //用户数据

    private ZjhConfig zjhConfig;

    public RobotLogic() {
        Arrays.fill(action, 0);
    }

    //参数计算
    public BigDecimal getEv(double deltaPx, double p) {
        //EV = （p -PX )* ( pool - bet ) - (1 - p + PX ) * bet
        p = p * 0.01;
        double ev = (p - deltaPx) * (totalBet - playerBets[seat]) - (1 - p + deltaPx) * playerBets[seat];
        return BigDecimal.valueOf(ev).setScale(4, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getEvo(double deltaPx, double p, int otherPlayerCount) {
        //EV` = (1-p+Delta（PX） ) * （ pool –  ( pool-bet) / N）- ( p - Delta（PX） ) * ( pool-bet)  / N ；
        p = p * 0.01;
        double evo = ((1 - p + deltaPx) * (totalBet - (totalBet - playerBets[seat]) / otherPlayerCount))
                -
                ((p - deltaPx) * ((totalBet - playerBets[seat]) / otherPlayerCount));
        return BigDecimal.valueOf(evo).setScale(4, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getPx(int otherPlayerCount) {
        //PX = ( X1 * A + X2 * B + X3 * C + X4 * D +X5 * E)/ N；
        BigDecimal px = BigDecimal.ZERO;
        for (int i = 0; i < action.length; ++i) {
            px = px.add(new BigDecimal(Action.getValues(i) * action[i]));
        }
        Arrays.fill(action, 0);
        return px.divide(BigDecimal.valueOf(otherPlayerCount), 2, RoundingMode.HALF_DOWN);
    }


    public double getP(int playerCount) {

        double p = 0;
        int incr;
        int type = handCardData.type.power;
        if (type == ZjhCardsType.ER_SAN_WU.power) {
            type = ZjhCardsType.DAN_ZHAN.power;
        }

        String key = playerCount + "" + type;
        double[] pConfig = zjhConfig.p.get(key);

        switch (handCardData.type) {
            case ER_SAN_WU:
            case DAN_ZHAN:
                incr = handCardData.maxCard.power - 5;
                p = pConfig[0] + pConfig[1] * incr;
                break;
            case DUI_ZI:
                incr = handCardData.midCard.power - 2;
                p = pConfig[0] + pConfig[1] * incr;
                break;
            case SHUN_ZI:
                incr = handCardData.minCard.power - 2;
                p = pConfig[0] + pConfig[1] * incr;
                break;
            case JIN_HUA:
            case SHUN_JIN:
            case BAO_ZI:
                p = pConfig[0];
                break;
        }
        return p;
    }

    private static RobotLogic robotLogic = new RobotLogic();
    private static BigDecimal deltaPx = BigDecimal.ZERO;

    public static void main(String[] args) {

        int[] actionRing = {1, 1, 1, 1, 1};
        getPXSelf(3, actionRing);


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
        deltaPx.add(px);

        int count = 1;
        robotLogic.getP(count);


    }

    public static void getEVoSelf() {
        //EV` = (1-p+Delta（PX） ) * （ pool –  ( pool-bet) / N）- ( p - Delta（PX） ) * ( pool-bet)  / N ；

    }


}
