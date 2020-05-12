package pers.dafagame.cardLogics.brnn;

//import com.dafagame.config.BrnnRoundConfig;
//import com.dafagame.utils.RandomUtil;
//import com.dafagame.utils.RandomUtils;

import java.util.*;

//import static com.dafagame.constant.CommonConstant.*;

public class BrnnLogic {
    /**
     * @Description:获取牌型的同时将提示牌型计算出来
     * @param cards
     * @param
     * @return
     */
    //获取牌型的同时 排序 前三张是凑整的牌
    public static BrnnCardsType getCardsType(List<BrnnCard> cards) {
        if (isWuXiao(cards)) {
            return BrnnCardsType.WU_XIAO;
        } else if (isSiZha(cards)) {
            return BrnnCardsType.SI_ZHA;
        } else if (isWuHua(cards)) {
            return BrnnCardsType.WU_HUA;
        } else if (isSiHua(cards)) {
            return BrnnCardsType.SI_HUA;
        } else {
            return getNiuNiuType(cards);
        }
    }

    /**
     * @Description:是否是五小,五小：5张牌都小于5,并且全部加起来小于或等于10有王也算五小牛
     * @param cards
     */
    public static boolean isWuXiao(List<BrnnCard> cards) {
        int totalNum = 0;
        for (BrnnCard card : cards) {
            if (card.num >= 5) {
                return false;
            }
            totalNum += (card == BrnnCard.DA_WANG || card == BrnnCard.XIAO_WANG) ? 1 : card.num;
        }

        if (totalNum <= 10) {
            return true;
        }

        return false;
    }

    /**
     * @Description:是否是五花,五花：5张牌全为花（如Q，J，J，Q，K
     * @param cards
     * @return
     */
    public static boolean isWuHua(List<BrnnCard> cards) {
        for (BrnnCard card : cards) {
            if (card.power < BrnnCard.HEI_TAO_J.power) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Description:是否是四花,四花：5张牌中一张为10，另外4张为花[指J、Q、K]（如10，J，J，Q，K）
     * @param cards
     * @return
     */
    public static boolean isSiHua(List<BrnnCard> cards) {
        // 10牌的个数
        int cardTenNum = 0;

        for (BrnnCard card : cards) {
            if (card.power == BrnnCard.HEI_TAO_SHI.power) {
                cardTenNum += 1;
            } else if (card.power < BrnnCard.HEI_TAO_SHI.power) {
                return false;
            }
        }
        if (cardTenNum == 1) {
            return true;
        }
        return false;
    }

    /**
     * @Description:是否四炸,四炸：5张牌中有4张一样的牌，此时无需有牛。若庄家闲家都是四炸牌型，则比较4张一样的牌的大小。
     * @param cards
     * @return
     */
    public static boolean isSiZha(List<BrnnCard> cards) {
        List<BrnnCard> tmpCards = new ArrayList<>(cards);
        Collections.sort(tmpCards, (a, b) -> Integer.compare(b.power, a.power));

        for(int j = 4; j > 0; --j) {
            int sameCount = 0;
            int power = tmpCards.get(j).power;
            for (int i = 0; i < tmpCards.size(); ++i) {
                if (tmpCards.get(i) == BrnnCard.XIAO_WANG || tmpCards.get(i) == BrnnCard.DA_WANG || power == tmpCards.get(i).power)
                    sameCount++;
            }
            if (sameCount >= 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * @Description:只获取牛牛类型
     * @param cards
     * @param
     * @return
     */
    private static BrnnCardsType getNiuNiuType(List<BrnnCard> cards) {
        List<BrnnCard> destTipCards = new ArrayList<>();
        // 王的数量
        int kindNum = 0;
        // 牌数字的合计
        int totalNum = 0;

        for (BrnnCard card : cards) {
            totalNum += card.num;
            if (card == BrnnCard.DA_WANG || card == BrnnCard.XIAO_WANG) {
                kindNum += 1;
            }
        }

        if (kindNum == 0) { // 没有王,大小是固定的
            // 用于计算 提示牌(前3张是整数，后2张是点数)
            LinkedHashSet<BrnnCard> destTipCardsTemp = new LinkedHashSet<>();
            for (int i = 0; i < cards.size(); i++) {
                for (int j = i + 1; j < cards.size(); j++) {
                    for (int k = j + 1; k < cards.size(); k++) {
                        int numCount = cards.get(i).num + cards.get(j).num + cards.get(k).num;
                        if (numCount % 10 == 0) {
                            destTipCardsTemp.add(cards.get(i));
                            destTipCardsTemp.add(cards.get(j));
                            destTipCardsTemp.add(cards.get(k));
                            destTipCardsTemp.addAll(cards);

                            cards.clear();
                            cards.addAll(destTipCardsTemp);
                            int niu = (totalNum -  numCount) % 10;
                            if (niu == 0) {
                                return BrnnCardsType.geCardsType(10);
                            } else {
                                return BrnnCardsType.geCardsType(niu);
                            }

                        }
                    }
                }
            }
        } else if (kindNum == 1) {// 有一张王， 必是有牛  先从剩余四张中选出3张加起来为整，找不到再从剩余四张中选出两张加起来最大

            // 用于计算 提示牌(前3张是整数，后2张是点数)
            List<BrnnCard> destTipCardsTemp = new ArrayList<>();
            destTipCardsTemp.addAll(cards);
            destTipCardsTemp.remove(BrnnCard.DA_WANG);
            destTipCardsTemp.remove(BrnnCard.XIAO_WANG);

            /*
             * 先从剩余四张中选出3张加起来为整,是牛牛
             */
            for (int i = 0; i < destTipCardsTemp.size(); i++) {
                for (int j = i + 1; j < destTipCardsTemp.size(); j++) {
                    for (int k = j + 1; k < destTipCardsTemp.size(); k++) {
                        BrnnCard card1 = destTipCardsTemp.get(i);
                        BrnnCard card2 = destTipCardsTemp.get(j);
                        BrnnCard card3 = destTipCardsTemp.get(k);

                        int numCount = card1.num + card2.num + card3.num;
                        if (numCount % 10 == 0) {
                            destTipCards.add(card1);
                            destTipCards.add(card2);
                            destTipCards.add(card3);
                            for (BrnnCard card : cards) {
                                if (!destTipCards.contains(card)) {
                                    destTipCards.add(card);
                                }
                            }
                            cards.clear();
                            cards.addAll(destTipCards);
                            return BrnnCardsType.NIU_NIU;
                        }
                    }
                }
            }

            /*
             * 剩余四张中选出两张加起来最大
             */
            int maxNiu = 0;
            // 点数牌1
            BrnnCard pointCard1 = null;
            // 点数牌2
            BrnnCard pointCard2 = null;

            for (int i = 0; i < destTipCardsTemp.size(); i++) {
                for (int j = i + 1; j < destTipCardsTemp.size(); j++) {
                    int niu = (destTipCardsTemp.get(i).num + destTipCardsTemp.get(j).num) % 10;
                    if (niu == 0) {
                        niu = 10;
                    }
                    if (niu > maxNiu) {
                        pointCard1 = destTipCardsTemp.get(i);
                        pointCard2 = destTipCardsTemp.get(j);
                        maxNiu = niu;
                    }
                }
            }

            for (BrnnCard card : cards) {
                if (card != pointCard1 && card != pointCard2) {
                    destTipCards.add(card);
                }
            }

            destTipCards.add(pointCard1);
            destTipCards.add(pointCard2);

            cards.clear();
            cards.addAll(destTipCards);
            return BrnnCardsType.geCardsType(maxNiu);
            // 有两张王，必是牛牛
        } else if (kindNum == 2) {
            destTipCards.add(BrnnCard.DA_WANG);
            // 用于计算 提示牌(前3张是整数，后2张是点数)
            for (BrnnCard card : cards) {
                if (card != BrnnCard.DA_WANG && card != BrnnCard.XIAO_WANG) {
                    destTipCards.add(card);
                }
            }
            destTipCards.add(BrnnCard.XIAO_WANG);

            cards.clear();
            cards.addAll(destTipCards);
            return BrnnCardsType.NIU_NIU;
        }

        return BrnnCardsType.MEI_NIU;

    }


    /**
     * @Description:比较大小
     * @param myCards
     * @param myCardsType
     * @param otherCards
     * @param otherCardsType
     * @return			true:我大  false：庄家大
     */
    public static boolean compare(List<BrnnCard> myCards, BrnnCardsType myCardsType,
                                  List<BrnnCard> otherCards, BrnnCardsType otherCardsType) {

        if (myCardsType.ordinal() > otherCardsType.ordinal()) {
            return true;
        } else if (myCardsType.ordinal() < otherCardsType.ordinal()) {
            return false;
        } else {
            //四炸
            if(myCardsType == BrnnCardsType.SI_ZHA && otherCardsType == BrnnCardsType.SI_ZHA){
                BrnnCard myMaxCard = getSameCard(myCards);
                BrnnCard otherMaxCard = getSameCard(otherCards);
                return myMaxCard.power > otherMaxCard.power ? true : false;
            }

            // 同牌型只有一张王：有王的比没王的小
            // 同牌型有两张王：有王的比没王的大
            if (!myCards.contains(BrnnCard.DA_WANG) && !myCards.contains(BrnnCard.XIAO_WANG)){
                if(otherCards.contains(BrnnCard.DA_WANG) && otherCards.contains(BrnnCard.XIAO_WANG)){
                    return false;
                }else if(otherCards.contains(BrnnCard.DA_WANG) || otherCards.contains(BrnnCard.XIAO_WANG)){
                    return true;
                }
            }

            if (!otherCards.contains(BrnnCard.DA_WANG) && !otherCards.contains(BrnnCard.XIAO_WANG)){
                if(myCards.contains(BrnnCard.DA_WANG) && myCards.contains(BrnnCard.XIAO_WANG)){
                    return true;
                }else if(myCards.contains(BrnnCard.DA_WANG) || myCards.contains(BrnnCard.XIAO_WANG)){
                    return false;
                }

            }

            //五小
            if(myCardsType == BrnnCardsType.WU_XIAO && otherCardsType == BrnnCardsType.WU_XIAO){

                int mNum = 0;
                for (BrnnCard card : myCards){
                    mNum += card.num;
                }
                int oNum = 0;
                for(BrnnCard card : otherCards){
                    oNum += card.num;
                }
                if(mNum != oNum) {
                    return mNum < oNum ? true : false;
                }
                BrnnCard myMinCard;
                BrnnCard otherMinCard;
                if((myCards.contains(BrnnCard.DA_WANG) && otherCards.contains(BrnnCard.XIAO_WANG))
                        || (myCards.contains(BrnnCard.XIAO_WANG) && otherCards.contains(BrnnCard.DA_WANG))){
                    myMinCard = getMinCard(myCards, true);
                    otherMinCard = getMinCard(otherCards, true);
                }else {
                    myMinCard = getMinCard(myCards, false);
                    otherMinCard = getMinCard(otherCards, false);
                }


                if(myMinCard.power != otherMinCard.power){
                    return myMinCard.power < otherMinCard.power;
                }

                return myMinCard.type.ordinal() > otherMinCard.type.ordinal();

            }

            BrnnCard myMaxCard;
            BrnnCard otherMaxCard;

            if((myCards.contains(BrnnCard.DA_WANG) && otherCards.contains(BrnnCard.XIAO_WANG))
                    || (myCards.contains(BrnnCard.XIAO_WANG) && otherCards.contains(BrnnCard.DA_WANG))){
                myMaxCard = getMaxCard(myCards, true);
                otherMaxCard = getMaxCard(otherCards, true);
            }else {
                myMaxCard = getMaxCard(myCards, false);
                otherMaxCard = getMaxCard(otherCards, false);
            }


            if(myMaxCard.power != otherMaxCard.power){
                return myMaxCard.power > otherMaxCard.power;
            }

            return myMaxCard.type.ordinal() > otherMaxCard.type.ordinal();
        }
    }

    public static BrnnCard getSameCard(List<BrnnCard> cards){
        for(int i = 0; i < cards.size(); ++i){
            BrnnCard card = cards.get(i);
            int sameNum = 1;
            if(card != BrnnCard.XIAO_WANG && card != BrnnCard.DA_WANG){
                for(int j = i + 1; j < cards.size(); ++j){
                    if(card.power == cards.get(j).power){
                        ++sameNum;
                    }
                    if(sameNum >= 2){
                        return card;
                    }
                }
            }
        }

        return null;
    }
    /**
     * @Description:获取最大的牌
     * @param cards
     * @return
     */
    private static BrnnCard getMaxCard(List<BrnnCard> cards, boolean king) {
        BrnnCard maxCard = null;
        for (BrnnCard card : cards) {
            if(king && card.power >= BrnnCard.XIAO_WANG.power) {
                continue;
            }
            if (maxCard == null || card.power > maxCard.power
                    || (card.power == maxCard.power && card.type.ordinal() > maxCard.type.ordinal())) {
                maxCard = card;
            }
        }

        return maxCard;
    }

    private static BrnnCard getMinCard(List<BrnnCard> cards, boolean king){
        BrnnCard minCard = null;
        for (BrnnCard card : cards) {
            if(king && card.power >= BrnnCard.XIAO_WANG.power){
                continue;
            }
            if (minCard == null || card.power < minCard.power
                    || (card.power == minCard.power && card.type.ordinal() < minCard.type.ordinal())) {
                minCard = card;
            }
        }

        return minCard;
    }

    //获取结算数据 正负相对于玩家
    //public static String getSettleData(CardsAndType banker, List<CardsAndType> players, String roundType){
    //    List<String> result = new ArrayList<>();
    //
    //    for(int i = 0; i < players.size(); ++i){
    //        CardsAndType player = players.get(i);
    //        boolean bWin = compare(player.getCards(), player.getType(), banker.getCards(), banker.getType());
    //        //闲家赢 按 闲家的牌型算赔率  否则 按 庄家的牌型算赔率
    //        int r = 0;
    //        //BrnnRoundConfig config = InitBaseConfig.getRoundConfig(roundType);
    //        //test
    //        BrnnRoundConfig config = new BrnnRoundConfig();
    //        config.setMultipleEnum(Arrays.asList(1,1,1,1,1,1,1,2,2,3,4,4,4,4,4));
    //        r = bWin ? config.getMultipleEnum().get(player.getType().niu) : -config.getMultipleEnum().get(banker.getType().niu);
    //        result.add(r + "");
    //    }
    //
    //    return String.join(",", result);
    //}

//    public static Map<String, String> lotteryCards(String roundType, long[] bets){
//        List<BrnnCard> cards = Arrays.asList(BrnnCard.values());
//
//        //Collections.shuffle(cards);
//        shuffleCards(cards);
//
//        int index = RandomUtil.random(20);
//
//        //随机牌
//        List<BrnnCard> lottery = new ArrayList<>(cards.subList(index, index + 25));
//
//        //牌型
//        List<BrnnCardsType> types = new ArrayList<>();
//        for(int i = 0; i < lottery.size(); i+=5){
//            types.add(getCardsType(lottery.subList(i, i+5)));
//        }
//
//        //牌 牌型 组合
//        CardsAndType banker = new CardsAndType();
//        banker.setCards(lottery.subList(0, 5));
//        banker.setType(types.get(0));
//
//        List<CardsAndType> cardsAndTypes = new ArrayList<>();
//        for (int i = 1; i < 5; ++i){
//            CardsAndType player = new CardsAndType();
//            player.setCards(lottery.subList(i*5, i * 5 + 5));
//            player.setType(types.get(i));
//            cardsAndTypes.add(player);
//        }
//
//        //输赢结果
//        String winLose = getSettleData(banker, cardsAndTypes, roundType);
//
//
//        //发好牌后有了输赢结果 先判断庄家是否盈利 如果不盈利  随机抽一个比庄家大的区域 与庄家交换 再判断是否盈利 如果没有 继续随机 区域 并交换
//        if(false){
//            String[] arrWinLose = winLose.split(",");
//            long winAmount = checkProfit(arrWinLose, bets);
//
//            if(winAmount < 0){
//                //找出玩家赢得区域
//                List<Integer> tempWinLose = new ArrayList<>();
//                for(int i = 0; i < arrWinLose.length; ++i){
//                    if(Integer.valueOf(arrWinLose[i]) > 0){
//                        tempWinLose.add(i);
//                    }
//                }
//
//                //随机从这些区域抽取一个 与庄家交换
//                do{
//                    int areaIndex = RandomUtil.random(tempWinLose.size());
//                    //交换
//                    CardsAndType pCardsAndType = cardsAndTypes.get(tempWinLose.remove(areaIndex));
//
//                    for(int i = 0; i < 5; ++i){
//                        BrnnCard tempCard = banker.cards.get(i);
//                        banker.cards.set(i, pCardsAndType.cards.get(i));
//                        pCardsAndType.cards.set(i, tempCard);
//                    }
//                    BrnnCardsType tempType = banker.type;
//                    banker.type = pCardsAndType.type;
//                    pCardsAndType.type = tempType;
//
//                    //检查庄家是否盈利
//                    winLose = getSettleData(banker, cardsAndTypes, roundType);
//
//                    arrWinLose = winLose.split(",");
//
//                    winAmount = checkProfit(arrWinLose, bets);
//
//                }while (winAmount < 0 && !tempWinLose.isEmpty());
//            }
//        }
//
//        Map<String, String> resultMap = new HashMap<>();
//
//        StringBuffer bufferPokers = new StringBuffer();
//        for(int i = 0; i < lottery.size(); ++i){
//            String s = i < lottery.size() - 1 ? lottery.get(i).index + "," : lottery.get(i).index + "";
//            bufferPokers.append(s);
//        }
//
//        StringBuffer bufferTypes = new StringBuffer();
//        bufferTypes.append(banker.type.niu + ",");
//        for(int i = 0; i < cardsAndTypes.size(); ++i){
//            String s = i < cardsAndTypes.size() - 1 ? cardsAndTypes.get(i).type.niu + "," : cardsAndTypes.get(i).type.niu + "";
//            bufferTypes.append(s);
//        }
//
//        resultMap.put(POKERS, bufferPokers.toString());
//        resultMap.put(WIN_LOSE, winLose);
//        resultMap.put(POKER_TYPE, bufferTypes.toString());
//
//        return resultMap;
//    }
//
//    public static long checkProfit(String[] winLose, long[] bets){
//        long winAmount = 0;
//
//        for (int i = 0; i < winLose.length; ++i) {
//            winAmount += -Integer.valueOf(winLose[i]) * bets[i];
//        }
//
//        return winAmount;
//    }
//    public static void main(String[] args){
////        List<BrnnCard> c1 = new ArrayList<>();
////        c1.add(BrnnCard.MEI_HUA_Q);c1.add(BrnnCard.FANG_KUAI_K);c1.add(BrnnCard.HEI_TAO_Q);c1.add(BrnnCard.DA_WANG);c1.add(BrnnCard.XIAO_WANG);
////        BrnnCardsType t1 = getCardsType(c1);
////        List<BrnnCard> c2 = new ArrayList<>();
////        c2.add(BrnnCard.HONG_TAO_WU);c2.add(BrnnCard.HEI_TAO_WU);c2.add(BrnnCard.MEI_HUA_ER);c2.add(BrnnCard.XIAO_WANG);c2.add(BrnnCard.DA_WANG);
////        BrnnCardsType t2 = getCardsType(c2);
////
////        boolean b = compare(c1, t1, c2, t2);
//
////        lotteryCards("101", new long[4]);
//
////        long[] bets = new long[4];
////        bets[0] = 1000;bets[1] = 50;bets[2] = 200;bets[3] = 10;
////        for(int i = 0; i < 1000; ++i) {
////            lotteryCardsByKill("101", bets, true);
////        }
//    }
//
//    public static Map<String, String> lotteryCardsByKill(String roundType, long[] bets, boolean isRealBanker){
//
//        List<BrnnCard> cards;
//        List<BrnnCard> lottery;
//        List<BrnnCardsType> types;
//        List<CardsAndType> cardsAndTypes;
//        List<Integer> tempWinLose;
//        long winAmount ;
//        CardsAndType banker;
//        String winLose;
//
//        //随机从这些区域抽取一个 与庄家交换
//        int count = 1000;
//        do{
//            cards = Arrays.asList(BrnnCard.values());
//            shuffleCards(cards);
//
//            int index = RandomUtil.random(20);
//
//            //随机牌
//            lottery = new ArrayList<>(cards.subList(index, index + 25));
//
//            //牌型
//            types = new ArrayList<>();
//            for(int i = 0; i < lottery.size(); i+=5){
//                types.add(getCardsType(lottery.subList(i, i+5)));
//            }
//
//            //牌 牌型 组合
//            banker = new CardsAndType();
//            banker.setCards(lottery.subList(0, 5));
//            banker.setType(types.get(0));
//
//            cardsAndTypes = new ArrayList<>();
//            for (int i = 1; i < 5; ++i){
//                CardsAndType player = new CardsAndType();
//                player.setCards(lottery.subList(i*5, i * 5 + 5));
//                player.setType(types.get(i));
//                cardsAndTypes.add(player);
//            }
//
//            //输赢结果
//            winLose = getSettleData(banker, cardsAndTypes, roundType);
//
//            //发好牌后有了输赢结果 先判断庄家是否盈利 如果不盈利  随机抽一个比庄家大的区域 与庄家交换 再判断是否盈利 如果没有 继续随机 区域 并交换
//            String[] arrWinLose = winLose.split(",");
//            winAmount = checkProfit(arrWinLose, bets);
//
//            if (winAmountJudge(isRealBanker, winAmount)){
//                //真人庄家 找 机器人输的区域 否则 找玩家赢得区域
//                tempWinLose = new ArrayList<>();
//                for(int i = 0; i < arrWinLose.length; ++i){
//                    if(isRealBanker ? Integer.parseInt(arrWinLose[i]) < 0 : Integer.parseInt(arrWinLose[i]) > 0){
//                        tempWinLose.add(i);
//                    }
//                }
//
//                do {
//                    if(tempWinLose.isEmpty()){
//                        break;
//                    }
//                    int areaIndex = RandomUtil.random(tempWinLose.size());
//                    //交换
//                    CardsAndType pCardsAndType = cardsAndTypes.get(tempWinLose.remove(areaIndex));
//
//                    for(int i = 0; i < 5; ++i){
//                        BrnnCard tempCard = banker.cards.get(i);
//                        banker.cards.set(i, pCardsAndType.cards.get(i));
//                        pCardsAndType.cards.set(i, tempCard);
//                    }
//                    BrnnCardsType tempType = banker.type;
//                    banker.type = pCardsAndType.type;
//                    pCardsAndType.type = tempType;
//
//                    //检查庄家是否盈利
//                    winLose = getSettleData(banker, cardsAndTypes, roundType);
//
//                    arrWinLose = winLose.split(",");
//
//                    winAmount = checkProfit(arrWinLose, bets);
//                } while (winAmountJudge(isRealBanker, winAmount) && !tempWinLose.isEmpty());
//            }
//            count--;
//            if(count < 0){
//                break;
//            }
//        } while (winAmountJudge(isRealBanker, winAmount));
//
//
//
//        Map<String, String> resultMap = new HashMap<>();
//
//        StringBuffer bufferPokers = new StringBuffer();
//        for(int i = 0; i < lottery.size(); ++i){
//            String s = i < lottery.size() - 1 ? lottery.get(i).index + "," : lottery.get(i).index + "";
//            bufferPokers.append(s);
//        }
//
//
//        StringBuffer bufferTypes = new StringBuffer();
//        bufferTypes.append(banker.type.niu + ",");
//        for(int i = 0; i < cardsAndTypes.size(); ++i){
//            String s = i < cardsAndTypes.size() - 1 ? cardsAndTypes.get(i).type.niu + "," : cardsAndTypes.get(i).type.niu + "";
//            bufferTypes.append(s);
//        }
//
//        resultMap.put(POKERS, bufferPokers.toString());
//        resultMap.put(WIN_LOSE, winLose);
//        resultMap.put(POKER_TYPE, bufferTypes.toString());
//
//        return resultMap;
//    }
//
//    public static void shuffleCards(List<BrnnCard> pokers){
//        int index;
//        BrnnCard tmp;
//        for(int i = 0; i < pokers.size(); ++i){
//            index = RandomUtils.randIntByMax(i+1);
//            tmp = pokers.get(i);
//            pokers.set(i, pokers.get(index));
//            pokers.set(index, tmp);
//        }
//    }

    public static boolean winAmountJudge(boolean isRealBanker, long winAmount){
        return isRealBanker ? winAmount > 0 : winAmount < 0;
    }
}
