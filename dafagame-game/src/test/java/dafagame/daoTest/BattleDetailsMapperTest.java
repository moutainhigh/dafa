package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.utils.SqlSessionFactoryUtils;
import pers.dafagame.enums.Card;
import pers.dafagame.mapper.BattleDetailsMapper;

import java.util.*;

/**
 * 斗地主 牌库
 */
public class BattleDetailsMapperTest {
    static SqlSession proGamesqlSession = SqlSessionFactoryUtils.openSqlSession("proGame");
    static SqlSession devSqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    static BattleDetailsMapper proGameBattleDetailsMapper = proGamesqlSession.getMapper(BattleDetailsMapper.class);
    static BattleDetailsMapper devBattleDetailsMapper = devSqlSession.getMapper(BattleDetailsMapper.class);

    public static void main(String[] args) {
        //poker();
        exportProToDev();
    }

    //牌库导入到测试环境
    public static void exportProToDev() {
        String maxId = "0";
        for (int j = 0; j < 10000; j++) {
            System.out.println("maxId :" + maxId);
            List<Map> list = proGameBattleDetailsMapper.getBattleDetailList(maxId);
            System.out.println("查询数据 " + list.size());
            if (list.size() == 0) {
                break;
            }
            if (list.size() < 10000) {
                int result = devBattleDetailsMapper.addBattleDetailList(list);
                System.out.println(" 写入尾数- " + result);
                list.clear();
                break;
            } else {
                int result = devBattleDetailsMapper.addBattleDetailList(list);
                System.out.println(" 写入整数 - " + result);
            }
            maxId = list.get(list.size() - 1).get("id").toString();
            list.clear();
        }
    }


    /**
     * 分析牌库的类型
     */
    public static void poker() {
        String maxId = "0";
        List<Map> mapList = proGameBattleDetailsMapper.getBattleDetailList(maxId);
        List<Card> list = new ArrayList<>();
        int count = 0;
        for (Map map : mapList) {
            String[] hands = map.get("hands").toString().split(",");
            //if (Integer.parseInt(hands[17]) == 57 || Integer.parseInt(hands[18]) == 57 || Integer.parseInt(hands[19]) == 57) {
            //    System.out.println(map.get("hands").toString());
            //}

            if (Integer.parseInt(hands[18]) == 56 && Integer.parseInt(hands[19]) == 57) {
                System.out.println(count++ + "-" + map.get("hands").toString());
                System.out.println(map.get("inning"));
            }
            //System.out.println(Integer.parseInt(hands[18]) == 56 ? "大" : Integer.parseInt(hands[18]) == 57 ? "小" : "1");
            //System.out.println(Card.getCard(Integer.parseInt(hands[17])).shown + "," + Card.getCard(Integer.parseInt(hands[18])).shown + "," + Card.getCard(Integer.parseInt(hands[19])).shown);

            //根据底牌找到开启杀率时从牌库取得牌
            //if (Card.getCard(Integer.parseInt(hands[17])).shown == 'S' && Card.getCard(Integer.parseInt(hands[18])).shown == 'K' && Card.getCard(Integer.parseInt(hands[19])).shown == 'W') {
            //    System.out.println(map.get("hands").toString());
            //    System.out.println(map.get("inning").toString());
            //
            //    for (String s : hands) {
            //        list.add(Card.getCard(Integer.parseInt(s)));
            //    }
            //}
        }

        //list.sort((o1, o2) -> {
        //    if (o1.num > o2.num)
        //        return -1;
        //    else if (o1.num < o2.num)
        //        return 1;
        //    else return Integer.compare(o2.extNum, o1.extNum);
        //});
        //
        //for (Card card : list) {
        //    System.out.println(card.getNum() + "\t" + card.extNum+ "\t" +card.shown);
        //}
    }


}
