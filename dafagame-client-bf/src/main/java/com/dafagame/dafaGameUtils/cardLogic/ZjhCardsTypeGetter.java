/**
 * Copyright (c) 2015, http://www.wuleyou.com/ All Rights Reserved.
 */
package com.dafagame.dafaGameUtils.cardLogic;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Description:炸金花牌型获取
 */
public class ZjhCardsTypeGetter {

    private ZjhCardsTypeGetter() {
    }

    /**
     * @param cards
     * @return
     * @Description:获取牌型 BAO_ZI(6),        //豹子
     * SHUN_JIN(5),	//顺金
     * JIN_HUA(4),		//金花
     * SHUN_ZI(3),		//顺子
     * DUI_ZI(2),		//对子
     * DAN_ZHAN(1),	//单张
     * ER_SAN_WU(0),	//235
     */
    public static ZjhCardsType geCardsType(List<ZjhCard> cards) {
        if (isBaoZi(cards)) {
            return ZjhCardsType.BAO_ZI;
        } else if (isShunJin(cards)) {
            return ZjhCardsType.SHUN_JIN;
        } else if (isJinHua(cards)) {
            return ZjhCardsType.JIN_HUA;
        } else if (isShunZi(cards)) {
            return ZjhCardsType.SHUN_ZI;
        } else if (isDuiZi(cards)) {
            return ZjhCardsType.DUI_ZI;
        } else if (isErSanWu(cards)) {
            return ZjhCardsType.ER_SAN_WU;
        } else if (isDanZhan(cards)) {
            return ZjhCardsType.DAN_ZHAN;
        }

        throw new RuntimeException("不能识别牌型的牌[" + cards + "]");
    }

    /**
     * @param cards
     * @return
     * @Description:
     */
    private static boolean isBaoZi(List<ZjhCard> cards) {

        return cards.get(0).num == cards.get(1).num && cards.get(1).num == cards.get(2).num;
    }

    /**
     * @param cards
     * @return
     * @Description:是否顺金
     */
    private static boolean isShunJin(List<ZjhCard> cards) {

        return isShunZi(cards) && isJinHua(cards);
    }

    /**
     * @param cards
     * @return
     * @Description:是否顺子
     */
    private static boolean isShunZi(List<ZjhCard> cards) {
        TreeSet<Integer> cardNums = new TreeSet<>();
        TreeSet<Integer> cardPowers = new TreeSet<>();
        for (ZjhCard card : cards) {
            cardNums.add(card.num);
            cardPowers.add(card.power);
        }
        //A23
        Integer min = cardNums.first();
        Integer middle = cardNums.higher(min);
        Integer max = cardNums.last();

        Integer minPower = cardPowers.first();
        Integer middlePower = cardPowers.higher(minPower);
        Integer maxPower = cardPowers.last();

        // 要满足AKQ类型
        return (min + 1 == middle && middle + 1 == max) || (minPower + 1 == middlePower && middlePower + 1 == maxPower);
        //return (minPower + 1 == middlePower && middlePower + 1 == maxPower);
    }

    /**
     * @param cards
     * @return
     * @Description:是否顺金
     */
    private static boolean isJinHua(List<ZjhCard> cards) {

        return cards.get(0).type == cards.get(1).type && cards.get(1).type == cards.get(2).type;
    }

    /**
     * @param cards
     * @return
     * @Description:是否对子
     */
    private static boolean isDuiZi(List<ZjhCard> cards) {
        Multiset<Integer> cardNums = HashMultiset.create();
        for (ZjhCard card : cards) {
            cardNums.add(card.num);
        }

        return cardNums.elementSet().size() == 2;
    }


    /**
     * @param cards
     * @return
     * @Description:是否单张
     */
    private static boolean isDanZhan(List<ZjhCard> cards) {
        Multiset<Integer> cardNums = HashMultiset.create();
        for (ZjhCard card : cards) {
            cardNums.add(card.num);
        }

        return cardNums.elementSet().size() == 3;
    }

    /**
     * @param cards
     * @return
     * @Description:是否二三五，不同花色
     */
    private static boolean isErSanWu(List<ZjhCard> cards) {
        Multiset<Integer> cardNums = HashMultiset.create();
        Multiset<CardSuit> cardSuits = HashMultiset.create();
        for (ZjhCard card : cards) {
            cardNums.add(card.num);
            cardSuits.add(card.type);
        }

        Set<Integer> elements = cardNums.elementSet();
        return elements.contains(2) && elements.contains(3) && elements.contains(5) && cardSuits.elementSet().size() >= 2;
    }

    public static void main(String[] args) {

        List<ZjhCard> cards = new ArrayList<>();
        cards.add(ZjhCard.HEI_TAO_WU);
        cards.add(ZjhCard.FANG_KUAI_ER);
        cards.add(ZjhCard.FANG_KUAI_SAN);

        List<ZjhCard> cards2 = new ArrayList<>();
        cards2.add(ZjhCard.MEI_HUA_WU);
        cards2.add(ZjhCard.HEI_TAO_ER);
        cards2.add(ZjhCard.HEI_TAO_SAN);

        ZjhCards c1 = new ZjhCards(cards);
        ZjhCards c2 = new ZjhCards(cards2);

        boolean bb = ZjhCardsTypeComparator.compare(c1, c2);
        System.out.println(bb);
    }

    /**
     * duke
     * 比较牌的大小
     * @param card1 六张牌的大小
     * */

    public static void comparaCard(ZjhCard card1, ZjhCard card2, ZjhCard card3, ZjhCard card4, ZjhCard card5, ZjhCard card6) {
        List<ZjhCard> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        List<ZjhCard> cards2 = new ArrayList<>();
        cards2.add(card4);
        cards2.add(card5);
        cards2.add(card6);

        ZjhCards c1 = new ZjhCards(cards);
        ZjhCards c2 = new ZjhCards(cards2);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            ZjhCard card = cards.get(i);
            sb.append(card.type.type + card.num);
        }
        System.out.print(getType(c1.type.power) + "：" + sb.toString()+" > ");
        for (int i = 0; i < cards.size(); i++) {
            ZjhCard card = cards.get(i);
            sb.append(card.type.type + card.num);
        }
        sb.setLength(0);
        for (int i = 0; i < cards2.size(); i++) {
            ZjhCard card = cards2.get(i);
            sb.append(card.type.type + card.num);
        }
        System.out.print(getType(c2.type.power) + "：" + sb.toString() +" = ");
        boolean bb = ZjhCardsTypeComparator.compare(c1, c2);
        System.out.println(bb);
    }

    private static String getType(int type) {
        switch (type) {
            case 0:
                return "235";
            case 1:
                return "单张";
            case 2:
                return "对子";
            case 3:
                return "顺子";
            case 4:
                return "金花";
            case 5:
                return "顺金";
            case 6:
                return "豹子";
            default:
                return "牌型错误";
        }
    }

    @Test(description = "比较牌")
    public static void test01() {
        //comparaCard(ZjhCard.FANG_KUAI_ER, ZjhCard.FANG_KUAI_A, ZjhCard.HEI_TAO_SAN, ZjhCard.HEI_TAO_K, ZjhCard.HEI_TAO_A, ZjhCard.FANG_KUAI_Q);
        ////
        //comparaCard(ZjhCard.FANG_KUAI_ER, ZjhCard.FANG_KUAI_WU, ZjhCard.HEI_TAO_SAN, ZjhCard.HEI_TAO_A, ZjhCard.HEI_TAO_A, ZjhCard.FANG_KUAI_A);
        ////
        //comparaCard(ZjhCard.FANG_KUAI_ER, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_SAN, ZjhCard.HEI_TAO_A, ZjhCard.HEI_TAO_A, ZjhCard.FANG_KUAI_A);

        //单张3张牌点数一样,比较最大牌的花色
        comparaCard(ZjhCard.HONG_TAO_LIU, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_SAN, ZjhCard.HEI_TAO_WU, ZjhCard.MEI_HUA_LIU, ZjhCard.HONG_TAO_SAN);
        comparaCard(ZjhCard.HONG_TAO_LIU, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_SAN, ZjhCard.HEI_TAO_WU, ZjhCard.HEI_TAO_LIU, ZjhCard.HONG_TAO_SAN);
        //单张，最大牌点数一样，比较第二大牌的点数
        comparaCard(ZjhCard.HONG_TAO_LIU, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_ER, ZjhCard.HEI_TAO_SAN, ZjhCard.HEI_TAO_LIU, ZjhCard.FANG_KUAI_ER);
        comparaCard(ZjhCard.HONG_TAO_LIU, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_SAN, ZjhCard.HEI_TAO_WU, ZjhCard.HEI_TAO_LIU, ZjhCard.MEI_HUA_A);
        comparaCard(ZjhCard.HONG_TAO_LIU, ZjhCard.FANG_KUAI_WU, ZjhCard.FANG_KUAI_SAN, ZjhCard.HEI_TAO_WU, ZjhCard.HEI_TAO_LIU, ZjhCard.MEI_HUA_ER);
    }

    @Test(description = "牌型测试power")
    public static void test02() {
        List<ZjhCard> cards = new ArrayList<>();
        cards.add(ZjhCard.FANG_KUAI_ER);
        cards.add(ZjhCard.FANG_KUAI_A);
        cards.add(ZjhCard.HEI_TAO_SAN);
        ZjhCards c1 = new ZjhCards(cards);
        System.out.println(c1.type.power);
    }


}
