package com.dafagame.dafaGameUtils.cardLogic;

import com.dafagame.protocol.zjh.Zjh;
import com.dafagame.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.fileUtils.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControlLogic {

    private static final Logger logger = LoggerFactory.getLogger(ControlLogic.class);

    public static boolean control(List<ZjhPlayer> players, boolean isOpen){
        int realCount = 0;
        int robotCount = 0;
        for(ZjhPlayer player : players){
            if(player != null && player.status == Zjh.PlayerStatus.Play){
                if(player.isRobot()) {
                    robotCount++;
                }else {
                    realCount++;
                }
            }
        }

        if(isOpen && realCount > 0 && robotCount > 0) {

            boolean needGoodCards = RandomUtil.random(100) < 90;

            List<ZjhCards> cards = getCards(robotCount, realCount, needGoodCards);

            for(ZjhPlayer player : players){
                if(player != null && player.status == Zjh.PlayerStatus.Play){
                    player.cards = cards.remove(0);
                }
            }

            int r = RandomUtil.random(0, 4);
            for(int i = 0; i < 5; ++i){
                ZjhPlayer player = players.get(r);
                if(player != null && player.isRobot() && player.status == Zjh.PlayerStatus.Play){
                    break;
                }
                r = (r + 1) % 5;
            }

            ZjhPlayer cRobot = players.get(r);

            for (ZjhPlayer player : players) {
                if (player != null && player.status == Zjh.PlayerStatus.Play
                        && player.getUid() != cRobot.getUid()) {
                    boolean win = ZjhCardsTypeComparator.compare(cRobot.cards, player.cards);
                    if (!win) {
                        ZjhCards temp = cRobot.cards;
                        cRobot.cards = player.cards;
                        player.cards = temp;
                    }
                }
            }

            String roundType = cRobot.roundType;
            //String roomNumber = cRobot.curRoom.roomNumber;
            //String inning = cRobot.record.getInning();

            if(needGoodCards){
                List<ZjhPlayer> badCardsPlayer = players.stream().filter((p)->{
                    return p != null && !p.isRobot() && p.cards.type.power < ZjhCardsType.DUI_ZI.power;
                }).collect(Collectors.toList());

                if(!badCardsPlayer.isEmpty()) {
                    List<ZjhPlayer> goodCardsRobot = players.stream().filter((p) -> {
                        return p != null && p.isRobot()
                                && p.seatId != cRobot.seatId && p.cards.type.power >= ZjhCardsType.DUI_ZI.power;
                    }).collect(Collectors.toList());

                    for(ZjhPlayer player : badCardsPlayer){
                        if(!goodCardsRobot.isEmpty()){
                            ZjhPlayer goodRobot = goodCardsRobot.remove(0);
                            ZjhCards temp = goodRobot.cards;
                            goodRobot.cards = player.cards;
                            player.cards = temp;
                        }
                    }
                }
                //logger.info(LogUtil.info("真人发了好牌",  roundType + "," + roomNumber + "," + inning));
            }

            //logger.info(LogUtil.info("杀率开启",  roundType + "," + roomNumber + "," + inning + "最大牌机器人座位号:" + cRobot.seatId + "发牌" + cRobot.cards.type + "|" + cRobot.cards.getCards()));
            return true;
        }else {
            List<ZjhCards> cards = getCards(robotCount, realCount, false);
            for(ZjhPlayer player : players){
                if(player != null && player.status == Zjh.PlayerStatus.Play){
                    player.cards = cards.remove(0);
                }
            }

            StringBuilder sb = new StringBuilder();
            //sb.append(needGoodCards + ";");
            for (ZjhPlayer player : players) {
                sb.append(player.cards + ";" + player.getType() + ";");
            }
            //System.out.println("--------------------");
            players.sort((a, b) -> {
                if (!b.equals(a)) {
                    return ZjhCardsTypeComparator.compare(a.cards, b.cards) ? 1 : -1;
                }
                return 0;
            });
            if (players.get(players.size() - 1).getType() == 0) {
                sb.append("win");
            } else {
                sb.append("lose");
            }
            System.out.print(sb.append("\n"));
            FileUtil.writeFile("/Users/duke/Documents/github/dafa/dafagame-client-bf/src/main/resources/a2.txt", sb.toString(), true);
            return false;
        }


    }

    public static List<ZjhCards> getCards(int robotCount, int realCount, boolean isKill){
        List<ZjhCards> cards;
        try {
            cards = ZjhLogic.newLottery(robotCount, realCount, isKill);
            Collections.shuffle(cards);
        }catch (Exception e){
            //logger.error(LogUtil.error("newLottery error", e));
            List<ZjhCard> list = new ArrayList<>(Arrays.asList(ZjhCard.values()));

            Collections.shuffle(list);

            cards = new ArrayList<>();

            for(int i = 0; i < robotCount + realCount; ++i){
                ZjhCard c0 = list.remove(0);
                ZjhCard c1 = list.remove(1);
                ZjhCard c2 = list.remove(2);
                List<ZjhCard> zl = new ArrayList<>(Arrays.asList(c0, c1, c2));
                cards.add(new ZjhCards(zl));
            }
        }
        return cards;
    }

    public static void main(String[] args) {
        for (int ii = 0; ii < 100000; ii++) {
            List<ZjhPlayer> players = new ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                ZjhPlayer p = new ZjhPlayer();
                p.setUid(i + 100);
                p.status = Zjh.PlayerStatus.Play;
                p.seatId = i;
                players.add(p);
            }
            //玩家类型 -2测试站正式账号,-1每个站的测试账号 0普通玩家 1-4机器人
            players.get(0).setType(0);
            players.get(0).setTenantCode("dafa");

            players.get(1).setType(1);
            players.get(1).setTenantCode("robot");

            players.get(2).setType(1);
            players.get(2).setTenantCode("robot");

            players.get(3).setType(1);
            players.get(3).setTenantCode("robot");

            players.get(4).setType(1);
            players.get(4).setTenantCode("robot");

            control(players, false);
        }
    }

//    public static void main(String[] args){
//        List<ZjhPlayer> players = new ArrayList<>();
//        for(int i = 0; i < 5; ++i){
//            ZjhPlayer p = new ZjhPlayer(i);
//            p.status = Zjh.PlayerStatus.Play;
//            p.seatId = i;
//            players.add(p);
//        }
//        players.get(0).setType(0);
//        players.get(1).setType(1);
//        players.get(2).setType(1);
//        players.get(3).setType(0);
//        players.get(4).setType(1);
//
//        control(players);
//
//    }

}
