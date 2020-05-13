package com.dafagame.dafaGameUtils.cardLogic;

import com.dafagame.dafaGameUtils.cardLogic.ZjhCard;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCards;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCardsType;
import com.dafagame.dafaGameUtils.cardLogic.ZjhCardsTypeGetter;
import com.dafagame.utils.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZjhLogic {
    public static List<Integer> cardsTypePro = Arrays.asList(650, 865, 965, 995, 997, 1000);

    public static List<ZjhCard> lotter() {
        List<ZjhCard> cards = new ArrayList<>(Arrays.asList(ZjhCard.values()));

        Collections.shuffle(cards);

        int index = RandomUtil.random(35);
        return cards.subList(index, index + 15);
    }

    public static List<ZjhCards> newLottery(int robotCount, int realCount, boolean isKill) {
        List<ZjhCard> cards = new ArrayList<>(Arrays.asList(ZjhCard.values()));

        List<Integer> cardType = new ArrayList<>();
//        for (int i = 0; i < 5; ++i){
//            cardType.add(4);
//        }

        if (isKill) {
            for (int i = 0; i < realCount + 1; ++i) {
                int pro = RandomUtil.random(650, 1000);
                for (int j = 1; j < cardsTypePro.size(); ++j) {
                    if (pro <= cardsTypePro.get(j)) {
                        cardType.add(j);
                        break;
                    }
                }
            }
            if (robotCount - 1 > 0) {
                for (int i = 0; i < robotCount - 1; ++i) {
                    int pro = RandomUtil.random(1000);
                    for (int j = 0; j < cardsTypePro.size(); ++j) {
                        if (pro < cardsTypePro.get(j)) {
                            cardType.add(j);
                            break;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < robotCount + realCount; ++i) {
                int pro = RandomUtil.random(1000);
                for (int j = 0; j < cardsTypePro.size(); ++j) {
                    if (pro < cardsTypePro.get(j)) {
                        cardType.add(j);
                        break;
                    }
                }
            }
        }

        cardType.sort((a, b) -> {
            if (!b.equals(a)) {
                return b > a ? 1 : -1;
            }
            return 0;
        });
        List<ZjhCards> sendCards = new ArrayList<>();
        List<ZjhCard> needCards;
        for (int i : cardType) {
            switch (i) {
                //豹子
                case 5:
                    needCards = getBaoZhi(cards);
                    break;
                //顺金
                case 4:
                    needCards = getShunJin(cards);
                    break;
                //金花
                case 3:
                    needCards = getJinHua(cards);
                    break;
                //顺子
                case 2:
                    needCards = getShunZhi(cards);
                    break;
                //对子
                case 1:
                    //单张
                case 0:
                    needCards = getNeedTypeByRand(cards, i);
                    break;
                default:
                    needCards = new ArrayList<>();
                    break;
            }

            cards.removeAll(needCards);
            sendCards.add(new ZjhCards(needCards));
        }

        return sendCards;
    }

    public static List<ZjhCard> getNeedTypeByRand(List<ZjhCard> cards, int type) {
        List<ZjhCard> resultList = new ArrayList<>();
        for (int i = 0; i < 200; ++i) {
            resultList = RandomUtil.randomList(cards, 3);

            ZjhCardsType cardsType = ZjhCardsTypeGetter.geCardsType(resultList);

            if ((type == 1 && cardsType == ZjhCardsType.DUI_ZI)
                    || (type == 0 && cardsType == ZjhCardsType.DAN_ZHAN)) {
                break;
            }
        }

        return resultList;
    }

    public static List<ZjhCard> getShunZhi(List<ZjhCard> cards) {
        List<ZjhCard> resultList = new ArrayList<>();

        for (int i = 0; i < 200; ++i) {
            ZjhCard c0 = cards.get(RandomUtil.random(cards.size()));

            List<CardSuit> suits = new ArrayList<>(Arrays.asList(CardSuit.values()));
            suits.remove(c0.type);

            ZjhCard c1 = null;
            ZjhCard c2 = null;

            boolean f = RandomUtil.random(2) == 0;

            Collections.shuffle(suits);

            for (CardSuit suit : suits) {
                int index;
                if (f) {
                    index = (c0.ordinal() + 1) % 13 + (suit.ordinal() * 13);
                } else {
                    int o = (c0.ordinal() - 1) < 0 ? 51 : (c0.ordinal() - 1);
                    index = o % 13 + (suit.ordinal() * 13);
                }
                ZjhCard f1 = ZjhCard.values()[index];
                if (cards.contains(f1)) {
                    c1 = f1;
                    break;
                }
            }

            if (c1 == null) {
                continue;
            }

            Collections.shuffle(suits);

            for (CardSuit suit : suits) {
                int index;
                if (f) {
                    index = (c0.ordinal() + 2) % 13 + (suit.ordinal() * 13);
                } else {
                    int o = (c0.ordinal() - 2) < 0 ? 50 : (c0.ordinal() - 2);
                    index = o % 13 + (suit.ordinal() * 13);
                }
                ZjhCard f2 = ZjhCard.values()[index];
                if (cards.contains(f2)) {
                    c2 = f2;
                    break;
                }
            }

            if (c2 == null) {
                continue;
            }

            resultList.add(c0);
            resultList.add(c1);
            resultList.add(c2);

            if (ZjhCardsTypeGetter.isShunZi(resultList)) {
                break;
            }
            resultList.clear();
        }

        if (resultList.isEmpty()) {
            return RandomUtil.randomList(cards, 3);
        }

        return resultList;
    }

    public static List<ZjhCard> getJinHua(List<ZjhCard> cards) {
        List<ZjhCard> resultList = new ArrayList<>();

        List<CardSuit> suitList = new ArrayList<>(Arrays.asList(CardSuit.values()));
        Collections.shuffle(suitList);

        for (CardSuit cardSuit : suitList) {
            resultList.clear();
            for (ZjhCard card : cards) {
                if (card.type == cardSuit) {
                    resultList.add(card);
                }
            }

            if (resultList.size() >= 3) {
                break;
            }
        }

        if (resultList.size() < 3) {
            return RandomUtil.randomList(cards, 3);
        }

        if (resultList.size() == 3) {
            return resultList;
        }

        ZjhCard c0 = resultList.remove(RandomUtil.random(resultList.size()));
        ZjhCard c1 = resultList.remove(RandomUtil.random(resultList.size()));
        ZjhCard c2 = resultList.remove(RandomUtil.random(resultList.size()));

        resultList.clear();

        resultList.add(c0);
        resultList.add(c1);
        resultList.add(c2);

        return resultList;
    }

    public static List<ZjhCard> getShunJin(List<ZjhCard> cards) {
        List<ZjhCard> resultList = new ArrayList<>();

        for (int i = 0; i < 200; ++i) {
            int randCard = RandomUtil.random(13);

            List<ZjhCard> arr = new ArrayList<>();

            ZjhCard c0 = ZjhCard.values()[randCard];
            ZjhCard c1 = ZjhCard.values()[randCard + 13];
            ZjhCard c2 = ZjhCard.values()[randCard + 26];
            ZjhCard c3 = ZjhCard.values()[randCard + 39];

            if (cards.contains(c0)) {
                arr.add(c0);
            }
            if (cards.contains(c1)) {
                arr.add(c1);
            }
            if (cards.contains(c2)) {
                arr.add(c2);
            }
            if (cards.contains(c3)) {
                arr.add(c3);
            }

            Collections.shuffle(arr);

            for (ZjhCard card : arr) {
                ZjhCard f1 = ZjhCard.values()[(card.ordinal() + 1) % 13 + (card.type.ordinal() * 13)];
                ZjhCard f2 = ZjhCard.values()[(card.ordinal() + 2) % 13 + (card.type.ordinal() * 13)];

                int o = (card.ordinal() - 1) < 0 ? 51 : (card.ordinal() - 1);
                ZjhCard b1 = ZjhCard.values()[o % 13 + (card.type.ordinal() * 13)];

                o = (card.ordinal() - 2) < 0 ? 50 : (card.ordinal() - 2);
                ZjhCard b2 = ZjhCard.values()[o % 13 + (card.type.ordinal() * 13)];

                ZjhCardsType fType = ZjhCardsType.DAN_ZHAN;
                ZjhCardsType bType = ZjhCardsType.DAN_ZHAN;
                if (cards.contains(f1) && cards.contains(f2)) {
                    fType = ZjhCardsTypeGetter.geCardsType(Arrays.asList(card, f1, f2));
                }

                if (cards.contains(b1) && cards.contains(b2)) {
                    bType = ZjhCardsTypeGetter.geCardsType(Arrays.asList(card, b1, b2));
                }

                if (fType != ZjhCardsType.SHUN_JIN && bType != ZjhCardsType.SHUN_JIN) {
                    continue;
                }

                resultList.add(card);

                if (fType == ZjhCardsType.SHUN_JIN && bType == ZjhCardsType.SHUN_JIN) {
                    if (RandomUtil.random(2) == 0) {
                        resultList.add(f1);
                        resultList.add(f2);
                    } else {
                        resultList.add(b1);
                        resultList.add(b2);
                    }
                } else if (fType == ZjhCardsType.SHUN_JIN) {
                    resultList.add(f1);
                    resultList.add(f2);
                } else {
                    resultList.add(b1);
                    resultList.add(b2);
                }

                break;
            }

            if (!resultList.isEmpty() && ZjhCardsTypeGetter.isShunJin(resultList)) {
                break;
            }
            resultList.clear();
        }

        if (resultList.isEmpty()) {
            resultList = RandomUtil.randomList(cards, 3);
        }

        return resultList;
    }

    public static List<ZjhCard> getBaoZhi(List<ZjhCard> cards) {
        List<ZjhCard> resultList = new ArrayList<>();

        for (int i = 0; i < 200; ++i) {
            int randCard = RandomUtil.random(13);

            ZjhCard[] arr = new ZjhCard[4];

            ZjhCard c0 = ZjhCard.values()[randCard];
            ZjhCard c1 = ZjhCard.values()[randCard + 13];
            ZjhCard c2 = ZjhCard.values()[randCard + 26];
            ZjhCard c3 = ZjhCard.values()[randCard + 39];

            int count = 0;

            if (cards.contains(c0)) {
                ++count;
                arr[0] = c0;
            }
            if (cards.contains(c1)) {
                ++count;
                arr[1] = c1;
            }
            if (cards.contains(c2)) {
                ++count;
                arr[2] = c2;
            }
            if (cards.contains(c3)) {
                ++count;
                arr[3] = c3;
            }

            if (count < 3) {
                continue;
            }

            if (count == 4) {
                int randOutSuit = RandomUtil.random(4);
                arr[randOutSuit] = null;
            }

            for (ZjhCard c : arr) {
                if (c != null) {
                    resultList.add(c);
                }
            }

            break;
        }

        if (resultList.isEmpty()) {
            return RandomUtil.randomList(cards, 3);
        }

        return resultList;
    }

    public static void main(String[] args) {

        List<ZjhCards> list;
        for (int i = 0; i < 100000; ++i) {
            list = newLottery(4, 1, true);
            if (list.size() != 5) {
                System.out.println(list);
                break;
            }
            for (ZjhCards c : list) {
                System.out.print(c.type.name() + ",");
//               if(c.midCard == c.maxCard || c.midCard == c.minCard){
//                   System.out.println("error");
//               }
            }
            System.out.println(i + "," + list.size());
        }


//
//        ZjhCard card = ZjhCard.FANG_KUAI_A;
//
//        int index =  (card.ordinal() + 2) % 13 + (card.type.ordinal() * 13);
//
//        int a = (card.ordinal() - 2) < 0 ? 50 : (card.ordinal() - 1);
//
//        int b =  a % 13 + (card.type.ordinal() * 13);
//        ZjhCard z = ZjhCard.values()[b];
//
//
//        Collections.shuffle(cards);
//        List<ZjhCard> list;
//        for (int i = 0; i < 100; ++i){
//            list = getNeedTypeByRand(cards, 0);
//            System.out.println(list.get(0).name() + "," + list.get(1).name() + "," + list.get(2).name());
//            cards.removeAll(list);
//            List<ZjhCard> c = RandomUtil.randomList(cards, 3);
//            ZjhCardsType t = ZjhCardsTypeGetter.geCardsType(c);
//            if(t == ZjhCardsType.DUI_ZI){
//                System.out.println(i);
//                break;
//            }
//        }
    }
}
